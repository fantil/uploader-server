package org.fanti.uploader.server.mapper;

import org.apache.ibatis.annotations.Param;
import org.fanti.uploader.server.db.DBFile;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/9/13
 */

@Service
public interface DBFileMapper extends Mapper<DBFile> {
    List<DBFile> getDBFileList(@Param("md5") String md5);

    List<DBFile> getDBFileByFileId(@Param("fileId") Integer fileId);

    List<DBFile> getDBFileListByFileIdList(List<Integer> fileIdList);
}
