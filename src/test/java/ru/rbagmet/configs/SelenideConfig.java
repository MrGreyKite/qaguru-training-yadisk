package ru.rbagmet.configs;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config/selenide.properties"})
public interface SelenideConfig extends Config {

    @DefaultValue("https://${login}:${password}@${url}/wd/hub/")
    String remoteUrl();

    @Key("selenide.login")
    String login();

    @Key("selenide.password")
    String password();

    @Key("selenide.url")
    String url();
}
