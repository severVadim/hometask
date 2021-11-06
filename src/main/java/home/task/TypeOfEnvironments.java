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

package home.task;


public enum TypeOfEnvironments {
    LOCAL("/properties/local.properties");

    private String propertyFileName;

    TypeOfEnvironments(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    public String getPropertyFilePath() {
        return propertyFileName;
    }
}
