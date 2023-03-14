package fr.fms.auth;

import java.util.Scanner;

import fr.fms.dao.Dao;
import fr.fms.dao.UserDao;
import fr.fms.entities.User;

public class UserAuth {

	public static boolean isConnected(Scanner scan, Dao<User> daoUser) {
		System.out.println("veuillez saisir votre login : ");
		String login = scan.next();
		System.out.println("veuillez saisir votre mot de passe : ");
		String password = scan.next();

		if (((UserDao) daoUser).verifUserAuth(login, password)) {
			return true;
		} else
			System.out.println("Veuillez saisir des identifiants valides !\n");
		return false;
	}

}
