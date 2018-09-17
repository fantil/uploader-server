package org.fanti.uploader.server.service.dir.impl;

import com.alibaba.fastjson.JSON;
import org.fanti.uploader.server.bean.FileInfo;
import org.fanti.uploader.server.db.User;
import org.fanti.uploader.server.db.UserDir;
import org.fanti.uploader.server.mapper.DBFileMapper;
import org.fanti.uploader.server.mapper.UserDirMapper;
import org.fanti.uploader.server.mapper.UserFileMapper;
import org.fanti.uploader.server.mapper.base.BaseMapperService;
import org.fanti.uploader.server.service.dir.UserDirService;
import org.fanti.uploader.server.util.DirUtil;
import org.fanti.uploader.server.util.StringUtil;
import org.fanti.uploader.server.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/25
 */

@Service
public class UserDirServiceImpl extends BaseMapperService<UserDir> implements UserDirService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDirServiceImpl.class);

    @Autowired
    DBFileMapper dbFileMapper;

    @Autowired
    UserDirMapper userDirMapper;

    @Autowired
    UserFileMapper userFileMapper;

    @Override
    public UserDir initUserDir(FileInfo fileInfo) {
        User user = UserUtil.getCurrentUser();

        DirUtil.initFullRelativePath(fileInfo);
        if (StringUtil.isNullString(fileInfo.getFullRelativePath())) {
            LOGGER.error("文件完整相对路径为空！");
            return null;
        }

        List<UserDir> userDirList = userDirMapper.getUserDirListWithFullPath(user.getId(), fileInfo.getFullRelativePath());
        LOGGER.info("userDirList:{}", JSON.toJSONString(userDirList));
        if (userDirList == null || userDirList.size() == 0) {
            userDirList = userDirMapper.getUserDirList(user.getId());
            UserDir userDir = DirUtil.initUserDirList(userDirList, fileInfo.getFullRelativePath());

            LOGGER.info("userDir:{}", JSON.toJSONString(userDir));
            userDir = this.createUserDir(userDir, fileInfo.getFullRelativePath());
            return userDir;
        }

        LOGGER.info("userDirList:{}", JSON.toJSONString(userDirList));
        return userDirList.get(0);
    }

    @Override
    public UserDir createUserDir(UserDir userDir, String fullPath) {
        if (userDir == null) {
            return null;
        }

        User user = UserUtil.getCurrentUser();
        userDir.setUserId(user.getId());

        if (userDir.getFullPath().equals(fullPath)) {
            userDirMapper.insert(userDir);

            return this.getUserDirByUserIdAndFullPath(user.getId(), userDir.getFullPath());
        }

        userDirMapper.insert(userDir);
        UserDir dir = getUserDirByUserIdAndFullPath(user.getId(), userDir.getFullPath());
        if (dir == null) {
            LOGGER.error("userDir is null");
            return null;
        }
        int parentDirId = dir.getId();
        while (!fullPath.equals(userDir.getFullPath())) {
            userDir = DirUtil.nextUserDir(userDir, fullPath, parentDirId);
            LOGGER.info("userDir:{}", JSON.toJSONString(userDir, true));
            userDirMapper.insert(userDir);

            dir = getUserDirByUserIdAndFullPath(user.getId(), userDir.getFullPath());
            if (dir == null) {
                LOGGER.error("userDir is null");
                return null;
            }
            parentDirId = dir.getId();
        }

        userDir.setId(parentDirId);
        LOGGER.info("userDir:{}", JSON.toJSONString(userDir));
        return userDir;
    }

    @Override
    public UserDir getUserDirByUserIdAndFullPath(int userId, String fullPath) {
        List<UserDir> userDirList = userDirMapper.getUserDirListWithFullPath(userId, fullPath);

        if (userDirList.size() == 0) {
            LOGGER.error("can't find userDir by userId:{}, fullPath:{}", userId, fullPath);
            return null;
        }

        if (userDirList.size() > 1) {
            LOGGER.info("userDirList:{}", JSON.toJSONString(userDirList));
            LOGGER.info("userDirList size > 1, please check it.");
        }
        return userDirList.get(0);
    }
}
