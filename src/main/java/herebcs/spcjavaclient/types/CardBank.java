package herebcs.spcjavaclient.types;

import java.util.*;

public class CardBank {

    Map<Suit, CardDeck> cardBank;

    public CardBank(Map<String, Set<Integer>> bank) {

        for (var e : bank.entrySet()) {
            var cardValues = e.getValue();
            List<Card> cardList = new ArrayList<>();
            for (int i : cardValues) {
                Card card = new Card(Suit.valueOf(e.getKey()), i);
                cardList.add(card);
            }
            cardBank.put(Suit.valueOf(e.getKey()), new CardDeck(cardList));
        }
    }

    public CardDeck getDeckBySuit(Suit suit) {
        for (var e : cardBank.entrySet()) {
            if (suit == e.getKey()) {
                return e.getValue();
            }
        }
        // returns an empty deck so we dont need to check for null
        return new CardDeck(new ArrayList<>());
    }

    public boolean hasCardsInDeck(Suit suit) {
        return cardBank.keySet().contains(suit);
    }

    public Set<Suit> getSuitsInBank() {
        return cardBank.keySet();
    }

}
