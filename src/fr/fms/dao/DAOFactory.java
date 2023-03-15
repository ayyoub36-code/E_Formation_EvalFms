package fr.fms.dao;

import fr.fms.entities.Category;
import fr.fms.entities.Customer;
import fr.fms.entities.Formation;
import fr.fms.entities.Order;
import fr.fms.entities.OrderItem;
import fr.fms.entities.User;
import fr.fms.entities.UserRole;

public class DAOFactory {

	public static Dao<Formation> getFormationDao() {
		return new FormationDao();
	}

	public static Dao<User> getUserDao() {
		return new UserDao();
	}

	public static Dao<Customer> getCustomerDao() {
		return new CustomerDao();
	}

	public static Dao<UserRole> getUserRoleDao() {
		return new UserRoleDao();
	}

	public static Dao<Category> getCategoryDao() {
		return new CategoryDao();
	}

	public static Dao<Order> getOrderDao() {
		return new OrderDao();
	}

	public static Dao<OrderItem> getOrderItemDao() {
		return new OrderItemDao();
	}

}
