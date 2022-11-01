package ru.levelp.at.homework3;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GmailPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public GmailPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public GmailPage openSignIn(String url) {
        driver.navigate().to(url);
        return this;
    }

    public GmailPage signIn(String login, String pass) {
        driver.findElement(By.cssSelector("[data-action='sign in']")).click();
        WebElement inputMailBox = driver.findElement(By.id("identifierId"));
        inputMailBox.sendKeys(login);
        driver.findElement(By.id("identifierNext")).click();
        WebElement inputPasswordBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        WebElement inputPassword = inputPasswordBox.findElement(By.xpath(".//input[@type='password']"));
        inputPassword.sendKeys(pass);
        driver.findElement(By.id("passwordNext")).click();
        WebElement title = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//a[@role='button'][contains(@aria-label,'Аккаунт Google:')]")));
        return this;
    }

    public GmailPage signOut() {
        driver.findElement(By.xpath("//a[@role='button'][contains(@aria-label,'Аккаунт Google:')]")).click();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("account"));
        driver.findElement(By.xpath("//a[div[text()='Выйти']]")).click();
        return this;
    }

    public GmailPage checkSignIn(String email) {
        Assertions.assertThat(driver.getTitle())
                .as("Title не содержит ожидаемый email")
                .contains(email);
        return this;
    }

    public GmailPage createMail(String login, String theme, String body) {
        driver.findElement(By.xpath("//div[@role='button'][text()='Написать']")).click();
        WebElement inputRecipientBox = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("[aria-label='Кому']")));
        WebElement inputRecipient = inputRecipientBox
                .findElement(By.cssSelector("input"));
        inputRecipient.sendKeys(login + "\n");
        WebElement inputThemeBox = driver.findElement(By.xpath("//input[@name='subjectbox']"));
        inputThemeBox.sendKeys(theme);
        WebElement inputBodyBox = driver.findElement(By.xpath("//div[@role='textbox']"));
        inputBodyBox.click();
        inputBodyBox.sendKeys(body);
        return this;
    }

    public GmailPage safeDraft() {
        WebElement save = driver.findElement(By.cssSelector("[data-tooltip='Сохранить и закрыть']"));
        save.click();
        wait.until(ExpectedConditions.invisibilityOf(save));
        return this;
    }

    public GmailPage checkDraft(String login, String theme, String body) {
        goingToPackage("Черновики");
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//tr[.//span[text()='" + theme + "']]"))).click();
        WebElement form = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@role = 'dialog']")));
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
        WebElement sent = driver.findElement(By.xpath("//div[@role='button'][text()='Отправить']"));
        sent.click();
        wait.until(ExpectedConditions.invisibilityOf(sent));
        return this;
    }

    public GmailPage deleteMail() {
        driver.findElement(By.xpath("//div[@data-tooltip='Ещё']")).click();
        driver.findElement(By.xpath("//div[text()='Удалить это письмо']")).click();
        return this;
    }

    public GmailPage checkEmptyDraft() {
        WebElement textArea = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//td[text()='Нет сохраненных черновиков.']")));
        driver.findElement(By.xpath("//td[text()='Нет сохраненных черновиков.']"));
        return this;
    }

    public GmailPage checkInbox(String login, String theme, String body) {
        goingToPackage("Входящие");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='main']//tr[.//span[text()='" + theme + "']]"))).click();
        WebElement main = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='main']")));
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

    public GmailPage checkSent(String login, String theme, String body) {
        goingToPackage("Отправленные");
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .xpath("//div[@role='main']//tr[.//span[text()='" + theme + "']]"))).click();
        WebElement main = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@role='main']")));
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

    public GmailPage checkPackage(String login, String theme, String body) {
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("[aria-label='Тест есть меню']"))).click();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .xpath("//div[@role='main']//tr[.//span[text()='" + theme + "']]"))).click();
        WebElement main = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@role='main']")));
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

    private void goingToPackage(String text) {
        WebElement draft = driver.findElement(By.xpath("//a[text()='" + text + "']"));
        draft.click();
        wait.until(ExpectedConditions.attributeToBe(By.xpath("//a[text()='" + text + "']"), "tabindex", "0"));
    }

}
