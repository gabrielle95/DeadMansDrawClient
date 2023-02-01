package herebcs.spcjavaclient.types;

import java.util.*;

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

    public Card getHighestInSuit(Suit suit) {
        var tmpDeck = cardDeck;
        Collections.sort(tmpDeck);
        Collections.reverse(tmpDeck);
        for (Card c : tmpDeck) {
            if (c.suit == suit) {
                return c;
            }
        }
        return null;
    }

    public Card getCard(Suit suit, int value) {
        for (Card c : cardDeck) {
            if (c.suit == suit && c.value == value) {
                return c;
            }
        }
        return null;
    }

    public void removeSuitCardsCommonWith(CardDeck otherDeck) {
        List<Card> uniqueDeck = new ArrayList<>();
        for (Card c : cardDeck) {
            if (!otherDeck.containsSuit(c.suit)) {
                uniqueDeck.add(c);
            }
        }
        cardDeck = uniqueDeck;
    }

    public Set<Suit> getSuitsInDeck() {
        Set<Suit> suits = new HashSet<>();
        for (Card c : cardDeck) {
            if (!suits.contains(c.suit)) {
                suits.add(c.suit);
            }
        }
        return suits;
    }

    @Override
    public String toString() {
        return cardDeck.toString();
    }

    public int size() {
        return cardDeck.size();
    }

    public int getNumberOfCardsBySuit(Suit suit) {
        int Count = 0 ;
        for (Card c : cardDeck) {
            if (c.suit == suit) {
               Count = Count + 1;
            }
        }
        return Count;
    }

    public int getSumOfCardsValuesBySuit(Suit suit) {
        int SumOfCardsValuesBySuit = 0 ;
        for (Card c : cardDeck) {
            if (c.suit == suit) {
                SumOfCardsValuesBySuit = SumOfCardsValuesBySuit + c.value;
            }
        }
        return SumOfCardsValuesBySuit;
    }
}
