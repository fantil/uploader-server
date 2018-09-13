package org.fanti.uploader.server.mapper;

import org.fanti.uploader.server.db.UserDir;
import org.fanti.uploader.server.db.UserFile;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/9/13
 */

public interface UserDirMapper extends Mapper<UserDir> {
    List<UserDir> getUserDirList();
}
