package org.fanti.uploader.server.service.dir;

import org.fanti.uploader.server.bean.FileInfo;
import org.fanti.uploader.server.db.UserDir;
import org.fanti.uploader.server.mapper.base.IMapperService;

import java.util.List;

public interface UserDirService extends IMapperService<UserDir> {
    int initUserDir(FileInfo fileInfo);

    int createUserDir(UserDir userDir, String fullPath);

    UserDir getUserDirByUserIdAndFullPath(int userId, String fullPath);
}
