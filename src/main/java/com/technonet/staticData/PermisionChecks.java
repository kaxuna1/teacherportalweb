package com.technonet.staticData;

import com.technonet.Enums.PERMISSIONS;
import com.technonet.model.Permission;
import com.technonet.model.Session;
import com.technonet.model.User;

/**
 * Created by kakha on 3/2/2017.
 */
public class PermisionChecks {
    static public boolean isAdmin(User user){
        return user.getPermissions().stream().filter(permission->permission.getCode().equals(PERMISSIONS.admin.name())).count()>0;
    }
    static public boolean FileManagement(Session session) {

        return session.isIsactive() && session.getUser().getPermissions().stream().filter(permission -> permission.getCode().equals(PERMISSIONS.fileManagement.name())).count() > 0;

    }
}
