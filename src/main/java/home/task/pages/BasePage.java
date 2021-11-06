/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 * Copyright (c) 2019-2020 Grid Solutions of GENERAL ELECTRIC RENEWABLE ENERGY Inc.
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE. The methods and techniques described here in are considered
 * trade secrets and/or confidential. Reproduction or distribution, in whole or in part, is forbidden except by
 * express written permission of GE.
 */

package home.task.pages;


import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@Slf4j
public class BasePage {

    protected WebDriver driver = null;
    private final int WAIT_TIME = 10;

    public void clickOnlLinkWithText(String text) {
        click(By.xpath(String.format("//a[contains(text(), '%s')]", text)));
    }

    protected WebElement waitVisibility(By locator, int time) {
        WebDriverWait wait = new WebDriverWait(driver, time);
        WebElement element = null;
        try {
            element = wait.until(visibilityOfElementLocated(locator));

        } catch (TimeoutException ignored) {
            log.error("Element {} isn't visible after {} second(s)", locator, time);
        }
        return element;
    }

    protected WebElement waitVisibility(By locator) {
        return waitVisibility(locator, WAIT_TIME);
    }

    protected WebElement waitToBeClickable(By locator) {
        return waitToBeClickable(locator, WAIT_TIME);
    }

    protected WebElement waitToBeClickable(By locator, int time) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);
        WebElement element = null;
        try {
            element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException | StaleElementReferenceException exception) {
            log.error("Element {} isn't clickable after {} second(s)", locator, time);
        }

        return element;
    }

    protected void click(By locator) {
        waitToBeClickable(locator).click();
    }
}
