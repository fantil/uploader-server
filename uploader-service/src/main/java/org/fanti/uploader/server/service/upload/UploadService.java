package org.fanti.uploader.server.service.upload;

import org.apache.commons.fileupload.FileItem;

import java.util.List;

public interface UploadService {
    void upload(List<FileItem> list);

    void merge(String filename, String identifier);
}
