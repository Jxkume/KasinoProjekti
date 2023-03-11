package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import simu.framework.Kello;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.Asiakas;


/**
 * Luokka Asiakas-luokan metodien testaukseen
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public class AsiakasTest {

	/** Simulaation kello */
	private Kello kello = Kello.getInstance();
	
	/**
	 * Antaa asiakkaalle poletteja
	 * @result Asiakas saa vahintaan 100, ja enintaan 1000 polettia
	 */
	@Test
	@DisplayName("annaPolettejaPalvelutiskilla(): Testaa onko asiakkaan saama polettimaara vahintaan 100 ja enintaan 1000.")
	public void testArvotaanPelimaksu() {
		// Trace-level taytyy asettaa, silla sita kutsutaan asiakkaan konstruktorissa
		Trace.setTraceLevel(Level.INFO);
		Asiakas asiakas = new Asiakas();
		asiakas.annaPolettejaPalvelutiskilla();
		int poletit = asiakas.getNykyinenPolettimaara();
		Assert.assertTrue(poletit >= 100 && poletit <= 1000);
	}
	
	/**
	 * Lisaa asiakkaalle poletteja
	 * @result Asiakkaalle lisataan 100 polettia
	 *
	 */
	@Test
	@DisplayName("lisaaPoletteja(): Testaa lisataanko asiakkaalle oikea maara poletteja.")
	public void testLisaaPoletteja() {
		Trace.setTraceLevel(Level.INFO);
		Asiakas asiakas = new Asiakas();
		asiakas.lisaaPoletteja(100);
		assertEquals(100, asiakas.getNykyinenPolettimaara(), "Asiakkaalle lisattiin vaara maara poletteja");
	}
	
	/**
	 * Asettaa asiakkaalle saapumisajan
	 * @result Asiakkaan saapumisaika on 10.4
	 */
	@Test
	@DisplayName("getSaapumisaika(): Testaa asetetaanko asiakkaalle oikea saapumisaika.")
	public void testGetSaapumisaika() {
		Trace.setTraceLevel(Level.INFO);
		kello.setAika(10.4);
		Asiakas asiakas = new Asiakas();
		assertEquals(10.4, asiakas.getSaapumisaika(), "Asiakas saapui vaaraan aikaan.");
	}
	
	/**
	 * Luo listan asiakkaita ja vertaa asiakkaiden id:ta toisiinsa
	 * @result Kaikilla asiakkailla on eri id:t
	 */
	@Test
	@DisplayName("getId(): Testaa onko kaikilla asiakkailla oma id.")
	public void testGetId() {
		Trace.setTraceLevel(Level.INFO);
		// Luodaan 10 asiakasta ja asetetaan ne listaan
		ArrayList<Asiakas> asiakkaat = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			asiakkaat.add(new Asiakas());
		}
		// Luodaan lista asiakkaiden ideille ja haetaan jokaisen asiakkaan id
		int idLista[] = new int[10];
		for (int i = 0; i < 10; i++) {
			idLista[i] = asiakkaat.get(i).getId();
		}
		// Kaydaan lapi kaikki asiakkaiden id:t ja tarkistetaan ettei samaa id:ta loydy
		boolean samaId = false;
		for (int i = 0; i < idLista.length; i++) {
			int count = 0;
			for (int j = 0; i < idLista.length; i++) {
				if (idLista[i] == idLista[j]) {
					count++;
					if (count >= 2) {
						samaId = true;
					}
				}
			}
		}
		assertEquals(false, samaId, "Asiakkaille ei aseteta omaa id:ta.");
	}
	
	/**
	 * Laskee kahden asiakkaan kasinolla viettaman ajan keskiarvon
	 * @result Asiakkaiden keskimaarin viettama aika on 15.0 aikayksikkoa
	 */
	@Test
	@DisplayName("getKeskimaarainenVietettyAika(): Testaa lasketaanko asiakkaiden keskimaarin vietetty aika oikein.")
	public void testGetKeskimaarainenVietettyAika() {
		Trace.setTraceLevel(Level.INFO);
		
		// Asiakas 1 viettaa kasinolla aikaa 10.0 aikayksikkoa
		kello.setAika(10.0);
		Asiakas asiakas1 = new Asiakas();
		asiakas1.setPoistumisaika(20.0);
		asiakas1.raportti();
		
		// Asiakas 2 viettaa kasinolla aikaa 20.0 aikayksikkoa
		kello.setAika(20.0);
		Asiakas asiakas2 = new Asiakas();
		asiakas2.setPoistumisaika(40.0);
		asiakas2.raportti();

		assertEquals(15.0, asiakas1.getKeskimaarainenVietettyAika(), "Asiakkaiden keskimaarin vietetty aika lasketaan vaarin.");
	}
	
}
