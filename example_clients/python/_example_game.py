from random import randrange
import time
from typing import Optional, cast
import openapi_client
from pprint import pprint
import openapi_client
from openapi_client.models import *

# The client must configure the authentication and authorization parameters
# in accordance with the API server security policy.
# Examples for each auth method are provided below, use the example that
# satisfies your auth use case.

# Configure HTTP basic authorization: basic
me_userid = "636438380ef778617e0e5be5"
configuration = openapi_client.Configuration(
    username=me_userid,
    password="testpass",
    # host = "https://spc22-test1.appspot.com"
    host="http://localhost:8080",
)

### main sub
def main():
    helloworld()

    # either use an exiting match or create a new practice match
    # matchid = create_match()
    matchid = "637a5e751ba7759a7601956d"

    play_game(matchid)


### helloworld service
def helloworld():
    with openapi_client.ApiClient(configuration) as api_client:
        api_instance = openapi_client.DiagnosticApi(api_client)

        try:
            api_response = api_instance.get_message()
            pprint(api_response, compact=True, width=160)
        except openapi_client.ApiException as e:
            print("Exception when calling DiagnosticApi: %s\n" % e)


### whoami service
def whoami():
    with openapi_client.ApiClient(configuration) as api_client:
        api_instance = openapi_client.DiagnosticApi(api_client)

        try:
            api_response = api_instance.get_message()
            pprint(api_response)
        except openapi_client.ApiException as e:
            print("Exception when calling DiagnosticApi: %s\n" % e)


### sanitize event - remove None values - to be reworked recursively / maybe only for pprint
def sanitize_responseitem(response_item1):
    #response_item2 = cast(ActionArray[], response_item1)
    response_item3 = {
        key: value
        for key, value in response_item1.to_dict().items()
        if value is not None
    }
    return response_item3


### create a new practice match
def create_match():
    with openapi_client.ApiClient(configuration) as api_client:
        game_api_instance = openapi_client.GameApi(api_client)

        match_creation_params = {}  # Create an practice default match with full deck
        api_response = cast(
            MatchCreateResponse,
            game_api_instance.create_match(match_creation_params),
        )
        matchid = api_response.id

        print_match_url(matchid)
        return matchid


### check if a move has such an event
def move_has_event(
    ris, event_type
) -> Optional[PartialPickMatchEventActionResponseReturnedProps]:
    ri = next((ri for ri in cast(list, ris) if ri.event_type == event_type), None)
    if ri:
        return cast(PartialPickMatchEventActionResponseReturnedProps, ri)


### print the match browsable url
def print_match_url(matchid):
    match_url = f"{configuration.host}/matches/{matchid}"
    print(f"Browse this match at: [ {match_url} ].")


### play a game of multiple turns with waiting for my turn
def play_game(matchid):
    print_match_url(matchid)
    with openapi_client.ApiClient(configuration) as api_client:
        game_api_instance = openapi_client.GameApi(api_client)

        # Turn loop
        turncount = 1
        api_response = None
        # str | optional waits with timeout executing the action - useful for waiting for other user to finish its action to avoid polling (optional)
        wait = "1"

        # TURNS: repeat turns as long as game is not finished - 403
        while True:
            # check matchended gracefully
            if isinstance(api_response, list):
                has_matchended_ri = move_has_event(
                    api_response, MatchEventType.MATCHENDED
                )
                if has_matchended_ri:
                    print(
                        "Match ended, winner is ",
                        has_matchended_ri.match_ended_winner_idx,
                        " scores ",
                        has_matchended_ri.match_ended_scores,
                    )
                    break

            print(
                f"=== TURN #{turncount} ========================================================================="
            )

            # 1_TURN: repeat draw until we decide not to draw anymore in this turn
            while True:

                # 1_MOVE repeat single draw action while 409
                doRetryMove = True
                doEndThisTurn = False
                while doRetryMove:
                    try:
                        # IUserAction | Match action execution parameters
                        body = {
                            "etype": MatchActionType.DRAW,
                            "autopick": openapi_client.AutoPickOptions.ALL,
                        }
                        api_response = game_api_instance.execute_action_for_match(
                            matchid, body, wait=wait
                        )
                        doRetryMove = False  # upon success, after processing - break from the single move while

                        for response_item in cast(list, api_response):
                            pprint(sanitize_responseitem(response_item), width=160)

                        # check if busted.
                        if move_has_event(api_response, MatchEventType.TURNENDED):
                            print("TURN Ended")  # busted or matchended
                            doEndThisTurn = True

                    except openapi_client.ApiException as e:
                        pprint(e.body or e)
                        if e.status != 409:  # retry loop only waitaible timeout
                            return
                        else:
                            print(
                                "... I am still not the current player - retrying the move"
                            )

                # if turn ended then
                if doEndThisTurn:
                    turncount += 1
                    break

                # on random determine whether to continue or stop
                doStop = randrange(0, 10) < 3

                # if to be stopped, just end the turn
                if doStop:
                    try:
                        # IUserAction | Match action execution parameters
                        body = {
                            "etype": MatchActionType.ENDTURN,
                            "autopick": openapi_client.AutoPickOptions.ALL,
                        }
                        api_response = game_api_instance.execute_action_for_match(
                            matchid, body
                        )
                        for response_item in cast(list, api_response):
                            pprint(sanitize_responseitem(response_item), width=160)
                        turncount += 1
                        break
                    except openapi_client.ApiException as e:
                        pprint(e.body or e)
                        return


# execute main sub
main()
