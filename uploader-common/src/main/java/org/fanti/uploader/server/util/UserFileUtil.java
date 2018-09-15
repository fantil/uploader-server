package org.fanti.uploader.server.util;

import com.alibaba.fastjson.JSON;
import org.fanti.uploader.server.bean.FileInfo;
import org.fanti.uploader.server.db.UserDir;
import org.fanti.uploader.server.db.UserFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/26
 */

public class UserFileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFileUtil.class);

    public static UserFile initUserFile(int userId, int fileId, int dirId) {
        UserFile userFile = new UserFile();

        userFile.setDirId(dirId);
        userFile.setUserId(userId);
        userFile.setFileId(fileId);
        userFile.setCreateTime(new Timestamp(System.currentTimeMillis()));
        userFile.setModifyTime(new Timestamp(System.currentTimeMillis()));
        return userFile;
    }


}
