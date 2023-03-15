package fr.fms.App;

import java.util.List;
import java.util.Scanner;

import fr.fms.auth.UserAuth;
import fr.fms.business.IE_ServicesImpl;
import fr.fms.dao.CustomerDao;
import fr.fms.dao.DAOFactory;
import fr.fms.dao.Dao;
import fr.fms.dao.UserDao;
import fr.fms.entities.Category;
import fr.fms.entities.Customer;
import fr.fms.entities.Formation;
import fr.fms.entities.User;
import fr.fms.entities.UserRole;

public class App {

	// TODO gérer le menu admin
	// TODO ajout user(name) panier(size)

	public static Dao<User> daoUser = DAOFactory.getUserDao();
	private static Customer customer;
	private static User user = (((UserDao) daoUser).getUser());
	public static Dao<Customer> daoCustomer = DAOFactory.getCustomerDao();
	public static Dao<UserRole> daoUserRole = DAOFactory.getUserRoleDao();
	public static IE_ServicesImpl services = new IE_ServicesImpl();

	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RED = "\u001B[31m";
	private static final String COLUMN_ID = "ID";
	private static final String COLUMN_DESCRIPTION = "DESCRIPTION";
	private static final String COLUMN_DURATION = "DUREE";
	private static final String COLUMN_FACETOFACE = "PRESENTIEL";
	private static final String COLUMN_PRICE = "PRIX";
	private static final String COLUMN_NAME = "FORMATION";
	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("Bienvenu sur notre platforme de vente de formation en ligne \n");
		displayFormations();
		shoping(scan, services);

	}

	private static void shoping(Scanner scan, IE_ServicesImpl services) {
		int idCustomer = 0;
		int idFormation = 0;
		int input = 0;

		while (input != 15) {
			menuWelcom(); // afficher le menu pricipal
			input = scan.nextInt();
			switch (input) {
			case 1: // lister les formations
				services.readAll().forEach(e -> System.out.println(e));
				break;
			case 2: // afficher une formation & sa category name
				displayFormation(scan, services, idFormation);
				break;
			case 3: // lister toutes les categories
				System.out.println("Liste des catégories de nos formations  :");
				services.readAllCategory().forEach(e -> System.out.println(e));
				break;
			case 4: // afficher les formations par category
				displayFormationByCat(scan, services);
				break;
			case 5: // effectuer une recherche
				displayFormationByKeyWord(scan, services);
				break;
			case 6: // afficher les formation en présentiel
				displayFormationByFaceToFace(scan, services);
				break;
			case 7: // ajouter au panier
				idFormation = addToCart(scan, services);
				break;
			case 8: // enlever du panier
				System.out.println("Veuillez saisir l'id de la formation que vous voulez enlever :");
				idFormation = scan.nextInt();
				services.deleteItemCart(idFormation);
				break;
			case 9: // voir le panier
				services.readCart();
				payFromCart(scan, services);
				break;
			case 10: // se connecter / deconnecter
				deconnection(scan);
				break;
			case 11: // passer la commande payer
				if (user != null) {
					idCustomer = chooseClientAccount(scan, services);
					pay(scan, services, idCustomer);
				} else {
					deconnection(scan);
					idCustomer = chooseClientAccount(scan, services);
					pay(scan, services, idCustomer);
				}
				break;
			case 12: // admin add formation
				break;
			case 13: // admin update formation
				break;
			case 14: // admin delete formation
				break;
			case 15: // admin afficher les commandes d'un client
				break;
			default:
				System.out.println("Saisissez une valeur valide !");
				break;
			}
		}
	}

	private static void payFromCart(Scanner scan, IE_ServicesImpl services) {
		int idCustomer;
		if (!services.getCart().isEmpty()) {
			System.out.println("Voulez vous passer la commande ? O/N");
			String response = scan.next();
			if (response.equalsIgnoreCase("Oui")) {
				System.out.println("Client chez nous ? O/N \n");
				String response2 = scan.next();
				if (response2.equalsIgnoreCase("Oui")) {
					while (UserAuth.isConnected(scan, daoUser) == false) // verif connection
						UserAuth.isConnected(scan, daoUser);
					user = (((UserDao) daoUser).getUser());
					idCustomer = chooseClientAccount(scan, services);
					if (idCustomer != 0) {
						pay(scan, services, idCustomer);
					}
				} else {
					int idUser = newUser();
					user = daoUser.read(idUser);
					idCustomer = newCustomer();
					pay(scan, services, idCustomer);
				}
			}
		}
	}

	private static boolean deconnection(Scanner scan) {
		if (user == null) {
			while (UserAuth.isConnected(scan, daoUser) == false) // verif connection
				UserAuth.isConnected(scan, daoUser);
			user = (((UserDao) daoUser).getUser());
			return true;
		} else { // deconnexion
			System.out.println("Souhaitez vous vous déconnecter ? Oui/Non");
			String response = scan.next();
			if (response.equalsIgnoreCase("Oui")) {
				System.out.println("A bientôt ");
				user = null;
			}
		}
		return false;
	}

	private static int newUser() {
		System.out.println("saisissez un login :");
		String login = scan.next();
		System.out.println("saisissez votre password :");
		String password = scan.next();
		// creer l user dans DB
		return ((UserDao) daoUser).createAndReturnId(new User(login, password));
	}

	private static int chooseClientAccount(Scanner scan, IE_ServicesImpl services) {
		int idCustomer = 0;
		List<Customer> customers = ((UserDao) daoUser).getUserCustomers(user.getId());
		System.out.println("Bienvenu " + user.getLogin() + "\n");
		if (!customers.isEmpty()) {
			System.out.println("Choisissez dans la liste le client :\n");
			customers.forEach((e -> System.out.println(e)));
			idCustomer = scan.nextInt();
			services.readCart();
			System.out.println("Montant de la commande : " + services.pay() + "€");
			System.out.println("Saisissez le montant à payer à l'abri des regards !");
			double amount = scan.nextDouble();
			if (amount == services.pay()) {
				System.out.println("Vous commencerez un lundi à 8h veuillez acheter un stylo !");
				services.createOrder(services.getCart(), idCustomer);// stocker la commande
			} else
				System.out.println("saisissez le montant correct !");
		} else {
			idCustomer = newCustomer(); // creer un compte client pour cet user
		}

		return idCustomer;
	}

	private static int newCustomer() {
		// int idCustomer = chooseClientAccount(scan, services);
		int idCustomer = 0;
		int idUser = user.getId();
		if (customer == null) {
			scan.nextLine();
			System.out.println("saisissez votre nom :");
			String fname = scan.nextLine();
			System.out.println("saisissez votre prénom :");
			String lName = scan.next();
			System.out.println("saisissez votre email :");
			String email = scan.next();
			while (!isValidEmail(email)) {
				System.out.println("saisissez un email valide  !");
				email = scan.next();
			}
			System.out.println("saisissez votre tel :");
			String tel = scan.next();
			scan.nextLine();
			System.out.println("saisissez votre adresse :");
			String address = scan.nextLine();
			customer = new Customer(fname, lName, email, tel, address, idUser);
			System.out.println(
					"Nous allons associer la commande en cours avec le compte client : " + customer.getLastName());
			return idCustomer = ((CustomerDao) daoCustomer).createAndReturnId(customer);
		}

		return idCustomer;
	}

	private static void pay(Scanner scan, IE_ServicesImpl services, int idCustomer) {
		if (idCustomer == 0) {
			System.out.println("veuillez choisir un compte client !");

		} else {
			services.readCart();
			System.out.println("Montant de la commande : " + services.pay() + "€");
			System.out.println("Saisissez le montant à payer à l'abri des regards !");
			double amount = scan.nextDouble();
			if (amount == services.pay())
				System.out.println("Vous commencerez un lundi à 8h veuillez acheter un stylo !");
			// stocker la commande
			services.createOrder(services.getCart(), idCustomer);
		}
	}

	private static int addToCart(Scanner scan, IE_ServicesImpl services) {
		int idFormation;
		System.out.println("Veuillez saisir l'id de la formation que vous voulez ajouter :");
		idFormation = scan.nextInt();
		if (services.addOrderItem(idFormation))
			System.out
					.println("La formation " + services.read(idFormation).getName() + " à été ajouter à votre panier");
		return idFormation;
	}

	private static void displayFormationByCat(Scanner scan, IE_ServicesImpl services) {
		System.out.println("Veuillez saisir l'id de la catégorie :");
		int idCategory = scan.nextInt();
		System.out.printf("                          AFFICHAGE PAR CATEGORIE    %n");
		System.out.printf("                                 %-10s               %n",
				ANSI_GREEN + services.readCategory(idCategory).getName() + ANSI_RESET);
		System.out.printf(
				"--------------------------------------------------------------------------------------------------%n");
		System.out.printf("%-10s | %-15s | %-35s | %-15s | %-15s %n", COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION,
				COLUMN_PRICE, COLUMN_FACETOFACE);
		System.out.printf(
				"--------------------------------------------------------------------------------------------------%n");

		for (Formation formation : services.readAllFormationByCategory(idCategory)) {
			String dist = (formation.isFaceToFace()) ? ANSI_GREEN + "oui" + ANSI_RESET : ANSI_RED + "non" + ANSI_RESET;
			System.out.printf("%-10s | %-15s | %-35s | %-15s | %-15s%n", formation.getId(), formation.getName(),
					formation.getDescription(), formation.getPrice() + "€", dist);
		}
	}

	private static void displayFormationByKeyWord(Scanner scan, IE_ServicesImpl services) {
		System.out.println("Saisissez le nom de la formation :");
		String search = scan.next();
		System.out.printf("                AFFICHAGE PAR MOT CLE    %n");
		System.out.printf("                     %-10s               %n", search);
		System.out.printf("-------------------------------------------------------------------------------%n");
		System.out.printf("%-10s | %-15s | %-35s | %-15s %n", COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_PRICE);
		System.out.printf("-------------------------------------------------------------------------------%n");
		services.readAllByKeyWord(search).forEach(a -> System.out.printf("%-10s | %-15s | %-35s | %-15s%n", a.getId(),
				a.getName(), a.getDescription(), a.getPrice() + "€"));
	}

	private static void displayFormationByFaceToFace(Scanner scan, IE_ServicesImpl services) {

		System.out.printf("                      AFFICHAGE FORMATIONS EN PRETENTIEL   %n");
		System.out.printf(
				"----------------------------------------------------------------------------------------------------------%n");
		System.out.printf("%-10s | %-15s | %-45s | %-15s | %-15s%n", COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION,
				COLUMN_PRICE, COLUMN_FACETOFACE);
		System.out.printf(
				"----------------------------------------------------------------------------------------------------------%n");
		for (Formation formation : services.readAllByFaceToFace()) {
			String dist = (formation.isFaceToFace()) ? "oui" : "non";
			System.out.printf("%-10s | %-15s | %-45s | %-15s | %-15s%n", formation.getId(), formation.getName(),
					formation.getDescription(), formation.getPrice() + "€", dist);
		}

	}

	private static void displayFormation(Scanner scan, IE_ServicesImpl services, int idFormation) {
		System.out.println("Veuillez saisir l'id de la formation qui vous interesse :");
		idFormation = scan.nextInt();
		if (services.read(idFormation) != null) {
			if (services.read(idFormation).getIdCategory() != 0) {
				Category category = services.readCategory(services.read(idFormation).getIdCategory());
				System.out.println(services.read(idFormation) + ", Catégorie : " + category.getName());
			} else
				System.out.println(services.read(idFormation) + ", Catégorie : pas de catégorie ! ");
		} else
			System.out.println("cet id n'existe pas ! ");
	}

	public static void displayFormations() {
		System.out.println(Formation.centerString(COLUMN_ID) + Formation.centerString(COLUMN_NAME)
				+ Formation.centerString(COLUMN_DURATION) + Formation.centerString(COLUMN_PRICE));
		services.readAll().forEach(System.out::println);
	}

	private static void menuWelcom() {
		// adapter le menu a l utilisateur qui est connecté
		if (((UserDao) daoUser).getUser() != null
				&& UserAuth.isAdmin(daoUserRole, ((UserDao) daoUser).getUser().getId())) {
//			System.out.println(ANSI_CYAN + "\n1/ Liste des formations" + "\n2/ Afficher les détails d'une formation"
//					+ "\n3/ Afficher la liste des catégories" + "\n4/ Afficher les formations par categorie"
//					+ "\n5/ Ajouter une formation au panier" + "\n6/ Enlever une formation du panier"
//					+ "\n7/ Voir le panier" + "\n8/ choisir un compte client" + "\n9/ Se déconnecter"
//					+ "\n10/ Passer la commande\n" + ANSI_RESET + 
			menuGuest();
			System.out.println("\n11/ Passer la commande\n" + ANSI_GREEN + "\nMenu Admin \n"
					+ "A/ Ajouter une formation\n" + "B/ supprimer une formation\n" + "C/ mettre à jour une formation\n"
					+ "D/ afficher les commandes d'un client\n" + ANSI_RESET);

		} else if (((UserDao) daoUser).getUser() != null) {

			// + "\n1/ Liste des formations" + "\n2/ Afficher les détails d'une formation"
//					+ "\n3/ Afficher la liste des catégories" + "\n4/ Afficher les formations par categorie"
//					+ "\n5/ Ajouter une formation au panier" + "\n6/ Enlever une formation du panier"
//					+ "\n7/ Voir le panier" + "\n8/ choisir un compte client" + "\n9/ Se déconnecter"
			menuGuest();
			System.out.println(ANSI_CYAN + "\n 11 : Passer la commande\n" + ANSI_RESET);
		} else {
			menuGuest();
		}
	}

	private static void menuGuest() {
		System.out.println(ANSI_CYAN + "\n 1 : Liste des formations" + "\n 2 : Afficher les détails d'une formation"
				+ "\n 3 : Afficher la liste des catégories" + "\n 4 : Afficher les formations par categorie"
				+ "\n 5 : Effectuer une recherche :" + "\n 6 : Afficher les formations en présentiel"
				+ "\n 7 : Ajouter une formation au panier" + "\n 8 : Enlever une formation du panier"
				+ "\n 9 : Voir le panier" + "\n 10: Se connecter / Se déconnecter" + ANSI_RESET);
	}

	public static boolean isValidEmail(String email) {
		String regExp = "^[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.[a-z][a-z]+$";
		return email.matches(regExp);
	}

}
