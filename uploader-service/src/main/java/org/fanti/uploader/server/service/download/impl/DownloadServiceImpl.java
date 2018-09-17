package org.fanti.uploader.server.service.download.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.list.AbstractLinkedList;
import org.fanti.uploader.server.db.DBFile;
import org.fanti.uploader.server.db.UserDir;
import org.fanti.uploader.server.db.UserFile;
import org.fanti.uploader.server.mapper.DBFileMapper;
import org.fanti.uploader.server.mapper.UserDirMapper;
import org.fanti.uploader.server.mapper.UserFileMapper;
import org.fanti.uploader.server.service.dir.UserDirService;
import org.fanti.uploader.server.service.download.DownloadService;
import org.fanti.uploader.server.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/30
 */

@Service
public class DownloadServiceImpl implements DownloadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadServiceImpl.class);

    @Autowired
    UserDirMapper userDirMapper;

    @Autowired
    UserFileMapper userFileMapper;

    @Autowired
    DBFileMapper dbFileMapper;

    @Autowired
    UserDirService userDirService;

    @Override
    public void download() {

    }

    @Override
    public List<DBFile> listFiles(String currentDir) {
        int userId = UserUtil.getCurrentUserId();
        UserDir userDir = userDirService.getUserDirByUserIdAndFullPath(userId, currentDir);
        LOGGER.info("userDir:{}", JSON.toJSONString(userDir));
        if (userDir == null) {
            return null;
        }

        List<UserFile> userFileList = userFileMapper.getUserFileList(userDir.getId());

        if (userFileList == null || userFileList.size() == 0) {
            return null;
        }

        LOGGER.info("userFileList:{}", JSON.toJSONString(userFileList));
        List<Integer> fileIdList = new ArrayList<>();

        for (UserFile userFile : userFileList) {
            fileIdList.add(userFile.getFileId());
        }
        LOGGER.info("fileIdList:{}", JSON.toJSONString(fileIdList));

        List<DBFile> dbFileList = dbFileMapper.getDBFileListByFileIdList(fileIdList);

        LOGGER.info("dbFileList:{}", JSON.toJSONString(dbFileList));
        return dbFileList;
    }
}
