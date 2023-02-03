package herebcs.spcjavaclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import herebcs.spcjavaclient.rest.EffectResponse;
import herebcs.spcjavaclient.rest.Event;
import herebcs.spcjavaclient.rest.Status;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import herebcs.spcjavaclient.types.Card;
import herebcs.spcjavaclient.types.CardBank;
import herebcs.spcjavaclient.types.CardDeck;
import herebcs.spcjavaclient.types.Suit;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

/**
 *
 * @author I816768
 */
public class Match {

    private String matchID;
    private final String tags;
    private final String playerID;
    private final String BASE_URL;
    private final String password;
    private CardDeck drawPile;
    private CardDeck discardPile;

    public Match(String matchID, String tags, String playerID, String password, String BASE_URL) {
        this.matchID = matchID;
        this.tags = tags;
        this.playerID = playerID;
        this.BASE_URL = BASE_URL;
        this.password = password;
    }

    private CardDeck initializeDrawPile() {
        drawPile = new CardDeck(new ArrayList<>());
        Suit[] allSuits = { Suit.Anchor, Suit.Cannon, Suit.Chest, Suit.Hook, Suit.Key, Suit.Kraken, Suit.Map,
                Suit.Oracle, Suit.Sword };
        Suit[] mermaidSuit = { Suit.Mermaid };

        for (Suit s : allSuits) {
            for (int value = 3; value <= 7; value++) {
                drawPile.addToDeck(new Card(s, value));
            }
        }

        for (Suit s : mermaidSuit) {
            for (int value = 5; value <= 9; value++) {
                drawPile.addToDeck(new Card(s, value));
            }
        }
        System.out.println("Draw Pile was initialized with size of: " + drawPile.cardDeck.size());
        return drawPile;
    }

    private CardDeck initializeDiscardPile() {
        discardPile = new CardDeck(new ArrayList<>());
        Suit[] allSuits = { Suit.Anchor, Suit.Cannon, Suit.Chest, Suit.Hook, Suit.Key, Suit.Kraken, Suit.Map,
                Suit.Oracle, Suit.Sword };

        for (Suit s : allSuits) {
            discardPile.addToDeck(new Card(s, 2));
        }

        discardPile.addToDeck(new Card(Suit.Mermaid, 4));

        System.out.println("Discard Pile was initialized with size of: " + discardPile.cardDeck.size());
        return discardPile;
    }

    private CardDeck removeCardFromDrawPile(Card card) {
        drawPile.cardDeck.remove(card);
        System.out.println(card + " was removed from Draw Pile and the Draw Pile size is now: " + drawPile.cardDeck.size());
        return drawPile;
    }

