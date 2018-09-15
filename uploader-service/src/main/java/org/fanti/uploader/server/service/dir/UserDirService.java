package org.fanti.uploader.server.service.dir;

import org.apache.commons.fileupload.FileItem;
import org.fanti.uploader.server.bean.FileInfo;
import org.fanti.uploader.server.db.UserDir;

import java.util.List;

public interface UserDirService {
    UserDir initUserDir(FileInfo fileInfo);

    UserDir createUserDir(UserDir userDir, String fullPath);
}
