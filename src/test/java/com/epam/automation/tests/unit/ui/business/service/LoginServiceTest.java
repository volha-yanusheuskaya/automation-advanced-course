package com.epam.automation.tests.unit.ui.business.service;

import com.epam.automation.ui.business.models.User;
import com.epam.automation.ui.business.pages.DashboardPage;
import com.epam.automation.ui.business.pages.LoginPage;
import com.epam.automation.ui.business.service.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoginService Unit Tests")
class LoginServiceTest {
    @Mock
    private LoginPage loginPage;

    @Test
    @DisplayName("Should construct LoginService with LoginPage instance")
    void constructorInitializesLoginService() {
        LoginService service = new LoginService(loginPage);
        assertThat(service).isNotNull();
    }

    @Test
    @DisplayName("Should perform login sequence with correct credentials")
    void loginAsEntersCredentialsClicksButtonAndWaits() {
        LoginService service = new LoginService(loginPage);
        User user = new User("testuser", "testpass123");
        service.loginAs(user);
        InOrder order = inOrder(loginPage);
        order.verify(loginPage).enterCredentials("testuser", "testpass123");
        order.verify(loginPage).clickLoginButton();
        order.verify(loginPage).waitForLoginSuccess();
    }

    @Test
    @DisplayName("Should return DashboardPage instance after successful login")
    void loginAsReturnsDashboardPage() {
        LoginService service = new LoginService(loginPage);
        User user = new User("admin", "password");
        DashboardPage result = service.loginAs(user);
        assertThat(result)
                .isNotNull()
                .isInstanceOf(DashboardPage.class);
    }

    @Test
    @DisplayName("Should verify all login steps are called in correct order")
    void loginAsVerifiesCallSequence() {
        LoginService service = new LoginService(loginPage);
        User user = new User("user1", "pass1");
        service.loginAs(user);
        verify(loginPage).enterCredentials("user1", "pass1");
        verify(loginPage).clickLoginButton();
        verify(loginPage).waitForLoginSuccess();
    }

    @Test
    @DisplayName("Should pass exact username from user object to enterCredentials")
    void loginAsPassesCorrectUsername() {
        LoginService service = new LoginService(loginPage);
        User user = new User("john_doe", "securepass");
        service.loginAs(user);
        verify(loginPage).enterCredentials("john_doe", "securepass");
    }

    @Test
    @DisplayName("Should pass exact password from user object to enterCredentials")
    void loginAsPassesCorrectPassword() {
        LoginService service = new LoginService(loginPage);
        User user = new User("user", "specialPass@123");
        service.loginAs(user);
        verify(loginPage).enterCredentials("user", "specialPass@123");
    }

    @Test
    @DisplayName("Should handle login with special characters in credentials")
    void loginAsWithSpecialCharactersInCredentials() {
        LoginService service = new LoginService(loginPage);
        User user = new User("user@example.com", "p@ss#w0rd!");
        DashboardPage result = service.loginAs(user);
        assertThat(result).isNotNull();
        verify(loginPage).enterCredentials("user@example.com", "p@ss#w0rd!");
    }

    @Test
    @DisplayName("Should always create new DashboardPage on successful login")
    void loginAsAlwaysReturnsNewDashboardPage() {
        LoginService service = new LoginService(loginPage);
        User user1 = new User("user1", "pass1");
        User user2 = new User("user2", "pass2");
        DashboardPage result1 = service.loginAs(user1);
        DashboardPage result2 = service.loginAs(user2);
        assertThat(result1).isNotSameAs(result2);
    }
}
