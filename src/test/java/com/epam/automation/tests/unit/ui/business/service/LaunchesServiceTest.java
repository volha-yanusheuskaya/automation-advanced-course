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
    @DisplayName("Should return true when total steps equal sum of passed, failed and skipped")
    void shouldReturnTrue_WhenTotalEqualsSumOfPassedFailedSkipped() {
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
    @DisplayName("Should return false when total steps do not equal sum of passed, failed and skipped")
    void shouldReturnFalse_WhenTotalDoesNotEqualSumOfPassedFailedSkipped() {
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
    @DisplayName("Should return false when launches list is empty")
    void shouldReturnFalse_WhenLaunchesListIsEmpty() {
        when(page.totalLaunchesEmpty()).thenReturn(true);
        assertThat(service.isLaunchesListSortedByMostRecent(new String[][]{{"x", "t"}}, 0)).isFalse();
    }

    @Test
    @DisplayName("Should return false when launches list is not displayed")
    void shouldReturnFalse_WhenLaunchesListIsNotDisplayed() {
        when(page.totalLaunchesEmpty()).thenReturn(false);
        when(page.isLaunchesListDisplayed()).thenReturn(false);
        assertThat(service.isLaunchesListSortedByMostRecent(new String[][]{{"x", "t"}}, 0)).isFalse();
    }

    @Test
    @DisplayName("Should return true when actual start time matches expected")
    void shouldReturnTrue_WhenActualStartTimeMatchesExpected() {
        when(page.totalLaunchesEmpty()).thenReturn(false);
        when(page.isLaunchesListDisplayed()).thenReturn(true);
        when(page.getAllStartTimes()).thenReturn(List.of("2026-05-10 12:00:00"));
        String[][] expected = {{"Demo #1", "2026-05-10 12:00:00"}};
        assertThat(service.isLaunchesListSortedByMostRecent(expected, 0)).isTrue();
    }

    @Test
    @DisplayName("Should return false when actual start time differs from expected")
    void shouldReturnFalse_WhenActualStartTimeDiffersFromExpected() {
        when(page.totalLaunchesEmpty()).thenReturn(false);
        when(page.isLaunchesListDisplayed()).thenReturn(true);
        when(page.getAllStartTimes()).thenReturn(List.of("2026-05-10 12:00:00"));
        String[][] expected = {{"Demo #1", "2026-05-10 11:00:00"}};
        assertThat(service.isLaunchesListSortedByMostRecent(expected, 0)).isFalse();
    }

    @Test
    @DisplayName("Should return true when selected launches match expected")
    void shouldReturnTrue_WhenSelectedLaunchesMatchExpected() {
        when(page.getSelectedLaunches()).thenReturn(List.of("Demo #1", "Demo #2"));
        String[][] expected = {{"Demo #1", "..."}, {"Demo #2", "..."}};
        assertThat(service.verifySelectedLaunches(expected)).isTrue();
    }

    @Test
    @DisplayName("Should return false when selected launches do not match expected")
    void shouldReturnFalse_WhenSelectedLaunchesDoNotMatchExpected() {
        when(page.getSelectedLaunches()).thenReturn(List.of("Demo #1"));
        String[][] expected = {{"Demo #2", "..."}};
        assertThat(service.verifySelectedLaunches(expected)).isFalse();
    }

    @Test
    @DisplayName("Should click actions button then compare button when comparing launches")
    void shouldClickActionsButtonThenCompareButton_WhenCompareLaunchesInvoked() {
        service.clickCompareLaunches();
        InOrder order = inOrder(page);
        order.verify(page).clickActionsButton();
        order.verify(page).clickCompareButton();
    }

    @Test
    @DisplayName("Should return true when launches list is sorted by name")
    void shouldReturnTrue_WhenLaunchesListSortedByName() {
        when(page.totalLaunchesEmpty()).thenReturn(false);
        when(page.isLaunchesListDisplayed()).thenReturn(true);
        when(page.getAllLaunchesNames()).thenReturn(List.of("Demo #1"));
        String[][] expected = {{"Demo #1", "..."}};
        assertThat(service.isLaunchesListSortedByName(expected, 0)).isTrue();
    }

    @Test
    @DisplayName("Should return false when launches list is empty during name sort check")
    void shouldReturnFalse_WhenLaunchesListIsEmptyForNameSorting() {
        when(page.totalLaunchesEmpty()).thenReturn(true);
        assertThat(service.isLaunchesListSortedByName(new String[][]{{"x", "t"}}, 0)).isFalse();
    }

    @Test
    @DisplayName("Should return false when launches list is not displayed during name sort check")
    void shouldReturnFalse_WhenLaunchesListIsNotDisplayedForNameSorting() {
        when(page.totalLaunchesEmpty()).thenReturn(false);
        when(page.isLaunchesListDisplayed()).thenReturn(false);
        assertThat(service.isLaunchesListSortedByName(new String[][]{{"x", "t"}}, 0)).isFalse();
    }

    @Test
    @DisplayName("Should wait for element text change when sorting launches by name")
    void shouldWaitForElementTextChange_WhenSortingLaunchesByName() {
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
    @DisplayName("Should click actions button then delete button when removing selected launch")
    void shouldClickActionsButtonThenDeleteButton_WhenRemovingSelectedLaunch() {
        service.removeSelectedLaunch();
        InOrder order = inOrder(page);
        order.verify(page).clickActionsButton();
        order.verify(page).clickDeleteButton();
    }
}
