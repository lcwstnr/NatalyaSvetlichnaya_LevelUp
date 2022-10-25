package ru.levelp.at.MailExercize;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.levelp.at.homework3.GmailPage;



public class DraftThanSendMailTest {

    private static final String MAIL_URL = "https://www.google.com/intl/ru/gmail/about/";
    private static final String EMAIL = "4coursetesting@gmail.com";
    private static final String PASSWORD = ",fv,fv,bv,bv";
    private static final String THEME = "Тестовая тема";
    private static final String BODY = "Тестовый текст";

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void createDraftThanSendMailTest() throws  InterruptedException {
        GmailPage gmailPage = new GmailPage(driver);

        gmailPage.openSignIn(MAIL_URL)
                .signIn(EMAIL, PASSWORD)
                .checkSignIn(EMAIL)
                .createMail(EMAIL, THEME, BODY)
                .safeDraft()
                .checkDraft(EMAIL, THEME, BODY)
                .sentMail()
                .checkEmptyDraft()
                .checkSent(EMAIL, THEME, BODY)
                .signOut();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}