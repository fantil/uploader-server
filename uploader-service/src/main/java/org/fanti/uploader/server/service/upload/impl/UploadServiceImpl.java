package org.fanti.uploader.server.service.upload.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.fileupload.FileItem;
import org.fanti.uploader.server.bean.FileInfo;
import org.fanti.uploader.server.db.DBFile;
import org.fanti.uploader.server.db.User;
import org.fanti.uploader.server.db.UserDir;
import org.fanti.uploader.server.db.UserFile;
import org.fanti.uploader.server.mapper.DBFileMapper;
import org.fanti.uploader.server.mapper.UserDirMapper;
import org.fanti.uploader.server.mapper.UserFileMapper;
import org.fanti.uploader.server.service.dir.UserDirService;
import org.fanti.uploader.server.service.upload.UploadService;
import org.fanti.uploader.server.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/25
 */

@Service
public class UploadServiceImpl implements UploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Autowired
    DBFileMapper dbFileMapper;

    @Autowired
    UserDirMapper userDirMapper;

    @Autowired
    UserFileMapper userFileMapper;

    @Autowired
    UserDirService userDirService;

    @Value("${upload.folder}")
    private String uploadFolder;

    @Value("${tmp.upload.folder}")
    private String tmpUploadFolder;

    @Override
    public void upload(List<FileItem> list) {
        FileInfo fileInfo = UploadUtil.initFileInfo(list);

        if (fileInfo.getTotalChunks() == 1) {
            UploadUtil.receiveSingleFile(fileInfo, uploadFolder);
            this.saveFileInfo(fileInfo);
        } else {
            UploadUtil.receiveChunkFile(fileInfo, tmpUploadFolder);
        }
    }

    @Override
    public void chunkMerge(FileInfo fileInfo) {
        UploadUtil.mergeChunks(fileInfo, uploadFolder, tmpUploadFolder);

        this.saveFileInfo(fileInfo);
    }

    /**
     * 检查文件分片数目
     * @param fileInfo 文件信息
     * @return 分片数目是否为1
     */
    @Override
    public boolean chunkCheck(FileInfo fileInfo) {
        String targetDir = "";
        String filename = "";
        long fileSize;

        if(fileInfo.getTotalChunks() == 1) {
            //TODO
            targetDir = uploadFolder;
            filename = fileInfo.getFilename();
            fileSize = fileInfo.getTotalSize();
        } else {
            targetDir = tmpUploadFolder + File.separator + fileInfo.getIdentifier();
            filename = fileInfo.getChunkNumber() + "";
            fileSize = fileInfo.getCurrentChunkSize();
        }

        String filePath = targetDir + File.separator + filename;
        LOGGER.info("filePath:{}", filePath);
        File file = new File(filePath);

        if (file.isFile() && file.length() == fileSize) {
            return true;
        }

        return false;
    }

    private void saveFileInfo(FileInfo fileInfo) {
        if (fileInfo == null || fileInfo.getFile() == null) {
            LOGGER.error("fileInfo or fileInfo.getFile is null");
            return;
        }
        LOGGER.info("fileInfo:{}", JSON.toJSONString(fileInfo));
        User user = UserUtil.getCurrentUser();

        DBFile dbFile = FileUtil.initDBFile(fileInfo);
        int fileId = dbFileMapper.add(dbFile);
        LOGGER.info("fileId:{}", JSON.toJSONString(fileId));
//        List<DBFile> dbFileList = dbFileMapper.getDBFileList(dbFile.getMd5());
//
//        if (dbFileList.size() == 0) {
//            LOGGER.error("insert userDir failed");
//            return ;
//        }
//        LOGGER.info("dbFileList:{}", JSON.toJSONString(dbFileList));
//        int fileId = dbFileList.get(0).getId();

        int dirId = userDirService.initUserDir(fileInfo);

        UserFile userFile = UserFileUtil.initUserFile(user.getId(), fileId, dirId);
        LOGGER.info("userFile:{}", JSON.toJSONString(userFile));
        userFileMapper.add(userFile);
    }
}
