package fr.fms.App;

import java.util.Scanner;

import fr.fms.auth.UserAuth;
import fr.fms.business.IE_ServicesImpl;
import fr.fms.dao.DAOFactory;
import fr.fms.dao.Dao;
import fr.fms.dao.UserDao;
import fr.fms.entities.Category;
import fr.fms.entities.User;

public class App {

	public static Dao<User> daoUser = DAOFactory.getUserDao();
	public static IE_ServicesImpl services = new IE_ServicesImpl();

	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_RESET = "\u001B[0m";

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Bienvenu sur notre platforme de vente de formation en ligne ");
		shoping(scan, services);
	}

	private static void shoping(Scanner scan, IE_ServicesImpl services) {
		int idCustomer = 0;
		int idFormation = 0;
		int input = 0;
		while (input != 10) {
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
				System.out.println("Liste des catégories de notre boutique :");
				services.readAllCategory().forEach(e -> System.out.println(e));
				break;
			case 4: // afficher les formations par category
				displayFormationByCat(scan, services);
				break;
			case 5: // ajouter au panier
				idFormation = addToCart(scan, services);
				break;
			case 6: // enlever du panier
				System.out.println("Veuillez saisir l'id de la formation que vous voulez enlever :");
				idFormation = scan.nextInt();
				services.deleteItemCart(idFormation);
				break;
			case 7: // voir le panier
				services.readCart();
				break;
			case 8: // choisir un compte client
				idCustomer = chooseClientAccount(scan, services);
				break;
			case 9: // payer
				pay(scan, services, idCustomer);
				break;
			case 10:
				System.exit(0);
				break;
			default:
				System.out.println("Saisissez une valeur valide !");
				break;
			}
		}
	}

	private static int chooseClientAccount(Scanner scan, IE_ServicesImpl services) {
		int idCustomer;
		System.out.println("Client chez nous Connecter vous : \n");
		while (UserAuth.isConnected(scan, daoUser) == false) // verif connection
			UserAuth.isConnected(scan, daoUser);
		System.out.println("Bienvenu " + ((UserDao) daoUser).getUser().getLogin() + "\n");
		System.out.println("Choisissez dans la liste le client :\n");
		((UserDao) daoUser).getUserCustomers(((UserDao) daoUser).getUser().getId())
				.forEach((e -> System.out.println(e)));
		idCustomer = scan.nextInt();
		System.out.println("\nVoulez vous payer ? O/N");
		String response = scan.next();
		if (response.equals("o")) {
			services.readCart();
			System.out.println("Montant de la commande : " + services.pay() + "€");
			System.out.println("Saisissez le montant à payer à l'abri des regards !");
			double amount = scan.nextDouble();
			if (amount == services.pay())
				System.out.println("Vous commencerez un lundi à 8h veuillez acheter un stylo !");
			// stocker la commande
			services.createOrder(services.getCart(), idCustomer);
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
		System.out.println("Liste des formations de cette catégorie : \n");
		services.readAllFormationByCategory(idCategory).forEach(c -> System.out.println(c));
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

	private static void menuWelcom() {
		System.out.println(ANSI_CYAN + "\n1/ Liste des formations" + "\n2/ Afficher les détails d'une formation"
				+ "\n3/ Afficher la liste des catégories" + "\n4/ Afficher les formations par categorie"
				+ "\n5/ Ajouter une formation au panier" + "\n6/ Enlever une formation du panier"
				+ "\n7/ Voir le panier" + "\n8/ choisir un compte client" + "\n9/ Passer la commande\n" + ANSI_RESET);
	}

}
