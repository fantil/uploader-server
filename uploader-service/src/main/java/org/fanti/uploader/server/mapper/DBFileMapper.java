package org.fanti.uploader.server.mapper;

import org.fanti.uploader.server.db.DBFile;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/9/13
 */

public interface DBFileMapper extends Mapper<DBFile> {
    List<DBFile> getDBFileList();
}
