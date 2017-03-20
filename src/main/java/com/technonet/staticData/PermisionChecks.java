package com.technonet.staticData;

import com.technonet.Enums.PERMISSIONS;
import com.technonet.model.Permission;
import com.technonet.model.Session;
import com.technonet.model.User;

/**
 * Created by kakha on 3/2/2017.
 */
public class PermisionChecks {
    static public boolean isAdmin(User user) {
        return user.getPermissions().stream().filter(permission -> permission.getCode().equals(PERMISSIONS.admin.name())).count() > 0;
    }

    static public boolean FileManagement(Session session) {

        return session.isIsactive() && session.getUser().getPermissions().stream().filter(permission -> permission.getCode().equals(PERMISSIONS.fileManagement.name())).count() > 0;

    }

    static public boolean isAdmin(Session session) {
        return session.isIsactive() && session.getUser()
                .getPermissions().stream()
                .filter(permission -> permission.getCode().equals(PERMISSIONS.admin.name()))
                .count() > 0;
    }
    static public boolean categoriesManagement(Session session){
        return session.isIsactive() && session.getUser()
                .getPermissions().stream()
                .filter(permission -> permission.getCode().equals(PERMISSIONS.categories.name()))
                .count() > 0;
    }
    static public boolean documentTypes(Session session){
        return session.isIsactive() && session.getUser()
                .getPermissions().stream()
                .filter(permission -> permission.getCode().equals(PERMISSIONS.docTypes.name()))
                .count() > 0;
    }
    static public boolean userManagement(Session session){
        return session.isIsactive() && session.getUser()
                .getPermissions().stream()
                .filter(permission -> permission.getCode().equals(PERMISSIONS.users.name()))
                .count() > 0;
    }
    static public boolean galleryManagement(Session session){
        return session.isIsactive() && session.getUser()
                .getPermissions().stream()
                .filter(permission -> permission.getCode().equals(PERMISSIONS.gallery.name()))
                .count() > 0;
    }
    static public boolean scheduleManagement(Session session){
        return session.isIsactive() && session.getUser()
                .getPermissions().stream()
                .filter(permission -> permission.getCode().equals(PERMISSIONS.scheduling.name()))
                .count() > 0;
    }static public boolean categoryConfirmation(Session session){
        return session.isIsactive() && session.getUser()
                .getPermissions().stream()
                .filter(permission -> permission.getCode().equals(PERMISSIONS.categoryConfirmation.name()))
                .count() > 0;
    }

    public static boolean student(Session session) {
        return session.isIsactive() && session.getUser()
                .getPermissions().stream()
                .filter(permission -> permission.getCode().equals(PERMISSIONS.student.name()))
                .count() > 0;
    }
}
