package dao;

import datasource.MariaDBJPAConnector;
import jakarta.persistence.EntityManager;
import simu.model.Palvelupiste;

/**
 * Luokka PalvelupisteDaolle, jossa paivitetaan tietokannassa olevan palvelupiste-taulun arvoja
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public class PalvelupisteDao{
	
	/**
	 * Paivittaa palvelupiste-taulun kokonaisoleskeluaika-sarakkeen arvon
	 *
	 * @param Palvelupiste-olio
	 * @return true, jos paivitys onnistuu
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
     * Paivittaa palvelupiste-taulun suoritusteho-sarakkeen arvon
     *
     * @param Palvelupiste-olio
     * @return true, jos paivitys onnistuu
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
     * Paivittaa palvelupiste-taulun kayttoaste-sarakkeen arvon
     *
     * @param Palvelupiste-olio
     * @return true, jos paivitys onnistuu
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
     * Paivittaa palvelupiste-taulun palvellutAsiakkaat-sarakkeen arvon
     *
     * @param Palvelupiste-olio
     * @return true, jos paivitys onnistuu
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
     * Paivittaa palvelupiste-taulun keskimaarainenPalveluaika-sarakkeen arvon
     *
     * @param Palvelupiste-olio
     * @return true, jos paivitys onnistuu
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
     * Paivittaa palvelupiste-taulun keskimaarainenJononpituus-sarakkeen arvon
     *
     * @param Palvelupiste-olio
     * @return true, jos paivitys onnistuu
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
     * Paivittaa palvelupiste-taulun keskimaarainenLapimenoaika-sarakkeen arvon
     *
     * @param Palvelupiste-olio
     * @return true, jos paivitys onnistuu
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
     * Paivittaa palvelupiste-taulun aktiiviaika-sarakkeen arvon
     *
     * @param Palvelupiste-olio
     * @return true, jos paivitys onnistuu
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
