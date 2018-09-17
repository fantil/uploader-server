package org.fanti.uploader.server.service.download;


import org.fanti.uploader.server.db.DBFile;

import java.util.List;

public interface DownloadService {
    void download();

    List<DBFile> listFiles(String currentDir);
}
