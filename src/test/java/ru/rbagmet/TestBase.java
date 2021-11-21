package ru.rbagmet;

import com.codeborne.selenide.Configuration;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import ru.rbagmet.configs.EnvConfig;
import ru.rbagmet.configs.SelenideConfig;

public class TestBase {
    public static EnvConfig env = ConfigFactory.create(EnvConfig.class);

    public static SelenideConfig selenideCreds = ConfigFactory
            .create(SelenideConfig.class, System.getProperties());

    //будет использоваться для авторизации на Диске при более сложных тестах
    // public static AuthorizeConfig authorizeParams = ConfigFactory.create(AuthorizeConfig.class);

    @BeforeAll
    static void setup(){
        if(env.isRemote()) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);

            Configuration.browserCapabilities = capabilities;

            Configuration.remote = selenideCreds.remoteUrl();
        }

        Configuration.browser = env.getBrowser();
        Configuration.browserVersion = env.getBrowserVersion();
        Configuration.startMaximized = true;

    }


}
