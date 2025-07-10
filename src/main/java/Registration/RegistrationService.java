package Registration;

import java.util.Optional;

import Entities.Account;

public class RegistrationService {
	AccountRepo accountRepo;

	public RegistrationService(AccountRepo accountRepo) {
		this.accountRepo = accountRepo;
	}

	public Account register(AccountPayload payload) throws IllegalArgumentException {
		// Let the ValidatorService throw an exception on failure.
		// The caller of the service is responsible for handling it.
		ValidatorService.validateAccount(payload);
		return createAccount(payload);
	}

	private Account createAccount(AccountPayload payload) {
		return accountRepo.save(new Account(payload));
	}

	public Optional<Account> suspend(String email) {
		Optional<Account> account = accountRepo.findByEmail(email);

		if (account.isPresent()) {
			account.get().setIsActive(false);
			// save to db
			accountRepo.save(account.get());
		}

		return account;
	}
}
