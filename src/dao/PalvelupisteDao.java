package dao;

import datasource.MariaDBJPAConnector;
import jakarta.persistence.EntityManager;
import simu.model.Palvelupiste;

/**
 * Luokka PalvelupisteDaolle, jossa päivitetään tietokannassa olevan palvelupiste-taulun arvoja
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public class PalvelupisteDao{
	
	/**
	 * Päivittää palvelupiste-taulun kokonaisoleskeluaika-sarakkeen arvon
	 *
	 * @param Palvelupiste-olio
	 * @return true, jos päivitys onnistuu
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
     * Päivittää palvelupiste-taulun suoritusteho-sarakkeen arvon
     *
     * @param Palvelupiste-olio
     * @return true, jos päivitys onnistuu
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
     * Päivittää palvelupiste-taulun kayttoaste-sarakkeen arvon
     *
     * @param Palvelupiste-olio
     * @return true, jos päivitys onnistuu
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
     * Päivittää palvelupiste-taulun palvellutAsiakkaat-sarakkeen arvon
     *
     * @param Palvelupiste-olio
     * @return true, jos päivitys onnistuu
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
     * Päivittää palvelupiste-taulun keskimaarainenPalveluaika-sarakkeen arvon
     *
     * @param Palvelupiste-olio
     * @return true, jos päivitys onnistuu
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
     * Päivittää palvelupiste-taulun keskimaarainenJononpituus-sarakkeen arvon
     *
     * @param Palvelupiste-olio
     * @return true, jos päivitys onnistuu
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
     * Päivittää palvelupiste-taulun keskimaarainenLapimenoaika-sarakkeen arvon
     *
     * @param Palvelupiste-olio
     * @return true, jos päivitys onnistuu
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
     * Päivittää palvelupiste-taulun aktiiviaika-sarakkeen arvon
     *
     * @param Palvelupiste-olio
     * @return true, jos päivitys onnistuu
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
