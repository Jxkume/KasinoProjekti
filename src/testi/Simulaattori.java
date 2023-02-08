package testi; //masdiasokdma
import simu.framework.*;
import simu.framework.Trace.Level;
import simu.model.OmaMoottori;

public class Simulaattori { //Tekstipohjainen

	public static void main(String[] args) {
		
		Trace.setTraceLevel(Level.INFO);
		Moottori m = new OmaMoottori();
		m.setSimulointiaika(100);
		m.aja();
		// hello
	}
}
