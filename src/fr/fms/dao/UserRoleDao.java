package fr.fms.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.fms.entities.UserRole;

public class UserRoleDao implements Dao<UserRole> {

	@Override
	public boolean create(UserRole obj) {
		String sql = "INSERT INTO T_UserRoles (IdUser,IdRole) VALUES (?,?);";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, obj.getIdUser());
			ps.setInt(2, obj.getIdRole());
			if (ps.executeUpdate() == 1)
				return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur l'attribution d'un role à un utilisateur" + e.getMessage());
		}
		return false;
	}

	@Override
	public UserRole read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(UserRole obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(UserRole obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<UserRole> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
