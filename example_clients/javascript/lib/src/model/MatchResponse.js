/**
 * SLHPC23 Arena
 * **SAP Labs Hungary Programming Competition 2023 Arena server**.  You can find more information about the game and the competititon rules at [github/SLH_SPC_2022](https://github.com/afarago/SLH_SPC_2022).   For a test run, you can use the crash test dummy user `000000000000000000000000/dummypass`.   *Note: All the APIs expect and return application/json*.
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
import MatchResponseReturned2 from './MatchResponseReturned2';
import PickMatchMatchResponseReturnedProps from './PickMatchMatchResponseReturnedProps';
import State from './State';

/**
 * The MatchResponse model module.
 * @module model/MatchResponse
 * @version 1.0.0
 */
class MatchResponse {
    /**
     * Constructs a new <code>MatchResponse</code>.
     * @alias module:model/MatchResponse
     * @implements module:model/PickMatchMatchResponseReturnedProps
     * @implements module:model/MatchResponseReturned2
     * @param id {String} A class representation of the BSON ObjectId type.
     * @param players {Array.<String>} 
     * @param startedAt {Date} 
     * @param moveCount {Number} 
     * @param lastMoveAt {Date} 
     */
    constructor(id, players, startedAt, moveCount, lastMoveAt) { 
        PickMatchMatchResponseReturnedProps.initialize(this, id, players, startedAt, moveCount, lastMoveAt);MatchResponseReturned2.initialize(this);
        MatchResponse.initialize(this, id, players, startedAt, moveCount, lastMoveAt);
    }

    /**
     * Initializes the fields of this object.
     * This method is used by the constructors of any subclasses, in order to implement multiple inheritance (mix-ins).
     * Only for internal use.
     */
    static initialize(obj, id, players, startedAt, moveCount, lastMoveAt) { 
        obj['_id'] = id;
        obj['players'] = players;
        obj['startedAt'] = startedAt;
        obj['moveCount'] = moveCount;
        obj['lastMoveAt'] = lastMoveAt;
    }

