package datasource;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


/**
 * Luokka MariaDBJPAConnector yhdistää projektin paikalliseen tietokantaan
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public class MariaDBJPAConnector {
	
	private static EntityManagerFactory emf = null; //** EntityManagerFactory:n interface */
	
	private static EntityManager em = null; //** EntityManagerin interface */
	
	 /**
 	 * Asettaa yhteyden paikalliseen tietokantaan
 	 *
 	 * @return palauttaa EntityManagerin instanssin
 	 */
 	public static EntityManager getInstance() {
	        if (em == null) {
	            if (emf == null) {
	                emf = Persistence.createEntityManagerFactory((String)"DevPU");
	            }
	            em = emf.createEntityManager();
	        }
	        return em;
	    }
}
