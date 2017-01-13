package com.spritelogic.blackjack;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * A simple blackjack card game!
 *
 */
public class Blackjack 
{
	private static enum GameResult {
		BLACKJACK,
		PLAYER_BUSTED, 
		DEALER_BUSTED, 
		PLAYER_WIN, 
		DEALER_WIN, 
		PUSH, 
		UNKNOWN
	}
	
	// Recollect and shuffle the card when deck is under this threshold
	private final int DECK_LOW = 10;
	// Dealer deal threshold
	private final int DEALER_DEAL = 17;
	private Deck deck;
	private BlackjackHand userHand;
	private BlackjackHand dealerHand;
	private boolean inProgress;	// true when card is dealt and before stand
	
	/**
	 * Construct a Blackjack game
	 */
	public Blackjack() {
		// create a deck of card for the game of blackjack
		deck = BlackjackCard.createDeck();
		userHand = new BlackjackHand();
		dealerHand = new BlackjackHand();
		inProgress = false;
	}
	
	/**
	 * Start playing the game
	 * 
	 * @throws IOException
	 */
	public void starPlay() throws IOException {
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;
		
		// shuffle the deck
		deck.recollect();
		deck.shuffle();
		
		System.out.println("Welcome! Please make your action [1-4].");
		while(!exit) 
		{ 
			int input = prompt(scanner);
			if (!isValid(input)) {
				continue;
			}
			
			switch (input) {
				case 1: {
					inProgress = true;
					userHand.reset();
					dealerHand.reset();
					System.out.println("==============================================");
					if (userHand.emptyHand() && dealerHand.emptyHand()) {
						// start of a new game, deal two cards each
						userHand.deal(deal());
						userHand.deal(deal());

						dealerHand.deal(deal());
						dealerHand.deal(deal());
					}
					printHands(false);
					GameResult gr = evaluate(false);
					printGameResult(gr);
					System.out.println("==============================================");
					break;
				}
				case 2: {
					System.out.println("==============================================");
					// deal one card to the user
					userHand.deal(deal());
					printHands(false);
					GameResult gr = evaluate(false);
					printGameResult(gr);
					System.out.println("==============================================");
					break;
				}
				case 3: {
					inProgress = false;
					System.out.println("==============================================");
					dealerDeal(dealerHand);
					printHands(true);
					GameResult gr = evaluate(true);
					printGameResult(gr);
					System.out.println("==============================================");
					break;
				}
				case 4: {
					System.out.println("Bye!");
					exit = true;
					break;
				}
			}
		}
		
		scanner.close();
	}
	
	private int prompt(Scanner scanner) {
        System.out.print( "[1]--Deal, [2]--Hit, [3]--Stand, [4]--Exit: " );
        
        return scanner.nextInt();
	}
	
	private Card deal() {
		if (deck.remaining() < DECK_LOW) {
			deck.recollect();
			deck.shuffle();
		}
		
		return deck.deal();
	}
	
	// Print all the cards as follows:
	//
	//  Dealer:                                You:
	//	 ___    ___
	//  | ♣ |  |   |
	//  | 7 |  | ? |
	//  |___|  |___|
	private void printHands(boolean showDealerHand) {
		System.out.println("    Dealer:                                You:");
		List<Card> userCards = userHand.getCards();
		List<Card> dealerCards = dealerHand.getCards();
		int dealerHandSize = dealerCards.size();
		int leftSpaceUserCards = 39 - 5 - 7 * (dealerHandSize - 1);
		for (int row = 1; row<=4; row++) {
			boolean firstCard = true;
			for (Card card:dealerCards) {
				if (firstCard) {
					printCardRow(card, 4, row, false);
					firstCard = false;
				}
				else {
					printCardRow(card, 2, row, !showDealerHand);
				}
			}
			
			firstCard = true;	// first user card
			for (Card card:userCards) {
				if (firstCard) {
					printCardRow(card, leftSpaceUserCards, row, false);
					firstCard = false;
				}
				else {
					printCardRow(card, 2, row, false);
				}
			}
			
			System.out.println();
		}
	}
	
	// print a single row of the given card
	private void printCardRow(Card card, int leftSpaces, int row,
			boolean faceDown) {
		char[] spaceChars = new char[leftSpaces];
		for (int i=0; i<spaceChars.length; i++) {
			spaceChars[i] = ' ';
		}
		String spaceStr = new String(spaceChars);
		System.out.print(spaceStr);
		switch (row) {
		case 1:
			System.out.print(" ___ ");
			break;
		case 2:
			if (faceDown) {
				System.out.print("|   |");
			}
			else {
				System.out.print("| " + card.suit + " |");
			}
			break;
		case 3:
			if (faceDown) {
				System.out.print("| ? |");
			}
			else {
				if (card.faceValue != 10) {
					System.out.print("| " + card.faceValueStr() + " |");
				}
				else {
					System.out.print("| " + card.faceValueStr() + "|");
				}
			}
			break;
		case 4:
			System.out.print("|___|");
			break;
		}
	}
	
	// evaluate the game result
	private GameResult evaluate(boolean standCalled) {
		if (!standCalled) {	// user has not called stand
			List<Card> cards = userHand.getCards();
			// check if the user has a blackjack (Ace + 10 card)
			if (cards.size() == 2) {
				if (userHand.calculateValue() == 21) {
					inProgress = false;
					return GameResult.BLACKJACK;
				}
			}
			else {
				// check if the user has busted
				if (userHand.calculateValue() > 21) {
					inProgress = false;
					return GameResult.PLAYER_BUSTED;
				}
			}
		}
		else {
			int dealerValue = dealerHand.calculateValue();
			if (dealerValue > 21) {
				return GameResult.DEALER_BUSTED;
			}
			int userValue = userHand.calculateValue();

			if (userValue == dealerValue) {
				return GameResult.PUSH;
			}
			
			if (userValue > dealerValue) {
				return GameResult.PLAYER_WIN;
			}
			else {
				return GameResult.DEALER_WIN;
			}
		}
		
		return GameResult.UNKNOWN;
	}

	// print game result
	private void printGameResult(GameResult gr) {
		String message = "";
		switch (gr) {
		case BLACKJACK:
			message = "You win! Blackjack!";
			break;
		case PLAYER_BUSTED:
			message = "You lose! Busted!";
			break;
		case DEALER_BUSTED:
			message = "You win! dealer busted!";
			break;
		case PLAYER_WIN:
			message = "You win!";
			break;
		case DEALER_WIN:
			message = "You lose!";
			break;
		case PUSH:
			message = "It's a tie (push)!";
			break;
		case UNKNOWN:
			return;
		}
		
		System.out.println(message);
	}
	
	// After stand is called, dealer need to deal as long as its value is
	// below 17
	private void dealerDeal(BlackjackHand dealerHand) {
		int value = 0;
		do {
			value = dealerHand.calculateValue();
			if (value < DEALER_DEAL) {
				dealerHand.deal(deal());
			}
			else {
				break;
			}
		} while (true);
	}
	
	// check if the user input is valid
	private boolean isValid(int input) {
		if (input == 4) {
			return true;
		}
		
		if (inProgress) {
			if (input == 2 || input == 3) {
				return true;
			}
			else {
				System.out.println("Invalid action. Please Hit or Stand!");
			}
		}
		else {
			if (input == 1) {
				return true;
			}
			else {
				System.out.println("Invalid action. Please Deal!");
			}
		}
		
		return false;
	}
	
    public static void main( String[] args ) throws IOException
    {
    	Blackjack bj = new Blackjack();
    	bj.starPlay();
    }
}