    public void play() throws IOException {
        var client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .authenticator((Route route, Response response) -> {
                    if (response.request().header("Authorization") != null) {
                        return null; // Give up, we've already attempted to authenticate.
                    }

                    String credential = Credentials.basic(playerID, password);
                    return response.request().newBuilder()
                            .header("Authorization", credential)
                            .build();
                })
                .build();

        if (matchID == null) {
            waitForMatch(client);
        }

        if (drawPile == null) {
            initializeDrawPile();
        }

        if (discardPile == null) {
            initializeDiscardPile();
        }
        
        System.out.println(playerID + ": playing match " + matchID + ".");

        Event lastDrawEvent = null;
        Event cardPlacedToPlayArea = null;
        do {
            Status status = null;

            while (status == null) {
                status = getStatus(client, true);
            }

            if (status.state.currentPlayerIndex == null) {
                if (status.state.winnerIdx == null) {
                    System.out.println(playerID + ": The game ended in a draw.");
                } else {
                    var winnerID = status.playerids[status.state.winnerIdx];
                    if (winnerID.equals(playerID)) {
                        System.out.println(playerID + ": I won.");
                    } else {
                        System.out.println(playerID + ": I lost.");
                    }
                }
                System.out.println("Draw pile at the end of game: " + drawPile);
                break;
            }

            if (status.state.pendingEffect == null) {
                if (status.state.playArea.length == 0) {
                    //toto nastane pri zaciatku hry a pri kazdom buste, vtedy sa dobre nepremaze drawpile.
                    lastDrawEvent = draw(client);
                } else {

                    CardBank opponentCardBank = new CardBank(status.state.banks[1 - status.state.currentPlayerIndex]);
                    CardBank myCardBank = new CardBank(status.state.banks[status.state.currentPlayerIndex]);
                    CardDeck playAreaDeck = new CardDeck(status.state.playArea);

                    OctopusBrain brain = new OctopusBrain(playAreaDeck, discardPile, drawPile, myCardBank, opponentCardBank);

                    var drawOkProb = brain.calcProbabilityOfDrawingOk();
                    var possiblePointsToScore = brain.calcPossiblePointsToScore();
                    var avgValueOfDrawingOkSuit = brain.calcAvgValueOfDrawingOkSuit();
                    var avgPointsScoredByDrawing = drawOkProb * (possiblePointsToScore + avgValueOfDrawingOkSuit);

                    // System.out.println("drawOkProb: " + drawOkProb);
                     System.out.println("possiblePointsToScore: " + possiblePointsToScore);
                    // System.out.println("avgValueOfDrawingOkSuit: " + avgValueOfDrawingOkSuit);
                     System.out.println("avgPointsScoredByDrawing: " + avgPointsScoredByDrawing);

                    if (possiblePointsToScore > avgPointsScoredByDrawing) {
                        //stop(client);

                        if (cardPlacedToPlayArea == null) {
                            System.out.println("Anchor -> Last cardPlacedToPlayArea was empty");
                            stop(client);
                        }
                        else {
                            if(cardPlacedToPlayArea.cardPlacedToPlayAreaCard == null) {
                                stop(client);
                            }
                            Card cardPlacedToPlayAreaCard = new Card(cardPlacedToPlayArea.cardPlacedToPlayAreaCard.suit, cardPlacedToPlayArea.cardPlacedToPlayAreaCard.value);
                            if (cardPlacedToPlayArea.cardPlacedToPlayAreaCard.suit == Suit.Anchor) {
                                System.out.println("Anchor -> keep drawing, last card was: " + cardPlacedToPlayAreaCard);
                                lastDrawEvent = draw(client);
                            } else {
                                stop(client);
                            }
                        }
                    } else {
                        lastDrawEvent = draw(client);
                    }
                }
            } else {
                var orig = Suit.valueOf(status.state.pendingEffect.effectType);

                switch (status.state.pendingEffect.effectType) {
                    //partly finished
                    case "Oracle" -> {
                        Card revealedCard = status.state.pendingEffect.cards[0];
                        System.out.println("Oracle -> Revelead card: " + revealedCard);
                        CardDeck playAreaDeck = new CardDeck(status.state.playArea);
                        Card card;
                        card = null;

                        if (playAreaDeck.containsSuit(revealedCard.suit)) {
                            System.out.println("Oracle -> don't draw, there is a card with same suit in play area");
                            stop(client);
                        } else {
                            if (revealedCard.suit == Suit.Kraken && playAreaDeck.cardDeck.size() > 2) {
                                System.out.println("Oracle -> don't draw, there are more than 2 cards in play area and Kraken might bust us");
                                stop(client);
                            } else {
                                card = revealedCard;
                                System.out.println("Oracle -> draw, there is no duplicate in play area");
                            }
                            //todo: hook and then bust care. sword and then bust care => both minor use cases, finish only when time is left.
                        }
                        cardPlacedToPlayArea = respond(client, orig, card);
                    }
                    //partly finished.
                    case "Hook" -> {
                        CardBank opponentCardBank = new CardBank(status.state.banks[1 - status.state.currentPlayerIndex]);
                        CardBank myCardBank = new CardBank(status.state.banks[status.state.currentPlayerIndex]);

                        var opponentCardSuits = new HashSet<>(opponentCardBank.getSuitsInBank());
                        var myCardSuits = new HashSet<>(myCardBank.getSuitsInBank());

                        CardDeck playAreaDeck = new CardDeck(status.state.playArea);
                        var playAreaCardSuits = new HashSet<>(playAreaDeck.getSuitsInDeck());

                        myCardSuits.removeAll(playAreaCardSuits);

                        //todo: calculate diff in this scenario
                        Utils utils = new Utils();
                        Card responseCard;
                        if (myCardSuits.isEmpty()) { // Ak mozem vybrat od seba len nieco co uz je na play area
                            CardDeck highestCards = new CardDeck(new ArrayList<>());
                            for (Suit o : playAreaCardSuits) {
                                var deck = myCardBank.getDeckBySuit(o);
                                if(deck != null){
                                    highestCards.addToDeck(deck.getHighestValueCard()); // we have to return the highest card of suit -  rulebook.
                                }
                                deck = null;
                            }
                            responseCard = highestCards.getLowestValueCard();
                        } else {
                            Suit chosenSuit;
                            if (opponentCardSuits.isEmpty()) {
                                //nema ziadne karty - urobme dake kombo ak sa da pre seba

                                boolean myBankHasChest = myCardBank.hasCardsInDeck(Suit.Chest);
                                boolean myBankHasKey = myCardBank.hasCardsInDeck(Suit.Key);
                                boolean playAreaHasChest = playAreaDeck.containsSuit(Suit.Chest);
                                boolean playAreaHasKey = playAreaDeck.containsSuit(Suit.Key);
                                
                                if(playAreaHasChest == true && myBankHasKey == true){
                                    chosenSuit = Suit.Key;
                                } else if(playAreaHasKey == true && myBankHasChest == true){
                                    chosenSuit = Suit.Chest;
                                } else {
                                //if chest key combo is not possible, take anchor/oracle/map...etc
                                chosenSuit = utils.getCardTypeScoredEmptyOpponent(myCardSuits);
                                }
                            } else {
                                // Ma nejake karty. Podme mu ublizit, ak sa da.
                                boolean opponentHasChest = opponentCardBank.hasCardsInDeck(Suit.Chest);
                                boolean opponentHasKey = opponentCardBank.hasCardsInDeck(Suit.Key);
                                boolean opponentHasAnchor = opponentCardBank.hasCardsInDeck(Suit.Anchor);
                                boolean opponentHasCannon = opponentCardBank.hasCardsInDeck(Suit.Cannon);
                                boolean myBankHasCannon = myCardBank.hasCardsInDeck(Suit.Cannon);
                                boolean myBankHasChest = myCardBank.hasCardsInDeck(Suit.Chest);
                                boolean myBankHasKey = myCardBank.hasCardsInDeck(Suit.Key);
                                boolean myBankHasAnchor = myCardBank.hasCardsInDeck(Suit.Anchor);
                                boolean myBankHasOracle = myCardBank.hasCardsInDeck(Suit.Oracle);
                                boolean playAreaHasOracle = playAreaDeck.containsSuit(Suit.Oracle);
                                boolean playAreaHasAnchor = playAreaDeck.containsSuit(Suit.Anchor);
                                boolean playAreaHasCannon = playAreaDeck.containsSuit(Suit.Cannon);
                                boolean playAreaHasChest = playAreaDeck.containsSuit(Suit.Chest);
                                boolean playAreaHasKey = playAreaDeck.containsSuit(Suit.Key);
                                boolean myCardSuitHasSword = playAreaDeck.containsSuit(Suit.Sword);
                                boolean myCardSuitHasCannon = playAreaDeck.containsSuit(Suit.Cannon);
                                boolean myCardSuitHasKey = playAreaDeck.containsSuit(Suit.Key);
                                boolean myCardSuitHasChest = playAreaDeck.containsSuit(Suit.Chest);
                                boolean myCardSuitHasAnchor = playAreaDeck.containsSuit(Suit.Anchor);
                                boolean myCardSuitHasOracle = playAreaDeck.containsSuit(Suit.Oracle);

                                if (myCardSuitHasSword == true && myBankHasCannon == false && opponentHasCannon == true && playAreaHasCannon == false ){
                                    chosenSuit = Suit.Sword;
                                }else if (myCardSuitHasSword == true && myBankHasAnchor == false && opponentHasAnchor == true && playAreaHasAnchor == false ){
                                    chosenSuit = Suit.Sword;
                                }else if (myCardSuitHasSword == true && myBankHasKey == false && opponentHasKey == true && playAreaHasKey == false ){
                                    chosenSuit = Suit.Sword;
                                }else if (myCardSuitHasSword == true && myBankHasChest == false && opponentHasChest == true && playAreaHasChest == false ){
                                    chosenSuit = Suit.Sword;
                                }else if(myCardSuitHasCannon == true && myBankHasCannon == true && playAreaHasCannon == false){
                                    chosenSuit = Suit.Cannon;
                                } else if(myCardSuitHasKey == true && playAreaHasChest == true && myBankHasKey == true){
                                    chosenSuit = Suit.Key;
                                } else if(myCardSuitHasChest == true && playAreaHasKey == true && myBankHasChest == true){
                                    chosenSuit = Suit.Chest;
                                } else if(myCardSuitHasAnchor == true && myBankHasAnchor == true && playAreaHasAnchor == false){
                                    System.out.println("Hook -> anchor if:" );
                                    chosenSuit = Suit.Anchor;
                                } else if(myCardSuitHasOracle == true && myBankHasOracle == true && playAreaHasOracle == false){
                                    chosenSuit = Suit.Oracle;
                                } else {
                                    chosenSuit = utils.getCardTypeScored(myCardSuits);
                                }
                            }
                            // vyberam od seba a musim top kartu, lebo tu pod tym nemozem, je to backup
                            System.out.println("Hook -> chosen suit: " + chosenSuit );
                            responseCard = myCardBank.getDeckBySuit(chosenSuit).getHighestValueCard();
                            System.out.println("Hook -> will not bust, we take: " + responseCard );
                        }
                        cardPlacedToPlayArea = respond(client, orig, responseCard);
                    }
                    case "Sword" -> {
                        CardBank opponentCardBank = new CardBank(status.state.banks[1 - status.state.currentPlayerIndex]);
                        CardBank myCardBank = new CardBank(status.state.banks[status.state.currentPlayerIndex]);
                        var opponentCardSuits = new HashSet<>(opponentCardBank.getSuitsInBank());
                        var opponentCardSuitsNotCleared = new HashSet<>(opponentCardBank.getSuitsInBank());
                        
                        var myCardSuits = myCardBank.getSuitsInBank();
                        CardDeck playAreaDeck = new CardDeck(status.state.playArea);
                        var playAreaCardSuits = playAreaDeck.getSuitsInDeck();
                        Utils utils = new Utils();
                        Card responseCard = null;

                        // Remove Suits we have in bank -> we cant steal those.
                        opponentCardSuits.removeAll(myCardSuits);
                        opponentCardSuitsNotCleared.removeAll(myCardSuits);

                        // Remove suits that are in play area -> so we dont bust.
                        opponentCardSuits.removeAll(playAreaCardSuits);

                        //There is no other option -> we must bust.
                        if (opponentCardSuits.isEmpty()) {
                            //Skopceny Cannon, znicit mu najvacsi diff, ubliz mu co najviac, aj ked bustnes.
                            Suit preferredSuit = null;
                            Integer maxDiff = 0;
                            for (Suit s : opponentCardSuitsNotCleared) {
                                List<Card> tmpCardsList = opponentCardBank.getDeckBySuit(s).cardDeck;
                                if (tmpCardsList != null) {
                                    if (tmpCardsList.size() == 1 && maxDiff < tmpCardsList.get(0).value) {
                                        maxDiff = tmpCardsList.get(0).value;
                                        preferredSuit = s;
                                    }
                                    if (tmpCardsList.size() > 1) {
                                        Collections.sort(tmpCardsList, Collections.reverseOrder());
                                        int tmpDiff = tmpCardsList.get(0).value - tmpCardsList.get(1).value;
                                        if (maxDiff < tmpDiff) {
                                            maxDiff = tmpDiff;
                                            preferredSuit = s;
                                        }
                                    }
                                }
                            }
                            if (maxDiff < 2) {
                                preferredSuit = utils.getCardTypeScored(opponentCardSuitsNotCleared);
                            }
                            responseCard = opponentCardBank.getDeckBySuit(preferredSuit).getHighestValueCard();
                            System.out.println("Sword -> will bust, but we atleast destroy biggest diff of: " + maxDiff + " to destroy " + responseCard );
                        
                        // We will not bust and can use sword to steal + gain.
                        } else {
                            boolean playAreaHasAnchor = playAreaDeck.containsSuit(Suit.Anchor);
                            boolean playAreaHasCannon = playAreaDeck.containsSuit(Suit.Cannon);
                            boolean playAreaHasChest = playAreaDeck.containsSuit(Suit.Chest);
                            boolean playAreaHasKey = playAreaDeck.containsSuit(Suit.Key);
                            
                            if(opponentCardSuits.contains(Suit.Cannon) == true && playAreaHasCannon == false){
                                responseCard = opponentCardBank.getDeckBySuit(Suit.Cannon).getHighestValueCard();
                            } else if(opponentCardSuits.contains(Suit.Anchor)  == true && playAreaHasAnchor == false){
                                responseCard = opponentCardBank.getDeckBySuit(Suit.Anchor).getHighestValueCard();
                            } else if(playAreaHasChest == true && opponentCardSuits.contains(Suit.Key) ){
                                responseCard = opponentCardBank.getDeckBySuit(Suit.Key).getHighestValueCard();
                            } else if(playAreaHasKey == true && opponentCardSuits.contains(Suit.Chest) ){
                                responseCard = opponentCardBank.getDeckBySuit(Suit.Chest).getHighestValueCard();
                            } else {
                            Suit chosenCardSuit = utils.getCardTypeScored(opponentCardSuits);
                            responseCard = opponentCardBank.getDeckBySuit(chosenCardSuit).getHighestValueCard();
                            System.out.println("Sword -> will not bust, we take: " + responseCard );
                            }
                        }
                        cardPlacedToPlayArea = respond(client, orig, responseCard);
                    }
                    //finished
                    case "Cannon" -> {
                        CardBank opponentCardBank = new CardBank(status.state.banks[1 - status.state.currentPlayerIndex]);
                        var opponentCardSuits = opponentCardBank.getSuitsInBank();

                        Card responseCard = null;
                        Utils utils = new Utils();
                        if (!opponentCardSuits.isEmpty()) {
                            Suit preferredSuit = null;
                            Integer maxDiff = 0;
                            for (Suit s : opponentCardSuits) {
                                List<Card> tmpCardsList = opponentCardBank.getDeckBySuit(s).cardDeck;
                                if (tmpCardsList != null) {
                                    if (tmpCardsList.size() == 1 && maxDiff < tmpCardsList.get(0).value) {
                                        maxDiff = tmpCardsList.get(0).value;
                                        preferredSuit = s;
                                    }
                                    if (tmpCardsList.size() > 1) {
                                        Collections.sort(tmpCardsList, Collections.reverseOrder());
                                        int tmpDiff = tmpCardsList.get(0).value - tmpCardsList.get(1).value;
                                        if (maxDiff < tmpDiff) {
                                            maxDiff = tmpDiff;
                                            preferredSuit = s;
                                        }
                                    }
                                }
                            }
                            if (maxDiff < 2) {
                                preferredSuit = utils.getCardTypeScored(opponentCardSuits);
                            }

                            responseCard = opponentCardBank.getDeckBySuit(preferredSuit).getHighestValueCard();
                            System.out.println("Cannon -> biggest diff of: " + maxDiff + " to destroy " + responseCard );
                        }
                        cardPlacedToPlayArea = respond(client, orig, responseCard);
                    }
                    //finished
                    case "Kraken" -> {
                        lastDrawEvent = draw(client);
                    }
                    //finished
                    case "Chest" -> {
                        var card = status.state.pendingEffect.cards[0];
                        cardPlacedToPlayArea = respond(client, orig, card);
                    }
                    //finished
                    case "Key" -> {
                        var card = status.state.pendingEffect.cards[0];
                        cardPlacedToPlayArea = respond(client, orig, card);
                    }
                    //finished
                    case "Anchor" -> {
                        System.out.println("Anchor -> draw, we are safe");
                        lastDrawEvent = draw(client);
                    }
                    case "Map" -> {
                        Card responseCard = null;
                        CardDeck mapChoicesDeck = new CardDeck(status.state.pendingEffect.cards);

                        // If there is no discarded card, then the next card must be drawn the standard
                        // way
                        if (mapChoicesDeck.isEmpty()) {
                            lastDrawEvent = draw(client);
                            break;
                        }

                        CardDeck playAreaDeck = new CardDeck(status.state.playArea);
                        mapChoicesDeck.removeSuitCardsCommonWith(playAreaDeck);

                        // if no usable cards are found we have to respond even if we loose
                        if (mapChoicesDeck.isEmpty()) {
                            responseCard = status.state.pendingEffect.cards[0];
                            cardPlacedToPlayArea = respond(client, orig, responseCard);
                            break;
                        }

                        CardBank opponentBank = new CardBank(status.state.banks[1 - status.state.currentPlayerIndex]);
                        Set<Suit> opponentCardTypes = opponentBank.getSuitsInBank();
                        CardBank myCardBank = new CardBank(status.state.banks[status.state.currentPlayerIndex]);
                        boolean playAreaHasChest = playAreaDeck.containsSuit(Suit.Chest);
                        boolean playAreaHasKey = playAreaDeck.containsSuit(Suit.Key);
                        boolean playAreaNeedsChest = playAreaHasKey && !playAreaHasChest;
                        boolean playAreaNeedsKey = playAreaHasChest && !playAreaHasKey;
                        boolean opponentHasChest = opponentBank.hasCardsInDeck(Suit.Chest);
                        boolean opponentHasKey = opponentBank.hasCardsInDeck(Suit.Key);
                        boolean opponentHasAnchor = opponentBank.hasCardsInDeck(Suit.Anchor);
                        boolean opponentHasCannon = opponentBank.hasCardsInDeck(Suit.Cannon);
                        boolean myBankHasCannon = myCardBank.hasCardsInDeck(Suit.Cannon);
                        boolean myBankHasChest = myCardBank.hasCardsInDeck(Suit.Chest);
                        boolean myBankHasKey = myCardBank.hasCardsInDeck(Suit.Key);
                        boolean myBankHasAnchor = myCardBank.hasCardsInDeck(Suit.Anchor);
                        boolean myBankHasOracle = myCardBank.hasCardsInDeck(Suit.Oracle);
                        boolean playAreaHasOracle = playAreaDeck.containsSuit(Suit.Oracle);
                        boolean playAreaHasAnchor = playAreaDeck.containsSuit(Suit.Anchor);
                        boolean playAreaHasCannon = playAreaDeck.containsSuit(Suit.Cannon);

                        Utils utils = new Utils();

                        if (opponentCardTypes.isEmpty()) { // responds with either chest, key or highest possible value
                            if (playAreaNeedsChest) {
                                responseCard = mapChoicesDeck.getHighestInSuit(Suit.Chest);
                            } else if (playAreaNeedsKey) {
                                responseCard = mapChoicesDeck.getHighestInSuit(Suit.Key);
                            }
                            if (responseCard == null) {
                                var suits = mapChoicesDeck.getSuitsInDeck();
                                var prioSuit = utils.getCardTypeScoredEmptyOpponent(suits);
                                responseCard = mapChoicesDeck.getHighestInSuit(prioSuit);
                            }
                        } else { //decide what to choose depending on oponent card values vs play area values
                            var suits = mapChoicesDeck.getSuitsInDeck();
                            System.out.println("Map -> suits: " + suits);

                            boolean mapHasSword = suits.contains(Suit.Sword);
                            boolean mapHasCannon = suits.contains(Suit.Cannon);
                            boolean mapHasKey = suits.contains(Suit.Key);
                            boolean mapHasChest = suits.contains(Suit.Chest);
                            boolean mapHasAnchor = suits.contains(Suit.Anchor);
                            boolean mapHasOracle = suits.contains(Suit.Oracle);
                            
                            //TODO: poradie: sword cannon, sword anchor, sword keychest, cannon, keychest, anchor, oracle

                            if (mapHasSword == true && myBankHasCannon == false && opponentHasCannon == true && playAreaHasCannon == false ){
                                responseCard = mapChoicesDeck.getHighestInSuit(Suit.Sword);
                            } else if (mapHasSword == true && myBankHasAnchor == false && opponentHasAnchor == true && playAreaHasAnchor == false ){
                                responseCard = mapChoicesDeck.getHighestInSuit(Suit.Sword);
                            } else if (mapHasSword == true && myBankHasKey == false && opponentHasKey == true && playAreaHasKey == false ){
                                responseCard = mapChoicesDeck.getHighestInSuit(Suit.Sword);
                            } else if (mapHasSword == true && myBankHasChest == false && opponentHasChest == true && playAreaHasChest == false ){
                                responseCard = mapChoicesDeck.getHighestInSuit(Suit.Sword);
                            } else if(mapHasCannon == true && myBankHasCannon == true && playAreaHasCannon == false){
                                responseCard = mapChoicesDeck.getHighestInSuit(Suit.Cannon);
                            } else if(mapHasKey == true && playAreaHasChest == true && myBankHasKey == true){
                                responseCard = mapChoicesDeck.getHighestInSuit(Suit.Key);
                            } else if(mapHasChest == true && playAreaHasKey == true && myBankHasChest == true){
                                responseCard = mapChoicesDeck.getHighestInSuit(Suit.Chest);
                            } else if(mapHasAnchor == true && myBankHasAnchor == true && playAreaHasAnchor == false){
                                responseCard = mapChoicesDeck.getHighestInSuit(Suit.Anchor);
                            } else if(mapHasOracle == true && myBankHasOracle == true && playAreaHasOracle == false){
                                responseCard = mapChoicesDeck.getHighestInSuit(Suit.Oracle);
                            } else {
                                System.out.println("else branch");
                                responseCard = mapChoicesDeck.getHighestInSuit(utils.getCardTypeScored(suits));
                                System.out.println("response card: " + responseCard );
                            }
                        }
                        cardPlacedToPlayArea = respond(client, orig, responseCard);
                    }
                    default -> {
                        var card = status.state.pendingEffect.cards[0];
                        cardPlacedToPlayArea = respond(client, orig, card);
                    }
                }
            }
        } while (true);

    }

