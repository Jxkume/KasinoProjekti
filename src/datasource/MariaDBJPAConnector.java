package datasource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Luokka MariaDBJPAConnectorille, jossa yhdistetään projekti paikalliseen tietokantaan
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public class MariaDBJPAConnector {

	/** EntityManagerFactory-olio */
	private static EntityManagerFactory emf = null;

	/** EntityManager-olio */
	private static EntityManager em = null;

	/**
	 * Luo yhteyden paikalliseen tietokantaan Singleton-mallin mukaisesti
	 *
	 * @return EntityManager-olio
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
