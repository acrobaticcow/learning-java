package Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@Column(name = "age")
	private Integer age;
	@Column(name = "password", nullable = false)
	private String password;
}