    private Set<String> getCardsFromPlayArea(Status status) {
        var playArea = status.state.playArea;
        Set<String> playAreaSet = new HashSet<String>();

        if (playArea != null) {
            for (int i = 0; i < playArea.length; i++) {
                playAreaSet.add(playArea[i].suit.name());
            }
        }
        return playAreaSet;
    }

    private String getCardTypeScored(Set<String> cardtypes) {
        // cards are ranked from the best to the worst according to my score

        if (cardtypes.contains("Sword")) {
            return "Sword";
        }
        if (cardtypes.contains("Cannon")) {
            return "Cannon";
        }
        if (cardtypes.contains("Oracle")) {
            return "Oracle";
        }
        if (cardtypes.contains("Map")) {
            return "Map";
        }
        if (cardtypes.contains("Hook")) {
            return "Hook";
        }
        if (cardtypes.contains("Anchor")) {
            return "Anchor";
        }
        if (cardtypes.contains("Mermaid")) {
            return "Mermaid";
        }
        if (cardtypes.contains("Chest")) {
            return "Chest";
        }
        if (cardtypes.contains("Key")) {
            return "Key";
        }
        // default :(
        return "Kraken";
    }

    private String getCardTypeScoredEmptyOpponent(Set<String> cardtypes) {
        // cards are ranked from the best to the worst according to my score

        if (cardtypes.contains("Oracle")) {
            return "Oracle";
        }
        if (cardtypes.contains("Map")) {
            return "Map";
        }
        if (cardtypes.contains("Hook")) {
            return "Hook";
        }
        if (cardtypes.contains("Anchor")) {
            return "Anchor";
        }
        if (cardtypes.contains("Mermaid")) {
            return "Mermaid";
        }
        if (cardtypes.contains("Chest")) {
            return "Chest";
        }
        if (cardtypes.contains("Key")) {
            return "Key";
        }
        if (cardtypes.contains("Cannon")) {
            return "Cannon";
        }
        if (cardtypes.contains("Sword")) {
            return "Sword";
        }
        // default :(
        return "Kraken";
    }

