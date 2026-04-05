package com.epam.automation.business.service;

import com.epam.automation.business.pages.LaunchesPage;
import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import com.epam.automation.core.utils.WaitUtil;

import java.util.ArrayList;
import java.util.List;

public class LaunchesService {
    private static final ILogger logger = LoggerFactory.getLogger(LaunchesService.class);

    private final LaunchesPage launchesPage;

    public LaunchesService(LaunchesPage launchesPage) {
        this.launchesPage = launchesPage;
    }

    public boolean isLaunchesListDisplayedCorrectly(String[][] expectedSortedLaunches) {
        boolean isLaunchesListDisplayed = launchesPage.isLaunchesListDisplayed();
        if (!isLaunchesListDisplayed) {
            logger.error("Launches list is not displayed");
            return false;
        }

        int actualLaunchesCount = launchesPage.getLaunchesCount();
        if (actualLaunchesCount != expectedSortedLaunches.length) {
            logger.error("Launches list has different sizes");
            return false;
        }

        return true;
    }

    private boolean isLaunchesListAbleToBeSorted() {
        if (launchesPage.totalLaunchesEmpty()) {
            logger.warn("Launches list is empty");
            return false;
        }

        int launchesCount = launchesPage.getLaunchesCount();
        if (launchesCount < 2) {
            logger.warn("Only {} launch found, need at least 2 to sort", launchesCount);
            return false;
        }

        return true;
    }

    public boolean isLaunchesListSortedByMostRecent(String[][] expectedSortedLaunches) {
        if (!isLaunchesListAbleToBeSorted()) {
            logger.error("Launches list is not able to be sorted");
            return false;
        }

        if (!isLaunchesListDisplayedCorrectly(expectedSortedLaunches)) {
            logger.error("Launches list is not displayed correctly");
            return false;
        }

        List<String> actualStartTimes = launchesPage.getAllStartTimes();
        List<String> expectedTimes = new ArrayList<>();
        for (String[] launch : expectedSortedLaunches) {
            expectedTimes.add(launch[1]);
        }

        logger.info("Expected start times: {}", expectedTimes);
        logger.info("Actual start times: {}", actualStartTimes);

        boolean result = expectedTimes.equals(actualStartTimes);
        logger.info("Start times match: {}", result);

        return result;
    }

    public boolean isLaunchesListSortedByName(String[][] expectedSortedLaunches) {
        if (!isLaunchesListAbleToBeSorted()) {
            logger.error("Launches list is not able to be sorted");
            return false;
        }

        if (!isLaunchesListDisplayedCorrectly(expectedSortedLaunches)) {
            logger.error("Launches list is not displayed correctly");
            return false;
        }

        List<String> actualLaunchNames = launchesPage.getAllLaunchesNames();
        List<String> expectedNames = new ArrayList<>();
        for (String[] launch : expectedSortedLaunches) {
            expectedNames.add(launch[0]);
        }

        logger.info("Expected launch names: {}", expectedNames);
        logger.info("Actual launch names: {}", actualLaunchNames);

        boolean result = expectedNames.equals(actualLaunchNames);
        logger.info("Start launch names: {}", result);

        return result;
    }

    public void sortLaunchesByName() {
        launchesPage.clickNameColumnHeaderToSort();
        for (int i = 0; i < launchesPage.getLaunchesCount(); i++) {
            WaitUtil.waitForElementVisible(launchesPage.getTotalLaunches().get(i));
        }
        logger.info("Launches sorted by name. First launch: {}", launchesPage.getAllLaunchesNames().getFirst());
    }

}
