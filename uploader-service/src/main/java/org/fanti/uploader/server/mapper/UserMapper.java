package org.fanti.uploader.server.mapper;

import org.fanti.uploader.server.db.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/9/13
 */

public interface UserMapper extends Mapper<User> {
    List<User> getUserList();
}
