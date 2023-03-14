package fr.fms.business;

import java.util.ArrayList;
import java.util.Map;

import fr.fms.entities.Category;
import fr.fms.entities.Formation;
import fr.fms.entities.Order;
import fr.fms.entities.OrderItem;

public interface IE_Services {

	/**
	 * lister toutes les formations
	 * 
	 * @return liste des F
	 */
	public ArrayList<Formation> readAll();

	/**
	 * @return liste des catégories
	 */
	public ArrayList<Category> readAllCategory();

	/**
	 * voir une Formation
	 * 
	 * @param id
	 * @return Formation
	 */
	public Formation read(int id);

	/**
	 * voir la categorie d'une Formation
	 * 
	 * @param idCat
	 * @return
	 */
	public Category readCategory(int idCat);

	/**
	 * voir les Formations d'une catégorie
	 * 
	 * @param idCategory
	 * @return
	 */
	public ArrayList<Formation> readAllFormationByCategory(int idCategory);

	/**
	 * lister les formations par mot clé
	 * 
	 * @param search key word
	 * @return liste des formations
	 */
	public ArrayList<Formation> readAllByKeyWord(String search);

	/**
	 * lister les formations par mode présentiel
	 * 
	 * @return liste des formations
	 */
	public ArrayList<Formation> readAllByFaceToFace();

	/**
	 * filtrer les formation par distanciel
	 * 
	 * @return liste des formations
	 */
	public ArrayList<Formation> readAllByNoFaceToFace();

	/**
	 * ajouter une Formation au panier
	 * 
	 * @param idFormation
	 * @return
	 */
	public boolean addOrderItem(int idFormation);

	/**
	 * voir le panier => {hashMap de Formations}
	 */
	public void readCart();

	/**
	 * enlever une Formation du panier
	 * 
	 * @param idFormation
	 */
	public void deleteItemCart(int id);

	/**
	 * calculer le montant de la commande
	 * 
	 * @return le montant de la commande
	 */
	public double pay();

	/**
	 * stocker la commande dans la BD => store order & all orderItems
	 * 
	 * @param cart
	 * @param idCustomer
	 */
	public void createOrder(Map<Integer, OrderItem> cart, int idCustomer);

	/**
	 * renvoi la liste des commandes d'un client
	 * 
	 * @param prends l'idCustomer
	 * @return renvoi la liste des commande pour un client
	 */
	public ArrayList<Order> readAllCustomerOrders(int idCustomer);

}
