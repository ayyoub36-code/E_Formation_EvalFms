package fr.fms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		UserRole role = null;
		String sql = "Select * from T_UserRoles where idUser =? ;";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				role = new UserRole(rs.getInt(1), rs.getInt(2), rs.getInt(3));
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur la lecture d'un User Role " + e.getMessage());
		}
		return role;
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
