package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;


import eduni.distributions.Normal;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.Asiakas;
import simu.model.Palvelupiste;
import simu.model.TapahtumanTyyppi;

public class PalvelupisteTest {

	private Tapahtumalista tapahtumalista;
	private Palvelupiste palvelupiste = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP2, "Ruletti");
	
	@Test
	@DisplayName("isVarattu(): Testaa onko palvelupiste varattu, jos asiakas jatkaa pelaamista.")
	public void testIsVarattu() {
		// Trace-level täytyy asettaa, sillä sitä kutsutaan asiakkaan konstruktorissa
		Trace.setTraceLevel(Level.INFO);
		Asiakas asiakas = new Asiakas();
		palvelupiste.jatkaPelaamista(asiakas);
		assertEquals(false, palvelupiste.isVarattu(), "Asiakas ei jatkanut pelaamista ruletissa.");
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
		assertEquals("Asiakkaat ruletin jonossa: 2, 3, 4, 5, 6.", palvelupiste.toString(), "Tulostettiin väärä palvelupiste.");
	}
	
}
