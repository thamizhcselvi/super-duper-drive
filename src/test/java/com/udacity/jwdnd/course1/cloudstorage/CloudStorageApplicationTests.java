package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.net.CacheRequest;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private static SignUpPage signup;
	private static LoginPage login;
	private static HomePage home;
	private static ResultPage result;
	public  String baseURL;
	String username = "user";
	String password = "userpassword";

	@Autowired
	private CredentialsService credentialsService;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		signup = new SignUpPage(driver);
		login = new LoginPage(driver);
		home = new HomePage(driver);
		result = new ResultPage(driver);
	}
	@BeforeEach
	public void beforeEach() {
		baseURL = "http://localhost:" + port;
	}

	@AfterAll
	public static void AfterAll()
	{
		driver.quit();
		driver = null;
	}



	@Test
	public void A_Test() throws InterruptedException {

		/* home assess test case */
		driver.get(baseURL+"/home");
		Assertions.assertEquals("Login",driver.getTitle());

		driver.get(baseURL+"/signup");

		signup.registerUser("Alex","Bob",username,password);

		login.login(username,password);
		driver.get(baseURL+"/home");
		Assertions.assertEquals("Home",driver.getTitle());
		home.logout();
		Thread.sleep(3000);
		Assertions.assertEquals("Login",driver.getTitle());
		Thread.sleep(3000);
		driver.get(baseURL+"/home");
		Assertions.assertNotEquals("Home",driver.getTitle());
	}

	@Test
	public void B_Test() throws InterruptedException {

		/* New Note test case */
		driver.get(baseURL + "/login");
		login.login(username, password);
		driver.get(baseURL + "/home");
		home.gotoNoteTab();
		home.addNote();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		home.createOrEditNote("noteTitle", "noteDescription");
		driver.get(baseURL + "/home");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		home.gotoNoteTab();
		Assertions.assertEquals("noteTitle", home.getTableNoteTitle());
		home.logout();
	}

	@Test
	public void C_Test() throws InterruptedException
	{
		/*Edit note test case*/
		driver.get(baseURL + "/login");
		login.login(username, password);
		driver.get(baseURL + "/home");
		home.gotoNoteTab();
		home.clickEditNote();
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		home.createOrEditNote("newTitle","newDescription");
		driver.get(baseURL+"/home");
		home.gotoNoteTab();
		Assertions.assertEquals("newTitle",home.getTableNoteTitle());
		home.logout();

	}

	@Test
	public void D_Test() throws InterruptedException {
		/*Delete note test case*/
		driver.get(baseURL + "/login");
		login.login(username, password);
		driver.get(baseURL+"/home");
		home.gotoNoteTab();
		home.deleteNote();
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		driver.get(baseURL+"/home");
		home.gotoNoteTab();
		Thread.sleep(3000);
		Assertions.assertEquals(1,home.noteCount());
		home.logout();
	}

}
