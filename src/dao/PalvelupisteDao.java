package dao;

import datasource.MariaDBJPAConnector;
import jakarta.persistence.EntityManager;
import simu.model.Palvelupiste;

public class PalvelupisteDao {
	
	  public void poistaTiski(String nimi) {
		  EntityManager em = MariaDBJPAConnector.getInstance();
	      em.getTransaction().begin();
	      Palvelupiste palvelupiste = (Palvelupiste)em.find(Palvelupiste.class, (Object)nimi);
	      if (palvelupiste != null) {
	           em.remove((Object)palvelupiste);
	           em.getTransaction().commit();
	           
	        }
	        
	    }
}