    private Event draw(OkHttpClient client) throws IOException {
        Response response = null;
        Event drawEvent = null;
        try {
            var body = "{\"etype\":\"Draw\"}";
            //response = call(client, body);
            var mediaType = MediaType.parse("application/json; charset=utf-8");
            var rbody = RequestBody.create(body, mediaType);
            var request = new Request.Builder()
                    .url(BASE_URL + "/api/matches/" + matchID)
                    .post(rbody)
                    .build();
            var call = client.newCall(request);
            response = call.execute();
            System.out.println(playerID + ": Draw.");

            var code = response.code();
            if (code != 200) {
                throw new IOException();
            }
            var responseBody = response.body();

            if (code == 200 && responseBody != null) {
                var objectMapper = new ObjectMapper();
                var resp = responseBody.string();
                var events = objectMapper.readValue(resp, Event[].class);
                for(int i = 0; i < events.length; i++) {
                    System.out.println("Event type: " + events[i].eventType);
                    if (events[i].eventType.equals("Draw")) {
                        drawEvent = events[i];
                        if(drawEvent.drawCard != null) {
                            Card lastDrawCard = new Card(drawEvent.drawCard.suit, drawEvent.drawCard.value);
                            removeCardFromDrawPile(lastDrawCard);
                            System.out.println("DRAW -> lastDrawnCard : " + drawEvent.drawCard + "removed from draw Pile");
                        }
                    }
                }
            }
            if (drawEvent == null) {
                System.out.println("DRAW - Did not find Draw event");
            }
            return drawEvent;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private void stop(OkHttpClient client) throws IOException {
        Response response = null;
        try {
            var body = "{\"etype\":\"EndTurn\"}";
            response = call(client, body);
            System.out.println(playerID + ": Stop.");

            if (response.code() != 200) {
                throw new IOException();
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private Event respond(OkHttpClient client, Suit responseType, Card chosen) throws IOException {
        Response response = null;
        Event cardPlacedToPlayArea = null;
        try {
            var eResp = new EffectResponse();
            eResp.effect.effectType = responseType.name();
            eResp.effect.card = chosen;
            var objectMapper = new ObjectMapper();
            var body = objectMapper.writeValueAsString(eResp);
            //response = call(client, body);

            var mediaType = MediaType.parse("application/json; charset=utf-8");
            var rbody = RequestBody.create(body, mediaType);
            var request = new Request.Builder()
                    .url(BASE_URL + "/api/matches/" + matchID)
                    .post(rbody)
                    .build();
            var call = client.newCall(request);
            response = call.execute();

            System.out.println(playerID + ": Response for " + responseType + ".");
            if (chosen != null)
                System.out.println(playerID + ": Responded with Card " + chosen.toString() + ".");
            if (response.code() != 200) {
                throw new IOException();
            }
            var responseBody = response.body();
            if (response.code() == 200 && responseBody != null) {
                var resp = responseBody.string();
                var events = objectMapper.readValue(resp, Event[].class);
                for(int i = 0; i < events.length; i++) {
                    System.out.println("Event type: " + events[i].eventType);
                    if (events[i].eventType.equals("CardPlacedToPlayArea")) {
                        cardPlacedToPlayArea = events[i];
                    }
                }
            }

        } finally {
            if (response != null) {
                response.close();
            }
        }
        return cardPlacedToPlayArea;
    }

    private Response call(OkHttpClient client, String body) throws IOException {
        Response response = null;

        try {
            var mediaType = MediaType.parse("application/json; charset=utf-8");
            var rbody = RequestBody.create(body, mediaType);
            var request = new Request.Builder()
                    .url(BASE_URL + "/api/matches/" + matchID)
                    .post(rbody)
                    .build();
            var call = client.newCall(request);
            response = call.execute();
            return response;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private Status getStatus(OkHttpClient client, boolean wait) throws IOException {

        Response response = null;

        try {
            String url = BASE_URL + "/api/matches/" + matchID + (wait ? "?waitactive=1" : "");
            var request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            var call = client.newCall(request);
            response = call.execute();
            var code = response.code();

            if (code == 409) { // it is not our turn
                return null;
            } else if (code == 410) {
                return getStatus(client, false);
            } else if (code != 200) {
                throw new IOException("Server could not return the status of match " + matchID);
            }

            var responseBody = response.body();
            if (code == 200 && responseBody != null) {
                var objectMapper = new ObjectMapper();
                var resp = responseBody.string();
                var status = objectMapper.readValue(resp, Status.class);
                return status;
            } else {
                throw new IOException("Server could not return the status of match " + matchID);
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private void waitForMatch(OkHttpClient client) throws IOException {

        String url = BASE_URL + "/api/matches?wait=1";
        if ((tags == null || tags.isEmpty()) || tags.isBlank()) {
            System.out.println(playerID + ": Waiting for match.");
        } else {
            url = url + "&tags=" + tags;
            System.out.println(playerID + ": Waiting for match with tags " + tags + ".");
        }

        var request = new Request.Builder()
                .url(url)
                .get()
                .build();

        do {

            Response response = null;

            try {

                var call = client.newCall(request);
                response = call.execute();
                var code = response.code();

                var responseBody = response.body();
                if (code == 200 && responseBody != null) {
                    var objectMapper = new ObjectMapper();
                    var resp = responseBody.string();
                    var statuses = objectMapper.readValue(resp, List.class);
                    if (statuses != null && !statuses.isEmpty()) {
                        var ids = new ArrayList<String>();
                        for (var status : (List<HashMap>) statuses) {
                            var player = status.get("activePlayerIndex");
                            if (player != null) {
                                var id = (String) status.get("_id");
                                ids.add(id);
                            }
                        }
                        if (!ids.isEmpty()) {
                            Collections.sort(ids);
                            matchID = ids.get(0);
                            return;
                        }
                    }
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } while (true);
    }
}