    /**
     * Constructs a <code>MatchResponse</code> from a plain JavaScript object, optionally creating a new instance.
     * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @param {module:model/MatchResponse} obj Optional instance to populate.
     * @return {module:model/MatchResponse} The populated <code>MatchResponse</code> instance.
     */
    static constructFromObject(data, obj) {
        if (data) {
            obj = obj || new MatchResponse();
            PickMatchMatchResponseReturnedProps.constructFromObject(data, obj);
            MatchResponseReturned2.constructFromObject(data, obj);

            if (data.hasOwnProperty('_id')) {
                obj['_id'] = ApiClient.convertToType(data['_id'], 'String');
            }
            if (data.hasOwnProperty('players')) {
                obj['players'] = ApiClient.convertToType(data['players'], ['String']);
            }
            if (data.hasOwnProperty('startedAt')) {
                obj['startedAt'] = ApiClient.convertToType(data['startedAt'], 'Date');
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
            if (data.hasOwnProperty('lastMoveAt')) {
                obj['lastMoveAt'] = ApiClient.convertToType(data['lastMoveAt'], 'Date');
            }
            if (data.hasOwnProperty('createdByPlayerId')) {
                obj['createdByPlayerId'] = ApiClient.convertToType(data['createdByPlayerId'], 'String');
            }
            if (data.hasOwnProperty('creationParams')) {
                obj['creationParams'] = MatchCreationParams.constructFromObject(data['creationParams']);
            }
            if (data.hasOwnProperty('currentPlayerId')) {
                obj['currentPlayerId'] = ApiClient.convertToType(data['currentPlayerId'], 'String');
            }
            if (data.hasOwnProperty('state')) {
                obj['state'] = State.constructFromObject(data['state']);
            }
        }
        return obj;
    }

    /**
     * Validates the JSON data with respect to <code>MatchResponse</code>.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @return {boolean} to indicate whether the JSON data is valid with respect to <code>MatchResponse</code>.
     */
    static validateJSON(data) {
        // check to make sure all required properties are present in the JSON string
        for (const property of MatchResponse.RequiredProperties) {
            if (!data[property]) {
                throw new Error("The required field `" + property + "` is not found in the JSON data: " + JSON.stringify(data));
            }
        }
        // ensure the json data is a string
        if (data['_id'] && !(typeof data['_id'] === 'string' || data['_id'] instanceof String)) {
            throw new Error("Expected the field `_id` to be a primitive type in the JSON string but got " + data['_id']);
        }
        // ensure the json data is an array
        if (!Array.isArray(data['players'])) {
            throw new Error("Expected the field `players` to be an array in the JSON data but got " + data['players']);
        }
        // validate the optional field `moveCountInTurn`
        if (data['moveCountInTurn']) { // data not null
          Number.validateJSON(data['moveCountInTurn']);
        }
        // validate the optional field `createdByPlayerId`
        if (data['createdByPlayerId']) { // data not null
          String.validateJSON(data['createdByPlayerId']);
        }
        // validate the optional field `creationParams`
        if (data['creationParams']) { // data not null
          MatchCreationParams.validateJSON(data['creationParams']);
        }
        // validate the optional field `currentPlayerId`
        if (data['currentPlayerId']) { // data not null
          String.validateJSON(data['currentPlayerId']);
        }
        // validate the optional field `state`
        if (data['state']) { // data not null
          State.validateJSON(data['state']);
        }

        return true;
    }


}

MatchResponse.RequiredProperties = ["_id", "players", "startedAt", "moveCount", "lastMoveAt"];

/**
 * A class representation of the BSON ObjectId type.
 * @member {String} _id
 */
MatchResponse.prototype['_id'] = undefined;

/**
 * @member {Array.<String>} players
 */
MatchResponse.prototype['players'] = undefined;

/**
 * @member {Date} startedAt
 */
MatchResponse.prototype['startedAt'] = undefined;

/**
 * @member {Number} moveCount
 */
MatchResponse.prototype['moveCount'] = undefined;

/**
 * @member {Number} turnCount
 */
MatchResponse.prototype['turnCount'] = undefined;

/**
 * @member {Number} moveCountInTurn
 */
MatchResponse.prototype['moveCountInTurn'] = undefined;

/**
 * @member {Date} lastMoveAt
 */
MatchResponse.prototype['lastMoveAt'] = undefined;

/**
 * @member {String} createdByPlayerId
 */
MatchResponse.prototype['createdByPlayerId'] = undefined;

/**
 * @member {module:model/MatchCreationParams} creationParams
 */
MatchResponse.prototype['creationParams'] = undefined;

/**
 * @member {String} currentPlayerId
 */
MatchResponse.prototype['currentPlayerId'] = undefined;

/**
 * @member {module:model/State} state
 */
MatchResponse.prototype['state'] = undefined;


// Implement PickMatchMatchResponseReturnedProps interface:
/**
 * A class representation of the BSON ObjectId type.
 * @member {String} _id
 */
PickMatchMatchResponseReturnedProps.prototype['_id'] = undefined;
/**
 * @member {Array.<String>} players
 */
PickMatchMatchResponseReturnedProps.prototype['players'] = undefined;
/**
 * @member {Date} startedAt
 */
PickMatchMatchResponseReturnedProps.prototype['startedAt'] = undefined;
/**
 * @member {Number} moveCount
 */
PickMatchMatchResponseReturnedProps.prototype['moveCount'] = undefined;
/**
 * @member {Number} turnCount
 */
PickMatchMatchResponseReturnedProps.prototype['turnCount'] = undefined;
/**
 * @member {Number} moveCountInTurn
 */
PickMatchMatchResponseReturnedProps.prototype['moveCountInTurn'] = undefined;
/**
 * @member {Date} lastMoveAt
 */
PickMatchMatchResponseReturnedProps.prototype['lastMoveAt'] = undefined;
/**
 * @member {String} createdByPlayerId
 */
PickMatchMatchResponseReturnedProps.prototype['createdByPlayerId'] = undefined;
/**
 * @member {module:model/MatchCreationParams} creationParams
 */
PickMatchMatchResponseReturnedProps.prototype['creationParams'] = undefined;
// Implement MatchResponseReturned2 interface:
/**
 * @member {String} currentPlayerId
 */
MatchResponseReturned2.prototype['currentPlayerId'] = undefined;
/**
 * @member {module:model/State} state
 */
MatchResponseReturned2.prototype['state'] = undefined;




export default MatchResponse;

