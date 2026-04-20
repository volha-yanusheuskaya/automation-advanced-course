package com.epam.automation.tests.ui.launches_bdd.step_definitions;

import com.epam.automation.ui.business.components.ToastComponent;
import com.epam.automation.ui.business.pages.DashboardPage;
import com.epam.automation.ui.business.pages.LaunchesPage;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private final Map<String, Object> context = new HashMap<>();

    /**
     * Stores DashboardPage in context.
     */
    public void setDashboardPage(DashboardPage page) {
        context.put("dashboardPage", page);
    }

    /**
     * Retrieves DashboardPage from context.
     */
    public DashboardPage getDashboardPage() {
        Object page = context.get("dashboardPage");
        if (page == null) {
            page = new DashboardPage();
            setDashboardPage((DashboardPage) page);
        }
        return (DashboardPage) page;
    }

    /**
     * Stores LaunchesPage in context.
     */
    public void setLaunchesPage(LaunchesPage page) {
        context.put("launchesPage", page);
    }

    /**
     * Retrieves LaunchesPage from context.
     */
    public LaunchesPage getLaunchesPage() {
        Object page = context.get("launchesPage");
        if (page == null) {
            page = new LaunchesPage();
            setLaunchesPage((LaunchesPage) page);
        }
        return (LaunchesPage) page;
    }

    /**
     * Stores ToastComponent in context.
     */
    public void setToastComponent(ToastComponent toastComponent) {
        context.put("toastComponent", toastComponent);
    }

    /**
     * Retrieves ToastComponent from context.
     */
    public ToastComponent getToastComponent() {
        Object page = context.get("toastComponent");
        if (page == null) {
            page = new ToastComponent();
            setToastComponent((ToastComponent) page);
        }
        return (ToastComponent) page;
    }

}
