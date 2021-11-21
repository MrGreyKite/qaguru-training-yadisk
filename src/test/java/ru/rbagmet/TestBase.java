package ru.rbagmet;

import com.codeborne.selenide.Configuration;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import ru.rbagmet.configs.*;

public class TestBase {
    public static EnvConfig environment = ConfigFactory.create(EnvConfig.class, System.getProperties());

    public static SelenideConfig selenideCreds = ConfigFactory.create(SelenideConfig.class, System.getProperties());

    //будет использоваться для авторизации на Диске при более сложных тестах
    // public static AuthorizeConfig authorizeParams = ConfigFactory.create(AuthorizeConfig.class);

    @BeforeAll
    static void setup(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);

        Configuration.browserCapabilities = capabilities;
        Configuration.startMaximized = true;

        Configuration.browser = environment.getBrowser();
        Configuration.browserVersion = environment.getBrowserVersion();

        if(environment.isRemote()) {
            Configuration.remote = selenideCreds.remoteUrl();
        }


    }


}
