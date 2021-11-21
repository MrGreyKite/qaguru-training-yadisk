package ru.rbagmet.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static org.assertj.core.api.Assertions.assertThat;

public class DiskTest {

    @Test
    @DisplayName("Открыть страницу Диска")
    void openMainPage() {
        open("https://disk.yandex.ru/");

        String expectedTitle = "Яндекс.Диск";
        String actualTitle = title();
        assertThat(actualTitle).isEqualTo(expectedTitle);
    }
}
