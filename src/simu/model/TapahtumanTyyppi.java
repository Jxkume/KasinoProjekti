package simu.model;

/**
 * Luokka jossa määritetään mahdolliset tapahtumatyypit
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum TapahtumanTyyppi {
	
	/** Saapuminen järjestelmään ja palvelutiskille */
	ARR1,
	
	/** Poistuminen palvelutiskista */
	DEP1,
	
	/** Poistuminen ruletista */
	DEP2,
	
	/** Poistuminen blackjackista */
	DEP3,
	
	/** Poistuminen krapsista */
	DEP4,
	
	/** Poistuminen voittojen nostopisteelta ja järjestelmästä */
	DEP5
}
