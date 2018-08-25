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
import java.io.FileOutputStream;
import java.io.InputStream;
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

    @Override
    public void upload(List<FileItem> list) {
        UploadUtil.handleUpload(list, this.uploadFolder);
    }
}