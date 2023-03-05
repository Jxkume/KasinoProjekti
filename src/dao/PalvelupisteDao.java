package dao;

import datasource.MariaDBJPAConnector;
import jakarta.persistence.EntityManager;
import simu.model.Palvelupiste;
import view.SimulaattorinPaaikkunaKontrolleri;


public class PalvelupisteDao extends SimulaattorinPaaikkunaKontrolleri {
	
	public boolean updateKokonaisoleskeluaika(Palvelupiste palvelupiste) {
    	EntityManager em = MariaDBJPAConnector.getInstance();
    	em.getTransaction().begin();
    	Palvelupiste palvelupistee = (Palvelupiste)em.find(Palvelupiste.class, (Object)palvelupiste.getNimi());
    	palvelupistee.setKokonaisoleskeluaika(palvelupiste.getKokonaisoleskeluaika());
    	try {
            em.getTransaction().commit();
            return true;
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
    
    public boolean updateSuoritusteho(Palvelupiste palvelupiste) {
    	EntityManager em = MariaDBJPAConnector.getInstance();
    	em.getTransaction().begin();
    	Palvelupiste palvelupistee = (Palvelupiste)em.find(Palvelupiste.class, (Object)palvelupiste.getNimi());
    	palvelupistee.setSuoritusteho(palvelupiste.getSuoritusteho(palvelupiste.getSimulointiaika()));
    	try {
            em.getTransaction().commit();
            return true;
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
    
    public boolean updateKayttoaste(Palvelupiste palvelupiste) {
    	EntityManager em = MariaDBJPAConnector.getInstance();
    	em.getTransaction().begin();
    	Palvelupiste palvelupistee = (Palvelupiste)em.find(Palvelupiste.class, (Object)palvelupiste.getNimi());
    	palvelupistee.setKayttoaste(palvelupiste.getKayttoaste(palvelupiste.getSimulointiaika()));
    	try {
            em.getTransaction().commit();
            return true;
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
	  
}
