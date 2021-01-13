package com.udacity.jwdnd.course1.cloudstorage;


import org.h2.mvstore.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {
    @FindBy(name = "firstName")
    private WebElement inputFirstName;

    @FindBy(name = "lastName")
    private WebElement inputLastName;

    @FindBy(name = "username")
    private WebElement inputUsername;

    @FindBy(name = "password")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "login-link")
    private WebElement loginLink;

    public SignUpPage(WebDriver driver)
    {
        PageFactory.initElements(driver,this);
    }

    public void registerUser(String firstname,String lastname,String username,String password)
    {
        inputFirstName.sendKeys(firstname);
        inputLastName.sendKeys(lastname);
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        submitButton.click();
    }

    public void gotoLogin()
    {
        if(loginLink.isDisplayed())
        {
            loginLink.click();
        }
        else
        {
            System.out.println("Error occurred, re-try!");
        }
    }

}
