package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open() {
        driver.get("https://www.mts.by/");
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public void setCookieAgreement() {
        driver.get("https://www.mts.by/404");
        driver.manage().addCookie(new Cookie("BITRIX_SM_COOKIES_AGREEMENT", "yes", ".mts.by", "/", null));
    }

    public void waitForPayBlock() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("section.pay")));
    }

    public String getPayBlockTitle() {
        WebElement blockTitle = driver.findElement(By.cssSelector("section.pay h2"));
        return blockTitle.getText().replaceAll("\\s+", " ").trim();
    }

    public List<WebElement> getPaymentLogos() {
        return driver.findElements(By.xpath(
            "//section[contains(@class,'pay')]//img[contains(@src,'visa') or contains(@src,'mastercard') or contains(@src,'belkart') or contains(@src,'webpay') or contains(@src,'bepaid')]"
        ));
    }

    public WebElement getMoreLink() {
        return driver.findElement(By.xpath(
            "//section[contains(@class,'pay')]//a[contains(text(),'Подробнее о сервисе')]"
        ));
    }

    public void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    public void clickMoreLink() {
        WebElement moreLink = getMoreLink();
        scrollTo(moreLink);
        moreLink.click();
    }

    public WebElement getPhoneInput() {
        return driver.findElement(By.xpath("//form[@id='pay-connection']//input[@id='connection-phone']"));
    }

    public WebElement getSumInput() {
        return driver.findElement(By.xpath("//form[@id='pay-connection']//input[@id='connection-sum']"));
    }

    public WebElement getEmailInput() {
        return driver.findElement(By.xpath("//form[@id='pay-connection']//input[@id='connection-email']"));
    }

    public WebElement getContinueButton() {
        return driver.findElement(By.xpath("//form[@id='pay-connection']//button[contains(text(),'Продолжить')]"));
    }

    public void fillPaymentForm(String phone, String sum, String email) {
        WebElement phoneInput = getPhoneInput();
        scrollTo(phoneInput);
        phoneInput.clear();
        phoneInput.sendKeys(phone);
        WebElement sumInput = getSumInput();
        sumInput.clear();
        sumInput.sendKeys(sum);
        WebElement emailInput = getEmailInput();
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void clickContinue() {
        WebElement continueBtn = getContinueButton();
        scrollTo(continueBtn);
        continueBtn.click();
    }

    public WebElement getPaymentIframe() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("iframe.bepaid-iframe")));
    }

    public WebElement getPayDescription() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".pay-description__text")));
    }

    public String getPhoneInputPlaceholder() {
        return getPhoneInput().getAttribute("placeholder");
    }

    public String getSumInputPlaceholder() {
        return getSumInput().getAttribute("placeholder");
    }

    public String getEmailInputPlaceholder() {
        return getEmailInput().getAttribute("placeholder");
    }

    public WebElement getSelectHeader() {
        return driver.findElement(By.cssSelector(".select__header"));
    }

    public void openSelect() {
        WebElement selectHeader = getSelectHeader();
        scrollTo(selectHeader);
        selectHeader.click();
    }

    private void waitForSelectListVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".select__list[style*='opacity: 1']")));
    }

    public void selectSecondOption() {
        waitForSelectListVisible();
        List<WebElement> options = wait.until(d -> d.findElements(By.cssSelector(".select__list .select__item")));
        if (options.size() > 1) {
            options.get(1).click();
        } else {
            throw new IllegalStateException("В выпадающем списке недостаточно опций");
        }
    }

    public void selectThirdOption() {
        waitForSelectListVisible();
        List<WebElement> options = wait.until(d -> d.findElements(By.cssSelector(".select__list .select__item")));
        if (options.size() > 2) {
            options.get(2).click();
        } else {
            throw new IllegalStateException("В выпадающем списке недостаточно опций");
        }
    }

    public void selectFourthOption() {
        waitForSelectListVisible();
        List<WebElement> options = wait.until(d -> d.findElements(By.cssSelector(".select__list .select__item")));
        if (options.size() > 3) {
            options.get(3).click();
        } else {
            throw new IllegalStateException("В выпадающем списке недостаточно опций");
        }
    }

    public WebElement getInternetPhoneInput() {
        return driver.findElement(By.xpath("//form[@id='pay-internet']//input[@id='internet-phone']"));
    }

    public WebElement getInternetSumInput() {
        return driver.findElement(By.xpath("//form[@id='pay-internet']//input[@id='internet-sum']"));
    }

    public WebElement getInternetEmailInput() {
        return driver.findElement(By.xpath("//form[@id='pay-internet']//input[@id='internet-email']"));
    }

    public String getInternetPhoneInputPlaceholder() {
        return getInternetPhoneInput().getAttribute("placeholder");
    }

    public String getInternetSumInputPlaceholder() {
        return getInternetSumInput().getAttribute("placeholder");
    }

    public String getInternetEmailInputPlaceholder() {
        return getInternetEmailInput().getAttribute("placeholder");
    }

    public WebElement getInstalmentAccountInput() {
        return driver.findElement(By.xpath("//form[@id='pay-instalment']//input[@id='score-instalment']"));
    }

    public WebElement getInstalmentSumInput() {
        return driver.findElement(By.xpath("//form[@id='pay-instalment']//input[@id='instalment-sum']"));
    }

    public WebElement getInstalmentEmailInput() {
        return driver.findElement(By.xpath("//form[@id='pay-instalment']//input[@id='instalment-email']"));
    }

    public String getInstalmentAccountInputPlaceholder() {
        return getInstalmentAccountInput().getAttribute("placeholder");
    }

    public String getInstalmentSumInputPlaceholder() {
        return getInstalmentSumInput().getAttribute("placeholder");
    }

    public String getInstalmentEmailInputPlaceholder() {
        return getInstalmentEmailInput().getAttribute("placeholder");
    }

    public WebElement getArrearsAccountInput() {
        return driver.findElement(By.xpath("//form[@id='pay-arrears']//input[@id='score-arrears']"));
    }

    public WebElement getArrearsSumInput() {
        return driver.findElement(By.xpath("//form[@id='pay-arrears']//input[@id='arrears-sum']"));
    }

    public WebElement getArrearsEmailInput() {
        return driver.findElement(By.xpath("//form[@id='pay-arrears']//input[@id='arrears-email']"));
    }

    public String getArrearsAccountInputPlaceholder() {
        return getArrearsAccountInput().getAttribute("placeholder");
    }

    public String getArrearsSumInputPlaceholder() {
        return getArrearsSumInput().getAttribute("placeholder");
    }

    public String getArrearsEmailInputPlaceholder() {
        return getArrearsEmailInput().getAttribute("placeholder");
    }

    public String getPaymentAmountText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'BYN')]"))).getText();
    }

    public String getPayButtonText() {
        return driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/app-card-page/div/div[1]/button")).getText();
    }

    public String getPayDescriptionText() {
        return driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/div/div[2]")).getText();
    }

    public String getCardNumberPlaceholder() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[contains(@autocomplete, 'cc-number')]")
        ));
        String placeholder = input.getAttribute("placeholder");
        if (placeholder != null && !placeholder.isEmpty()) {
            return placeholder;
        }
        try {
            WebElement label = input.findElement(By.xpath("../label"));
            return label.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getCardExpiryPlaceholder() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[contains(@autocomplete, 'cc-exp')]")));
        String placeholder = input.getAttribute("placeholder");
        if (placeholder != null && !placeholder.isEmpty()) {
            return placeholder;
        }
        try {
            WebElement label = input.findElement(By.xpath("../label"));
            return label.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getCardHolderPlaceholder() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[contains(@autocomplete, 'cc-name')]")));
        String placeholder = input.getAttribute("placeholder");
        if (placeholder != null && !placeholder.isEmpty()) {
            return placeholder;
        }
        try {
            WebElement label = input.findElement(By.xpath("../label"));
            return label.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getCvcPlaceholder() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[contains(@autocomplete, 'cc-csc') or contains(@name, 'verification_value') or contains(@formcontrolname, 'cvc')]")
        ));
        String placeholder = input.getAttribute("placeholder");
        if (placeholder != null && !placeholder.isEmpty()) {
            return placeholder;
        }
        try {
            WebElement label = input.findElement(By.xpath("../label"));
            return label.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public int getPaymentIconsCount() {
        return driver.findElements(By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/app-card-page/div/div[1]/app-card-input/form/div[1]/div[1]/app-input/div/div/div[2]/div/div/img")).size();
    }
} 
