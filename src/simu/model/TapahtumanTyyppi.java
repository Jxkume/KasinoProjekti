package simu.model;

/**
 * Enum-luokka, jossa määritetään simulaation tapahtumien tapahtumatyypit
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public enum TapahtumanTyyppi {
	
	/** Asiakkaan saapuminen järjestelmään ja palvelutiskille */
	ARR1,
	
	/** Asiakkaan poistuminen palvelutiskiltä */
	DEP1,
	
	/** Asiakkaan poistuminen ruletista */
	DEP2,
	
	/** Asiakkaan poistuminen Blackjackista */
	DEP3,
	
	/** Asiakkaan poistuminen Krapsista */
	DEP4,
	
	/** Asiakkaan poistuminen voittojen nostopisteeltä ja järjestelmästä */
	DEP5
	
}
