package com.mekn.demo.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.mekn.demo.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {

    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(STUDENT_READ,STUDENT_WRITE,COURSE_WRITE,COURSE_READ)),
    ADMINTRAINEE(Sets.newHashSet(STUDENT_READ,COURSE_READ));
    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }


}
