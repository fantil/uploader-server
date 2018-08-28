package org.fanti.uploader.server.service.upload.impl;

import org.apache.commons.fileupload.FileItem;
import org.fanti.uploader.server.bean.FileInfo;
import org.fanti.uploader.server.service.upload.UploadService;
import org.fanti.uploader.server.util.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Value("${upload.folder}")
    private String uploadFolder;

    @Value("${tmp.upload.folder}")
    private String tmpUploadFolder;

    @Override
    public void upload(List<FileItem> list) {
        FileInfo fileInfo = UploadUtil.initFileInfo(list);

        if (fileInfo.getTotalChunks() == 1) {
            UploadUtil.receiveSingleFile(fileInfo, uploadFolder);
        } else {
            UploadUtil.receiveChunkFile(fileInfo, tmpUploadFolder);
        }
    }

    @Override
    public void chunkMerge(FileInfo fileInfo) {
        UploadUtil.mergeChunks(fileInfo, uploadFolder, tmpUploadFolder);
    }

    @Override
    public boolean chunkCheck(FileInfo fileInfo) {
        String targetDir = "";
        String filename = "";
        long fileSize;

        if(fileInfo.getTotalChunks() == 1) {
            targetDir = uploadFolder;
            filename = fileInfo.getFilename();
            fileSize = fileInfo.getTotalSize();
        } else {
            targetDir = tmpUploadFolder;
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
}
