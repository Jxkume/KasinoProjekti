package dao;

import datasource.MariaDBJPAConnector;
import jakarta.persistence.EntityManager;
import simu.model.Kasino;

//
/**
 * Luokka KasinoDaolle, jossa paivitetaan tietokannassa olevan kasino-taulun arvoja
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public class KasinoDao {

	/**
	 * Paivitta kasino-taulun talonVoittoEuroina-sarakkeen arvon
	 *
	 * @param Kasino olion
	 * @return true, jos ehdot tayttyvat
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
	 * Paivitta kasino-taulun asiakasLkm-sarakkeen arvon
	 *
	 * @param Kasino-olio
	 * @return true, jos paivitys onnistuu
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
	 * Paivitta kasino-taulun kello-sarakkeen arvon
	 *
	 * @param Kasino-olio
	 * @return true, jos paivitys onnistuu
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
	 * Paivittaa kasino-taulun keskimaarainenVietettyAika-sarakkeen arvon
	 *
	 * @param Kasino-olio
	 * @return true, jos paivitys onnistuu
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
