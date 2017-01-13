package com.spritelogic.blackjack;

/**
 * Enum to represent the suits of playing cards
 * 
 * @author Ryan Ding
 * @version 1.0
 * @since 2017
 */
public enum Suit {
	SPADE("\u2660"),
	DIAMOND("\u2666"),
	CLUB("\u2663"),
	HEART("\u2764");
	
	// For visual representation of the suits: ♠, ♦, ♣, ❤
	private String strSymbol;
	
	private Suit(String strSymbol) {
		this.strSymbol = strSymbol;
	}

	@Override
	public String toString() {
		return strSymbol;
	}
}
