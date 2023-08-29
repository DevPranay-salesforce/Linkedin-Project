package LinkedInPackage;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LinkedInDataCollection {
	public static WebDriver driver;

	public static WebDriver setChromeDriver() {
		WebDriverManager.chromedriver().setup();
		return new ChromeDriver();
	}

	public static void clickElementWithJS(WebElement element) {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript("arguments[0].click();", element);
	}

	public static void waitForSeconds(int sec) {

		try {
			Thread.sleep(sec * 1000);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setBrowser(String url) {
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		driver = LinkedInDataCollection.setChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		waitForSeconds(5);
	}

	public static void main(String[] args) {

		
        String url = "https://www.linkedin.com/login?fromSignIn=true&trk=guest_homepage-basic_nav-header-signin";
        String userName = "pranay.shelkeitechcloud@gmail.com";
		String password = "Linkedin@123466";
		LinkedInDataCollection.setBrowser(url);
		

		WebElement loginusername = driver.findElement(By.xpath("//Input[@id='username']"));
		loginusername.click();
		loginusername.sendKeys(userName);
		waitForSeconds(2);

		WebElement loginPwd= driver.findElement(By.xpath("//Input[@id='password']"));
		loginPwd.click();
		loginPwd.sendKeys(password);
		waitForSeconds(2);

		WebElement LoginButton= driver.findElement(By.xpath("//button[@type='submit']"));
		LoginButton.click();
		waitForSeconds(10);
		
		
		List<WebElement> listOfMessages = driver.findElements(By.xpath("//h3[contains(@class,'msg-conversation-listitem__participant-names msg-conversation-card__participant-names truncate t-14 t-black')]"));
	
		for (WebElement webElement : listOfMessages) {
			webElement.click();
			waitForSeconds(10);	
			List<WebElement> userMessages = driver.findElements(By.xpath("//div[@class='msg-s-event__content']//p"));
			WebElement dispalyUserName = driver.findElement(By.xpath("//a[contains(@href,'/in/') and @class='ember-view']//span"));
			System.out.println("Displayed User Name : "+dispalyUserName.getText());
			System.out.println("Size of messages : "+userMessages.size());
			for (WebElement webElement2 : userMessages) {
				System.out.println(webElement2.getAttribute("textContent"));
			}
			waitForSeconds(2);	
		    dispalyUserName = driver.findElement(By.xpath("//a[contains(@href,'/in/') and @class='ember-view']//span"));
		    dispalyUserName.click();
			waitForSeconds(5);	
		    WebElement closeBtn = driver.findElement(By.xpath("//button[@class='msg-overlay-bubble-header__control artdeco-button artdeco-button--circle artdeco-button--muted artdeco-button--1 artdeco-button--tertiary ember-view']//li-icon[@type='close']"));
	        closeBtn.click();
	        WebElement DvlprPostn = driver.findElement(By.xpath("//h1[@class='text-heading-xlarge inline t-24 v-align-middle break-words']//..//.. //div[@class='text-body-medium break-words']"));
	        System.out.println("Displayed Devloper Position : "+DvlprPostn.getText());
	        waitForSeconds(3);	
	    	
	        WebElement DvlprExp = driver.findElement(By.xpath("//div[@class='display-flex flex-column full-width align-self-center']//div[contains(@class,'display-flex align-items-center')]//span"));
	        System.out.println("Displayed Devloper Experience : "+DvlprExp.getText());
	        System.out.println("<!!------------------------------------------------------------------------------------!!>");
	}
		
	                        
	    
		
		//waitForSeconds(10);
      // driver.quit();
 

	}
}
