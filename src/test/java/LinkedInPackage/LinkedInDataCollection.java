
package LinkedInPackage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LinkedInDataCollection {
	public static WebDriver driver;
	
	public static void moveToElement(WebDriver driver, WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	public static void writeInToExcelFile(String data, int rowNo, int colNo) {
       
		try {
			// Open the existing Excel file using FileInputStream
			FileInputStream fileInputStream = new FileInputStream(
					"LinkExcelSheet/LinkedIn Microsoft Excel Worksheet.xlsx");
			Workbook workbook = new XSSFWorkbook(fileInputStream);

			// Get the sheet you want to write data into (e.g., the first sheet)
			Sheet sheet = workbook.getSheetAt(0);

			// Create or update a cell at a specific row and column

			Row row = sheet.getRow(rowNo);
			if (row == null) {
				row = sheet.createRow(rowNo);
			}
			Cell cell = row.createCell(colNo);
			cell.setCellValue(data);

			// Save the changes to the Excel file
			FileOutputStream outputStream = new FileOutputStream(
					"LinkExcelSheet/LinkedIn Microsoft Excel Worksheet.xlsx");
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();

			System.out.println("Data written successfully!");
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
 
	public static WebDriver setChromeDriver() {
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--headless");
		
		return new ChromeDriver(options);
	}
            //click using javascript //
	public static void clickElementWithJS(WebElement element) {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript("arguments[0].click();", element);
	}

	public static void waitForSeconds(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void setBrowser(String url) {
		driver = setChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		waitForSeconds(8);
	}

	@Test
	public void extractDataFromLinkedIn() {
		String file = "LinkExcelSheet/LinkedIn Microsoft Excel Worksheet.xlsx";
		
		String url = "https://www.linkedin.com/login?fromSignIn=true&trk=guest_homepage-basic_nav-header-signin";
		String userName = "pranay.shelkeitechcloud@gmail.com";
		String password = "Linkedin@123466";

		LinkedInDataCollection.setBrowser(url);

		WebElement loginusername = driver.findElement(By.xpath("//Input[@id='username']"));
		loginusername.click();
		loginusername.sendKeys(userName);
		//waitForSeconds(2);

		WebElement loginPwd = driver.findElement(By.xpath("//Input[@id='password']"));
		loginPwd.click();
		loginPwd.sendKeys(password); 
		//waitForSeconds(2);

		WebElement LoginButton = driver.findElement(By.xpath("//button[@type='submit']"));
		LoginButton.click();
		waitForSeconds(10);
 		
		int r = 1;
		int c = 0;
		List<WebElement> listOfUsers = driver.findElements(By.xpath("//h3[contains(@class,'msg-conversation-listitem__participant-names msg-conversation-card__participant-names truncate t-14 t-black')]"));

		for (WebElement webElement : listOfUsers) {
			
			moveToElement(driver, webElement); //for scroll down
			waitForSeconds(1);
			webElement.click();
			waitForSeconds(10);
			//List<WebElement> userMessages = driver.findElements(By.xpath("//div[@class='msg-s-event__content']//p"));
			
			List<WebElement> userMessages = driver.findElements(By.xpath("//ul[@class='msg-s-message-list-content list-style-none full-width mbA']//li"));
			
			WebElement dispalyUserName = driver.findElement(By.xpath("//a[contains(@href,'/in/') and @class='ember-view']//span"));
			//System.out.println("Displayed User Name : " + dispalyUserName.getText());
			LinkedInDataCollection.writeInToExcelFile(dispalyUserName.getText(), r, c);
			c++;
			//System.out.println("Size of messages : " + userMessages.size());
			LinkedInDataCollection.writeInToExcelFile(String.valueOf(userMessages.size()), r, c);
			c++;
			String msg = "";
			for (WebElement webElement2 : userMessages) {
				//System.out.println(webElement2.getAttribute("textContent"));
				//msg = msg + "\n" + webElement2.getAttribute("textContent");
				
				if(webElement2.getAttribute("innerText").contains("Reply to")) {
					System.out.println("Reply to if conditions");
					break;
				}
				
				String str =  webElement2.getAttribute("innerText").trim();
				if(str.contains("sent the following message")){
					msg = msg +"\n"+"\n";
				}	
				
				msg = msg + webElement2.getAttribute("innerText").trim()+"\n";
				
				
			}
			
			LinkedInDataCollection.writeInToExcelFile(msg, r, c);
			c++;
			//waitForSeconds(2);
			dispalyUserName = driver.findElement(By.xpath("//a[contains(@href,'/in/') and @class='ember-view']//span"));
			dispalyUserName.click();
			waitForSeconds(7);
			//System.out.println("User URL : " + driver.getCurrentUrl());
			LinkedInDataCollection.writeInToExcelFile(driver.getCurrentUrl(), r, c);
			c++;
			WebElement closeBtn = driver.findElement(By.xpath("//button[@class='msg-overlay-bubble-header__control artdeco-button artdeco-button--circle artdeco-button--muted artdeco-button--1 artdeco-button--tertiary ember-view']//li-icon[@type='close']"));
			closeBtn.click();
			WebElement DvlprPostn = driver.findElement(By.xpath("//h1[@class='text-heading-xlarge inline t-24 v-align-middle break-words']//..//.. //div[@class='text-body-medium break-words']"));
			//System.out.println("Displayed Devloper Position : " + DvlprPostn.getText());
			LinkedInDataCollection.writeInToExcelFile(DvlprPostn.getText(), r, c);
			c++;
			//waitForSeconds(3);

			WebElement DvlprExp = driver.findElement(By.xpath("//div[@class='display-flex flex-column full-width align-self-center']//div[contains(@class,'display-flex align-items-center')]//span"));
			//System.out.println("Displayed Devloper Experience : " + DvlprExp.getText());
			LinkedInDataCollection.writeInToExcelFile(DvlprExp.getText(), r, c); 

			//System.out.println(	"<!!------------------------------------------------------------------------------------!!>");
			//waitForSeconds(3);

			List<WebElement> ClkOnLatstCompany = driver.findElements(By.xpath("//a[@data-field='experience_company_logo']"));
			if(ClkOnLatstCompany.size() > 0) {
			clickElementWithJS(ClkOnLatstCompany.get(0));
			waitForSeconds(10);
			WebElement ClkOnAbout = driver.findElement(By.xpath("//a[text()='About']"));
			clickElementWithJS(ClkOnAbout);
			waitForSeconds(6);

			List<WebElement> aboutCompanyDetails = driver.findElements(By.xpath("//dd"));

			for (WebElement ele : aboutCompanyDetails) {
				//  System.out.println("Detail : " + ele.getText());
				LinkedInDataCollection.writeInToExcelFile(ele.getText(), r, c);
				c++;
			}
			
			List<WebElement> location = driver.findElements(By.xpath("//p[@dir='ltr']"));

			// when location present then print otherwise not print 
			if (location.size() > 0)                                                  
				LinkedInDataCollection.writeInToExcelFile(location.get(0).getText(), r, c);
			else
				System.out.println("Location not found");
			}
			else {
			System.out.println("No Experience Added");}
			
			r++; 
			c = 0;
		}

		waitForSeconds(10);
		driver.quit();

	}
}
