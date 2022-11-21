# coding: utf-8

# flake8: noqa
"""
    SLHPC23 Arena

    **SAP Labs Hungary Programming Competition 2023 Arena server**.  You can find more information about the game and the competititon rules at [github/SLH_SPC_2022](https://github.com/afarago/SLH_SPC_2022).   For a test run, you can use the crash test dummy user `000000000000000000000000/dummypass`.   *Note: All the APIs expect and return application/json*.  # noqa: E501

    The version of the OpenAPI document: 1.0.0
    Contact: DL SLHPC23 <DL_637A3F6466D808029A65636A@global.corp.sap>
    Generated by: https://openapi-generator.tech
"""


from __future__ import absolute_import

# import models into model package
from openapi_client.models.action_error_response import ActionErrorResponse
from openapi_client.models.auto_pick_options import AutoPickOptions
from openapi_client.models.card import Card
from openapi_client.models.card_effect import CardEffect
from openapi_client.models.card_effect_response import CardEffectResponse
from openapi_client.models.card_effect_type import CardEffectType
from openapi_client.models.card_suit import CardSuit
from openapi_client.models.card_value import CardValue
from openapi_client.models.delete_match_request import DeleteMatchRequest
from openapi_client.models.draw_card_pile import DrawCardPile
from openapi_client.models.error_response import ErrorResponse
from openapi_client.models.hello_world_response import HelloWorldResponse
from openapi_client.models.i_state_delta import IStateDelta
from openapi_client.models.i_state_delta_stack import IStateDeltaStack
from openapi_client.models.i_user_action import IUserAction
from openapi_client.models.match_action_type import MatchActionType
from openapi_client.models.match_create_response import MatchCreateResponse
from openapi_client.models.match_creation_params import MatchCreationParams
from openapi_client.models.match_event_type import MatchEventType
from openapi_client.models.match_response import MatchResponse
from openapi_client.models.match_response_returned2 import MatchResponseReturned2
from openapi_client.models.partial_pick_match_event_action_response_returned_props import PartialPickMatchEventActionResponseReturnedProps
from openapi_client.models.partial_pick_match_event_action_response_returned_props_response_to_effect_card import PartialPickMatchEventActionResponseReturnedPropsResponseToEffectCard
from openapi_client.models.pick_match_match_response_returned_props import PickMatchMatchResponseReturnedProps
from openapi_client.models.pick_player_exclude_keyof_player_passwordhash import PickPlayerExcludeKeyofPlayerPasswordhash
from openapi_client.models.state import State
from openapi_client.models.who_ami_i_response import WhoAmiIResponse
