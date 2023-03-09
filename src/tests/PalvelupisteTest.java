package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;

import de.saxsys.javafx.test.JfxRunner;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.Asiakas;
import simu.model.Palvelupiste;
import simu.model.TapahtumanTyyppi;
import view.SimulaattorinPaaikkunaKontrolleri;

@RunWith(JfxRunner.class)
public class PalvelupisteTest {

	private Tapahtumalista tapahtumalista;
	private Palvelupiste palvelupiste = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP2, "Ruletti");
	private Kello kello = Kello.getInstance();
	private SimulaattorinPaaikkunaKontrolleri kontrolleri = new SimulaattorinPaaikkunaKontrolleri();
	
	@Test
	@DisplayName("isVarattu(): Testaa onko palvelupiste varattu, jos asiakas jatkaa pelaamista.")
	public void testIsVarattu() {
		Trace.setTraceLevel(Level.INFO);
		Asiakas asiakas = new Asiakas();
		palvelupiste.jatkaPelaamista(asiakas);
		assertEquals(false, palvelupiste.isVarattu(), "Asiakas ei jatkanut pelaamista ruletissa.");
	}
	
	@Test
	@DisplayName("getSuoritusteho(): Testaa lasketaanko palvelupisteen suoritusteho oikein.")
	public void testGetSuoritusteho() {
		palvelupiste.setPalvellutAsiakkaat(10);
		double suoritusteho = palvelupiste.getSuoritusteho(25.0);
		assertEquals(0.4, suoritusteho, "Palvelupisteen suoritusteho lasketaan väärin.");
	}
	
	@Test
	@DisplayName("getLapimenoaika(): Testaa lasketaanko asiakkaan läpimenoaika oikein.")
	public void testGetLapimenoaika() {
		Trace.setTraceLevel(Level.INFO);
		Asiakas asiakas = new Asiakas();
		kello.setAika(1.0);
		palvelupiste.lisaaJonoon(asiakas);
		kello.setAika(9.2);
		palvelupiste.otaJonosta(asiakas);
		palvelupiste.laskeLapimenoaika(asiakas);
		palvelupiste.getLapimenoaika(asiakas);
		assertEquals(8.2, palvelupiste.getLapimenoaika(asiakas), "Asiakkaan läpimenoaika lasketaan väärin.");
	}

	@Test
	@DisplayName("arvotaanPelimaksu(): Testaa, että peli voi maksaa enintään yhtä paljon kuin asiakkaan nykyinen polettimäärä")
	public void testArvotaanPelimaksu() {
		Trace.setTraceLevel(Level.INFO);
		Asiakas asiakas = new Asiakas();
		// Asiakkaalle annetaan 70 polettia, eli peli ei voi maksaa enempää kuin 70 polettia
		asiakas.lisaaPoletteja(70);
		int pelimaksu = palvelupiste.arvotaanPelimaksu(asiakas);
		Assert.assertTrue(pelimaksu <= 70);
	}
	
	public int arvotaanPelimaksu(Asiakas asiakas) {
		// Arvotaan pelin maksu 50-100 polettia ottaen huomioon asiakkaan polettimäärä
		int maksimi;
		if (asiakas.getNykyinenPolettimaara() < 100) {
			maksimi = asiakas.getNykyinenPolettimaara() / 10;
		} else {
			maksimi = 10;
		}
		int poletteja = (int) Math.floor(Math.random() * (maksimi - 5 + 1) + 5) * 10;
		return poletteja;
	}
	
	@Test
	@DisplayName("toString(): Testaa tulostetaanko asiakkaat oikealta palvelupisteeltä.")
	public void testToString() {
		// Trace-level täytyy asettaa, sillä sitä kutsutaan asiakkaan konstruktorissa
		Trace.setTraceLevel(Level.INFO);
		// Luodaan 5 asiakasta ja laitetaan ne palvelupisteen jonoon
		for (int i = 0; i < 5; i++) {
			Asiakas asiakas = new Asiakas();
			palvelupiste.lisaaJonoon(asiakas);
		}
		assertEquals("Asiakkaat ruletin jonossa: 3, 4, 5, 6, 7.", palvelupiste.toString(), "Tulostettiin väärä palvelupiste.");
	}

}
