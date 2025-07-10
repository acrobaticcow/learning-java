package Connection;

import org.hibernate.SessionFactory;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Map;

public class HibernateConnection {
	private static SessionFactory sessionFactory; // Cached SessionFactory for the application

	/**
	 * Gets the singleton SessionFactory for the application, configured via
	 * persistence.xml.
	 */
	public static synchronized SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			// Creates the EMF based on the "learning_java_pu" unit in persistence.xml
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("learning_java_pu");
			sessionFactory = emf.unwrap(SessionFactory.class);
		}
		return sessionFactory;
	}

	/**
	 * Creates a new SessionFactory for tests, allowing properties to be overridden.
	 * This is crucial for connecting to the test database provided by Testcontainers.
	 *
	 * @param properties A map of properties to override (e.g., JDBC URL, user, password).
	 * @return A new SessionFactory configured with the given properties.
	 */
	public static SessionFactory getSessionFactory(Map<String, String> properties) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("learning_java_pu", properties);
		return emf.unwrap(SessionFactory.class);
	}
}