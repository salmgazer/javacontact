package com.micontact.app.service;

import com.micontact.app.controller.LoginPayload;
import com.micontact.app.model.User;

public class JWTService {
    /**
     * This is fake
     * @param user
     * @return
     */
    public String createJWT(LoginPayload loginPayload) {
        return loginPayload.username + "_" + loginPayload.password;
    }

    public Boolean confirm(String token) {
        String[] tokenParts = token.split("_", 2);
        System.out.println("%%%%%%%%%%%%%%%%");
        System.out.println(tokenParts[0]);
        System.out.println(tokenParts[1]);
        String username = tokenParts[0];
        String password = tokenParts[1];

        User user = new UserService().getUserByUsername(username);
        System.out.println(user);
        return user.getPassword().equals(password);
    }
}
