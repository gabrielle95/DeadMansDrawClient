# coding: utf-8

"""
    SLHPC23 Arena

    **SAP Labs Hungary Programming Competition 2023 Arena server**.  You can find more information about the game and the competititon rules at [github/SLH_SPC_2022](https://github.com/afarago/SLH_SPC_2022).   For a test run, you can use the crash test dummy user `000000000000000000000000/dummypass`.   *Note: All the APIs expect and return application/json*.  # noqa: E501

    The version of the OpenAPI document: 1.0.0
    Contact: DL SLHPC23 <DL_637A3F6466D808029A65636A@global.corp.sap>
    Generated by: https://openapi-generator.tech
"""


try:
    from inspect import getfullargspec
except ImportError:
    from inspect import getargspec as getfullargspec
import pprint
import re  # noqa: F401
import six

from openapi_client.configuration import Configuration


class PartialPickMatchEventActionResponseReturnedProps(object):
    """NOTE: This class is auto generated by OpenAPI Generator.
    Ref: https://openapi-generator.tech

    Do not edit the class manually.
    """

    """
    Attributes:
      openapi_types (dict): The key is attribute name
                            and the value is attribute type.
      attribute_map (dict): The key is attribute name
                            and the value is json key in definition.
    """
    openapi_types = {
        'event_type': 'MatchEventType',
        'match_started_seed': 'str',
        'draw_card': 'Card',
        'card_played_effect': 'CardEffect',
        'card_placed_to_play_area_card': 'Card',
        'card_removed_from_bank_card': 'Card',
        'card_removed_from_bank_index': 'int',
        'turn_ended_delta': 'IStateDelta',
        'match_ended_scores': 'list[int]',
        'match_ended_winner_idx': 'int',
        'match_ended_comment': 'str',
        'match_ended_terminated': 'bool',
        'response_to_effect_type': 'CardEffectType',
        'response_to_effect_card': 'PartialPickMatchEventActionResponseReturnedPropsResponseToEffectCard',
        'turn_started_delta': 'IStateDelta'
    }

    attribute_map = {
        'event_type': 'eventType',
        'match_started_seed': 'matchStartedSeed',
        'draw_card': 'drawCard',
        'card_played_effect': 'cardPlayedEffect',
        'card_placed_to_play_area_card': 'cardPlacedToPlayAreaCard',
        'card_removed_from_bank_card': 'cardRemovedFromBankCard',
        'card_removed_from_bank_index': 'cardRemovedFromBankIndex',
        'turn_ended_delta': 'turnEndedDelta',
        'match_ended_scores': 'matchEndedScores',
        'match_ended_winner_idx': 'matchEndedWinnerIdx',
        'match_ended_comment': 'matchEndedComment',
        'match_ended_terminated': 'matchEndedTerminated',
        'response_to_effect_type': 'responseToEffectType',
        'response_to_effect_card': 'responseToEffectCard',
        'turn_started_delta': 'turnStartedDelta'
    }

    def __init__(self, event_type=None, match_started_seed=None, draw_card=None, card_played_effect=None, card_placed_to_play_area_card=None, card_removed_from_bank_card=None, card_removed_from_bank_index=None, turn_ended_delta=None, match_ended_scores=None, match_ended_winner_idx=None, match_ended_comment=None, match_ended_terminated=None, response_to_effect_type=None, response_to_effect_card=None, turn_started_delta=None, local_vars_configuration=None):  # noqa: E501
        """PartialPickMatchEventActionResponseReturnedProps - a model defined in OpenAPI"""  # noqa: E501
        if local_vars_configuration is None:
            local_vars_configuration = Configuration.get_default_copy()
        self.local_vars_configuration = local_vars_configuration

        self._event_type = None
        self._match_started_seed = None
        self._draw_card = None
        self._card_played_effect = None
        self._card_placed_to_play_area_card = None
        self._card_removed_from_bank_card = None
        self._card_removed_from_bank_index = None
        self._turn_ended_delta = None
        self._match_ended_scores = None
        self._match_ended_winner_idx = None
        self._match_ended_comment = None
        self._match_ended_terminated = None
        self._response_to_effect_type = None
        self._response_to_effect_card = None
        self._turn_started_delta = None
        self.discriminator = None

        if event_type is not None:
            self.event_type = event_type
        if match_started_seed is not None:
            self.match_started_seed = match_started_seed
        if draw_card is not None:
            self.draw_card = draw_card
        if card_played_effect is not None:
            self.card_played_effect = card_played_effect
        if card_placed_to_play_area_card is not None:
            self.card_placed_to_play_area_card = card_placed_to_play_area_card
        if card_removed_from_bank_card is not None:
            self.card_removed_from_bank_card = card_removed_from_bank_card
        if card_removed_from_bank_index is not None:
            self.card_removed_from_bank_index = card_removed_from_bank_index
        if turn_ended_delta is not None:
            self.turn_ended_delta = turn_ended_delta
        if match_ended_scores is not None:
            self.match_ended_scores = match_ended_scores
        self.match_ended_winner_idx = match_ended_winner_idx
        if match_ended_comment is not None:
            self.match_ended_comment = match_ended_comment
        if match_ended_terminated is not None:
            self.match_ended_terminated = match_ended_terminated
        if response_to_effect_type is not None:
            self.response_to_effect_type = response_to_effect_type
        self.response_to_effect_card = response_to_effect_card
        if turn_started_delta is not None:
            self.turn_started_delta = turn_started_delta

    @property
    def event_type(self):
        """Gets the event_type of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501


        :return: The event_type of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :rtype: MatchEventType
        """
        return self._event_type

    @event_type.setter
    def event_type(self, event_type):
        """Sets the event_type of this PartialPickMatchEventActionResponseReturnedProps.


        :param event_type: The event_type of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :type event_type: MatchEventType
        """

        self._event_type = event_type

    @property
    def match_started_seed(self):
        """Gets the match_started_seed of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501


        :return: The match_started_seed of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :rtype: str
        """
        return self._match_started_seed

    @match_started_seed.setter
    def match_started_seed(self, match_started_seed):
        """Sets the match_started_seed of this PartialPickMatchEventActionResponseReturnedProps.


        :param match_started_seed: The match_started_seed of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :type match_started_seed: str
        """

        self._match_started_seed = match_started_seed

    @property
    def draw_card(self):
        """Gets the draw_card of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501


        :return: The draw_card of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :rtype: Card
        """
        return self._draw_card

    @draw_card.setter
    def draw_card(self, draw_card):
        """Sets the draw_card of this PartialPickMatchEventActionResponseReturnedProps.


        :param draw_card: The draw_card of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :type draw_card: Card
        """

        self._draw_card = draw_card

    @property
    def card_played_effect(self):
        """Gets the card_played_effect of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501


        :return: The card_played_effect of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :rtype: CardEffect
        """
        return self._card_played_effect

    @card_played_effect.setter
    def card_played_effect(self, card_played_effect):
        """Sets the card_played_effect of this PartialPickMatchEventActionResponseReturnedProps.


        :param card_played_effect: The card_played_effect of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :type card_played_effect: CardEffect
        """

        self._card_played_effect = card_played_effect

    @property
    def card_placed_to_play_area_card(self):
        """Gets the card_placed_to_play_area_card of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501


        :return: The card_placed_to_play_area_card of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :rtype: Card
        """
        return self._card_placed_to_play_area_card

    @card_placed_to_play_area_card.setter
    def card_placed_to_play_area_card(self, card_placed_to_play_area_card):
        """Sets the card_placed_to_play_area_card of this PartialPickMatchEventActionResponseReturnedProps.


        :param card_placed_to_play_area_card: The card_placed_to_play_area_card of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :type card_placed_to_play_area_card: Card
        """

        self._card_placed_to_play_area_card = card_placed_to_play_area_card

    @property
    def card_removed_from_bank_card(self):
        """Gets the card_removed_from_bank_card of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501


        :return: The card_removed_from_bank_card of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :rtype: Card
        """
        return self._card_removed_from_bank_card

    @card_removed_from_bank_card.setter
    def card_removed_from_bank_card(self, card_removed_from_bank_card):
        """Sets the card_removed_from_bank_card of this PartialPickMatchEventActionResponseReturnedProps.


        :param card_removed_from_bank_card: The card_removed_from_bank_card of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :type card_removed_from_bank_card: Card
        """

        self._card_removed_from_bank_card = card_removed_from_bank_card

    @property
    def card_removed_from_bank_index(self):
        """Gets the card_removed_from_bank_index of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501


        :return: The card_removed_from_bank_index of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :rtype: int
        """
        return self._card_removed_from_bank_index

    @card_removed_from_bank_index.setter
    def card_removed_from_bank_index(self, card_removed_from_bank_index):
        """Sets the card_removed_from_bank_index of this PartialPickMatchEventActionResponseReturnedProps.


        :param card_removed_from_bank_index: The card_removed_from_bank_index of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :type card_removed_from_bank_index: int
        """

        self._card_removed_from_bank_index = card_removed_from_bank_index

    @property
    def turn_ended_delta(self):
        """Gets the turn_ended_delta of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501


        :return: The turn_ended_delta of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :rtype: IStateDelta
        """
        return self._turn_ended_delta

    @turn_ended_delta.setter
    def turn_ended_delta(self, turn_ended_delta):
        """Sets the turn_ended_delta of this PartialPickMatchEventActionResponseReturnedProps.


        :param turn_ended_delta: The turn_ended_delta of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :type turn_ended_delta: IStateDelta
        """

        self._turn_ended_delta = turn_ended_delta

    @property
    def match_ended_scores(self):
        """Gets the match_ended_scores of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501


        :return: The match_ended_scores of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :rtype: list[int]
        """
        return self._match_ended_scores

    @match_ended_scores.setter
    def match_ended_scores(self, match_ended_scores):
        """Sets the match_ended_scores of this PartialPickMatchEventActionResponseReturnedProps.


        :param match_ended_scores: The match_ended_scores of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :type match_ended_scores: list[int]
        """

        self._match_ended_scores = match_ended_scores

    @property
    def match_ended_winner_idx(self):
        """Gets the match_ended_winner_idx of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501


        :return: The match_ended_winner_idx of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :rtype: int
        """
        return self._match_ended_winner_idx

    @match_ended_winner_idx.setter
    def match_ended_winner_idx(self, match_ended_winner_idx):
        """Sets the match_ended_winner_idx of this PartialPickMatchEventActionResponseReturnedProps.


        :param match_ended_winner_idx: The match_ended_winner_idx of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :type match_ended_winner_idx: int
        """

        self._match_ended_winner_idx = match_ended_winner_idx

    @property
    def match_ended_comment(self):
        """Gets the match_ended_comment of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501


        :return: The match_ended_comment of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :rtype: str
        """
        return self._match_ended_comment

    @match_ended_comment.setter
    def match_ended_comment(self, match_ended_comment):
        """Sets the match_ended_comment of this PartialPickMatchEventActionResponseReturnedProps.


        :param match_ended_comment: The match_ended_comment of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :type match_ended_comment: str
        """

        self._match_ended_comment = match_ended_comment

    @property
    def match_ended_terminated(self):
        """Gets the match_ended_terminated of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501


        :return: The match_ended_terminated of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :rtype: bool
        """
        return self._match_ended_terminated

    @match_ended_terminated.setter
    def match_ended_terminated(self, match_ended_terminated):
        """Sets the match_ended_terminated of this PartialPickMatchEventActionResponseReturnedProps.


        :param match_ended_terminated: The match_ended_terminated of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :type match_ended_terminated: bool
        """

        self._match_ended_terminated = match_ended_terminated

    @property
    def response_to_effect_type(self):
        """Gets the response_to_effect_type of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501


        :return: The response_to_effect_type of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :rtype: CardEffectType
        """
        return self._response_to_effect_type

    @response_to_effect_type.setter
    def response_to_effect_type(self, response_to_effect_type):
        """Sets the response_to_effect_type of this PartialPickMatchEventActionResponseReturnedProps.


        :param response_to_effect_type: The response_to_effect_type of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :type response_to_effect_type: CardEffectType
        """

        self._response_to_effect_type = response_to_effect_type

    @property
    def response_to_effect_card(self):
        """Gets the response_to_effect_card of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501


        :return: The response_to_effect_card of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :rtype: PartialPickMatchEventActionResponseReturnedPropsResponseToEffectCard
        """
        return self._response_to_effect_card

    @response_to_effect_card.setter
    def response_to_effect_card(self, response_to_effect_card):
        """Sets the response_to_effect_card of this PartialPickMatchEventActionResponseReturnedProps.


        :param response_to_effect_card: The response_to_effect_card of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :type response_to_effect_card: PartialPickMatchEventActionResponseReturnedPropsResponseToEffectCard
        """

        self._response_to_effect_card = response_to_effect_card

    @property
    def turn_started_delta(self):
        """Gets the turn_started_delta of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501


        :return: The turn_started_delta of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :rtype: IStateDelta
        """
        return self._turn_started_delta

    @turn_started_delta.setter
    def turn_started_delta(self, turn_started_delta):
        """Sets the turn_started_delta of this PartialPickMatchEventActionResponseReturnedProps.


        :param turn_started_delta: The turn_started_delta of this PartialPickMatchEventActionResponseReturnedProps.  # noqa: E501
        :type turn_started_delta: IStateDelta
        """

        self._turn_started_delta = turn_started_delta

    def to_dict(self, serialize=False):
        """Returns the model properties as a dict"""
        result = {}

        def convert(x):
            if hasattr(x, "to_dict"):
                args = getfullargspec(x.to_dict).args
                if len(args) == 1:
                    return x.to_dict()
                else:
                    return x.to_dict(serialize)
            else:
                return x

        for attr, _ in six.iteritems(self.openapi_types):
            value = getattr(self, attr)
            attr = self.attribute_map.get(attr, attr) if serialize else attr
            if isinstance(value, list):
                result[attr] = list(map(
                    lambda x: convert(x),
                    value
                ))
            elif isinstance(value, dict):
                result[attr] = dict(map(
                    lambda item: (item[0], convert(item[1])),
                    value.items()
                ))
            else:
                result[attr] = convert(value)

        return result

    def to_str(self):
        """Returns the string representation of the model"""
        return pprint.pformat(self.to_dict())

    def __repr__(self):
        """For `print` and `pprint`"""
        return self.to_str()

    def __eq__(self, other):
        """Returns true if both objects are equal"""
        if not isinstance(other, PartialPickMatchEventActionResponseReturnedProps):
            return False

        return self.to_dict() == other.to_dict()

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        if not isinstance(other, PartialPickMatchEventActionResponseReturnedProps):
            return True

        return self.to_dict() != other.to_dict()
