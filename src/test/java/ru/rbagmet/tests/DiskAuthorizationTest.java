package ru.rbagmet.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.rbagmet.DiskTabs;
import ru.rbagmet.TestBase;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("auth")
public class DiskAuthorizationTest extends TestBase {

    @Test
    @DisplayName("Открыть страницу Диска")
    void openMainPage() {
        open("https://disk.yandex.ru/");

        String expectedTitle = "Яндекс.Диск";
        String actualTitle = title();
        assertThat(actualTitle).isEqualTo(expectedTitle);
    }

    @Test
	@DisplayName("Авторизоваться с корректными логином и паролем")
    void authorizeWithCorrectCredentials() {
        open("https://disk.yandex.ru/"); // шаг - открыть главную страницу 
		$(".button_login").click(); // шаг - нажать кнопку входа 
		$("#passp-field-login").setValue(authorizeParams.getUserLogin());
		$("[data-t=\"button:action:passp:sign-in\"]").click(); //ввести логин
		$("#passp-field-passwd").setValue(authorizeParams.getUserPassword());
		$("[data-t=\"button:action:passp:sign-in\"]").click(); //ввести пароль

		if ($("div.gialog-wrap").isDisplayed()){$("button.dialog__close").click();}

		$("img.user-pic__image").shouldBe(visible);
		$("img.user-pic__image").click();
		$(".legouser__menu-header").$(".user-account__name").shouldHave(text(authorizeParams.getUserLogin())); //убедиться, что авторизован нужный пользователь
    }

    @Test
    void authorizeWithAbsentLogin() {
		open("/"); // шаг - открыть главную страницу 
		$(".button_login").click();
		$("[data-t=\"button:action:passp:sign-in\"]").click(); //нажать кнопку перехода к вводу пароля
		$("[data-t=\"field:input-login:hint\"]").shouldHave(text("Логин не указан"));
		$("[data-t=\"field:input-login:hint\"]").shouldHave(cssValue("color", "rgba(255, 0, 0, 1)"));
    }

    @Test
    void authorizeWithInCorrectPassword() {
		open(""); // шаг - открыть главную страницу
		$(".button_login").click(); // шаг - нажать кнопку входа 
		$("#passp-field-login").setValue(authorizeParams.getUserLogin()); //ввести логин
		$("[data-t=\"button:action:passp:sign-in\"]").click();
		$("#passp-field-passwd").setValue("1");
		$("[data-t=\"button:action:passp:sign-in\"]").click();
		$("[data-t=\"field:input-passwd:hint\"]").shouldHave(text("Неверный пароль"));
		$("[data-t=\"field:input-passwd:hint\"]").shouldHave(cssValue("color", "rgba(255, 0, 0, 1)"));
    }

}
