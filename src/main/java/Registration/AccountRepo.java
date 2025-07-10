package Registration;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;

import Connection.HibernateConnection;
import Entities.Account;

public class AccountRepo {
	SessionFactory sessionFactory;

	// Constructor to initialize the session factory
	public AccountRepo() {
		this.sessionFactory = HibernateConnection.getSessionFactory();
	}

	// New constructor for dependency injection (used in tests)
	public AccountRepo(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Account save(Account account) {
		return sessionFactory.fromTransaction(session -> {
			return session.merge(account);
		});
	}

	public Optional<Account> findByEmail(String email) {
		return findUniqueByAttribute("email", email);
	}

	/**
	 * Example of a new finder method that reuses the generic helper.
	 * 
	 * @param dob The date of birth to search for.
	 * @return A list of accounts matching the date of birth.
	 */
	public List<Account> findByDob(LocalDate dob) {
		return findByAttribute("dob", dob);
	}

	private Optional<Account> findUniqueByAttribute(String attributeName, Object value) {
		return sessionFactory.fromSession(session -> {
			HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
			JpaCriteriaQuery<Account> cq = cb.createQuery(Account.class);
			JpaRoot<Account> root = cq.from(Account.class);
			cq.select(root).where(cb.equal(root.get(attributeName), value));
			// Using getResultStream().findFirst() is a clean way to get an Optional
			// and avoids a NoResultException if no account is found.
			return session.createQuery(cq).getResultStream().findFirst();
		});
	}

	private List<Account> findByAttribute(String attributeName, Object value) {
		return sessionFactory.fromSession(session -> {
			HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
			JpaCriteriaQuery<Account> cq = cb.createQuery(Account.class);
			JpaRoot<Account> root = cq.from(Account.class);
			cq.select(root).where(cb.equal(root.get(attributeName), value));
			return session.createQuery(cq).getResultList();
		});
	}
}
