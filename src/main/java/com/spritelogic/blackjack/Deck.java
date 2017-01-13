package com.spritelogic.blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Deck represents a deck of playing cards.
 * The constructor takes an array of cards.
 * The typical usage of a deck of cards in a game is the following:
 *
 * 1. Create an instance of Deck
 * 2. Shuffle the deck
 * 3. Deal the card repeatedly until the end of one game
 * 4. Recollect all the cards
 * 
 * To start another game, repeat steps 2 to 4.
 * 
 * @author Ryan Ding
 * @version 1.0
 * @since 2017
 */
public class Deck {
	private Card[] cards;
	private int count;

	/**
	 * Constructor that takes an array of cards
	 * 
	 * @param cards All the cards for this deck
	 */
	public Deck(Card[] cards) {
		this.cards = cards;
		if (cards != null) {
			count = cards.length;
		}
		else {
			count = 0;
		}
	}
	
	/**
	 * Return the number of cards remaining on the deck
	 * 
	 * @return number of cards remaining on the deck
	 */
	public int remaining() {
		return count;
	}
	
	/**
	 * Recollect all the cards after a game.
	 * We'll randomly pick the 52 cards one by one from
	 * the original array of cards simulating putting all
	 * the cards together randomly at the end of a game.
	 */
	public void recollect() {
		if (cards == null) {
			return;
		}
		
		// put all the cards into a list
		List<Card> cardList = new ArrayList<>(Arrays.asList(cards));
		
		// reset the count
		count = cards.length;
		Random rand = new Random();
		for (int i=count; i>1; i--) {
			// randomly pick a card and put on the deck
			int pickIndex = rand.nextInt(i);
			cards[count-i] = cardList.remove(pickIndex);
		}
		
		// put the last remaining card on the deck
		cards[count-1] = cardList.get(0);
	}
	
	/**
	 * Shuffle the deck of card. It shuffles only those that have not 
	 * been dealt yet.
	 * We'll simulate how human shuffle a deck cards: Split the cards
	 * roughly in half and then wave them.
	 */
	public void shuffle() {
		if (cards == null) {
			return;
		}
		
		// if we have less than 2 cards, do nothing
		if (count < 2) {
			return;
		}
		
		// we'll need to use random numbers a lot
		Random rand = new Random();
		
		// make a copy of the array, we only shuffle what's remaining
		// on the deck
		Card[] cardCopy = new Card[count];
		System.arraycopy(cards, 0, cardCopy, 0, count);
		
		// cut the deck roughly in half with up to 10% margin
		int half = count / 2;
		int margin = (int) (count*0.1);
		int randMargin = rand.nextInt(margin);	// up to 10% randomly
		int cut = half - margin/2 + randMargin;
		int i = 0;		// index of the first half
		int j=cut+1;	// index of the second half
		int deckIndex = 0;	// index of the deck
		do {
			// randomly pick of the two halves and put the card on the deck
			// Randomly generated a number between 0 to 100, if a random number
			// is less than 49 (inclusive), first half otherwise second half.
			int coinFlip = rand.nextInt(100);
			if (coinFlip < 50) {
				cards[deckIndex++] = cardCopy[i++];
			}
			else {
				cards[deckIndex++] = cardCopy[j++];
			}
		
		} while (i<=cut && j<=count-1);
		
		// put whatever still remaining from either half back to the deck
		int s = 0;	// start index of the remaining card (inclusive)
		int e = 0;	// end index of the remain card (exclusive)
		if (i == cut+1) {	// exhausted the first half first
			s = j;
			e = count;
		}
		else {
			s = i;
			e = cut+1;
		}
		for (int k=s; k<e; k++) {
			cards[deckIndex++] = cardCopy[k];
		}
	}
	
	/**
	 * Deal a single card. Returns null when there is no more card to deal
	 * 
	 * @return A card
	 */
	public Card deal() {
		if (cards == null || count == 0) {
			return null;
		}
		
		int totalCards = cards.length;
		return cards[totalCards-count--];
	}
}
