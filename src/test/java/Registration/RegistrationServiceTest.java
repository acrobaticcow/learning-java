package Registration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import Entities.Account;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {
	@Mock
	private AccountRepo accountRepo;
	private RegistrationService registrationService;

	@BeforeEach
	public void init() {
		registrationService = new RegistrationService(accountRepo);

		when(accountRepo.save(any(Account.class))).thenAnswer(invocation -> {
			return invocation.getArgument(0);
		});
	}

	@Test
	@DisplayName("Given valid account payload, then account object is created")
	public void GivenValidAccountPayload_thenAccountObjIsCreate() throws Exception {
		// Arrange
		var expectedEmail = "test@example.com";
		var expectedPassword = "Password123!";
		var expectedDob = LocalDate.now().minusYears(20);

		AccountPayload accountPayload = new AccountPayload(expectedEmail, expectedPassword, expectedDob);

		// Act
		var account = registrationService.register(accountPayload);

		assertAll("Account properties should match the payload",
				() -> assertNotNull(account, "Account should not be null"),
				() -> assertEquals(accountPayload.email(), account.getEmail(), "Email should match"),
				() -> assertEquals(accountPayload.password(), account.getPassword(), "Password should match"),
				() -> assertEquals(accountPayload.dob(), account.getDob(), "DOB should match"));
	}
}
