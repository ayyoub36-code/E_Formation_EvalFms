package fr.fms.App;

import fr.fms.dao.DAOFactory;
import fr.fms.dao.Dao;
import fr.fms.dao.FormationDao;
import fr.fms.entities.Formation;

public class App {

	public static void main(String[] args) {
		// TODO couche App => menu User Admin
		// TODO creer un compte si il en a pas avant de passer la commande

		// test
		Dao<Formation> formationDao = DAOFactory.getFormationDao();
		((FormationDao) formationDao).readAllByNoFaceToFace().forEach(f -> System.out.println(f));

	}

}
