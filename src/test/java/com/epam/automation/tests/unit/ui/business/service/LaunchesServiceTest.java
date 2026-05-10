package com.epam.automation.tests.unit.ui.business.service;

import com.epam.automation.ui.business.models.Launch;
import com.epam.automation.ui.business.pages.LaunchesPage;
import com.epam.automation.ui.business.service.LaunchesService;
import com.epam.automation.ui.core.utils.WaitUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LaunchesService Unit Tests")
class LaunchesServiceTest {

    @Mock
    LaunchesPage page;
    @InjectMocks
    LaunchesService service;

    @Test
    void totalEqualsSumOfPassedFailedSkipped() {
        Launch launch = Launch.builder()
                .name("L #1")
                .date("2026-01-01")
                .totalSteps(10)
                .passedSteps(7)
                .failedSteps(2)
                .skippedSteps(1)
                .build();
        assertThat(service.isTotalStepsEqualToSum(launch)).isTrue();
    }

    @Test
    void totalDoesNotEqualSumWhenMismatch() {
        Launch launch = Launch.builder()
                .name("L #1")
                .date("2026-01-01")
                .totalSteps(10)
                .passedSteps(5)
                .failedSteps(2)
                .skippedSteps(1)
                .build();
        assertThat(service.isTotalStepsEqualToSum(launch)).isFalse();
    }

    @Test
    void returnsFalseWhenLaunchesEmpty() {
        when(page.totalLaunchesEmpty()).thenReturn(true);
        assertThat(service.isLaunchesListSortedByMostRecent(new String[][]{{"x", "t"}}, 0)).isFalse();
    }

    @Test
    void returnsFalseWhenListNotDisplayed() {
        when(page.totalLaunchesEmpty()).thenReturn(false);
        when(page.isLaunchesListDisplayed()).thenReturn(false);
        assertThat(service.isLaunchesListSortedByMostRecent(new String[][]{{"x", "t"}}, 0)).isFalse();
    }

    @Test
    void returnsTrueWhenActualMatchesExpectedTime() {
        when(page.totalLaunchesEmpty()).thenReturn(false);
        when(page.isLaunchesListDisplayed()).thenReturn(true);
        when(page.getAllStartTimes()).thenReturn(List.of("2026-05-10 12:00:00"));
        String[][] expected = {{"Demo #1", "2026-05-10 12:00:00"}};
        assertThat(service.isLaunchesListSortedByMostRecent(expected, 0)).isTrue();
    }

    @Test
    void returnsFalseWhenActualDiffersFromExpectedTime() {
        when(page.totalLaunchesEmpty()).thenReturn(false);
        when(page.isLaunchesListDisplayed()).thenReturn(true);
        when(page.getAllStartTimes()).thenReturn(List.of("2026-05-10 12:00:00"));
        String[][] expected = {{"Demo #1", "2026-05-10 11:00:00"}};
        assertThat(service.isLaunchesListSortedByMostRecent(expected, 0)).isFalse();
    }

    @Test
    void verifySelectedLaunchesMatchesExpected() {
        when(page.getSelectedLaunches()).thenReturn(List.of("Demo #1", "Demo #2"));
        String[][] expected = {{"Demo #1", "..."}, {"Demo #2", "..."}};
        assertThat(service.verifySelectedLaunches(expected)).isTrue();
    }

    @Test
    void verifySelectedLaunchesFailsOnMismatch() {
        when(page.getSelectedLaunches()).thenReturn(List.of("Demo #1"));
        String[][] expected = {{"Demo #2", "..."}};
        assertThat(service.verifySelectedLaunches(expected)).isFalse();
    }

    @Test
    void clickCompareLaunchesClicksActionsThenCompare() {
        service.clickCompareLaunches();
        InOrder order = inOrder(page);
        order.verify(page).clickActionsButton();
        order.verify(page).clickCompareButton();
    }

    @Test
    void returnsIgnoresToastComponentDisplayedWhenNameSorted() {
        when(page.totalLaunchesEmpty()).thenReturn(false);
        when(page.isLaunchesListDisplayed()).thenReturn(true);
        when(page.getAllLaunchesNames()).thenReturn(List.of("Demo #1"));
        String[][] expected = {{"Demo #1", "..."}};
        assertThat(service.isLaunchesListSortedByName(expected, 0)).isTrue();
    }

    @Test
    void isLaunchesListSortedByNameReturnsFalseWhenEmpty() {
        when(page.totalLaunchesEmpty()).thenReturn(true);
        assertThat(service.isLaunchesListSortedByName(new String[][]{{"x", "t"}}, 0)).isFalse();
    }

    @Test
    void isLaunchesListSortedByNameReturnsFalseWhenNotDisplayed() {
        when(page.totalLaunchesEmpty()).thenReturn(false);
        when(page.isLaunchesListDisplayed()).thenReturn(false);
        assertThat(service.isLaunchesListSortedByName(new String[][]{{"x", "t"}}, 0)).isFalse();
    }

    @Test
    void sortLaunchesByNameWaitsForChange() {
        WebElement mockElement = mock(WebElement.class);
        when(mockElement.getText()).thenReturn("Old Name");
        when(page.getTotalLaunches()).thenReturn(List.of(mockElement));

        try (MockedStatic<WaitUtil> waitUtilMock = mockStatic(WaitUtil.class)) {
            service.sortLaunchesByName();

            verify(page).clickNameColumnHeaderToSort();
            waitUtilMock.verify(() -> WaitUtil.waitForElementTextToChange(mockElement, "Old Name"));
        }
    }

    @Test
    void removeSelectedLaunchClicksActionsAndDelete() {
        service.removeSelectedLaunch();
        InOrder order = inOrder(page);
        order.verify(page).clickActionsButton();
        order.verify(page).clickDeleteButton();
    }
}
