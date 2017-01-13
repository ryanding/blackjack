package com.spritelogic.blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * A hand of cards for the blackjack game
 * 
 * @author Ryan Ding
 * @version 1.0
 * @since 2017
 */
public class BlackjackHand {
	private List<Card> cards;
	
	/**
	 * Constructor
	 */
	public BlackjackHand() {
		cards = new ArrayList<>();
	}
	
	/**
	 * Deal a card to this hand
	 * 
	 * @param card A card dealt to this hand 
	 */
	public void deal(Card card) {
		cards.add(card);
	}
	
	/**
	 * Calculate the best value of this hand, automatically determine
	 * if Ace is 1 or 11
	 * 
	 * @return the calculated value of this hand
	 */
	public int calculateValue() {
		int valueHard = 0;
		for (Card card:cards) {
			int cardValue = card.value();
			
			valueHard += cardValue;
		}
		
		if (valueHard > 11) {	// Soft value will be over 21
			return valueHard;
		}
		
		int valueSoft = 0;
		boolean firstAce = true;
		for (Card card:cards) {
			int cardValue = card.value();
			if (cardValue == 1 && firstAce) {	// Found first Ace
				cardValue = 11;
				firstAce = false;
			}
			
			valueSoft += cardValue;
		}
		return valueSoft;
	}
	
	/**
	 * Reset this hand. Clear all the cards.
	 */
	public void reset() {
		cards.clear();
	}
	
	/**
	 * Check if the hand is empty
	 * 
	 * @return Return true when the hand is empty
	 */
	public boolean emptyHand() {
		return cards.isEmpty();
	}
	
	/**
	 * Return the list of cards in this hand
	 * 
	 * @return Card list in this hand
	 */
	public List<Card> getCards() {
		return cards;
	}
}
