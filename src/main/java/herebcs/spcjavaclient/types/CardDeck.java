package herebcs.spcjavaclient.types;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardDeck {

    List<Card> cardDeck;

    public CardDeck(List<Card> cardDeck) {
        this.cardDeck = cardDeck;
    }

    public CardDeck(Card[] cardArray) {
        this.cardDeck = Arrays.asList(cardArray);
    }

    public boolean containsCard(Card card) {
        for (Card c : cardDeck) {
            if (c.equals(card)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsSuit(Suit suit) {
        for (Card c : cardDeck) {
            if (c.suit == suit) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return cardDeck.isEmpty();
    }

    public boolean containsValue(int value) {
        for (Card c : cardDeck) {
            if (c.value == value) {
                return true;
            }
        }
        return false;
    }

    public Card getHighestValueCard() {
        return Collections.max(cardDeck);
    }

    public Card getLowestValueCard() {
        return Collections.min(cardDeck);
    }

    public Card getCard(Suit suit, int value) {
        for(Card c: cardDeck) {
            if(c.suit == suit && c.value == value) {
                return c;
            }
        }
        return null;
    }
}
