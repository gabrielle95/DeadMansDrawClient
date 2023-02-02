package herebcs.spcjavaclient;
import herebcs.spcjavaclient.types.Card;
import herebcs.spcjavaclient.types.CardBank;
import herebcs.spcjavaclient.types.CardDeck;
import herebcs.spcjavaclient.types.Suit;

/**
 * OctopusBrain
 */
public class OctopusBrain {

    public Double probabilityOfDrawingDuplicate;
    public Double probabilityOfDrawingOk;
    public Double possiblePointsToScore;
    public Double AvgValueOfDrawingOkSuit;

    private CardDeck playArea;
    private CardDeck discardPile;
    private CardDeck drawPile;
    private CardBank myBank;
    private CardBank opponentBank;

    public OctopusBrain(CardDeck playArea, CardDeck discardPile, CardDeck drawPile, CardBank myBank,
            CardBank opponentBank) {
        this.playArea = playArea;
        this.discardPile = discardPile;
        this.drawPile = drawPile;
        this.myBank = myBank;
        this.opponentBank = opponentBank;
    }

    public Double calcProbabilityOfDrawingDuplicate() {
        var suits = playArea.getSuitsInDeck();
        Double numberOfCards = 0.00;
        for (Suit s : suits) {
            numberOfCards = numberOfCards + drawPile.getNumberOfCardsBySuit(s);
        }
        probabilityOfDrawingDuplicate = numberOfCards / drawPile.size();
        return probabilityOfDrawingDuplicate;
    }

    public Double calcProbabilityOfDrawingOk() {
        Double probabilityOfDrawingOk = 1 - calcProbabilityOfDrawingDuplicate();
        return probabilityOfDrawingOk;
    }

    public Double calcPossiblePointsToScore() {
        // ziskat vsetky karty aj s ich hodnotami z play arei
        // ziskat vsetky karty aj s ich hodnotami z banku
        // vypocitat bodovy rozdiel, ktory ziskame pre kazdy suit a sumu.
        possiblePointsToScore = 0.00;
        var suits = playArea.getSuitsInDeck();

        for (Suit s : suits) {
            if (myBank.hasCardsInDeck(s)) {
                if (playArea.getHighestInSuit(s).value > myBank.getDeckBySuit(s).getHighestInSuit(s).value) {
                    possiblePointsToScore = possiblePointsToScore
                            + (playArea.getHighestInSuit(s).value - myBank.getDeckBySuit(s).getHighestInSuit(s).value);
                }
            } else {
                possiblePointsToScore = possiblePointsToScore + playArea.getHighestInSuit(s).value;
            }
        }
        return possiblePointsToScore;
    }

    public Double calcAvgValueOfDrawingOkSuit() {

        AvgValueOfDrawingOkSuit = 0.00;
        Double numberOfDrawingOkCards = 0.00;
        Double sumOfCardsValues = 0.00;
        var drawPileSuits = drawPile.getSuitsInDeck();
        var playAreaSuits = playArea.getSuitsInDeck();
        drawPileSuits.removeAll(playAreaSuits);

        for (Suit s : drawPileSuits) {
            numberOfDrawingOkCards = numberOfDrawingOkCards + drawPile.getNumberOfCardsBySuit(s);
            sumOfCardsValues = sumOfCardsValues + drawPile.getSumOfCardsValuesBySuit(s);
        }
        AvgValueOfDrawingOkSuit = sumOfCardsValues / numberOfDrawingOkCards;

        return AvgValueOfDrawingOkSuit;
    }
}