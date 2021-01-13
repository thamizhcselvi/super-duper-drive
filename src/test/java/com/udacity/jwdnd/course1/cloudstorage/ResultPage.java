package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;

public class ResultPage {

    @FindBy(id = "successHome")
    private WebElement successHome;

    @FindBy(id = "errorHome")
    private WebElement errorHome;

    private WebDriver driver;

    public ResultPage(WebDriver webDriver)
    {
        PageFactory.initElements(driver,this);
        this.driver = webDriver;
    }

    public void clickLinkByHref() {
        /*List<WebElement> anchors = driver.findElements(By.tagName("a"));
        Iterator<WebElement> i = anchors.iterator();

        while(i.hasNext()) {
            WebElement anchor = i.next();
            if(anchor.getAttribute("href").contains(href)) {
                anchor.click();
                break;
            }
        }*/
        //driver.findElement(By.partialLinkText("/home")).click();
    }


}
