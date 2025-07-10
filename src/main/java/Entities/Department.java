package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments")
public class Department {
	@Id
	@GeneratedValue(generator = "UUID")
	@ColumnDefault("uuid_generate_v4()")
	private UUID id;

	@Column(name = "name", nullable = false)
	private String name;

	@ManyToMany(mappedBy = "departments")
	private List<Employees> employees = new ArrayList<>();

	// No-arg constructor for JPA
	public Department() {
	}

	// Optional: constructor for convenience
	public Department(String name) {
		this.name = name;
	}

	// Getters and setters
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employees> getEmployees() {
		return employees;
	}

}