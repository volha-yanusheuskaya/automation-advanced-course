package com.epam.automation.ui.business.models;

import com.epam.automation.common.core.config.ConfigurationReader;
import lombok.Getter;

@Getter
public class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static User defaultUser() {
        return new User(
                ConfigurationReader.getUsername(),
                ConfigurationReader.getPassword()
        );
    }
}
