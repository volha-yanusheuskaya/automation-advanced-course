package com.epam.automation.tests.data;

import org.testng.annotations.DataProvider;

public class LaunchTestDataProvider {

    @DataProvider(name = "launchDataByDefault")
    public Object[][] provideDefaultLaunchData() {
        return new Object[][]{{LaunchTestData.getLaunchesSortedByMostRecent()}};
    }

    @DataProvider(name = "launchDataByName")
    public Object[][] provideLaunchDataByName() {
        return new Object[][]{{LaunchTestData.getLaunchesSortedByName()}};
    }
}
