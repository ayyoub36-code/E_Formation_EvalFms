package fr.fms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.fms.entities.Customer;
import fr.fms.entities.User;
import fr.fms.entities.UserRole;

public class UserDao implements Dao<User> {

	private User user;

	public User getUser() {
		return user;
	}

	@Override
	public boolean create(User obj) {
		String sql = "INSERT INTO T_Users (Login,Password) VALUES (?,?);";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, obj.getLogin());
			ps.setString(2, obj.getPassword());
			if (ps.executeUpdate() == 1)
				return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la création d'un utilisateur" + e.getMessage());
		}
		return false;

	}

	@Override
	public User read(int id) {
		User user = null;
		String sql = "Select * from T_Users where idUser =? ;";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet resultSet = ps.executeQuery()) {
				if (resultSet.next()) {
					int rs_IdUser = resultSet.getInt(1);
					String rs_Login = resultSet.getString(2);
					String rs_Password = resultSet.getString(3);
					user = new User(rs_IdUser, rs_Login, rs_Password);
				}
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur la lecture d'un utilisateur " + e.getMessage());
		}
		return user;
	}

	@Override
	public boolean update(User obj) {
		String sql = "UPDATE T_Users set Login=?, password =? where idUser = ?;";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, obj.getLogin());
			ps.setString(2, obj.getPassword());
			ps.setInt(3, obj.getId());
			if (ps.executeUpdate() == 1)
				return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la mis à jour d'un utilisateur" + e.getMessage());
		}

		return false;
	}

	@Override
	public boolean delete(User obj) {
		String sql = "delete from t_Users where idUser = ? ;";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, obj.getId());
			if (ps.executeUpdate() == 1)
				return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la suppression d'un utilisateur " + e.getMessage());
		}
		return false;
	}

	@Override
	public ArrayList<User> readAll() {
		ArrayList<User> users = new ArrayList<>();
		String sql = "SELECT * FROM T_Users";
		try (Statement statement = connection.createStatement()) {
			try (ResultSet resultSet = statement.executeQuery(sql)) {
				while (resultSet.next()) {
					int rs_IdUser = resultSet.getInt(1);
					String rs_Login = resultSet.getString(2);
					String rs_Password = resultSet.getString(3);
					users.add(new User(rs_IdUser, rs_Login, rs_Password));
				}
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur l'affichage des utilisateurs " + e.getMessage());
		}
		return users;
	}

	/**
	 * @param prends en param un user
	 * @return un id de l utilisateur a ajouter
	 */
	public int createAndReturnId(User obj) {
		int id = 0;
		String sql = "INSERT INTO T_Users (Login,Password) VALUES (?,?);";
		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, obj.getLogin());
			ps.setString(2, obj.getPassword());
			if (ps.executeUpdate() == 1) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
					new UserRoleDao().create(new UserRole(id, 1));
				}

			}
		} catch (SQLException e) {
			logger.severe("pb sql sur la création d'un utilisateur " + e.getMessage());
		}
		return id;
	}

	/**
	 * trouver les customers associés à un user
	 * 
	 * @param idUser
	 * @return la liste des clients
	 */
	public List<Customer> getUserCustomers(int idUser) {
		ArrayList<Customer> customers = new ArrayList<>();
		String sql = "SELECT * FROM T_Customers where idUser = ? ;";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, idUser);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int rs_IdCustomer = rs.getInt(1);
					String rs_FirstName = rs.getString(2);
					String rs_LastName = rs.getString(3);
					String rs_Email = rs.getString(4);
					String rs_Adress = rs.getString(5);
					String rs_Phone = rs.getString(6);
					int rs_IdUser = rs.getInt(7);
					customers.add(new Customer(rs_IdCustomer, rs_FirstName, rs_LastName, rs_Email, rs_Adress, rs_Phone,
							rs_IdUser));
				}
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur l'affichage des clients associés à un  utilisateurs " + e.getMessage());
		}
		return customers;

	}

	/**
	 * connection user verif login & password
	 * 
	 * @param login
	 * @param password
	 * @return true si les identifiants sont correctes
	 */
	public boolean verifUserAuth(String login, String password) {
		String sql = "SELECT * FROM T_Users where login = ? ;";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, login);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					int rs_IdUser = rs.getInt(1);
					String rs_Login = rs.getString(2);
					String rs_Password = rs.getString(3);
					user = new User(rs_IdUser, rs_Login, rs_Password);
					if (rs_Password.equals(password)) // comparer le password à password
						return true;
				}
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur la vérification des identifiants d'un  utilisateur " + e.getMessage());
		}
		return false;
	}
}
