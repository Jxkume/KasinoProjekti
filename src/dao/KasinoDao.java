package dao;

import datasource.MariaDBJPAConnector;
import jakarta.persistence.EntityManager;
import simu.model.Kasino;


public class KasinoDao {

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
