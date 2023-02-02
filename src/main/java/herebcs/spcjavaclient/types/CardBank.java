package herebcs.spcjavaclient.types;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CardBank {

    ConcurrentHashMap<Suit, CardDeck> cardBank;

    public CardBank(Map<String, Set<Integer>> bank) {
        cardBank = new ConcurrentHashMap<>();
        var entries = bank.entrySet();
        for (var e : entries) {
            var cardValues = e.getValue();
            List<Card> cardList = new ArrayList<>();
            for (int i : cardValues) {
                Card card = new Card(Suit.valueOf(e.getKey()), i);
                cardList.add(card);
            }
            var key = Suit.valueOf(e.getKey());
            cardBank.put(key, new CardDeck(cardList));
        }
    }

    public CardDeck getDeckBySuit(Suit suit) {
        return cardBank.get(suit);
    }

    public boolean hasCardsInDeck(Suit suit) {
        return cardBank.keySet().contains(suit);
    }

    public Set<Suit> getSuitsInBank() {
        return cardBank.keySet();
    }

    @Override
    public String toString() {
        String string = new String();
        for (var e : cardBank.entrySet()) {
            string += "[ " + e.getKey() + e.getValue().toString() + " ] \n";
        }
        return string;
    }

}
