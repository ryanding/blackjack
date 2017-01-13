package com.spritelogic.blackjack;

import static org.junit.Assert.*;

import org.junit.Test;

public class BlackjackCardTest {
	private BlackjackCard card = new BlackjackCard(Suit.DIAMOND, (byte)3);
	private BlackjackCard faceCard = new BlackjackCard(Suit.CLUB, (byte)12);

	@Test
	public void cardShouldBeConstructedProperly() {
		assertEquals("Suit not constructed properly", Suit.DIAMOND, card.getSuit());
		assertEquals("Face value not constructed properly", (byte)3, card.faceValue());
		assertEquals("Suit not constructed properly", Suit.CLUB, faceCard.getSuit());
		assertEquals("Face value not constructed properly", (byte)12, faceCard.faceValue());
	}
	
	@Test
	public void cardValueShouldBeCorrect() {
		assertEquals("Card value is incorrect", 3, card.value());
		assertEquals("Card value is incorrect", 10, faceCard.value());
	}

	@Test
	public void cardShouldBeComparable() {
		assertTrue(faceCard.compareTo(card) > 0);
	}
	
	@Test
	public void cardShouldHaveFacevalueStr() {
		assertEquals("Face value string problem!", "3", card.faceValueStr());
		assertEquals("Face value string problem!", "Q", faceCard.faceValueStr());
	}
}
