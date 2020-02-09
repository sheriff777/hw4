import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class OrderCardWithDeliveryTest {

    SelenideElement form = $("form");
    SelenideElement city = form.$("input[placeholder='Город'");
    SelenideElement dateOrder = form.$("input[placeholder='Дата встречи']");
    SelenideElement name = form.$("input[name='name']");
    SelenideElement phoneNumber = form.$("input[name='phone']");
    SelenideElement checkBox = form.$(".checkbox__text");
    SelenideElement buttonConfirm = $$(".button__icon~.button__text").find(exactText("Забронировать"));
    SelenideElement notification = $(withText("Успешно"));

    private String getOrderDate(int plusDays) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        LocalDate currentDate = LocalDate.now();
        LocalDate controlDate = currentDate.plusDays(plusDays);
        String formatControlDate = controlDate.format(format);
        return formatControlDate;
    }


    @BeforeEach
    void openLocalhost() {
        open("http://localhost:9999");
    }

    @Test
    void shouldReservationCard() {
        city.setValue("Тула");
        dateOrder.doubleClick().sendKeys(Keys.BACK_SPACE);
        dateOrder.setValue(getOrderDate(7));
        name.setValue("Айбек Сыдыгалиев");
        phoneNumber.setValue("+75668785550");
        checkBox.click();
        buttonConfirm.click();
        notification.waitUntil(exist, 15000);
    }
}
