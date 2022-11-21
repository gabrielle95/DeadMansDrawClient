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


class State(object):
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
        'banks': 'list[dict]',
        'draw_pile': 'DrawCardPile',
        'discard_pile': 'list[Card]',
        'play_area': 'list[Card]',
        'current_player_index': 'int',
        'pending_effect': 'CardEffect',
        'pending_kraken_cards': 'int',
        'winner_idx': 'int'
    }

    attribute_map = {
        'banks': 'banks',
        'draw_pile': 'drawPile',
        'discard_pile': 'discardPile',
        'play_area': 'playArea',
        'current_player_index': 'currentPlayerIndex',
        'pending_effect': 'pendingEffect',
        'pending_kraken_cards': 'pendingKrakenCards',
        'winner_idx': 'winnerIdx'
    }

    def __init__(self, banks=None, draw_pile=None, discard_pile=None, play_area=None, current_player_index=None, pending_effect=None, pending_kraken_cards=None, winner_idx=None, local_vars_configuration=None):  # noqa: E501
        """State - a model defined in OpenAPI"""  # noqa: E501
        if local_vars_configuration is None:
            local_vars_configuration = Configuration.get_default_copy()
        self.local_vars_configuration = local_vars_configuration

        self._banks = None
        self._draw_pile = None
        self._discard_pile = None
        self._play_area = None
        self._current_player_index = None
        self._pending_effect = None
        self._pending_kraken_cards = None
        self._winner_idx = None
        self.discriminator = None

        self.banks = banks
        if draw_pile is not None:
            self.draw_pile = draw_pile
        if discard_pile is not None:
            self.discard_pile = discard_pile
        self.play_area = play_area
        if current_player_index is not None:
            self.current_player_index = current_player_index
        if pending_effect is not None:
            self.pending_effect = pending_effect
        if pending_kraken_cards is not None:
            self.pending_kraken_cards = pending_kraken_cards
        if winner_idx is not None:
            self.winner_idx = winner_idx

    @property
    def banks(self):
        """Gets the banks of this State.  # noqa: E501


        :return: The banks of this State.  # noqa: E501
        :rtype: list[dict]
        """
        return self._banks

    @banks.setter
    def banks(self, banks):
        """Sets the banks of this State.


        :param banks: The banks of this State.  # noqa: E501
        :type banks: list[dict]
        """
        if self.local_vars_configuration.client_side_validation and banks is None:  # noqa: E501
            raise ValueError("Invalid value for `banks`, must not be `None`")  # noqa: E501

        self._banks = banks

    @property
    def draw_pile(self):
        """Gets the draw_pile of this State.  # noqa: E501


        :return: The draw_pile of this State.  # noqa: E501
        :rtype: DrawCardPile
        """
        return self._draw_pile

    @draw_pile.setter
    def draw_pile(self, draw_pile):
        """Sets the draw_pile of this State.


        :param draw_pile: The draw_pile of this State.  # noqa: E501
        :type draw_pile: DrawCardPile
        """

        self._draw_pile = draw_pile

    @property
    def discard_pile(self):
        """Gets the discard_pile of this State.  # noqa: E501

        Discard pile - object to represent discard pile  # noqa: E501

        :return: The discard_pile of this State.  # noqa: E501
        :rtype: list[Card]
        """
        return self._discard_pile

    @discard_pile.setter
    def discard_pile(self, discard_pile):
        """Sets the discard_pile of this State.

        Discard pile - object to represent discard pile  # noqa: E501

        :param discard_pile: The discard_pile of this State.  # noqa: E501
        :type discard_pile: list[Card]
        """

        self._discard_pile = discard_pile

    @property
    def play_area(self):
        """Gets the play_area of this State.  # noqa: E501

        Play area - object to represent the play area  # noqa: E501

        :return: The play_area of this State.  # noqa: E501
        :rtype: list[Card]
        """
        return self._play_area

    @play_area.setter
    def play_area(self, play_area):
        """Sets the play_area of this State.

        Play area - object to represent the play area  # noqa: E501

        :param play_area: The play_area of this State.  # noqa: E501
        :type play_area: list[Card]
        """
        if self.local_vars_configuration.client_side_validation and play_area is None:  # noqa: E501
            raise ValueError("Invalid value for `play_area`, must not be `None`")  # noqa: E501

        self._play_area = play_area

    @property
    def current_player_index(self):
        """Gets the current_player_index of this State.  # noqa: E501


        :return: The current_player_index of this State.  # noqa: E501
        :rtype: int
        """
        return self._current_player_index

    @current_player_index.setter
    def current_player_index(self, current_player_index):
        """Sets the current_player_index of this State.


        :param current_player_index: The current_player_index of this State.  # noqa: E501
        :type current_player_index: int
        """

        self._current_player_index = current_player_index

    @property
    def pending_effect(self):
        """Gets the pending_effect of this State.  # noqa: E501


        :return: The pending_effect of this State.  # noqa: E501
        :rtype: CardEffect
        """
        return self._pending_effect

    @pending_effect.setter
    def pending_effect(self, pending_effect):
        """Sets the pending_effect of this State.


        :param pending_effect: The pending_effect of this State.  # noqa: E501
        :type pending_effect: CardEffect
        """

        self._pending_effect = pending_effect

    @property
    def pending_kraken_cards(self):
        """Gets the pending_kraken_cards of this State.  # noqa: E501


        :return: The pending_kraken_cards of this State.  # noqa: E501
        :rtype: int
        """
        return self._pending_kraken_cards

    @pending_kraken_cards.setter
    def pending_kraken_cards(self, pending_kraken_cards):
        """Sets the pending_kraken_cards of this State.


        :param pending_kraken_cards: The pending_kraken_cards of this State.  # noqa: E501
        :type pending_kraken_cards: int
        """

        self._pending_kraken_cards = pending_kraken_cards

    @property
    def winner_idx(self):
        """Gets the winner_idx of this State.  # noqa: E501


        :return: The winner_idx of this State.  # noqa: E501
        :rtype: int
        """
        return self._winner_idx

    @winner_idx.setter
    def winner_idx(self, winner_idx):
        """Sets the winner_idx of this State.


        :param winner_idx: The winner_idx of this State.  # noqa: E501
        :type winner_idx: int
        """

        self._winner_idx = winner_idx

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
        if not isinstance(other, State):
            return False

        return self.to_dict() == other.to_dict()

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        if not isinstance(other, State):
            return True

        return self.to_dict() != other.to_dict()
