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
/**
* Enum class CardValue.
* @enum {}
* @readonly
*/
export default class CardValue {
    
        /**
         * value: 2
         * @const
         */
        "2" = 2;

    
        /**
         * value: 3
         * @const
         */
        "3" = 3;

    
        /**
         * value: 4
         * @const
         */
        "4" = 4;

    
        /**
         * value: 5
         * @const
         */
        "5" = 5;

    
        /**
         * value: 6
         * @const
         */
        "6" = 6;

    
        /**
         * value: 7
         * @const
         */
        "7" = 7;

    
        /**
         * value: 8
         * @const
         */
        "8" = 8;

    
        /**
         * value: 9
         * @const
         */
        "9" = 9;

    

    /**
    * Returns a <code>CardValue</code> enum value from a Javascript object name.
    * @param {Object} data The plain JavaScript object containing the name of the enum value.
    * @return {module:model/CardValue} The enum <code>CardValue</code> value.
    */
    static constructFromObject(object) {
        return object;
    }
}

