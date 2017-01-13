package com.spritelogic.blackjack;

/**
 * The concrete class of playing card for playing blackjack. 
 * Jack, Queen and King has a different value in blackjack.
 * 
 * 
 * @author Ryan Ding
 * @version 1.0
 * @since 2017
 */
public class BlackjackCard extends Card {
	/**
	 * Construct a Blackjack card with a suit and a face value
	 * 
	 * @param suit
	 * @param faceValue
	 */
	public BlackjackCard(Suit suit, byte faceValue) {
		super(suit, faceValue);
	}

	/**
	 * Create and return a deck of Blackjack cards
	 * 
	 * @return A deck of blackjack cards
	 */
	public static Deck createDeck() {
		final byte FACE_MAX = 13;
		int totalCards = Suit.values().length*FACE_MAX;
		Card[] cards = new Card[totalCards];
		int cardIdx = 0;
		for (Suit suit : Suit.values()) {
			for (byte i=1; i<=FACE_MAX; i++) {
				cards[cardIdx++] = new BlackjackCard(suit, i);
			}
		}
		
		return new Deck(cards);
	}
	
	
	/**
	 * The value of each card in the blackjack game
	 * Jack, Queen and King all have values of 10 in blackjack
	 * 
	 * @return the card value in the game of blackjack
	 */
	@Override
	public int value() {
		return faceValue>10?10:faceValue;
	}
	
	/**
	 * Compare two blackjack cards
	 */
	@Override
	public int compareTo(Card o) {
		BlackjackCard otherBlackjackCard = (BlackjackCard)o;
		Integer thisValue = value();
		Integer otherValue = otherBlackjackCard.value();
	
		return thisValue.compareTo(otherValue);
	}
}
