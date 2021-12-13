package ru.rbagmet.configs;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/authorization.properties")
public interface AuthorizeConfig extends Config {

    @Key("user.login")
    String getUserLogin();

    @Key("user.password")
    String getUserPassword();

    @Key("user.token")
    String getToken();
}
