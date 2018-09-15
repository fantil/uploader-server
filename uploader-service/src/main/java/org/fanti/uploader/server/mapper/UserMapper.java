package org.fanti.uploader.server.mapper;

import org.fanti.uploader.server.db.User;
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
public interface UserMapper extends Mapper<User> {
    List<User> getUserList();
}
