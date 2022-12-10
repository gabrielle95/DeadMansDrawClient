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

/**
 * The MatchCreateResponse model module.
 * @module model/MatchCreateResponse
 * @version 1.0.0
 */
class MatchCreateResponse {
    /**
     * Constructs a new <code>MatchCreateResponse</code>.
     * Match creation result
     * @alias module:model/MatchCreateResponse
     * @param id {String} Stringified Object Id.
     */
    constructor(id) { 
        
        MatchCreateResponse.initialize(this, id);
    }

    /**
     * Initializes the fields of this object.
     * This method is used by the constructors of any subclasses, in order to implement multiple inheritance (mix-ins).
     * Only for internal use.
     */
    static initialize(obj, id) { 
        obj['id'] = id;
    }

    /**
     * Constructs a <code>MatchCreateResponse</code> from a plain JavaScript object, optionally creating a new instance.
     * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @param {module:model/MatchCreateResponse} obj Optional instance to populate.
     * @return {module:model/MatchCreateResponse} The populated <code>MatchCreateResponse</code> instance.
     */
    static constructFromObject(data, obj) {
        if (data) {
            obj = obj || new MatchCreateResponse();

            if (data.hasOwnProperty('randomSeed')) {
                obj['randomSeed'] = ApiClient.convertToType(data['randomSeed'], 'String');
            }
            if (data.hasOwnProperty('id')) {
                obj['id'] = ApiClient.convertToType(data['id'], 'String');
            }
        }
        return obj;
    }

    /**
     * Validates the JSON data with respect to <code>MatchCreateResponse</code>.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @return {boolean} to indicate whether the JSON data is valid with respect to <code>MatchCreateResponse</code>.
     */
    static validateJSON(data) {
        // check to make sure all required properties are present in the JSON string
        for (const property of MatchCreateResponse.RequiredProperties) {
            if (!data[property]) {
                throw new Error("The required field `" + property + "` is not found in the JSON data: " + JSON.stringify(data));
            }
        }
        // ensure the json data is a string
        if (data['randomSeed'] && !(typeof data['randomSeed'] === 'string' || data['randomSeed'] instanceof String)) {
            throw new Error("Expected the field `randomSeed` to be a primitive type in the JSON string but got " + data['randomSeed']);
        }
        // ensure the json data is a string
        if (data['id'] && !(typeof data['id'] === 'string' || data['id'] instanceof String)) {
            throw new Error("Expected the field `id` to be a primitive type in the JSON string but got " + data['id']);
        }

        return true;
    }


}

MatchCreateResponse.RequiredProperties = ["id"];

/**
 * @member {String} randomSeed
 */
MatchCreateResponse.prototype['randomSeed'] = undefined;

/**
 * Stringified Object Id.
 * @member {String} id
 */
MatchCreateResponse.prototype['id'] = undefined;






export default MatchCreateResponse;

