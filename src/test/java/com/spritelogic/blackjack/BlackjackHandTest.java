package com.spritelogic.blackjack;

import static org.junit.Assert.*;

import org.junit.Test;

public class BlackjackHandTest {
	private BlackjackHand hand = new BlackjackHand();

	@Test
	public void cardsShouldNotBeNullAfterConstruction() {
		assertNotNull("cards not initialized.", hand.getCards());
		assertTrue("cards is not empty after construction", hand.emptyHand());
	}

	@Test
	public void handShouldHaveCardsAfterDeal() {
		hand.deal(new BlackjackCard(Suit.DIAMOND, (byte)7));
		assertFalse("hand should not be empty after deal", hand.emptyHand());
		hand.deal(new BlackjackCard(Suit.CLUB, (byte)1));
		assertEquals("Value is not calculated correctly", 18, hand.calculateValue());
		hand.deal(new BlackjackCard(Suit.HEART, (byte)4));
		assertEquals("Value is not calculated correctly", 12, hand.calculateValue());
	}
	
	@Test
	public void handShouldBeEmptyAfterReset() {
		hand.deal(new BlackjackCard(Suit.DIAMOND, (byte)7));
		hand.deal(new BlackjackCard(Suit.CLUB, (byte)1));
		assertFalse("hand should not be empty after deal", hand.emptyHand());
		hand.reset();
		assertTrue("hand should be empty after reset", hand.emptyHand());
	}
}
