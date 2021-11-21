package ru.rbagmet.configs;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config/selenide.properties"})
public interface SelenideConfig extends Config {

    @Key("selenide.login")
    String login();

    @Key("selenide.password")
    String password();

    @Key("selenide.url")
    String url();

    @DefaultValue("https://${selenide.login}:${selenide.password}@${selenide.url}/wd/hub/")
    String remoteUrl();
}
