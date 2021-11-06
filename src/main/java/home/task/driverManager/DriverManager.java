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

package home.task.driverManager;

import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public abstract class DriverManager {

    protected WebDriver driver;

    protected abstract void startService();

    protected abstract void stopService();

    protected abstract void createDriver(boolean remote) throws MalformedURLException;

    public void quitDriver() {
        if (null != driver) {
            driver.quit();
            driver = null;
        }
    }

    public WebDriver getDriver(boolean remote) throws MalformedURLException {
        if (null == driver) {
            if (!remote) startService();
            createDriver(remote);
        }
        return driver;
    }
}
