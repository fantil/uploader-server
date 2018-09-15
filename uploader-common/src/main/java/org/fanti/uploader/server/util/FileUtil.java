package org.fanti.uploader.server.util;

import com.alibaba.fastjson.JSON;
import org.fanti.uploader.server.bean.FileInfo;
import org.fanti.uploader.server.db.DBFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Timestamp;


/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/26
 */

public class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 加载文件最终存放文件夹
     * @param fileInfo 文件信息
     * @param targetDir 基础目标文件夹
     * @return 文件最终存放文件夹
     */
    public static String initRealPath(FileInfo fileInfo, String targetDir) {
        if (fileInfo == null || StringUtil.isNullString(targetDir)) {
            return "";
        }

        if (StringUtil.isNullString(fileInfo.getIdentifier())) {
            LOGGER.error("文件md5为空,请检查文件信息: fileInfo:{}", JSON.toJSONString(fileInfo));
            return "";
        }

        String parentDir = fileInfo.getIdentifier().substring(0, 2);

        String realPath = targetDir + File.separator + parentDir + File.separator + fileInfo.getIdentifier();
        File dir = new File(realPath);
        dir.mkdirs();

        LOGGER.info("realPath:{}", realPath);
        //FIXME
        return realPath + File.separator + fileInfo.getFilename();
    }

    /**
     * 加载数据库文件信息
     * @param fileInfo 文件信息
     * @return 数据库文件信息
     */
    public static DBFile initDBFile(FileInfo fileInfo) {
        if (fileInfo == null) {
            return null;
        }
        String[] info = fileInfo.getFilename().split("\\.");

        String ext = info[info.length - 1];

        DBFile dbFile = new DBFile();
        dbFile.setName(fileInfo.getFilename());
        dbFile.setExt(ext);
        dbFile.setLength(fileInfo.getTotalSize());
        dbFile.setMd5(fileInfo.getIdentifier());
        dbFile.setRealPath(fileInfo.getRealPath());
        dbFile.setFirstUploadUserId(UserUtil.getCurrentUser().getId());
        dbFile.setFirstUploadTime(new Timestamp(System.currentTimeMillis()));

        return dbFile;
    }
}
