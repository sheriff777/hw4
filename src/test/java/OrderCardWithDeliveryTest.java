
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;


class OrderCardWithDeliveryTest {

    SelenideElement form = $("form.form");
    LocalDate nowDate =  LocalDate.now();
    LocalDate plusSevenDays = nowDate.plusDays(7);
    String DateForDelivery = plusSevenDays.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));


    @BeforeEach
    void setup() {
        open("http:\\localhost:9999");
        Configuration.waitUntil(exist, 15000);
    }

    @AfterEach
    void closeBrowser() {
        Selenide.close();
    }


    @DisplayName("Should register card for delivery")
    @Test
    void shouldOrderCardWithDelivery() {
        form.$("[data-test-id=city] input").setValue("Тула");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] .input__control").setValue(DateForDelivery);
        form.$("[data-test-id=name] input").setValue("Сыдыгалиев Айбек");
        form.$("[data-test-id=phone] input").setValue("+72544112250");
        form.$("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification] .notification__title").shouldHave(exactText("Успешно!"));
        $("[data-test-id=notification] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + DateForDelivery));

    }
}