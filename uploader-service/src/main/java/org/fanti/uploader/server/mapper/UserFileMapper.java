package org.fanti.uploader.server.mapper;

import org.apache.ibatis.annotations.Param;
import org.fanti.uploader.server.db.UserFile;
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
public interface UserFileMapper extends Mapper<UserFile> {
    List<UserFile> getUserFileList(@Param("dirId") int dirId);
}
