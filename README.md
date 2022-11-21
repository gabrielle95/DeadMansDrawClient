SAP Labs Hungary Programming Competition 2023
============

# Competition objective
Goal of each team is implement a client to ace the Dead-Man's-Draw card game using their language and tools of choice.

During the competition period there will be several practice tournaments, while the competition will close with a grand tournament where teams' solutions will compete and the most victorious teams will ace the SLH-PC-2023.

# Competition flow
1. Teams register themselves to the organizers
2. Organizers enter team with a generated passwordhash to the DB
   1. Organizers send Player id and passwordhash to the Team -  e.g `{id: '636438380ee778617e6e5be8', password: 'playerpass'}`
3. Players implement test client using the specification and the example clients
   1. Organizers publish example clients to a public repo beforehands
   2. Players test themselves on practice Matches and develop a good strategy
4. Organizers announce Practice Tournaments, where Players are challenged to compete creating appropriate Matches between the Players -- e.g. at the end of each day 3pm-4pm
   1. Teams are expectd to keep their solution up and running during the tournament and react to ongoing matches with their involvement with the logic below
      1. (tbd) Players host their solution either on any internet enabled local machine - e.g. dev laptop or choose to publish their client in the cloud
   2. Once all Matches are finished, Organizers announce ranking and winners
   3. Teams are expected to react on matches within 30 seconds (tbd) time otherwise a central watchdog may terminate the match and announce the other player winner

