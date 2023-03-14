package fr.fms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.fms.entities.Order;

public class OrderDao implements Dao<Order> {

	/**
	 * @param prends en param une commande
	 * @return un id de la commande ajouter
	 */
	public int createAndReturnId(Order obj) {
		int id = 0;
		String sql = "INSERT INTO T_Orders (OrderDate,IdCustomer,TotalAmount) VALUES (?,?,?);";
		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setDate(1, obj.getDate());
			ps.setLong(2, obj.getIdCustomer());
			ps.setDouble(2, obj.getTotalAmount());
			if (ps.executeUpdate() == 1) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next())
					id = rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur la création d'une commande " + e.getMessage());
		}
		return id;
	}

	/**
	 * @param prends l'idCustomer
	 * @return renvoi la liste des commande pour un client
	 */
	public ArrayList<Order> readAllCustomerOrders(int idCustomer) {
		ArrayList<Order> orders = new ArrayList<>();
		String sql = "SELECT * FROM T_Orders where idCustomer=?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, idCustomer);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					orders.add(new Order(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getDouble(4)));
				}
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur l'affichage des commandes " + e.getMessage());
		}
		return orders;
	}

	@Override
	public boolean create(Order obj) {
		return false;
	}

	@Override
	public Order read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Order> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
