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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.net.CacheRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

class CredTest {

    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private static SignUpPage signup;
    private static LoginPage login;
    private static HomePage home;
    private static ResultPage result;
    public String baseURL;
    WebDriverWait wait = new WebDriverWait(driver, 5);
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
    public static void AfterAll() {
        driver.quit();
        driver = null;
    }

    @Test
    public void A_Test() throws InterruptedException {

        driver.get(baseURL+"/signup");
        signup.registerUser("Alex","Bob",username,password);
        driver.get(baseURL+"/login");
        login.login(username,password);
        driver.get(baseURL+"/home");
        home.gotoCredTab();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        home.addCred();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        home.createOrEditCred("sampleurl", "sampleusername", "samplepassword");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(baseURL + "/home");
        home.gotoCredTab();

        int index = 1;
        WebElement table = driver.findElement(By.id("credentialTable"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        WebElement tr = rows.get(index);
        List<WebElement> thList = tr.findElements(By.tagName("td"));
        Boolean result = wait.until(ExpectedConditions.textToBePresentInElement(thList.get(1), "sampleusername"));
        Assertions.assertTrue(result);
        String pwd = thList.get(1).getText();

        /* Check whether password is encrypted*/
        Assertions.assertNotEquals(pwd, "samplePassword");

        home.logout();
    }

    @Test
    public void B_Test() throws InterruptedException
    {

        /*Edit password test case*/

        String edittedUsername = "testUsername";
        driver.get(baseURL+"/login");
        login.login(username,password);

        Thread.sleep(3000);
        home.gotoCredTab();
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("button-Edit-cred")));
        button.click();

        Thread.sleep(3000);
        WebElement uname = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username")));
        uname.clear();
        uname.sendKeys(edittedUsername);

        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(By.id("credSubmit")));
        submit.click();
        Thread.sleep(3000);

        driver.get(baseURL + "/home");
        home.gotoCredTab();

        Thread.sleep(3000);

        int index = 1;
        WebElement table = driver.findElement(By.id("credentialTable"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        WebElement tr = rows.get(index);

        List<WebElement> thList = tr.findElements(By.tagName("td"));

        Boolean result = wait.until(ExpectedConditions.textToBePresentInElement(thList.get(1), edittedUsername));
        Assertions.assertTrue(result);

        String insertedUsername = thList.get(1).getText();
        Assertions.assertEquals(insertedUsername, edittedUsername);

        home.logout();

    }

    @Test
    public void C_Test() throws InterruptedException
    {
        /*
        Deleting the credentials and verifying the result
         */

        driver.get(baseURL+"/login");
        login.login(username,password);

        home.gotoCredTab();
        WebElement delete = driver.findElement(By.id("button-Delete-Cred"));
        delete.click();

        driver.get(baseURL+"/home");
        home.gotoCredTab();

        WebElement table = driver.findElement(By.id("credentialTable"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));
        Thread.sleep(3000);

        Assertions.assertEquals(rows.size(), 1);
        home.logout();
    }
}



