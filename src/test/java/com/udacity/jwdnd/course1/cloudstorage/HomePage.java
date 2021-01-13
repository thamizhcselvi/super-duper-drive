package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.h2.mvstore.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class HomePage {

    @FindBy(name = "logout-button")
    private WebElement logout;

    @FindBy(id="nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id="nav-credential-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "addNewNote")
    private WebElement buttonAddNote;

    @FindBy(id = "addnewCred")
    private WebElement buttonAddCredential;

    @FindBy(css = "#note-title")
    private WebElement noteTitle;

    @FindBy(css = "#note-description")
    private WebElement noteDescription;

    @FindBy(css = "#note-save-changes")
    private WebElement saveNote;

    @FindBy(css = "#textNoteTitle")
    private WebElement textNotetitle;

    @FindBy(id = "button-Edit")
    private WebElement editNoteId;

    @FindBy(css = "#button-Delete")
    private WebElement deleteNoteId;

    @FindBy(id = "table-trId")
    private WebElement tableTrId;

    @FindBy(name="url")
    private WebElement modalInputCredentialUrl;

    @FindBy(name="username")
    private WebElement modalInputCredentialUsername;

    @FindBy(name="password")
    private WebElement modalInputCredentialPassword;

    @FindBy(css = "#cred-save-changes")
    private WebElement saveCredential;

    @FindBy(css = "#outputUrl")
    private WebElement tableCredentialUrl;

    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

    @FindBy(css = "#button-Edit-cred")
    private WebElement editCredentialId;

    @FindBy(id = "button-Delete-Cred")
    private WebElement deleteCredentialId;

    private WebDriver driver;

    private WebDriverWait wait;

    public HomePage(WebDriver webDriver)
    {
        PageFactory.initElements(driver,this);
        this.driver = webDriver;
        this.wait = new WebDriverWait(driver,30);
    }

    public void gotoNoteTab()
    {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();
    }

    public void gotoCredTab()
    {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credential-tab"))).click();
    }

    public void addNote()
    {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("addNewNote"))).click();
    }

    public void addCred()
    {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("addnewCred"))).click();
    }

    public void createOrEditNote(String title,String description)
    {
        String existing_title = String.valueOf(By.cssSelector("#note-title"));
        if(existing_title.length()>=0)
        {
            driver.findElement(By.cssSelector("#note-title")).clear();
            driver.findElement(By.cssSelector("#note-description")).clear();
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#note-title"))).sendKeys(title);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#note-description"))).sendKeys(description);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#note-save-changes"))).click();
    }

    public void createOrEditCred(String url,String username,String password)
    {
        String existing_cred = String.valueOf(By.cssSelector("#credential-url"));
        if(existing_cred.length()>=0)
        {
            driver.findElement(By.cssSelector("#credential-url")).clear();
            driver.findElement(By.cssSelector("#credential-username")).clear();
            driver.findElement(By.cssSelector("#credential-password")).clear();
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#credential-url"))).sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#credential-username"))).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#credential-password"))).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credSubmit"))).click();
    }

    public void deleteNote() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button-Delete"))).click();
    }

    public int noteCount(){
        int count = driver.findElements(By.id("note-title")).size();
        return count;
    }

    public void clickEditNote()
    {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button-Edit"))).click();
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("logout-button"))).click();
    }

    public String getTableNoteTitle()
    {
       return  wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#textNoteTitle"))).getText();
    }



    public void waitForVisibility(WebElement element) throws Error
    {
        new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOf(element));
    }


}
