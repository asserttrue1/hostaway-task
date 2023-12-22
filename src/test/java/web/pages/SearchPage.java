package web.pages;

import org.openqa.selenium.By;
import com.codeborne.selenide.SelenideElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SearchPage {
    public SelenideElement filterButton() {
        return $(By.cssSelector("button[class='sc-giAqHp eaGTVv'] span"));
    }

    public void setLocation(String location) {
        $(By.xpath("//div[@class='sc-csTbgd jqmzTW' and text()='Location']")).click();
        $(By.xpath("//div[@class='sc-dFRpbK ctxABk']/input")).setValue(location);
    }

    public void setDate(int daysToAdd, String entryField) {
        String label = (entryField.equalsIgnoreCase("check-in")) ? "Check-in" : "Check-out";

        $(By.xpath("//div[@class='sc-csTbgd jqmzTW' and text()='" + label + "']")).click();

        LocalDate currentDate = LocalDate.now();

        LocalDate desiredDate = currentDate.plusDays(daysToAdd);

        String desiredMonth = desiredDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        while (!$(By.xpath("//div[@class='sc-ikXwFM hZHGfK' and text()='" + desiredMonth + "']")).is(visible)) {
            $(By.xpath("//button[contains(@class, 'sc-giAqHp') and .//*[name()='use' and contains(@xlink:href, 'chevron-right')]]")).click();
        }

        String locator = String.format("//div[@class='sc-biJonm gBUQwF CalendarDay' and text()='%s']", desiredDate.getDayOfMonth());
        $(By.xpath(locator)).click();
    }

    public void verifyCheckinDate(LocalDate expectedDate) {
        verifyDateSelection("Check-in", expectedDate);
    }

    public void verifyCheckoutDate(LocalDate expectedDate) {
        verifyDateSelection("Check-out", expectedDate);
    }

    private void verifyDateSelection(String label, LocalDate expectedDate) {
        String formattedExpectedDate = expectedDate.format(DateTimeFormatter.ofPattern("d MMMM yyyy"));
        $(By.xpath("//div[@class='sc-csTbgd jqmzTW' and text()='" + label + "']/following-sibling::div"))
                .shouldHave(text(formattedExpectedDate));
    }
}
