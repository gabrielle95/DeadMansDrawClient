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
import CardEffectResponse from './CardEffectResponse';
import MatchActionType from './MatchActionType';

/**
 * The IUserAction model module.
 * @module model/IUserAction
 * @version 1.0.0
 */
class IUserAction {
    /**
     * Constructs a new <code>IUserAction</code>.
     * User action sent by the player
     * @alias module:model/IUserAction
     */
    constructor() { 
        
        IUserAction.initialize(this);
    }

    /**
     * Initializes the fields of this object.
     * This method is used by the constructors of any subclasses, in order to implement multiple inheritance (mix-ins).
     * Only for internal use.
     */
    static initialize(obj) { 
    }

    /**
     * Constructs a <code>IUserAction</code> from a plain JavaScript object, optionally creating a new instance.
     * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @param {module:model/IUserAction} obj Optional instance to populate.
     * @return {module:model/IUserAction} The populated <code>IUserAction</code> instance.
     */
    static constructFromObject(data, obj) {
        if (data) {
            obj = obj || new IUserAction();

            if (data.hasOwnProperty('etype')) {
                obj['etype'] = MatchActionType.constructFromObject(data['etype']);
            }
            if (data.hasOwnProperty('effect')) {
                obj['effect'] = CardEffectResponse.constructFromObject(data['effect']);
            }
            if (data.hasOwnProperty('autopick')) {
                obj['autopick'] = ApiClient.convertToType(data['autopick'], 'Boolean');
            }
        }
        return obj;
    }

    /**
     * Validates the JSON data with respect to <code>IUserAction</code>.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @return {boolean} to indicate whether the JSON data is valid with respect to <code>IUserAction</code>.
     */
    static validateJSON(data) {
        // validate the optional field `effect`
        if (data['effect']) { // data not null
          CardEffectResponse.validateJSON(data['effect']);
        }

        return true;
    }


}



/**
 * @member {module:model/MatchActionType} etype
 */
IUserAction.prototype['etype'] = undefined;

/**
 * @member {module:model/CardEffectResponse} effect
 */
IUserAction.prototype['effect'] = undefined;

/**
 * @member {Boolean} autopick
 */
IUserAction.prototype['autopick'] = undefined;






export default IUserAction;

