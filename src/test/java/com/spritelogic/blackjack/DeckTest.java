package com.spritelogic.blackjack;

import static org.junit.Assert.*;

import org.junit.Test;

public class DeckTest {
	private final int TOTAL_CARDS = 52;
	
	private Deck deck = BlackjackCard.createDeck();

	@Test
	public void deckShouldBeConstructedProperly() {
		assertEquals("Number of card in the deck is incorrect", TOTAL_CARDS, 
				deck.remaining());
	}

	@Test
	public void dealRecollectShuffleShouldWork() {
		// deal 52 cards
		for (int i=0; i<TOTAL_CARDS; i++) {
			Card card = deck.deal();
			assertNotNull("Cannot deal while the deck has card", card);
			assertEquals("Remain card count incorrect", TOTAL_CARDS-i-1, 
					deck.remaining());
		}
		
		// Null should be returned if deal again
		Card card = deck.deal();
		assertNull("Card should not be returned after all cards are dealt", card);
		
		// Recollecting card should give us 52 card again
		deck.recollect();
		assertEquals("Deck is not full after recollecting", TOTAL_CARDS, 
				deck.remaining());
		// There should not be duplicate cards after recollect
		assertTrue("There are duplicate cards after recollect", noDuplicate(deck));
		
		// Shuffle should not affect the remaining card
		deck.recollect();
		int oldCount = deck.remaining();
		deck.shuffle();
		assertEquals("Deck card count changed after shuffle", oldCount, 
				deck.remaining());
		// There should not be duplicate cards after shuffle
		assertTrue("There are duplicate cards after shuffle", noDuplicate(deck));
	}
	
	// Check to make sure the deck has no duplicate
	private boolean noDuplicate(Deck deck) {
		final byte FACE_MAX = 13;
		final byte SUIT_MAX = 4;
		
		boolean[][] present = new boolean[SUIT_MAX][FACE_MAX];
		for (byte suit = 0; suit < SUIT_MAX; suit++) {
			for (byte fv = 0; fv < FACE_MAX; fv++) {
				present[suit][fv] = false;
			}
		}
		
		for (int i =  0; i < FACE_MAX*SUIT_MAX; i++) {
			Card card = deck.deal();
			Suit suit = card.suit;
			byte faceValue = card.faceValue();
			int suitIdx = 0;
			switch (card.suit) {
			case SPADE:
				suitIdx = 0;
				break;
			case DIAMOND:
				suitIdx = 1;
				break;
			case CLUB:
				suitIdx = 2;
				break;
			case HEART:
				suitIdx = 3;
				break;
			}

			if (present[suitIdx][faceValue-1] == true) {
				return false;
			}
			else {
				present[suitIdx][faceValue-1] = true;
			}
		}
		
		return true;
	}
}
