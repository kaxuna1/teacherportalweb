package com.technonet.staticData;

import com.technonet.model.User;

/**
 * Created by kakha on 3/2/2017.
 */
public class PermisionChecks {
    static public boolean isAdmin(User user){
        return user.getPermissions().stream().filter(permission->permission.getCode().equals("admin")).count()>0;
    }
}
