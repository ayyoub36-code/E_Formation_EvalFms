package fr.fms.entities;

public class Role {

	private int id;
	private String role;

	public Role(int id, String role) {
		this.id = id;
		this.role = role;
	}

	public Role(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + "]";
	}

}
