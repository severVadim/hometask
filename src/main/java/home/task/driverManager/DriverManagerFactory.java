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

public class DriverManagerFactory {

    public static DriverManager getManager(DriverType type) {

        DriverManager driverManager;

        switch (type) {
            case CHROME:
                driverManager = new ChromeDriverManager();
                break;
            default:
                driverManager = new ChromeDriverManager();
                break;
        }
        return driverManager;
    }
}
