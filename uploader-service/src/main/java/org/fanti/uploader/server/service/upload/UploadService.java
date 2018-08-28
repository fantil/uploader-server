package org.fanti.uploader.server.service.upload;

import org.apache.commons.fileupload.FileItem;
import org.fanti.uploader.server.bean.FileInfo;

import java.util.List;

public interface UploadService {
    void upload(List<FileItem> list);

    void chunkMerge(FileInfo fileInfo);

    boolean chunkCheck(FileInfo fileInfo);
}
