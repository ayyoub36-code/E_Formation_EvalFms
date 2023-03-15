package fr.fms.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import fr.fms.dao.DAOFactory;
import fr.fms.dao.Dao;
import fr.fms.dao.FormationDao;
import fr.fms.dao.OrderDao;
import fr.fms.entities.Category;
import fr.fms.entities.Formation;
import fr.fms.entities.Order;
import fr.fms.entities.OrderItem;

public class IE_ServicesImpl implements IE_Services {

	private OrderItem orderItem = null;
	public Dao<Formation> daoFormation; // injection du dao
	public Dao<Category> daoCategory;
	public Dao<Order> daoOrder;
	public Dao<OrderItem> daoOrderItem;
	private Map<Integer, OrderItem> cart; // stockage du panier

	public IE_ServicesImpl() {
		cart = new HashMap<>();
		daoFormation = DAOFactory.getFormationDao();
		daoOrder = DAOFactory.getOrderDao();
		daoOrderItem = DAOFactory.getOrderItemDao();
		daoCategory = DAOFactory.getCategoryDao();
	}

	@Override
	public ArrayList<Formation> readAll() {
		return daoFormation.readAll();
	}

	@Override
	public Formation read(int id) {
		return daoFormation.read(id);
	}

	/**
	 * ajouter un Formation
	 * 
	 * @param Formation
	 * @author Mehdioui_Ayyoub
	 */
	@Override
	public boolean addOrderItem(int idFormation) {
		if (daoFormation.readAll().get(idFormation) != null) {
			if (!cart.containsKey(idFormation)) {
				orderItem = new OrderItem(idFormation); // creer orderItem avec l idFormation
				cart.put(idFormation, orderItem);
				return true;
			} else
				System.out.println("Cette formation est déja dans votre panier !");
		} else
			System.out.println("Veuillez choisir un id valide ! ");
		return false;
	}

	/**
	 * afficher le contenu du panier
	 */
	@Override
	public void readCart() {
		if (!cart.isEmpty()) {
			System.out.println("Voici le contenu de votre panier : \n");
			display(cart);
		} else
			System.out.println("Ajouter des formations dans votre panier !");
	}

	/**
	 * supprimer un Formation du panier
	 * 
	 * @param id de l Formation
	 */
	@Override
	public void deleteItemCart(int id) {
		if (cart.containsKey(id)) {
			cart.remove(id);
			System.out.println("Formation supprimé de votre panier ");
		} else
			System.out.println("Formation introuvable !");

	}

	/**
	 * payer la commande
	 * 
	 * @return la somme totale double
	 */
	@Override
	public double pay() {
		double sum = 0;
		// lire cart
		for (Entry<Integer, OrderItem> set : cart.entrySet()) {
			Formation Formation = daoFormation.read(set.getValue().getIdFormation());
			sum += Formation.getPrice();// calculer le montant
		}
		return sum;
	}

	public Map<Integer, OrderItem> getCart() {
		return cart;
	}

	public void display(Map<Integer, OrderItem> cart2) {
		for (Entry<Integer, OrderItem> set : cart2.entrySet()) {
			Formation Formation = daoFormation.read(set.getValue().getIdFormation());
			System.out.println("Formation : " + Formation.getId() + "  " + Formation.getDescription() + "   Prix  : "
					+ Formation.getPrice() + " €");
		}
	}

	@Override
	public ArrayList<Category> readAllCategory() {
		return daoCategory.readAll();
	}

	@Override
	public ArrayList<Formation> readAllFormationByCategory(int idCategory) {
		return ((FormationDao) daoFormation).readAllByCat(idCategory);
	}

	@Override
	public Category readCategory(int idCat) {
		return daoCategory.read(idCat);
	}

	@Override
	public void createOrder(Map<Integer, OrderItem> cart, int idCustomer) { // stocker order and OrderItems
		int idOrder = 0;
		idOrder = ((OrderDao) daoOrder)
				.createAndReturnId(new Order(java.sql.Date.valueOf(java.time.LocalDate.now()), idCustomer, pay()));
		for (Entry<Integer, OrderItem> set : cart.entrySet()) {
			Formation formation = daoFormation.read(set.getValue().getIdFormation());
			OrderItem oi = new OrderItem(formation.getId(), idOrder);
			daoOrderItem.create(oi);
		}
		cart.clear(); // vider le panier
	}

	@Override
	public ArrayList<Formation> readAllByKeyWord(String search) {
		return ((FormationDao) daoFormation).readAllByKeyWord(search);
	}

	@Override
	public ArrayList<Formation> readAllByFaceToFace() {
		return ((FormationDao) daoFormation).readAllByFaceToFace();
	}

	@Override
	public ArrayList<Formation> readAllByNoFaceToFace() {
		return ((FormationDao) daoFormation).readAllByNoFaceToFace();
	}

	@Override
	public ArrayList<Order> readAllCustomerOrders(int idCustomer) {
		return ((OrderDao) daoOrder).readAllCustomerOrders(idCustomer);
	}
}
