package ru.levelp.at.homework3;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GmailPage {

    private final WebDriver driver;

    public GmailPage(WebDriver driver) {
        this.driver = driver;
    }

    public GmailPage openSignIn(String url) {
        driver.navigate().to(url);
        return this;
    }

    @SuppressWarnings("checkstyle:LineLength")
    public GmailPage signIn(String login, String pass) throws InterruptedException {
        driver.findElement(By.cssSelector("[data-action='sign in']")).click();
        WebElement inputMailBox = driver.findElement(By.id("identifierId"));
        inputMailBox.sendKeys(login);
        driver.findElement(By.id("identifierNext")).click();
        Thread.sleep(5000);
        WebElement inputPasswordBox = driver.findElement(By.id("password"))
                .findElement(By.xpath(".//input[@type='password']"));
        inputPasswordBox.sendKeys(pass);
        driver.findElement(By.id("passwordNext")).click();
        Thread.sleep(5000);
        return this;
    }

    public GmailPage signOut() throws InterruptedException {
        driver.findElement(By.xpath("//a[@role='button'][contains(@aria-label,'Аккаунт Google:')]")).click();
        Thread.sleep(2000);
        driver.switchTo().frame("account");
        driver.findElement(By.xpath("//a[div[text()='Выйти']]")).click();
        return this;
    }

    public GmailPage checkSignIn(String email) {
        Assertions.assertThat(driver.getTitle())
                .as("Title не содержит ожидаемый email")
                .contains(email);
        return this;
    }

    public GmailPage createMail(String login, String theme, String body) throws InterruptedException {
        driver.findElement(By.xpath("//div[@role='button'][text()='Написать']")).click();
        Thread.sleep(1000);
        WebElement inputRecipientBox = driver.findElement(By.cssSelector("[aria-label='Кому']"))
                .findElement(By.cssSelector("input"));
        inputRecipientBox.sendKeys(login + "\n");
        WebElement inputThemeBox = driver.findElement(By.xpath("//input[@name='subjectbox']"));
        inputThemeBox.sendKeys(theme);
        WebElement inputBodyBox = driver.findElement(By.xpath("//div[@role='textbox']"));
        inputBodyBox.click();
        inputBodyBox.sendKeys(body);
        return this;
    }

    public GmailPage safeDraft() {
        driver.findElement(By.cssSelector("[data-tooltip='Сохранить и закрыть']")).click();
        return this;
    }

    public GmailPage checkDraft(String login, String theme, String body) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[text()='Черновики']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//tr[.//span[text()='" + theme + "']]")).click();
        Thread.sleep(2000);
        WebElement form =  driver.findElement(By.xpath("//div[@role = 'dialog']"));
        String actualEmail = form.findElement(By.xpath(".//span[contains(@email,'@')]"))
                .getAttribute("email");
        String actualSubject = form.findElement(By.xpath(".//input[@name='subject']")).getAttribute("value");
        String actualText = form.findElement(By.xpath(".//div[contains(@aria-label,'Текст письма')]")).getText();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualEmail)
                .as("Email содержит некорректное значение")
                .isEqualTo(login);
        softAssertions.assertThat(actualSubject)
                .as("Subject содержит некорректное значение")
                .isEqualTo(theme);
        softAssertions.assertThat(actualText)
                .as("Text содержит некорректное значение")
                .isEqualTo(body);
        softAssertions.assertAll();
        return this;
    }

    public GmailPage sentMail() {
        driver.findElement(By.xpath("//div[@role='button'][text()='Отправить']")).click();
        return this;
    }

    public GmailPage deleteMail() {
        driver.findElement(By.xpath("//div[@data-tooltip='Ещё']")).click();
        driver.findElement(By.xpath("//div[text()='Удалить это письмо']")).click();
        return this;
    }

    public GmailPage checkEmptyDraft() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//td[text()='Нет сохраненных черновиков.']"));
        return this;
    }

    public  GmailPage checkInbox(String login, String theme, String body) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[text()='Входящие']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@role='main']//tr[.//span[text()='" + theme + "']]")).click();
        Thread.sleep(2000);
        WebElement main = driver.findElement(By.xpath("//div[@role='main']"));
        String actualEmail = main.findElement(By.xpath(".//span[contains(@email,'@')]"))
                .getAttribute("email");
        String actualSubject = main.findElement(By.xpath(".//h2")).getText();
        String actualText = main.findElement(By.xpath(".//div[@dir='ltr']")).getText();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualEmail)
                .as("Email содержит некорректное значение")
                .isEqualTo(login);
        softAssertions.assertThat(actualSubject)
                .as("Subject содержит некорректное значение")
                .isEqualTo(theme);
        softAssertions.assertThat(actualText)
                .as("Text содержит некорректное значение")
                .isEqualTo(body);
        softAssertions.assertAll();
        return this;
    }

    public  GmailPage checkSent(String login, String theme, String body) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[text()='Отправленные']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@role='main']//tr[.//span[text()='" + theme + "']]")).click();
        Thread.sleep(2000);
        WebElement main = driver.findElement(By.xpath("//div[@role='main']"));
        String actualEmail = main.findElement(By.xpath(".//span[contains(@email,'@')]"))
                .getAttribute("email");
        String actualSubject = main.findElement(By.xpath(".//h2")).getText();
        String actualText = main.findElement(By.xpath(".//div[@dir='ltr']")).getText();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualEmail)
                .as("Email содержит некорректное значение")
                .isEqualTo(login);
        softAssertions.assertThat(actualSubject)
                .as("Subject содержит некорректное значение")
                .isEqualTo(theme);
        softAssertions.assertThat(actualText)
                .as("Text содержит некорректное значение")
                .isEqualTo(body);
        softAssertions.assertAll();
        return this;
    }

    public GmailPage checkPackage(String login, String theme, String body) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("[aria-label='Тест есть меню']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@role='main']//tr[.//span[text()='" + theme + "']]")).click();
        Thread.sleep(2000);
        WebElement main = driver.findElement(By.xpath("//div[@role='main']"));
        String actualEmail = main.findElement(By.xpath(".//span[contains(@email,'@')]"))
                .getAttribute("email");
        String actualSubject = main.findElement(By.xpath(".//h2")).getText();
        String actualText = main.findElement(By.xpath(".//div[@dir='ltr']")).getText();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualEmail)
                .as("Email содержит некорректное значение")
                .isEqualTo(login);
        softAssertions.assertThat(actualSubject)
                .as("Subject содержит некорректное значение")
                .isEqualTo(theme);
        softAssertions.assertThat(actualText)
                .as("Text содержит некорректное значение")
                .isEqualTo(body);
        softAssertions.assertAll();
        return this;
    }

    public GmailPage checkBin(String login, String theme, String body) {
        driver.findElement(By.xpath("//span[text() = 'Ещё']")).click();

        return this;
    }

}
