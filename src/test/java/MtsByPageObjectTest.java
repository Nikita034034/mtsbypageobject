import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import java.time.Duration;

public class MtsByPageObjectTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriverWait longWait;
    private MainPage mainPage;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().window().setSize(new Dimension(1920, 1080));
        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testContinueButton() {
        mainPage.setCookieAgreement();
        mainPage.open();
        mainPage.waitForPayBlock();
        mainPage.fillPaymentForm("297777777", "100", "test@test.com");
        mainPage.clickContinue();
        WebElement paymentIframe = mainPage.getPaymentIframe();
        driver.switchTo().frame(paymentIframe);
        Assertions.assertEquals("100.00 BYN", mainPage.getPaymentAmountText(), "Некорректная сумма в окне оплаты");
        Assertions.assertEquals("Оплатить 100.00 BYN", mainPage.getPayButtonText().replaceAll("\\s+", " ").trim(), "Некорректный текст кнопки оплаты");
        String payDescriptionText = mainPage.getPayDescriptionText();
        Assertions.assertTrue(
            payDescriptionText.contains("Оплата: Услуги связи") && payDescriptionText.contains("375297777777"),
            "Некорректное описание оплаты: " + payDescriptionText
        );
        Assertions.assertEquals("Номер карты", mainPage.getCardNumberPlaceholder(), "Некорректный плейсхолдер номера карты");
        String expiryPlaceholder = mainPage.getCardExpiryPlaceholder();
        if ("MM / YY".equals(expiryPlaceholder)) {
            Assertions.assertEquals("MM / YY", expiryPlaceholder, "Некорректный плейсхолдер срока действия");
        } else {
            Assertions.assertEquals("Срок действия", expiryPlaceholder, "Некорректный label срока действия");
        }
        String holderPlaceholder = mainPage.getCardHolderPlaceholder();
        if (holderPlaceholder == null || holderPlaceholder.isEmpty()) {
            Assertions.assertEquals("Имя и фамилия на карте", holderPlaceholder, "Некорректный label имени держателя");
        } else {
            Assertions.assertEquals("Имя и фамилия на карте", holderPlaceholder, "Некорректный плейсхолдер имени держателя");
        }
        String cvcPlaceholder = mainPage.getCvcPlaceholder();
        if (cvcPlaceholder == null || cvcPlaceholder.isEmpty()) {
            Assertions.assertEquals("CVC", cvcPlaceholder, "Некорректный label CVC");
        } else {
            Assertions.assertEquals("CVC", cvcPlaceholder, "Некорректный плейсхолдер CVC");
        }
        Assertions.assertEquals(3, mainPage.getPaymentIconsCount(), "Некорректное количество иконок платёжных систем");
        WebElement payDescription = mainPage.getPayDescription();
        boolean textAppeared = wait.until(driver ->
            payDescription.getText() != null && payDescription.getText().contains("Оплата: Услуги связи Номер:375297777777")
        );
        Assertions.assertTrue(
            textAppeared,
            "Модальное окно оплаты не появилось или текст не совпадает"
        );
        driver.switchTo().defaultContent();
        driver.navigate().refresh();
        mainPage.waitForPayBlock();
    }

    @Test
    public void testPaymentFormPlaceholders() {
        mainPage.setCookieAgreement();
        mainPage.open();
        mainPage.waitForPayBlock();
        Assertions.assertEquals(
            "Номер телефона",
            mainPage.getPhoneInputPlaceholder(),
            "Плейсхолдер поля телефона не совпадает с ожидаемым"
        );
        Assertions.assertEquals(
            "Сумма",
            mainPage.getSumInputPlaceholder(),
            "Плейсхолдер поля суммы не совпадает с ожидаемым"
        );
        Assertions.assertEquals(
            "E-mail для отправки чека",
            mainPage.getEmailInputPlaceholder(),
            "Плейсхолдер поля email не совпадает с ожидаемым"
        );
        mainPage.openSelect();
        mainPage.selectSecondOption();
        Assertions.assertEquals(
            "Номер абонента",
            mainPage.getInternetPhoneInputPlaceholder(),
            "Плейсхолдер поля телефона для 'Домашний интернет' не совпадает с ожидаемым"
        );
        Assertions.assertEquals(
            "Сумма",
            mainPage.getInternetSumInputPlaceholder(),
            "Плейсхолдер поля суммы для 'Домашний интернет' не совпадает с ожидаемым"
        );
        Assertions.assertEquals(
            "E-mail для отправки чека",
            mainPage.getInternetEmailInputPlaceholder(),
            "Плейсхолдер поля email для 'Домашний интернет' не совпадает с ожидаемым"
        );
        mainPage.openSelect();
        mainPage.selectThirdOption();
        Assertions.assertEquals(
            "Номер счета на 44",
            mainPage.getInstalmentAccountInputPlaceholder(),
            "Плейсхолдер поля номера счета для 'Рассрочка' не совпадает с ожидаемым"
        );
        Assertions.assertEquals(
            "Сумма",
            mainPage.getInstalmentSumInputPlaceholder(),
            "Плейсхолдер поля суммы для 'Рассрочка' не совпадает с ожидаемым"
        );
        Assertions.assertEquals(
            "E-mail для отправки чека",
            mainPage.getInstalmentEmailInputPlaceholder(),
            "Плейсхолдер поля email для 'Рассрочка' не совпадает с ожидаемым"
        );
        mainPage.openSelect();
        mainPage.selectFourthOption();
        Assertions.assertEquals(
            "Номер счета на 2073",
            mainPage.getArrearsAccountInputPlaceholder(),
            "Плейсхолдер поля номера счета для 'Задолженность' не совпадает с ожидаемым"
        );
        Assertions.assertEquals(
            "Сумма",
            mainPage.getArrearsSumInputPlaceholder(),
            "Плейсхолдер поля суммы для 'Задолженность' не совпадает с ожидаемым"
        );
        Assertions.assertEquals(
            "E-mail для отправки чека",
            mainPage.getArrearsEmailInputPlaceholder(),
            "Плейсхолдер поля email для 'Задолженность' не совпадает с ожидаемым"
        );
    }
} 
