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
import IStateDeltaStack from './IStateDeltaStack';

/**
 * The IStateDelta model module.
 * @module model/IStateDelta
 * @version 1.0.0
 */
class IStateDelta {
    /**
     * Constructs a new <code>IStateDelta</code>.
     * @alias module:model/IStateDelta
     */
    constructor() { 
        
        IStateDelta.initialize(this);
    }

    /**
     * Initializes the fields of this object.
     * This method is used by the constructors of any subclasses, in order to implement multiple inheritance (mix-ins).
     * Only for internal use.
     */
    static initialize(obj) { 
    }

    /**
     * Constructs a <code>IStateDelta</code> from a plain JavaScript object, optionally creating a new instance.
     * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @param {module:model/IStateDelta} obj Optional instance to populate.
     * @return {module:model/IStateDelta} The populated <code>IStateDelta</code> instance.
     */
    static constructFromObject(data, obj) {
        if (data) {
            obj = obj || new IStateDelta();

            if (data.hasOwnProperty('drawPile')) {
                obj['drawPile'] = IStateDeltaStack.constructFromObject(data['drawPile']);
            }
            if (data.hasOwnProperty('discardPile')) {
                obj['discardPile'] = IStateDeltaStack.constructFromObject(data['discardPile']);
            }
            if (data.hasOwnProperty('banks')) {
                obj['banks'] = ApiClient.convertToType(data['banks'], [IStateDeltaStack]);
            }
        }
        return obj;
    }

    /**
     * Validates the JSON data with respect to <code>IStateDelta</code>.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @return {boolean} to indicate whether the JSON data is valid with respect to <code>IStateDelta</code>.
     */
    static validateJSON(data) {
        // validate the optional field `drawPile`
        if (data['drawPile']) { // data not null
          IStateDeltaStack.validateJSON(data['drawPile']);
        }
        // validate the optional field `discardPile`
        if (data['discardPile']) { // data not null
          IStateDeltaStack.validateJSON(data['discardPile']);
        }
        if (data['banks']) { // data not null
            // ensure the json data is an array
            if (!Array.isArray(data['banks'])) {
                throw new Error("Expected the field `banks` to be an array in the JSON data but got " + data['banks']);
            }
            // validate the optional field `banks` (array)
            for (const item of data['banks']) {
                IStateDeltaStack.validateJsonObject(item);
            };
        }

        return true;
    }


}



/**
 * @member {module:model/IStateDeltaStack} drawPile
 */
IStateDelta.prototype['drawPile'] = undefined;

/**
 * @member {module:model/IStateDeltaStack} discardPile
 */
IStateDelta.prototype['discardPile'] = undefined;

/**
 * @member {Array.<module:model/IStateDeltaStack>} banks
 */
IStateDelta.prototype['banks'] = undefined;






export default IStateDelta;

