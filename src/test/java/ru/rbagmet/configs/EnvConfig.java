package ru.rbagmet.configs;

import org.aeonbits.owner.Config;

import static org.aeonbits.owner.Config.LoadType.MERGE;

@Config.LoadPolicy(MERGE)
@Config.Sources(
        {"system:properties",
        "classpath:${browser}-${remote}.properties"}
)
public interface EnvConfig extends Config {

    @Key("browser.name")
    @DefaultValue("chrome")
    String getBrowser();

    @Key("browser.version")
    @DefaultValue("96")
    String getBrowserVersion();

    @Key("browser.remote")
    @DefaultValue("false")
    boolean isRemote();

}
