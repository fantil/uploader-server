package org.fanti.uploader.server.service.download;


import org.fanti.uploader.server.db.DBFile;

import java.util.List;

public interface DownloadService {
    DBFile download(int fileId);

    List<DBFile> listFiles(String currentDir);
}
