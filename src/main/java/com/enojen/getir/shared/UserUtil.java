package com.enojen.getir.shared;

import com.enojen.getir.security.service.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;


public class UserUtil {
    public static String getUserId() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getId();
    }

}