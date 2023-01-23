/**
 * SLCEE-2023-PC Arena
 * **SAP Labs CEE Hub Programming Competition 2023 Arena server**.  You can find more information about the game and the competititon rules at [github/SLCEE-2023-PC-public](https://github.com/afarago/SLCEE-2023-PC-public).   For a test run, you can use the crash test dummy user `000000000000000000000000/dummypass`.   *Note: All the APIs expect and return application/json*.
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: DL SLCEE 2023 PC <DL_637A3F6466D808029A65636A@global.corp.sap>
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 *
 */

import ApiClient from '../ApiClient';
/**
* Enum class CardSuit.
* @enum {}
* @readonly
*/
export default class CardSuit {
    
        /**
         * value: "Anchor"
         * @const
         */
        "Anchor" = "Anchor";

    
        /**
         * value: "Hook"
         * @const
         */
        "Hook" = "Hook";

    
        /**
         * value: "Cannon"
         * @const
         */
        "Cannon" = "Cannon";

    
        /**
         * value: "Key"
         * @const
         */
        "Key" = "Key";

    
        /**
         * value: "Chest"
         * @const
         */
        "Chest" = "Chest";

    
        /**
         * value: "Map"
         * @const
         */
        "Map" = "Map";

    
        /**
         * value: "Oracle"
         * @const
         */
        "Oracle" = "Oracle";

    
        /**
         * value: "Sword"
         * @const
         */
        "Sword" = "Sword";

    
        /**
         * value: "Kraken"
         * @const
         */
        "Kraken" = "Kraken";

    
        /**
         * value: "Mermaid"
         * @const
         */
        "Mermaid" = "Mermaid";

    

    /**
    * Returns a <code>CardSuit</code> enum value from a Javascript object name.
    * @param {Object} data The plain JavaScript object containing the name of the enum value.
    * @return {module:model/CardSuit} The enum <code>CardSuit</code> value.
    */
    static constructFromObject(object) {
        return object;
    }
}

