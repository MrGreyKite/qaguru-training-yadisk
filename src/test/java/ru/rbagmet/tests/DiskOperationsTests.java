package ru.rbagmet.tests;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.rbagmet.DiskTabs;
import ru.rbagmet.TestBase;

import java.io.File;
import java.io.FileNotFoundException;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.DragAndDropOptions.usingJavaScript;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static org.apache.commons.io.FilenameUtils.getName;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("main")
public class DiskOperationsTests extends TestBase {

	@BeforeEach
	void login() {
		open("https://disk.yandex.ru/client/disk",
				AuthenticationType.BASIC, authorizeParams.getUserLogin(), authorizeParams.getUserPassword());
		$("#passp-field-login").setValue(authorizeParams.getUserLogin());
		$("[data-t=\"button:action:passp:sign-in\"]").click(); //ввести логин
		$("#passp-field-passwd").setValue(authorizeParams.getUserPassword());
		$("[data-t=\"button:action:passp:sign-in\"]").click();
         }


	@ParameterizedTest(name = "Choosing tab on Disk Menu - {0}")
	@EnumSource(value = DiskTabs.class, names = {"RECENT", "PHOTO", "PUBLIC", "TRASH"}, mode = EnumSource.Mode.INCLUDE)
	void chooseTabsOnDisk(DiskTabs tab){
		ElementsCollection navItems = $$(".navigation__link"); //коллекция всех табов
		SelenideElement selectedTab = navItems.find(text(tab.getTabText()));
		selectedTab.click();//выбор таба
		selectedTab.shouldHave(cssClass("navigation__link_current")); //проверка класса
		selectedTab.shouldHave(attribute("aria-selected", "true"));
		selectedTab.shouldHave(pseudo("after",
				"background", "rgba(0, 0, 0, 0.07) none repeat scroll 0% 0% / auto padding-box border-box")); //проверка выделения цветом
	}

	@Test
	void throwFileInEmptyTrashBin() {
		//PreSelectFile(testFileName).dragAndDropTo($(byId("/trash"))); не работает перетаскивание - элемент, к-рый надо перетащить, не тащится
		PreSelectFile(testFileName).click();
		$(".resources-action-bar__body").$(".groupable-buttons__visible-button_name_delete").click();
		$(".notifications__item_trash").shouldHave(text("перемещён в Корзину"));
		$(".notifications__item_trash")
				.shouldHave(cssValue("color", "rgba(255, 255, 255, 0.8)"));
		$(".notifications__item_trash")
				.shouldHave(cssValue("background-color", "rgba(34, 34, 34, 1)"));
		$("span.file-icon_dir_trash-full").shouldBe(visible); //иконка пустой корзины изменилась на иконку заполненной

		restoreFile(testFileName); //шаг восстановления тестового файла после проверок
	}

	@Test
	void retrieveOnlyFileFromTrashBin() {
		deleteFile(testFileName); //шаг удаления тестового файла перед восстановлением

		open("/client/trash");
		PreSelectFile(testFileName).click();
		$(".resources-action-bar__body").
				$(".ufo-resources-action-bar__primary-button_action_restore").click();
		$(".notifications__item_moved").shouldHave(text("восстановлен"));
		$("div.listing__items").
				$$(".listing-item_theme_tile").shouldHave(size(0)); //что в корзине нет файлов

	}

	@Test
	@Disabled
	void downloadFileFromDisk() throws FileNotFoundException {

		PreSelectFile(testFileName).click();
		File downloaded = $(".resources-action-bar_visible").
				$(".ufo-resources-action-bar__primary-button_action_download").download();
		assertThat(getName(downloaded.toString())).isEqualTo(testFileName);
	}



}