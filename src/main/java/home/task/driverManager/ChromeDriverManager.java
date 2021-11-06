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

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

public class ChromeDriverManager extends DriverManager {

    private final String DRIVER_PATH = "src\\main\\resources\\drivers\\chromedriver.exe";

    private ChromeDriverService chService;

    @Override
    public void startService() {
        if (null == chService) {
            try {
                chService = new ChromeDriverService.Builder()
                        .usingDriverExecutable(new File(DRIVER_PATH))
                        .usingAnyFreePort()
                        .build();
                chService.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stopService() {
        if (null != chService && chService.isRunning())
            chService.stop();
    }

    @Override
    public void createDriver(boolean remote) throws MalformedURLException {
        if (remote) {
            driver = new RemoteWebDriver(URI.create("remoteUrl").toURL(), getCapabilities());
        } else {
            driver = new ChromeDriver(chService, getCapabilities());
        }
    }

    private DesiredCapabilities getCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setAcceptInsecureCerts(true);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        options.addArguments("start-maximized");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return capabilities;
    }
}
