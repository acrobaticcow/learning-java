import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hibernate.SessionFactory;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import Connection.HibernateConnection;

import Entities.Account;
import Registration.AccountPayload;
import Registration.AccountRepo;
import Registration.RegistrationService;

@Testcontainers
public class EndToEndIT {
	private RegistrationService registrationService;

	@Container
	MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.3.0")
			.withDatabaseName("test_db")
			.withUsername("root")
			.withPassword("66773508");

	@BeforeEach
	void init() {
		// 1. Dynamically create properties to connect to the test container
		Map<String, String> properties = new HashMap<>();
		properties.put("jakarta.persistence.jdbc.url", mysqlContainer.getJdbcUrl());
		properties.put("jakarta.persistence.jdbc.user", mysqlContainer.getUsername());
		properties.put("jakarta.persistence.jdbc.password", mysqlContainer.getPassword());
		properties.put("hibernate.hbm2ddl.auto", "create-drop"); // Re-create schema for each test run

		// 2. Get a SessionFactory specifically for this test
		SessionFactory testSessionFactory = HibernateConnection.getSessionFactory(properties);

		// 3. Inject the test-specific SessionFactory into the repository
		AccountRepo accountRepo = new AccountRepo(testSessionFactory);
		registrationService = new RegistrationService(accountRepo);
	}

	@Test
	@DisplayName("If the account is active then account is suspended")
	public void givenAnActiveAccountIsProvided_thenAccountIsSuspended() {
		// Given
		String email = "test@example.com";
		String password = "Password123!";
		LocalDate dob = LocalDate.of(1990, 1, 1);

		// Prepare
		Account activeAccount = registrationService.register(new AccountPayload(email, password, dob));

		// Pre-assert
		assertNotNull(activeAccount, "Account should not be null after registration");
		assertTrue(activeAccount.getIsActive(), "Account should be active initially");

		// When
		Optional<Account> suspendedAccount = registrationService.suspend(email);
		assertTrue(suspendedAccount.isPresent(), "Account should be present after suspension");
		assertFalse(suspendedAccount.get().getIsActive(), "Account should be suspended");
	}

}
