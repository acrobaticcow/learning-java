package Registration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorServiceTest {

	@Test
	@DisplayName("Should return true for a valid account payload")
	void validateAccount_withValidPayload_returnsTrue() {
		AccountPayload validAccount = new AccountPayload(
				"test.user@example.com",
				"Password@123",
				LocalDate.now().minusYears(20));
		assertDoesNotThrow(() -> {
			assertTrue(ValidatorService.validateAccount(validAccount));
		});
	}

	@Test
	@DisplayName("Should throw IllegalArgumentException for a null account payload")
	void validateAccount_withNullPayload_throwsIllegalArgumentException() {
		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> ValidatorService.validateAccount(null));
		assertEquals("Account payload cannot be null", exception.getMessage());
	}

	@Test
	@DisplayName("Should throw IllegalArgumentException for an invalid email")
	void validateAccount_withInvalidEmail_throwsIllegalArgumentException() {
		AccountPayload invalidEmailAccount = new AccountPayload(
				"invalid-email",
				"Password@123",
				LocalDate.now().minusYears(25));

		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> ValidatorService.validateAccount(invalidEmailAccount));

		assertTrue(exception.getMessage().contains("Email is not valid"));
	}

	@Test
	@DisplayName("Should throw IllegalArgumentException for an invalid password")
	void validateAccount_withInvalidPassword_throwsIllegalArgumentException() {
		AccountPayload invalidPasswordAccount = new AccountPayload(
				"test.user@example.com",
				"weak",
				LocalDate.now().minusYears(25));

		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> ValidatorService.validateAccount(invalidPasswordAccount));

		assertTrue(exception.getMessage().contains("Password must be at least 8 characters long"));
	}

	@Test
	@DisplayName("Should throw IllegalArgumentException for an underage user")
	void validateAccount_withUnderageDob_throwsIllegalArgumentException() {
		AccountPayload underageAccount = new AccountPayload(
				"test.user@example.com",
				"Password@123",
				LocalDate.now().minusYears(17));

		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> ValidatorService.validateAccount(underageAccount));

		assertTrue(exception.getMessage().contains("User must be at least 18 years old."));
	}

	@Test
	@DisplayName("Should throw IllegalArgumentException with all error messages for a fully invalid payload")
	void validateAccount_withAllInvalidFields_throwsIllegalArgumentExceptionWithCombinedMessage() {
		AccountPayload allInvalidAccount = new AccountPayload("invalid-email", "weak", LocalDate.now().minusYears(10));

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> ValidatorService.validateAccount(allInvalidAccount));

		String message = exception.getMessage();
		assertTrue(message.contains("Email is not valid"));
		assertTrue(message.contains("Password must be at least 8 characters long"));
		assertTrue(message.contains("User must be at least 18 years old."));
	}

}