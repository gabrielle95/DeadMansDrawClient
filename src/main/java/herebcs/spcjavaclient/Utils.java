package herebcs.spcjavaclient;

import herebcs.spcjavaclient.types.Suit;

import java.util.Set;

public class Utils {

    public Suit getCardTypeScored(Set<Suit> cardSuits) {
        // cards are ranked from the best to the worst according to my score

        if (cardSuits.contains(Suit.Cannon)) {
            return Suit.Cannon;
        }
        if (cardSuits.contains(Suit.Anchor)) {
            return Suit.Anchor;
        }
        if (cardSuits.contains(Suit.Mermaid)) {
            return Suit.Mermaid;
        }
        if (cardSuits.contains(Suit.Oracle)) {
            return Suit.Oracle;
        }
        if (cardSuits.contains(Suit.Chest)) {
            return Suit.Chest;
        }
        if (cardSuits.contains(Suit.Key)) {
            return Suit.Key;
        }
        if (cardSuits.contains(Suit.Sword)) {
            return Suit.Sword;
        }
        if (cardSuits.contains(Suit.Map)) {
            return Suit.Map;
        }
        if (cardSuits.contains(Suit.Hook)) {
            return Suit.Hook;
        }
        // default :(
        return Suit.Kraken;
    }

    public Suit getCardTypeScoredEmptyOpponent(Set<Suit> cardSuits) {
        // cards are ranked from the best to the worst according to my score
        if (cardSuits.contains(Suit.Anchor)) {
            return Suit.Anchor;
        }
        if (cardSuits.contains(Suit.Oracle)) {
            return Suit.Oracle;
        }
        //decide if map or mermaid is better in HOOK.
        if (cardSuits.contains(Suit.Map)) {
            return Suit.Map;
        }
        if (cardSuits.contains(Suit.Mermaid)) {
            return Suit.Mermaid;
        }
        if (cardSuits.contains(Suit.Chest)) {
            return Suit.Chest;
        }
        if (cardSuits.contains(Suit.Key)) {
            return Suit.Key;
        }
        if (cardSuits.contains(Suit.Cannon)) {
            return Suit.Cannon;
        }
        if (cardSuits.contains(Suit.Sword)) {
            return Suit.Sword;
        }
        if (cardSuits.contains(Suit.Hook)) {
            return Suit.Hook;
        }
        // default :(
        return Suit.Kraken;
    }
}
