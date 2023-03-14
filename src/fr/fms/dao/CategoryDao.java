package fr.fms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.fms.entities.Category;

public class CategoryDao implements Dao<Category> {

	@Override
	public boolean create(Category obj) {
		String sql = "INSERT INTO T_Categories (Name,Description) VALUES (?,?);";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getDescription());
			if (ps.executeUpdate() == 1)
				return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la création d'une catégorie" + e.getMessage());
		}
		return false;

	}

	@Override
	public Category read(int id) {
		Category category = null;
		String sql = "Select * from T_categories where idCategory =? ;";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					category = new Category(rs.getInt(1), rs.getString(2), rs.getString(3));
				}
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur la lecture d'une catégorie " + e.getMessage());
		}
		return category;
	}

	@Override
	public boolean update(Category obj) {
		String sql = "UPDATE T_Categories set Name=?, Description=? where idCategory = ?;";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getDescription());
			ps.setLong(3, obj.getId());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la mis à jour d'une catégorie" + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean delete(Category obj) {
		String sql = "delete from T_Categories where idCategory = ? ;";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, obj.getId());
			if (ps.executeUpdate() == 1)
				return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la suppression d'une catégorie " + e.getMessage());
		}
		return false;
	}

	@Override
	public ArrayList<Category> readAll() {
		ArrayList<Category> categories = new ArrayList<Category>();
		String sql = "select * from T_Categories";
		try (Statement statement = connection.createStatement()) {
			try (ResultSet resultSet = statement.executeQuery(sql)) {
				while (resultSet.next()) {
					categories.add(new Category(resultSet.getInt("idCategory"), resultSet.getString(2),
							resultSet.getString(3)));
				}
				return categories;
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur l'affichage des catégories " + e.getMessage());
		}
		return null;
	}

}
