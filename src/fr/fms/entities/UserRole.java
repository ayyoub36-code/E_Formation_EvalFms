package fr.fms.entities;

public class UserRole {

	private int id;
	private int idUser;
	private int idRole;

	public UserRole(int id, int idUser, int idRole) {
		this.id = id;
		this.idUser = idUser;
		this.idRole = idRole;
	}

	public UserRole(int idUser, int idRole) {
		this.idUser = idUser;
		this.idRole = idRole;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdRole() {
		return idRole;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "UserRole [id=" + id + ", idUser=" + idUser + ", idRole=" + idRole + "]";
	}

}
