package Registration;

import java.time.LocalDate;

public class ValidatorService {
	private static String EmailErrorMsg = "Email is not valid";
	private static String PasswordErrorMsg = "Password must be at least 8 characters long, contain at least one digit, one lowercase letter, one uppercase letter, and one special character.";
	private static String DateErrorMsg = "User must be at least 18 years old.";

	private static boolean isValidEmail(String email) {
		if (email == null || email.isEmpty()) {
			return false;
		}
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		return email.matches(emailRegex);
	}

	private static boolean isValidPassword(String password) {
		if (password == null || password.length() < 8) {
			return false;
		}

		// Check for at least one digit, one lowercase letter, one uppercase letter, and
		// one special character (punctuation)
		String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\p{Punct}).{8,}$";
		return password.matches(passwordRegex);
	}

	// User must be at least 18 years old.
	private static boolean isValidDob(LocalDate date) {
		if (date == null) {
			return false;
		}
		LocalDate today = LocalDate.now();
		LocalDate minDate = today.minusYears(18);
		return !date.isAfter(minDate);
	}

	public static boolean validateAccount(AccountPayload account)
			throws IllegalArgumentException {
		if (account == null) {
			throw new IllegalArgumentException("Account payload cannot be null");
		}
		boolean isValid = true;
		StringBuilder errorMessage = new StringBuilder();

		if (!isValidEmail(account.email())) {
			isValid = false;
			errorMessage.append(EmailErrorMsg).append("\n");
		}

		if (!isValidPassword(account.password())) {
			isValid = false;
			errorMessage.append(PasswordErrorMsg).append("\n");
		}

		if (!isValidDob(account.dob())) {
			isValid = false;
			errorMessage.append(DateErrorMsg).append("\n");
		}

		if (!isValid) {
			throw new IllegalArgumentException(errorMessage.toString());
		}
		return true;
	}
}
