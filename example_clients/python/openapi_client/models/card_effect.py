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


class CardEffect(object):
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
        'effect_type': 'CardEffectType',
        'card': 'Card',
        'cards': 'list[Card]'
    }

    attribute_map = {
        'effect_type': 'effectType',
        'card': 'card',
        'cards': 'cards'
    }

    def __init__(self, effect_type=None, card=None, cards=None, local_vars_configuration=None):  # noqa: E501
        """CardEffect - a model defined in OpenAPI"""  # noqa: E501
        if local_vars_configuration is None:
            local_vars_configuration = Configuration.get_default_copy()
        self.local_vars_configuration = local_vars_configuration

        self._effect_type = None
        self._card = None
        self._cards = None
        self.discriminator = None

        if effect_type is not None:
            self.effect_type = effect_type
        if card is not None:
            self.card = card
        if cards is not None:
            self.cards = cards

    @property
    def effect_type(self):
        """Gets the effect_type of this CardEffect.  # noqa: E501


        :return: The effect_type of this CardEffect.  # noqa: E501
        :rtype: CardEffectType
        """
        return self._effect_type

    @effect_type.setter
    def effect_type(self, effect_type):
        """Sets the effect_type of this CardEffect.


        :param effect_type: The effect_type of this CardEffect.  # noqa: E501
        :type effect_type: CardEffectType
        """

        self._effect_type = effect_type

    @property
    def card(self):
        """Gets the card of this CardEffect.  # noqa: E501


        :return: The card of this CardEffect.  # noqa: E501
        :rtype: Card
        """
        return self._card

    @card.setter
    def card(self, card):
        """Sets the card of this CardEffect.


        :param card: The card of this CardEffect.  # noqa: E501
        :type card: Card
        """

        self._card = card

    @property
    def cards(self):
        """Gets the cards of this CardEffect.  # noqa: E501


        :return: The cards of this CardEffect.  # noqa: E501
        :rtype: list[Card]
        """
        return self._cards

    @cards.setter
    def cards(self, cards):
        """Sets the cards of this CardEffect.


        :param cards: The cards of this CardEffect.  # noqa: E501
        :type cards: list[Card]
        """

        self._cards = cards

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
        if not isinstance(other, CardEffect):
            return False

        return self.to_dict() == other.to_dict()

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        if not isinstance(other, CardEffect):
            return True

        return self.to_dict() != other.to_dict()
