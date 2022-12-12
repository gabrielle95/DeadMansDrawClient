/**
 * SLHPC23 Arena
 * **SAP Labs CEE Hub Programming Competition 2023 Arean server**.  You can find more information about the game and the competititon rules at [github/SLH_SPC_2022](https://github.com/afarago/SLH_SPC_2022).   For a test run, you can use the crash test dummy user `000000000000000000000000/dummypass`.   *Note: All the APIs expect and return application/json*.
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: DL SLHPC23 <DL_637A3F6466D808029A65636A@global.corp.sap>
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 *
 */

import ApiClient from '../ApiClient';
import MatchCreationParams from './MatchCreationParams';
import MoveDTO from './MoveDTO';
import State from './State';

/**
 * The MatchDTO model module.
 * @module model/MatchDTO
 * @version 1.0.0
 */
class MatchDTO {
    /**
     * Constructs a new <code>MatchDTO</code>.
     * Match Response item
     * @alias module:model/MatchDTO
     * @param id {String} A class representation of the BSON ObjectId type.
     * @param playerids {Array.<String>} 
     * @param startedAt {Date} 
     * @param lastMoveAt {Date} 
     * @param moveCount {Number} 
     * @param turnCount {Number} 
     * @param moveCountInTurn {Number} 
     * @param currentPlayerIndex {Number} 
     * @param drawPileSize {Number} 
     * @param discardPileSize {Number} 
     */
    constructor(id, playerids, startedAt, lastMoveAt, moveCount, turnCount, moveCountInTurn, currentPlayerIndex, drawPileSize, discardPileSize) { 
        
        MatchDTO.initialize(this, id, playerids, startedAt, lastMoveAt, moveCount, turnCount, moveCountInTurn, currentPlayerIndex, drawPileSize, discardPileSize);
    }

    /**
     * Initializes the fields of this object.
     * This method is used by the constructors of any subclasses, in order to implement multiple inheritance (mix-ins).
     * Only for internal use.
     */
    static initialize(obj, id, playerids, startedAt, lastMoveAt, moveCount, turnCount, moveCountInTurn, currentPlayerIndex, drawPileSize, discardPileSize) { 
        obj['_id'] = id;
        obj['playerids'] = playerids;
        obj['startedAt'] = startedAt;
        obj['lastMoveAt'] = lastMoveAt;
        obj['moveCount'] = moveCount;
        obj['turnCount'] = turnCount;
        obj['moveCountInTurn'] = moveCountInTurn;
        obj['currentPlayerIndex'] = currentPlayerIndex;
        obj['drawPileSize'] = drawPileSize;
        obj['discardPileSize'] = discardPileSize;
    }

    /**
     * Constructs a <code>MatchDTO</code> from a plain JavaScript object, optionally creating a new instance.
     * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @param {module:model/MatchDTO} obj Optional instance to populate.
     * @return {module:model/MatchDTO} The populated <code>MatchDTO</code> instance.
     */
    static constructFromObject(data, obj) {
        if (data) {
            obj = obj || new MatchDTO();

            if (data.hasOwnProperty('_id')) {
                obj['_id'] = ApiClient.convertToType(data['_id'], 'String');
            }
            if (data.hasOwnProperty('playerids')) {
                obj['playerids'] = ApiClient.convertToType(data['playerids'], ['String']);
            }
            if (data.hasOwnProperty('startedAt')) {
                obj['startedAt'] = ApiClient.convertToType(data['startedAt'], 'Date');
            }
            if (data.hasOwnProperty('createdByPlayerId')) {
                obj['createdByPlayerId'] = ApiClient.convertToType(data['createdByPlayerId'], 'String');
            }
            if (data.hasOwnProperty('creationParams')) {
                obj['creationParams'] = MatchCreationParams.constructFromObject(data['creationParams']);
            }
            if (data.hasOwnProperty('lastMoveAt')) {
                obj['lastMoveAt'] = ApiClient.convertToType(data['lastMoveAt'], 'Date');
            }
            if (data.hasOwnProperty('moveCount')) {
                obj['moveCount'] = ApiClient.convertToType(data['moveCount'], 'Number');
            }
            if (data.hasOwnProperty('turnCount')) {
                obj['turnCount'] = ApiClient.convertToType(data['turnCount'], 'Number');
            }
            if (data.hasOwnProperty('moveCountInTurn')) {
                obj['moveCountInTurn'] = ApiClient.convertToType(data['moveCountInTurn'], 'Number');
            }
            if (data.hasOwnProperty('state')) {
                obj['state'] = State.constructFromObject(data['state']);
            }
            if (data.hasOwnProperty('stateAtTurnStart')) {
                obj['stateAtTurnStart'] = State.constructFromObject(data['stateAtTurnStart']);
            }
            if (data.hasOwnProperty('currentPlayerIndex')) {
                obj['currentPlayerIndex'] = ApiClient.convertToType(data['currentPlayerIndex'], 'Number');
            }
            if (data.hasOwnProperty('playernames')) {
                obj['playernames'] = ApiClient.convertToType(data['playernames'], ['String']);
            }
            if (data.hasOwnProperty('moves')) {
                obj['moves'] = ApiClient.convertToType(data['moves'], [MoveDTO]);
            }
            if (data.hasOwnProperty('drawPileSize')) {
                obj['drawPileSize'] = ApiClient.convertToType(data['drawPileSize'], 'Number');
            }
            if (data.hasOwnProperty('discardPileSize')) {
                obj['discardPileSize'] = ApiClient.convertToType(data['discardPileSize'], 'Number');
            }
            if (data.hasOwnProperty('activePlayerIndex')) {
                obj['activePlayerIndex'] = ApiClient.convertToType(data['activePlayerIndex'], 'Number');
            }
        }
        return obj;
    }

