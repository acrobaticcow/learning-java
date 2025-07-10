package Entities;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employees {
	@Id
	@GeneratedValue(generator = "UUID")
	@ColumnDefault("uuid_generate_v4()")
	private UUID id;
	@Column(name = "name", nullable = false)
	private String name;
	@ManyToMany
	@JoinTable(name = "employee_department", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "department_id"))
	private List<Department> departments = List.of(); // Initialize with an empty list

	// Default constructor required by JPA
	public Employees() {
	}

	// Detailed real-world constructor
	public Employees(String name, List<Department> departments) {
		this.name = name;
		this.departments = departments;
	}

	public Employees(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Department... departments) {
		for (Department department : departments) {
			this.departments.add(department);
		}
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public void removeDepartment(String id) {
		this.departments.removeIf(d -> d.getId().toString() == id);
	}

	public void removeDepartmentAll() {
		this.departments.clear();
	}
}