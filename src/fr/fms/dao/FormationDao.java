package fr.fms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.fms.entities.Formation;

public class FormationDao implements Dao<Formation> {

	@Override
	public boolean create(Formation obj) {
		String str = "INSERT INTO T_Formations (Name,Description,Duration,IsFaceToFace, Price, IdCategory) VALUES (?,?,?,?,?,?);";
		try (PreparedStatement ps = connection.prepareStatement(str)) {
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getDescription());
			ps.setInt(3, obj.getDuration());
			ps.setBoolean(4, obj.isFaceToFace());
			ps.setDouble(5, obj.getPrice());
			ps.setInt(6, obj.getIdCategory());
			if (ps.executeUpdate() == 1)
				return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la création d'une formation" + e.getMessage());
		}
		return false;
	}

	@Override
	public Formation read(int id) {
		Formation formation = null;
		String sql = "Select * from T_formations where idFormation =? ;";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				formation = new Formation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
						rs.getBoolean(5), rs.getDouble(6), rs.getInt(7));
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur la lecture d'une formation " + e.getMessage());
		}
		return formation;
	}

	@Override
	public boolean update(Formation obj) {
		String str = "UPDATE T_Formations set Name=?, Description=? , Duration=? , IsFaceToFace=?, Price=?, IdCategory=? where idFormation=?;";
		try (PreparedStatement ps = connection.prepareStatement(str)) {
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getDescription());
			ps.setInt(3, obj.getDuration());
			ps.setBoolean(4, obj.isFaceToFace());
			ps.setDouble(5, obj.getPrice());
			ps.setInt(6, obj.getIdCategory());
			if (ps.executeUpdate() == 1)
				return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la mis à jour d'une formation" + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean delete(Formation obj) {
		try (Statement statement = connection.createStatement()) {
			String str = "DELETE FROM T_Formations where IdFormation=" + obj.getId() + ";";
			statement.executeUpdate(str);
			return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la suppression d'une formation " + e.getMessage());
		}
		return false;
	}

	@Override
	public ArrayList<Formation> readAll() {
		ArrayList<Formation> formations = new ArrayList<>();
		String sql = "SELECT * FROM T_Formations";
		try (Statement statement = connection.createStatement()) {
			try (ResultSet rs = statement.executeQuery(sql)) {
				while (rs.next()) {
					formations.add(new Formation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
							rs.getBoolean(5), rs.getDouble(6), rs.getInt(7)));
				}
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur l'affichage des formations " + e.getMessage());
		}
		return formations;
	}

	/**
	 * filtrer les formation par catégorie
	 * 
	 * @param id de la catégorie
	 * @return liste des formations
	 */
	public ArrayList<Formation> readAllByCat(int id) {
		ArrayList<Formation> formations = new ArrayList<Formation>();
		String strSql = "SELECT * FROM T_Formations where idCategory=" + id;
		try (Statement statement = connection.createStatement()) {
			try (ResultSet rs = statement.executeQuery(strSql)) {
				while (rs.next()) {
					formations.add(new Formation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
							rs.getBoolean(5), rs.getDouble(6), rs.getInt(7)));
				}
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur l'affichage des Formations par catégories " + e.getMessage());
		}
		return formations;
	}

	/**
	 * filtrer les formation par search word
	 * 
	 * @param String search
	 * @return liste des formations
	 */
	public ArrayList<Formation> readAllByKeyWord(String search) {
		ArrayList<Formation> formations = new ArrayList<Formation>();
		String strSql = "SELECT * FROM T_Formations where Name Like '%" + search + "%'";
		try (Statement statement = connection.createStatement()) {
			try (ResultSet rs = statement.executeQuery(strSql)) {
				while (rs.next()) {
					formations.add(new Formation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
							rs.getBoolean(5), rs.getDouble(6), rs.getInt(7)));
				}
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur l'affichage des Formations par keyWord " + e.getMessage());
		}
		return formations;
	}

	/**
	 * filtrer les formation par présentiel
	 * 
	 * @return liste des formations
	 */
	public ArrayList<Formation> readAllByFaceToFace() {
		ArrayList<Formation> formations = new ArrayList<Formation>();
		String strSql = "SELECT * FROM T_Formations where isFaceToFace=true;";
		try (Statement statement = connection.createStatement()) {
			try (ResultSet rs = statement.executeQuery(strSql)) {
				while (rs.next()) {
					formations.add(new Formation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
							rs.getBoolean(5), rs.getDouble(6), rs.getInt(7)));
				}
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur l'affichage des Formations par présentiel " + e.getMessage());
		}
		return formations;
	}

	/**
	 * filtrer les formation par distanciel
	 * 
	 * @return liste des formations
	 */
	public ArrayList<Formation> readAllByNoFaceToFace() {
		ArrayList<Formation> formations = new ArrayList<Formation>();
		String strSql = "SELECT * FROM T_Formations where isFaceToFace=false;";
		try (Statement statement = connection.createStatement()) {
			try (ResultSet rs = statement.executeQuery(strSql)) {
				while (rs.next()) {
					formations.add(new Formation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
							rs.getBoolean(5), rs.getDouble(6), rs.getInt(7)));
				}
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur l'affichage des Formations par distanciel " + e.getMessage());
		}
		return formations;
	}

}
