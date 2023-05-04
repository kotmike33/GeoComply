package Gmail;

import Config.ConfigVariables;
import MainSelenium.SeleniumStarter;
import io.qameta.allure.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import java.time.Duration;

public class Gmail extends SeleniumStarter {
@FindBy(xpath = "//div[@role='button'][@style='user-select: none']")
private static WebElement newMailButton;
@FindBy(xpath = "//span[text()='To']/parent::div/parent::td/following-sibling::td//input")
private static WebElement receiverEmailInput;
@FindBy(xpath = "//input[@name='subjectbox']")
private static WebElement subjectInput;
@FindBy(xpath = "//div[@role='textbox'][contains(@aria-label, 'essage')]")
private static WebElement messageInput;
@FindBy(xpath = "//div[@role='button'][text()='Send']")
private static WebElement sendButton;
@FindBy(xpath = "//td/div[2]//span[@email='kotmike33@gmail.com']")
private static WebElement lastEmailFromMe;
@FindBy(xpath = "//div[@data-message-id]//span[@email][@name]")
private static WebElement senderOfMail;
@FindBy(xpath = "//div[@role='main']//h2")
private static WebElement subjectOfMail;
@FindBy(xpath = "//div[@data-message-id]//div[@class='']")
private static WebElement messageOfMail;
	@Step("Test 1")
	@Description("Sending an email")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void sendMail() throws Exception {
		driver.get(ConfigVariables.URL);
		PageFactory.initElements(driver, Gmail.class);
		newMailButton.click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='To']/parent::div/parent::td/following-sibling::td//input")));
		Allure.step("Sending Email keys to the inputs", () -> {
		receiverEmailInput.sendKeys(ConfigVariables.EMAIL);
		subjectInput.sendKeys(ConfigVariables.EMAIL_SUBJECT);
		messageInput.sendKeys(ConfigVariables.EMAIL_MESSAGE);
		});
		sendButton.click();
		Thread.sleep(2000);
		Reporter.log("Email sent");
	}
	@Step("Test 2")
	@Description("Verifying an email")
	@Severity(SeverityLevel.NORMAL)
	@Test(dependsOnMethods = { "sendMail" })
	public void verifyEmailReceived() {
		driver.navigate().refresh();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(lastEmailFromMe)));
		try {
			lastEmailFromMe.click();
		} catch (UnhandledAlertException e) {
			Alert alert = driver.switchTo().alert();
			System.out.println("Alert text: " + alert.getText());
			alert.dismiss();
			lastEmailFromMe.click();
		}
		Allure.step("Verifying Email data", () -> {
			Assert.assertEquals(senderOfMail.getAttribute("email"), ConfigVariables.EMAIL);
			Assert.assertEquals(subjectOfMail.getText(), ConfigVariables.EMAIL_SUBJECT);
			Assert.assertEquals(messageOfMail.getText(), ConfigVariables.EMAIL_MESSAGE);
		});
	}
}
