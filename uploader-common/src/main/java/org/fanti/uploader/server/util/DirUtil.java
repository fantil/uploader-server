package org.fanti.uploader.server.util;

import com.alibaba.fastjson.JSON;
import org.fanti.uploader.server.bean.FileInfo;
import org.fanti.uploader.server.db.DBFile;
import org.fanti.uploader.server.db.UserDir;
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

public class DirUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirUtil.class);

    /**
     * 加载文件的完整相对路径
     * @param fileInfo 文件信息
     * @return 完整的相对路径
     */
    public static FileInfo initFullRelativePath(FileInfo fileInfo) {
        if (fileInfo == null) {
            return null;
        }
        if (StringUtil.isNullString(fileInfo.getCurrentDir())) {
            fileInfo.setCurrentDir(File.separator);
        }
        if (StringUtil.isNullString(fileInfo.getRelativePath())) {
            fileInfo.setRelativePath(File.separator);
        }

        String separator = fileInfo.getRelativePath().substring(0, 1);
        LOGGER.info("RelativePath:{}", fileInfo.getRelativePath());
        if (!File.separator.equals(separator)) {
            fileInfo.setRelativePath(fileInfo.getRelativePath().replace(separator, File.separator));
            LOGGER.info("RelativePath:{}", fileInfo.getRelativePath());
        }

        separator = fileInfo.getCurrentDir().substring(0, 1);
        if (!File.separator.equals(separator)) {
            fileInfo.setCurrentDir(fileInfo.getCurrentDir().replace(separator, File.separator));
            LOGGER.info("RelativePath:{}", fileInfo.getRelativePath());
        }
        LOGGER.info("fileInfo:{}", JSON.toJSONString(fileInfo));
        String fullRelativePath = "";

        if (File.separator.equals(fileInfo.getCurrentDir()) && File.separator.equals(fileInfo.getRelativePath())) {
            fullRelativePath = File.separator;
        }

        if (File.separator.equals(fileInfo.getCurrentDir()) && !File.separator.equals(fileInfo.getRelativePath())) {
            fullRelativePath = fileInfo.getRelativePath();
        }

        if (!File.separator.equals(fileInfo.getCurrentDir()) && File.separator.equals(fileInfo.getRelativePath())) {
            fullRelativePath = fileInfo.getCurrentDir();
        }

        if (!File.separator.equals(fileInfo.getCurrentDir()) && !File.separator.equals(fileInfo.getRelativePath())) {
            fullRelativePath = fileInfo.getCurrentDir() + File.separator + fileInfo.getRelativePath();
        }

        LOGGER.info("fullRelativePath:{}", fullRelativePath);
        fileInfo.setFullRelativePath(fullRelativePath);

        return fileInfo;
    }

    public static UserDir initUserDirList(List<UserDir> userDirList, String fullRelativePath) {
        if (StringUtil.isNullString(fullRelativePath)) {
            return null;
        }

        List<String> pathList = new ArrayList<>();
        pathList.add(File.separator);
        LOGGER.info("fullRelativePath:{}", fullRelativePath);
        LOGGER.info("File.separator:{}", File.separator);
        if (!File.separator.equals(fullRelativePath)) {
            String[] dirArray = StringUtil.splitByFileSeparator(fullRelativePath);
            String parentDir = File.separator;
            for (String dirName : dirArray) {
                if (!StringUtil.isNullString(dirName) && !File.separator.equals(dirName)) {
                    String currentPath = "";
                    if (File.separator.equals(parentDir)) {
                        currentPath = parentDir + dirName;
                    } else {
                        currentPath = parentDir + File.separator + dirName;
                    }
                    pathList.add(currentPath);
                    parentDir = currentPath;
                }
            }
        }

        String nonExistsPath = "";
        int parentId = -1;
        if (userDirList == null || userDirList.size() == 0) {
            nonExistsPath = File.separator;
        } else {
            for (String path : pathList) {
                boolean flag = false;
                for (UserDir dir : userDirList) {
                    if (dir.getFullPath().equals(path)) {
                        flag = true;
                        parentId = dir.getId();
                    }
                }
                if (!flag) {
                    nonExistsPath = path;
                }
            }
        }


        UserDir dir = new UserDir();

        dir.setParentId(parentId);
        dir.setFullPath(nonExistsPath);
        dir.setDirName(initDirName(nonExistsPath));
        dir.setCreateTime(new Timestamp(System.currentTimeMillis()));
        dir.setModifyTime(new Timestamp(System.currentTimeMillis()));

        return dir;
    }

    /**
     * 根据完整路径得到最后一层目录名
     * @param fullPath 目录的完整路径
     * @return 目录名,前后无File.separator
     */
    public static String initDirName(String fullPath) {
        String dirName = fullPath.substring(fullPath.lastIndexOf(File.separator));
        return dirName;
    }



    public static UserDir nextUserDir(UserDir userDir, String fullPath, int parentId) {
        String currentPath = userDir.getFullPath();
        LOGGER.info("currentPath:{}, fullPath:{}", currentPath, fullPath);
        String[] dirNameArray = StringUtil.splitByFileSeparator(fullPath.substring(currentPath.length()));

        String dirName = "";
        if (dirNameArray.length == 1) {
            dirName = dirNameArray[0];
        } else {
            dirName = dirNameArray[1];
        }
        userDir.setDirName(dirName);
        userDir.setParentId(parentId);
        if (File.separator.equals(currentPath)) {
            userDir.setFullPath(currentPath + dirName);
        } else {
            userDir.setFullPath(currentPath + File.separator + dirName);
        }
        userDir.setCreateTime(new Timestamp(System.currentTimeMillis()));
        userDir.setModifyTime(new Timestamp(System.currentTimeMillis()));

        return userDir;
    }
}
