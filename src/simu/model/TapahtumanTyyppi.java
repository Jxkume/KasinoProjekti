package simu.model;

/**
 * Enum-luokka, jossa maaritetaan simulaation tapahtumien tapahtumatyypit
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public enum TapahtumanTyyppi {
	
	/** Asiakkaan saapuminen jarjestelmaan ja palvelutiskille */
	ARR1,
	
	/** Asiakkaan poistuminen palvelutiskilta */
	DEP1,
	
	/** Asiakkaan poistuminen ruletista */
	DEP2,
	
	/** Asiakkaan poistuminen Blackjackista */
	DEP3,
	
	/** Asiakkaan poistuminen Krapsista */
	DEP4,
	
	/** Asiakkaan poistuminen voittojen nostopisteelta ja jarjestelmasta */
	DEP5
	
}
