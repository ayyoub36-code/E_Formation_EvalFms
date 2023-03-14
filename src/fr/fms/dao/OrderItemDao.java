package fr.fms.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.fms.entities.OrderItem;

public class OrderItemDao implements Dao<OrderItem> {

	@Override
	public boolean create(OrderItem obj) {
		String sqlOItem = "INSERT INTO T_OrderItems (IdFormation,idOrder) VALUES (?,?);";
		try (PreparedStatement ps = connection.prepareStatement(sqlOItem)) {
			ps.setInt(1, obj.getIdFormation());
			ps.setInt(2, obj.getIdOrder());
			if (ps.executeUpdate() == 1)
				return true;
		} catch (SQLException e) {
			logger.severe("pb lors de la création d'une mini commande" + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean update(OrderItem obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<OrderItem> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderItem read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(OrderItem obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
