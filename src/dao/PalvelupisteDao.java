package dao;

import datasource.MariaDBJPAConnector;
import jakarta.persistence.EntityManager;
import simu.model.Palvelupiste;
import view.SimulaattorinPaaikkunaKontrolleri;


public class PalvelupisteDao{
	
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

    public boolean updatePalveltujaasiakkaita(Palvelupiste palvelupiste) {
    	EntityManager em = MariaDBJPAConnector.getInstance();
    	em.getTransaction().begin();
    	Palvelupiste palvelupistee = (Palvelupiste)em.find(Palvelupiste.class, (Object)palvelupiste.getNimi());
    	palvelupistee.setPalvellutAsiakkaat(palvelupiste.getPalvellutAsiakkaat());
    	try {
            em.getTransaction().commit();
            return true;
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
    
    public boolean updateKeskimaarainenPalveluaika(Palvelupiste palvelupiste) {
    	EntityManager em = MariaDBJPAConnector.getInstance();
    	em.getTransaction().begin();
    	Palvelupiste palvelupistee = (Palvelupiste)em.find(Palvelupiste.class, (Object)palvelupiste.getNimi());
    	palvelupistee.setKeskimaarainenPalveluaika(palvelupiste.getKeskimaarainenPalveluaika());
    	try {
            em.getTransaction().commit();
            return true;
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
    
    public boolean updateKeskimaarainenJononpituus(Palvelupiste palvelupiste) {
    	EntityManager em = MariaDBJPAConnector.getInstance();
    	em.getTransaction().begin();
    	Palvelupiste palvelupistee = (Palvelupiste)em.find(Palvelupiste.class, (Object)palvelupiste.getNimi());
    	palvelupistee.setKeskimaarainenJononpituus(palvelupiste.getKeskimaarainenJononpituus(palvelupiste.getSimulointiaika()));
    	try {
            em.getTransaction().commit();
            return true;
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
    
    public boolean updateKeskimaarainenLapimenoaika(Palvelupiste palvelupiste) {
    	EntityManager em = MariaDBJPAConnector.getInstance();
    	em.getTransaction().begin();
    	Palvelupiste palvelupistee = (Palvelupiste)em.find(Palvelupiste.class, (Object)palvelupiste.getNimi());
    	palvelupistee.setKeskimaarainenLapimenoaika(palvelupiste.getKeskimaarainenLapimenoaika());
    	try {
            em.getTransaction().commit();
            return true;
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
    
    public boolean updateAktiiviajat(Palvelupiste palvelupiste) {
    	EntityManager em = MariaDBJPAConnector.getInstance();
    	em.getTransaction().begin();
    	Palvelupiste palvelupistee = (Palvelupiste)em.find(Palvelupiste.class, (Object)palvelupiste.getNimi());
    	palvelupistee.setAktiiviaika(palvelupiste.getAktiiviaika());
    	try {
            em.getTransaction().commit();
            return true;
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
	  
}