# Interfaces available
Game is available through a public server .
* REST API with OpenAPI available in [/api](https://spc22-test1.appspot.com/api), documented in [/docs](https://spc22-test1.appspot.com/docs)
* Frontent UI available under [/matches](https://spc22-test1.appspot.com/matches) -- eye fancy, to enable easy comprehension, do not use it for your automated solution.  
TODO: no filter/authorization enabled yet
# Match
A Match is a game between two players. Can be created by the Organizers between any two Players or as a practice Match between a player and its second practice user.

## Match Flow
1. Player listens to the [/matches?active=1&wait=1&tags=GAMETAG](https://spc22-test1.appspot.com/api/matches?active=1&wait=1&tags=GAMETAG) endpoint.
   1. Arena server returns any matches if found
   2. In absence it the connection for ~30 seconds, and then returns with an empty array
   3. Teams are advised to instantly retry the polling
   4. (Match is started either by the Organizers or the Player itself)
   5. API endpoint returns with a matchiung Match Ids so user gets notified about a match start.
      1. Organizers will start one active match at a time for each team, so no teams should deal with simultaneous matches.
2. Player issues `Draw` action via the API and waits for result via POST [/api/matches/{matchid}](https://spc22-test1.appspot.com/api/matches/{matchid})
   1. Player receives information on events resulted by the action in the response
   2. If it is the start of the turn for the player all changes ot the playarea delta is added as a first item
   3. If the card triggers an effect that requires user response (e.g. `Map`) and the effect is not nullified the user is expected to call the action API with the appropriate response. with `ResponseToEffect`
   4. As any action can bust the turn Players should check if the returned response contains a TurnEnded or even `MatchEnded` event or not. (Though Arena server will prevent any invalid actions, such as drawing a card when already the other player is on turn.)
3. After a number of cards player issues `EndTurn` action via the API
   1. Areana server returns the resulting list of state change events similarly to `Draw` including a turn ended aggregate state delta
4. If busted or draw pile is exhaused turn ends automatically -- server adds TurnEnded event in response.
5. If draw pile is exhaused match ends automatically and winner or tie is announced -- server adds `MatchEnded` event in response.


# Development journey
Teams are not expected to ace the game at the first try, therefore a number of tools are at your disposal during the development journey to gradually excel with the result.

* Teams can onboard on helloworld and then whoami endpoints
* REST API is easer than you would think - use [web browser for testing](https://spc22-test1.appspot.com/api/hello), [curl](https://curl.se/), [PostMan](https://www.postman.com/) or even [Excel macros](https://learn.microsoft.com/en-us/office/dev/scripts/develop/external-calls), [Excel grid](https://www.conradakunga.com/blog/consuming-rest-json-apis-from-excel)
* Teams receive a number of example clients te ease development in most popular languages
* Teams can start and conduct any amount of Practice Matches to test game logic with the same user
* Teams can start and conduct any amount of Practice Matches to test game logic between the two own users to test dialogue handling
* Teams can set initial state of the game to test out different situations in Practice Matches, and set randomization or even turn out randomization completely
* Teams can recreate and replay any match in Practice Matches 
* Teams can use the autopick mechanism to neglect any abligation for Card Effect responses
* Teams can check relevant match results via a web browser, rednering all moves and match state

### Practice Match
A practice Match is a Match that is created by the Player itself, and contains twice the PlayerId or the PlayerId and the DummyId so that it can act as both sides of the table.  
For a practice Match extended amounts of information is delivered via the API for easier debugging.

DummyPlayer is identified with `"000000000000000000000000":"dummypass"` accepting any match as an opponent.  
Be aware that you need to play with the dummyuser as well if you start a match with it.  
Be aware that dummyuser will receive many matches from other players as well - react only on matches you have started yourself, consider using a tag for such maches.

### Initial debug parameters
Practice match can be started with specifying any initial starting state with regards to draw, discard pile or banks. See the OpenAPI documentation for the parameters - check out `MatchCreationParams`.

#### Random seed
Practice match can be started with specifying the random seed so that a previous match can re replayed and any programming errors can be understood better.

Each game creation returns the random seed used or generated - check out `MatchCreateResponse`.

Special random seed parameter is "norandom" when areana server picks first available choice - useful for knowing how initial player from players array or discard pile drawing on "map" will result.

### Autopick
If the card triggers an effect that requires user response (e.g. Map) and the effect is not nullified the user is expected to call the action API with the appropriate response. with `ResponseToEffect`.

To ease the initial learning curve teams can use the autopick mechanism that provides an automatic answer for any card effects, though not the most strategically smart one - check out IUserAction.

### Web Frontend
To ease comprehension teams can check relevant match results via a web browser, rendering all moves and match state.  
The frontent is accessible via  [/matches](https://spc22-test1.appspot.com/matches). An example gameplay of a match including user turns and moves look like this.
[![example gameplay](/doc/example_match_crop.png)](/doc/example_match.png)  
*Draw and discard piles are displayed only for Practice Matches.*


# API and timeout
There is an extensive OpenAPI documentation under /docs path of the server.
API works on JSON format.
Match request and Action execution endpoints works with a timeout with `wait=1` parameter specified to avoid constant polling. As there is a timeout of cc 30 sec, eventually the request needs to be restarted.

## High level API list
* `GET /api/matches` - get matches
* `POST /api/matches` - create a match
* `GET /api/matches/{id}` - get match status
* `POST /api/matches/{id}` - execute action

## Authentication
Basic authentication is used in the header with the username and passwordhash provided.

## Security, Fair play
The Areana server is a development artifact not thourougly tested for security flaws or performance bottlenecks.
To keep chances fair, we expect all teams to respect arena integrity, report and not exploit any flaws to and solely the organizers right away.
To keep chances fair, we expect that each team polls server with the timeout frequency implemented (~30 sec) to avoid potential overload.

# Game rules

The game is a popular [card game](/docs/rules/5b-dead-mans-draw-rulebook.pdf). 

Two players play against each other, their goal is to collect the highest number of cards and secure them in their own treasure chest.

One player starts the game. The players take turns and in each turn only one of the players is active. The active player draws cards one by one. After each draw he/she can finish the turn or draw another card. If the player stops drawing, all the cards drawn in the round can be moved to the player's chest. However, if a player keeps drawing and the type of a new card matches one already drawn in the same round, all the cards drawn in the round must be discarded and the round is completed without gaining anything. Every new card a player draws increases the potential value he/she can gain in the round but at the same time the chance of drawing a duplicate and completing the round with 0 points is increased as well.

There are 9 type of cards, all of them having 6 identical cards (54 in total). All of the 9 types affect the player's turn in a different way. When a duplicate is drawn it is discarded instantly together with the other cards without its ability affecting the round.

## Card Suits

![](/arena_server/src/public/img/suit_anchor.png) **Anchor** - the cards drawn before the anchor are placed into the treasure chest even if the round ends with a duplicate.

![](/arena_server/src/public/img/suit_cannon.png) **Cannon** - the player can choose a card from the other player's chest to discard (if the other player has at least one card in the chest).

![](/arena_server/src/public/img/suit_sword.png) **Sword** - the next card must be choosen from the opponent's chest (if there is any). If a player chooses a card that matches one already drawn before (e.g. because there is only one card in the chest), the turn ends just as a duplicate was drawn and the cards must be discarded (except those protected by the anchor).

![](/arena_server/src/public/img/suit_hook.png) **Hook** - the player must choose a card from his/her own chest (if there is any) and move it back to the play field. The card must be treated as it was drawn, i.e. the action based on its type must be executed. If a player chooses a card that matches one already drawn before (e.g. because there is only one card in the chest), the turn ends just as a duplicate was drawn and the cards must be discarded (except those protected by the anchor).

![](/arena_server/src/public/img/suit_oracle.png) **Oracle** - player can look at the next card before deciding to stop the round or draw the card.

![](/arena_server/src/public/img/suit_map.png) **Map** - the player must choose the next card from three cards randomly selected from the discarded ones. One must be choosen even if it completes the turn by being a duplicate. If there is no three cards in the discarded set, then the player must choose from less options. If there is no discarded card, then the next card must be drawn the standard way.

![](/arena_server/src/public/img/suit_kraken.png) **Kraken** - the player has two draw two additional cards before stopping. If the first drawn card is a sword, map or hook, the next card counts towards the two regardless where it was drawn from.

![](/arena_server/src/public/img/suit_chest.png) ![](/arena_server/src/public/img/suit_key.png) **Chest & Key** - these cards have no special ability, but when both of these cards are drawn in a round and the player decides to stop (i.e. the turn does not end by drawing a duplicate) the player can move twice as many card to his chest that he/she would normally do. The additional cards are selected randomly from the discarded pack. If there are less cards discarded than the number of cards drawn, then all the discarded cards are moved to the player's chest.

## Game End

The game ends when there are no more cards to draw. The player having the highest number of cards in his/her treasure chest wins the game

## FAQ and small details
* **Kraken** must draw two if drawpile allows
  * ignores Oracle effect if drawn first, as you will need draw a second card anyways: Kraken->Oracle[X]->X
  * if first card successfully triggers effect (Hook, Map, Sword), no forced second draw is triggered
  * if card effect for first Kraken-pulled-card cannot be fulfilled, thus is nullified, the second card needs to be drawn (e.g. Kraken->Map(empty discard)->[draw second], Kraken->Hook(with empty bank)->[draw second], Kraken->Sword(with empty or all existing suit stacks)->[draw second] ))
* **Sword** effect cannot pick enemy Suit from which own Player already has someting in its Bank, yet not taking anything on the PlayArea in account
* **Chest & Key** draws from the DiscardPile randomly, bad luck if there are no or not enough cards for you in the pile
* DiscardPile - initially filled with the smallest of each suit
