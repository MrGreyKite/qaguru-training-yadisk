package ru.rbagmet;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import ru.rbagmet.configs.AuthorizeConfig;
import ru.rbagmet.configs.EnvConfig;
import ru.rbagmet.configs.SelenideConfig;

import java.time.Duration;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class TestBase {
    public static EnvConfig env = ConfigFactory.create(EnvConfig.class); 

    public static SelenideConfig selenideCreds = ConfigFactory
            .create(SelenideConfig.class, System.getProperties());

	public static AuthorizeConfig authorizeParams = ConfigFactory
			.create(AuthorizeConfig.class); //для авторизации на целевом ресурсе

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
		//Configuration.browserSize = System.getProperty("browserSize", "1366×768");
		Configuration.startMaximized = true;

//		Configuration.proxyEnabled = true;
//		Configuration.fileDownload = FileDownloadMode.PROXY;

		Configuration.baseUrl = "https://disk.yandex.ru/";
		
		SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));

    }

	@AfterEach
	void teardown(){
		/*	НЕ ЗАБЫТЬ РАСКОММЕНТИРОВАТЬ ПЕРЕД ОКОНЧАТЕЛЬНОЙ ОТЛАДКОЙ
		Attachments.pageSource();
        Attachments.browserConsoleLogs();
        Attachments.addVideo();
        */
		Selenide.closeWindow();
		}
	
	@AfterAll
	static void tearDownAll() {
	SelenideLogger.removeListener("AllureSelenide");
	}
	
	
	//т.к. без пейджобъектов, вспомогательные методы идут просто как методы
	
	public SelenideElement PreSelectFile(String filename) {
		SelenideElement preselected = $$(".listing-item").findBy(text(filename));
		return preselected;
	}
	
	protected void deleteFile(String filename) {
		SelenideElement actionMenu = $(".resources-actions-popup__wrapper");
		PreSelectFile(filename).contextClick();
		actionMenu.$("[value=delete]").click();
		$(".MessageBox").should(disappear, Duration.ofSeconds(10));
		
	}
	
	protected void restoreFile(String filename) {
		open("/client/trash");
		SelenideElement actionMenu = $(".resources-actions-popup__wrapper");
		PreSelectFile(filename).contextClick();
		actionMenu.$("[value=restore]").click();
		$(".MessageBox").should(disappear, Duration.ofSeconds(10));

	}

	public static final String testFileName = "Mountains.jpg";

}
