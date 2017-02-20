package com.spritelogic.blackjack;

/**
 * The abstract class of playing card with default implementations 
 * for getting the card value and card comparison
 * 
 * @author Ryan Ding
 * @version 1.0
 * @since 2017
 */
public abstract class Card implements Comparable<Card> {
	protected Suit suit;
	protected byte faceValue;

	/**
	 * Construct a card with its suit and face value
	 * 
	 * @param suit The suit of the card
	 * @param faceValue The face value of the card
	 */
	public Card(Suit suit, byte faceValue) {
		this.suit = suit;
		this.faceValue = faceValue;
	}
	
	/**
	 * @return The suit of the card
	 */
	public Suit getSuit() {
		return suit;
	}
	
	/**
	 * @return The face value of the card
	 */
	public byte faceValue() {
		return faceValue;
	}
	
	/**
	 * @return The game specific value of the card
	 */
	public int value() {
		return faceValue;
	}
	
	/**
	 * Return the String representing the face value
	 * A - 1, J - 11, Q - 12 and K - 13 etc.
	 * @return
	 */
	public String faceValueStr() {
		switch (faceValue) {
		case 1:
			return "A";
		case 11:
			return "J";
		case 12:
			return "Q";
		case 13:
			return "K";
		default:
			return Byte.valueOf(faceValue).toString();
		}
	}
	
	@Override
	public int compareTo(Card o) {
		// Default implementation simply compare the face value
		return Byte.valueOf(faceValue).compareTo(o.faceValue);
	}
}
