package herebcs.spcjavaclient.types;

public class Card implements Comparable<Card> {
    public Suit suit;
    public int value;

    public Card() {

    }

    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    @Override
    public int compareTo(Card o) {
        if (this.value > o.value) {
            return 1;
        } else if (this.value == o.value) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Card)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Card c = (Card) o;

        // Compare the data members and return accordingly
        return suit == c.suit && value == c.value;
    }
}