    /**
     * Validates the JSON data with respect to <code>MatchDTO</code>.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @return {boolean} to indicate whether the JSON data is valid with respect to <code>MatchDTO</code>.
     */
    static validateJSON(data) {
        // check to make sure all required properties are present in the JSON string
        for (const property of MatchDTO.RequiredProperties) {
            if (!data[property]) {
                throw new Error("The required field `" + property + "` is not found in the JSON data: " + JSON.stringify(data));
            }
        }
        // ensure the json data is a string
        if (data['_id'] && !(typeof data['_id'] === 'string' || data['_id'] instanceof String)) {
            throw new Error("Expected the field `_id` to be a primitive type in the JSON string but got " + data['_id']);
        }
        // ensure the json data is an array
        if (!Array.isArray(data['playerids'])) {
            throw new Error("Expected the field `playerids` to be an array in the JSON data but got " + data['playerids']);
        }
        // validate the optional field `createdByPlayerId`
        if (data['createdByPlayerId']) { // data not null
          String.validateJSON(data['createdByPlayerId']);
        }
        // validate the optional field `creationParams`
        if (data['creationParams']) { // data not null
          MatchCreationParams.validateJSON(data['creationParams']);
        }
        // validate the optional field `moveCountInTurn`
        if (data['moveCountInTurn']) { // data not null
          Number.validateJSON(data['moveCountInTurn']);
        }
        // validate the optional field `state`
        if (data['state']) { // data not null
          State.validateJSON(data['state']);
        }
        // validate the optional field `stateAtTurnStart`
        if (data['stateAtTurnStart']) { // data not null
          State.validateJSON(data['stateAtTurnStart']);
        }
        // validate the optional field `currentPlayerIndex`
        if (data['currentPlayerIndex']) { // data not null
          Number.validateJSON(data['currentPlayerIndex']);
        }
        // ensure the json data is an array
        if (!Array.isArray(data['playernames'])) {
            throw new Error("Expected the field `playernames` to be an array in the JSON data but got " + data['playernames']);
        }
        if (data['moves']) { // data not null
            // ensure the json data is an array
            if (!Array.isArray(data['moves'])) {
                throw new Error("Expected the field `moves` to be an array in the JSON data but got " + data['moves']);
            }
            // validate the optional field `moves` (array)
            for (const item of data['moves']) {
                MoveDTO.validateJsonObject(item);
            };
        }
        // validate the optional field `activePlayerIndex`
        if (data['activePlayerIndex']) { // data not null
          Number.validateJSON(data['activePlayerIndex']);
        }

        return true;
    }


}

MatchDTO.RequiredProperties = ["_id", "playerids", "startedAt", "lastMoveAt", "moveCount", "turnCount", "moveCountInTurn", "currentPlayerIndex", "drawPileSize", "discardPileSize"];

/**
 * A class representation of the BSON ObjectId type.
 * @member {String} _id
 */
MatchDTO.prototype['_id'] = undefined;

/**
 * @member {Array.<String>} playerids
 */
MatchDTO.prototype['playerids'] = undefined;

/**
 * @member {Date} startedAt
 */
MatchDTO.prototype['startedAt'] = undefined;

/**
 * @member {String} createdByPlayerId
 */
MatchDTO.prototype['createdByPlayerId'] = undefined;

/**
 * @member {module:model/MatchCreationParams} creationParams
 */
MatchDTO.prototype['creationParams'] = undefined;

/**
 * @member {Date} lastMoveAt
 */
MatchDTO.prototype['lastMoveAt'] = undefined;

/**
 * @member {Number} moveCount
 */
MatchDTO.prototype['moveCount'] = undefined;

/**
 * @member {Number} turnCount
 */
MatchDTO.prototype['turnCount'] = undefined;

/**
 * @member {Number} moveCountInTurn
 */
MatchDTO.prototype['moveCountInTurn'] = undefined;

/**
 * @member {module:model/State} state
 */
MatchDTO.prototype['state'] = undefined;

/**
 * @member {module:model/State} stateAtTurnStart
 */
MatchDTO.prototype['stateAtTurnStart'] = undefined;

/**
 * @member {Number} currentPlayerIndex
 */
MatchDTO.prototype['currentPlayerIndex'] = undefined;

/**
 * @member {Array.<String>} playernames
 */
MatchDTO.prototype['playernames'] = undefined;

/**
 * @member {Array.<module:model/MoveDTO>} moves
 */
MatchDTO.prototype['moves'] = undefined;

/**
 * @member {Number} drawPileSize
 */
MatchDTO.prototype['drawPileSize'] = undefined;

/**
 * @member {Number} discardPileSize
 */
MatchDTO.prototype['discardPileSize'] = undefined;

/**
 * @member {Number} activePlayerIndex
 */
MatchDTO.prototype['activePlayerIndex'] = undefined;






export default MatchDTO;

