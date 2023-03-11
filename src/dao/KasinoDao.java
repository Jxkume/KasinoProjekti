package dao;

import datasource.MariaDBJPAConnector;
import jakarta.persistence.EntityManager;
import simu.model.Kasino;

//
/**
 * Luokka KasinoDaolle, jossa päivitetään tietokannassa olevan kasino-taulun arvoja
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public class KasinoDao {

	/**
	 * Päivittää kasino-taulun talonVoittoEuroina-sarakkeen arvon
	 *
	 * @param Kasino olion
	 * @return true, jos ehdot täyttyvät
	 */
	public boolean updatetalonVoittoEuroina(Kasino kasino) {
    	EntityManager em = MariaDBJPAConnector.getInstance();
    	em.getTransaction().begin();
    	Kasino kasinoo = (Kasino)em.find(Kasino.class, (Object)kasino.getNimi());
    	kasinoo.setTalonVoittoEuroina(kasino.getTalonVoittoEuroina());
    	try {
            em.getTransaction().commit();
            return true;
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
	
	/**
	 * Päivittää kasino-taulun asiakasLkm-sarakkeen arvon
	 *
	 * @param Kasino-olio
	 * @return true, jos päivitys onnistuu
	 */
	public boolean updateAsiakasLKM(Kasino kasino) {
    	EntityManager em = MariaDBJPAConnector.getInstance();
    	em.getTransaction().begin();
    	Kasino kasinoo = (Kasino)em.find(Kasino.class, (Object)kasino.getNimi());
    	kasinoo.setAsiakasLKM(kasino.getAsiakasLKM());
    	try {
            em.getTransaction().commit();
            return true;
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
	
	/**
	 * Päivittää kasino-taulun kello-sarakkeen arvon
	 *
	 * @param Kasino-olio
	 * @return true, jos päivitys onnistuu
	 */
	public boolean updateKello(Kasino kasino) {
    	EntityManager em = MariaDBJPAConnector.getInstance();
    	em.getTransaction().begin();
    	Kasino kasinoo = (Kasino)em.find(Kasino.class, (Object)kasino.getNimi());
    	kasinoo.setKello(kasino.getKello());
    	try {
            em.getTransaction().commit();
            return true;
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
	
	/**
	 * Päivittää kasino-taulun keskimaarainenVietettyAika-sarakkeen arvon
	 *
	 * @param Kasino-olio
	 * @return true, jos päivitys onnistuu
	 */
	public boolean updatekeskimaarainenVietettyAika(Kasino kasino) {
    	EntityManager em = MariaDBJPAConnector.getInstance();
    	em.getTransaction().begin();
    	Kasino kasinoo = (Kasino)em.find(Kasino.class, (Object)kasino.getNimi());
    	kasinoo.setkeskimaarainenVietettyAika(kasino.getkeskimaarainenVietettyAika());
    	try {
            em.getTransaction().commit();
            return true;
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
	
}
