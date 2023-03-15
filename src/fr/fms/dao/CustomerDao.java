package fr.fms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.fms.entities.Customer;

public class CustomerDao implements Dao<Customer> {

	public int createAndReturnId(Customer obj) {
		int idCustomer = 0;
		String str = "INSERT INTO T_Customers (FirstName,LastName,Email,Address, Phone, IdUser) VALUES (?,?,?,?,?,?);";
		try (PreparedStatement ps = connection.prepareStatement(str, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, obj.getFirstName());
			ps.setString(2, obj.getLastName());
			ps.setString(3, obj.getEmail());
			ps.setString(4, obj.getAddress());
			ps.setString(5, obj.getPhone());
			ps.setInt(6, obj.getIdUser());
			if (ps.executeUpdate() == 1) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next())
					idCustomer = rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur la création d'un client" + e.getMessage());
		}
		return idCustomer;
	}

	@Override
	public Customer read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Customer obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Customer obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Customer> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean create(Customer obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
