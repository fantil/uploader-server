package org.fanti.uploader.server.util;

import org.fanti.uploader.server.db.User;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/9/13
 */

public class UserUtil {

    public static User getCurrentUser() {
        User user = new User();
        user.setId(1);
        user.setName("default");

        return user;
    }
}
