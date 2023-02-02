package herebcs.spcjavaclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import herebcs.spcjavaclient.rest.EffectResponse;
import herebcs.spcjavaclient.rest.Status;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;

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
    
    public Match (String matchID, String tags, String playerID, String password, String BASE_URL) {
        this.matchID = matchID;
        this.tags = tags;
        this.playerID = playerID;
        this.BASE_URL = BASE_URL;
        this.password = password;
    }

    private CardDeck initializeDrawPile() {
        drawPile = new CardDeck(new ArrayList<>());
        Suit[] allSuits = {Suit.Anchor, Suit.Cannon, Suit.Chest, Suit.Hook, Suit.Key, Suit.Kraken, Suit.Map, Suit.Oracle, Suit.Sword};
        Suit[] mermaidSuit = {Suit.Mermaid};

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
        return drawPile;
    }

    private CardDeck initializeDiscardPile() {
        discardPile = new CardDeck(new ArrayList<>());
        Suit[] allSuits = {Suit.Anchor, Suit.Cannon, Suit.Chest, Suit.Hook, Suit.Key, Suit.Kraken, Suit.Map, Suit.Oracle, Suit.Sword};

        for (Suit s : allSuits) {
            discardPile.addToDeck(new Card(s, 2));
        }

        discardPile.addToDeck(new Card(Suit.Mermaid, 4));

        return discardPile;
    }

    private CardDeck removeCardFromDrawPile(Card card) {
        drawPile.cardDeck.remove(card);
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

        initializeDiscardPile();
        initializeDrawPile();
        System.out.println(playerID + ": playing match " + matchID + ".");
//        System.out.println("Draw pile " + drawPile + ".");
//        System.out.println("Discard pile " + discardPile + ".");

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
                //System.out.println("draw pile size:" + drawPile.cardDeck.size());
                break;
            }

            if (status.state.pendingEffect == null) {
                // draw or stop
                if (status.state.playArea.length == 0) {
                    draw(client);
                } else {

                   CardBank opponentCardBank = new CardBank(status.state.banks[1 - status.state.currentPlayerIndex]);
                   CardBank myCardBank = new CardBank(status.state.banks[status.state.currentPlayerIndex]);
                   CardDeck playAreaDeck = new CardDeck(status.state.playArea);
                   //CardDeck drawDeck = new CardDeck(status.state.drawPile);
                   //CardDeck discardDeck = new CardDeck(status.state.discardPile);

                   OctopusBrain brain = new OctopusBrain(playAreaDeck, discardPile, drawPile, myCardBank, opponentCardBank);

                   var drawOkProb = brain.calcProbabilityOfDrawingOk();
                   var possiblePointsToScore = brain.calcPossiblePointsToScore();
                   var avgValueOfDrawingOkSuit = brain.calcAvgValueOfDrawingOkSuit();

                   var avgPointsScoredByDrawing = drawOkProb * (possiblePointsToScore + avgValueOfDrawingOkSuit);

                   //System.out.println("drawOkProb: " + drawOkProb);
                   //System.out.println("possiblePointsToScore: " + possiblePointsToScore);
                   //System.out.println("avgValueOfDrawingOkSuit: " + avgValueOfDrawingOkSuit);
                   //System.out.println("avgPointsScoredByDrawing: " + avgPointsScoredByDrawing);

                   if (possiblePointsToScore > avgPointsScoredByDrawing) {
                       stop(client);
                   } else {
                       draw(client);
                   }
                }
            } else {
                var orig = Suit.valueOf(status.state.pendingEffect.effectType);

                switch (status.state.pendingEffect.effectType) {
                    case "Oracle" -> {
                        Card revealedCard = status.state.pendingEffect.cards[0];
                        CardDeck playAreaDeck = new CardDeck(status.state.playArea);
                        Card card;

                        if (playAreaDeck.containsSuit(revealedCard.suit)) {
                            card = null;
                        } else {
                            card = revealedCard;
                        }
                        respond(client, orig, card);
                    }
                    case "Hook" -> {
                        CardBank opponentCardBank = new CardBank(status.state.banks[1 - status.state.currentPlayerIndex]);
                        CardBank myCardBank = new CardBank(status.state.banks[status.state.currentPlayerIndex]);

                        var opponentCardSuits = opponentCardBank.getSuitsInBank();
                        var myCardSuits = myCardBank.getSuitsInBank();

                        CardDeck playAreaDeck = new CardDeck(status.state.playArea);
                        var playAreaCardSuits = playAreaDeck.getSuitsInDeck();

                        myCardSuits.removeAll(playAreaCardSuits);

                        Utils utils = new Utils();
                        Card responseCard;
                        if (myCardSuits.isEmpty()) { // Ak mozem vybrat od seba len nieco co uz je na play area
                            CardDeck lowestCards = new CardDeck(new ArrayList<>());
                            for (Suit o : playAreaCardSuits) {
                                var deck = myCardBank.getDeckBySuit(o);
                                lowestCards.addToDeck(deck.getLowestValueCard()); // TODO: check backups
                            }
                            responseCard = lowestCards.getLowestValueCard();
                        } else {
                            Suit chosenSuit;
                            if (opponentCardSuits.isEmpty()) {
                                // nema ziadne karty
                                chosenSuit = utils.getCardTypeScoredEmptyOpponent(myCardSuits);
                            } else {
                                // Ma nejake karty. Podme mu ublizit, ak sa da.
                                chosenSuit = utils.getCardTypeScored(myCardSuits);
                            }
                            //vyberam od seba a musim top kartu, lebo tu pod tym nemozem, je to backup
                            responseCard = myCardBank.getDeckBySuit(chosenSuit).getHighestValueCard();
                        }
                        respond(client, orig, responseCard);
                    }
                    case "Sword" -> {
                        CardBank opponentCardBank = new CardBank(status.state.banks[1 - status.state.currentPlayerIndex]);
                        CardBank myCardBank = new CardBank(status.state.banks[status.state.currentPlayerIndex]);

                        var opponentCardSuits = opponentCardBank.getSuitsInBank();
                        var myCardSuits = myCardBank.getSuitsInBank();

                        CardDeck playAreaDeck = new CardDeck(status.state.playArea);
                        var playAreaCardSuits = playAreaDeck.getSuitsInDeck();

                        // seems like I cant steal suit I already have in bank?
                        opponentCardSuits.removeAll(myCardSuits);

                        // we also preferably dont want suits in play area
                        opponentCardSuits.remove(playAreaCardSuits);

                        Utils utils = new Utils();
                        Card responseCard;
                        if (opponentCardSuits.isEmpty()) {
                            // Dostaneme duplikat: zbav sa vrchnej karty s najvyssou hodnotou
                            CardDeck highestCards = new CardDeck(new ArrayList<>());
                            for (Suit o : playAreaCardSuits) {
                                var odeck = opponentCardBank.getDeckBySuit(o);
                                highestCards.addToDeck(odeck.getHighestValueCard()); // TODO: check backups
                            }

                            responseCard = highestCards.getHighestValueCard();
                        } else {
                            Suit chosenCardSuit = utils.getCardTypeScored(opponentCardSuits);
                            responseCard = opponentCardBank.getDeckBySuit(chosenCardSuit).getHighestValueCard();

                        }
                        respond(client, orig, responseCard);
                    }
                    case "Cannon" -> {
                        CardBank opponentCardBank = new CardBank(status.state.banks[1 - status.state.currentPlayerIndex]);
                        var opponentCardSuits = opponentCardBank.getSuitsInBank();

                        Card responseCard = null;
                        Utils utils = new Utils();
                        if (!opponentCardSuits.isEmpty()) {

//                            ConcurrentHashMap<Suit, Integer> differences = new ConcurrentHashMap<>();
//                            for (Suit o : opponentCardSuits) {
//                                CardDeck deck = opponentCardBank.getDeckBySuit(o);
//                                System.out.println(deck);
//                                differences.put(o, deck.getBackupDifference(o));
//                            }
//
//                            Suit highestDifferenceSuit = Collections.max(differences.entrySet(), Map.Entry.comparingByValue()).getKey();
//                            System.out.println("Highest Difference Suit: " + highestDifferenceSuit.name());
//                            System.out.println(opponentCardBank.getDeckBySuit(highestDifferenceSuit));
                            var preferredSuit = utils.getCardTypeScored(opponentCardSuits);
                            responseCard = opponentCardBank.getDeckBySuit(preferredSuit).getHighestValueCard();
                        }
                        respond(client, orig, responseCard);
//                        var bank = status.state.banks[1 - status.state.currentPlayerIndex];
//                        var firstCardType = bank.keySet().iterator().next();
//                        var maxCardValue = Collections.max(bank.get(firstCardType));
//                        var card = new Card();
//                        card.suit = Suit.valueOf(firstCardType);
//                        card.value = maxCardValue;
//                        respond(client, orig, card);
                    }
                    case "Kraken" -> {
                        draw(client);
                    }
                    case "Chest" -> {
                        var card = status.state.pendingEffect.cards[0];
                        respond(client, orig, card);
                    }
                    case "Key" -> {
                        var card = status.state.pendingEffect.cards[0];
                        respond(client, orig, card);
                    }
                    case "Map" -> {
                        Card responseCard = null;
                        CardDeck mapChoicesDeck = new CardDeck(status.state.pendingEffect.cards);

                        // If there is no discarded card, then the next card must be drawn the standard way
                        if (mapChoicesDeck.isEmpty()) {
                            draw(client);
                            break;
                        }

                        CardDeck playAreaDeck = new CardDeck(status.state.playArea);
                        mapChoicesDeck.removeSuitCardsCommonWith(playAreaDeck);

                        // if no usable cards are found we have to respond even if we loose
                        if (mapChoicesDeck.isEmpty()) {
                            responseCard = status.state.pendingEffect.cards[0];
                            respond(client, orig, responseCard);
                            break;
                        }

                        CardBank opponentBank = new CardBank(status.state.banks[1 - status.state.currentPlayerIndex]);
                        Set<Suit> opponentCardTypes = opponentBank.getSuitsInBank();

                        boolean playAreaHasChest = playAreaDeck.containsSuit(Suit.Chest);
                        boolean playAreaHasKey = playAreaDeck.containsSuit(Suit.Key);
                        boolean playAreaNeedsChest = playAreaHasKey && !playAreaHasChest;
                        boolean playAreaNeedsKey = playAreaHasChest && !playAreaHasKey;

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
                        } else { //TODO: decide what to choose depending on oponent card values vs play area values
                            var suits = mapChoicesDeck.getSuitsInDeck();
                            var prioSuit = utils.getCardTypeScored(suits);

                            if (prioSuit == Suit.Sword || prioSuit == Suit.Cannon) {
                                responseCard = mapChoicesDeck.getHighestInSuit(prioSuit);
                            } else if (playAreaNeedsChest) {
                                responseCard = mapChoicesDeck.getHighestInSuit(Suit.Chest);
                            } else if (playAreaNeedsKey) {
                                responseCard = mapChoicesDeck.getHighestInSuit(Suit.Key);
                            }
                            if (responseCard == null) {
                                responseCard = mapChoicesDeck.getHighestInSuit(prioSuit);
                            }
                        }
                        respond(client, orig, responseCard);
                    }
                    default -> {
                        var card = status.state.pendingEffect.cards[0];
                        respond(client, orig, card);
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
    
    private void draw(OkHttpClient client) throws IOException {
        Response response = null;
        try {
            var body = "{\"etype\":\"Draw\"}";
            response = call(client, body);
            System.out.println(playerID + ": Draw.");

            if (response.code() != 200) {
                throw new IOException();
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        var statusRealTime = getStatus(client, true);
        CardDeck playAreaDeck = new CardDeck(statusRealTime.state.playArea);
        //System.out.println("Last Play Area Array:" + playAreaDeck );
        if (playAreaDeck.cardDeck == null || playAreaDeck.cardDeck.size() == 0 ){
            //System.out.println("Last Play Area Card was empty");
        } else {
            Card lastPlayAreaCard = playAreaDeck.cardDeck.get(playAreaDeck.cardDeck.size() - 1);
            //System.out.println("DRAW last play card: " + lastPlayAreaCard);
            removeCardFromDrawPile(lastPlayAreaCard);
            //System.out.println("DRAW draw pile size:" + drawPile.cardDeck.size());
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
    
    private void respond(OkHttpClient client, Suit responseType, Card chosen) throws IOException {
        Response response = null;
        
        try {      
            var eResp = new EffectResponse();
            eResp.effect.effectType = responseType.name();
            eResp.effect.card = chosen;
            var objectMapper = new ObjectMapper();
            var body = objectMapper.writeValueAsString(eResp);
            response = call(client, body);
            System.out.println(playerID + ": Response for " + responseType + ".");
            if(chosen != null) System.out.println(playerID + ": Responded with Card " + chosen.toString() + ".");
            if (response.code() != 200) {
                throw new IOException();
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        var statusRealTime = getStatus(client, true);
        CardDeck playAreaDeck = new CardDeck(statusRealTime.state.playArea);
        //System.out.println("Last Play Area Array:" + playAreaDeck );
        if (playAreaDeck.cardDeck == null || playAreaDeck.cardDeck.size() == 0 ){
            //System.out.println("Last Play Area Card was empty");
        } else {
            Card lastPlayAreaCard = playAreaDeck.cardDeck.get(playAreaDeck.cardDeck.size() - 1);
            //System.out.println("RESPONSE LAST CARD: " + lastPlayAreaCard);
            removeCardFromDrawPile(lastPlayAreaCard);
            //System.out.println("RESPONSE draw pile size:" + drawPile.cardDeck.size());
        }
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
            String url = BASE_URL + "/api/matches/" + matchID + (wait ? "?waitactive=1" : "" );
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
                        for (var status: (List<HashMap>)statuses) {
                            var player = status.get("activePlayerIndex");
                            if (player != null) {
                                var id = (String)status.get("_id");
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