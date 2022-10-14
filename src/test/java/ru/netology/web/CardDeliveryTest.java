package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class CardDeliveryTest {

    @Test
    void shouldOkWhenAllFillIn() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань");
        SelenideElement dateField = $("[placeholder='Дата встречи']"); // вводим элемент поля с датой
        dateField.click();
        String date = LocalDate.now().plusDays(3).format(ofPattern("dd.MM.yyyy")); // переменная с логикой даты по условиям
        dateField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE)); // очищаем поле с датой
        dateField.setValue(date); // устанавливаем дату
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79250001122");
        $("[data-test-id='agreement'] span").click();
        $("[type='button']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=notification] .notification__content").shouldBe(visible, ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + date));

    }
}
