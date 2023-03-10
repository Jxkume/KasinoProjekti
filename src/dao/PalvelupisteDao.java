package dao;

import datasource.MariaDBJPAConnector;
import jakarta.persistence.EntityManager;
import simu.model.Palvelupiste;


//
/**
 * Luokka PalvelupisteDao päivittää tietokannassa olevan palvelupisteet taulun arvot
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public class PalvelupisteDao{
	
	/**
	 * Päivittää kokonaisoleskeluaika sarakkeen arvon
	 *
	 * @param Palvelupiste olion
	 * @return true, jos ehdot täyttyvät
	 */
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
    
    /**
     * Päivittää suoritustehon sarakkeen arvon
     *
     * @param Palvelupiste olion
     * @return true, jos ehdot täyttyvät
     */
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
    
    /**
     * Päivittää kayttoasteen sarakkeen arvon
     *
     * @param Palvelupiste olion
     * @return true, jos ehdot täyttyvät
     */
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

    /**
     * Päivittää palvellutAsiakkaat sarakkeen arvon
     *
     * @param Palvelupiste olion
     * @return true, jos ehdot täyttyvät
     */
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
    
    /**
     * Päivittää keskimaarainenPalveluaika sarakkeen arvon
     *
     * @param Palvelupiste olion
     * @return true, jos ehdot täyttyvät
     */
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
    
    /**
     * Päivittää keskimaarainenJononpituus sarakkeen arvon
     *
     * @param Palvelupiste eli palvelupiste olion
     * @return true, jos ehdot täyttyvät
     */
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
    
    /**
     * Päivittää keskimaarainenLapimenoajan sarakkeen arvon
     *
     * @param Palvelupiste olion
     * @return true, jos ehdot täyttyvät
     */
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
    
    /**
     * Päivittää aktiiviajan sarakkeen arvon
     *
     * @param Palvelupiste olion
     * @return true, jos ehdot täyttyvät
     */
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
