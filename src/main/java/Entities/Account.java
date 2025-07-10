package Entities;

import java.time.LocalDate;

import Registration.AccountPayload;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String password;
	private LocalDate dob;
	private boolean isActive;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_id", referencedColumnName = "id")
	private Profile profile;

	public Account() {
		// Default constructor
		this.isActive = true;
	}

	public Account(String email, String password, LocalDate dob) {
		this.email = email;
		this.password = password;
		this.dob = dob;

		this.isActive = true;
	}

	public Account(AccountPayload payload) {
		this.email = payload.email();
		this.password = payload.password();
		this.dob = payload.dob();

		this.isActive = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Profile getProfile() {
		return profile;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}