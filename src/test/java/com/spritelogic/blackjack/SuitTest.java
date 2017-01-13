package com.spritelogic.blackjack;

import static org.junit.Assert.*;

import org.junit.Test;

public class SuitTest {
	private Suit spade = Suit.SPADE;
	private Suit diamond = Suit.DIAMOND;
	private Suit club = Suit.CLUB;
	private Suit heart = Suit.HEART;
	
	@Test
	public void suitShouldHaveVisualSymbol() {
		assertEquals("Spade symbol is wrong!", "\u2660", spade.toString());
		assertEquals("Diamond symbol is wrong!", "\u2666", diamond.toString());
		assertEquals("Club symbol is wrong!", "\u2663", club.toString());
		assertEquals("Heart symbol is wrong!", "\u2764", heart.toString());
	}

}
