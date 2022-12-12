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


import ApiClient from './ApiClient';
import ActionErrorResponse from './model/ActionErrorResponse';
import BoolLikeString from './model/BoolLikeString';
import Card from './model/Card';
import CardEffect from './model/CardEffect';
import CardEffectResponse from './model/CardEffectResponse';
import CardEffectType from './model/CardEffectType';
import CardOrNull from './model/CardOrNull';
import CardSuit from './model/CardSuit';
import CardValue from './model/CardValue';
import DeleteMatchRequest from './model/DeleteMatchRequest';
import ErrorResponse from './model/ErrorResponse';
import HelloWorldResponse from './model/HelloWorldResponse';
import IStateDelta from './model/IStateDelta';
import IStateDeltaStack from './model/IStateDeltaStack';
import IUserAction from './model/IUserAction';
import MatchActionType from './model/MatchActionType';
import MatchCreateResponse from './model/MatchCreateResponse';
import MatchCreationParams from './model/MatchCreationParams';
import MatchDTO from './model/MatchDTO';
import MatchEventDTO from './model/MatchEventDTO';
import MatchEventType from './model/MatchEventType';
import MoveDTO from './model/MoveDTO';
import PlayerDTO from './model/PlayerDTO';
import State from './model/State';
import WhoAmiIResponse from './model/WhoAmiIResponse';
import DiagnosticApi from './api/DiagnosticApi';
import GameApi from './api/GameApi';
import PlayersApi from './api/PlayersApi';


/**
* **SAP Labs CEE Hub Programming Competition 2023 Arean server**.  You can find more information about the game and the competititon rules at [github/SLH_SPC_2022](https://github.com/afarago/SLH_SPC_2022).   For a test run, you can use the crash test dummy user &#x60;000000000000000000000000/dummypass&#x60;.   *Note: All the APIs expect and return application/json*..<br>
* The <code>index</code> module provides access to constructors for all the classes which comprise the public API.
* <p>
* An AMD (recommended!) or CommonJS application will generally do something equivalent to the following:
* <pre>
* var Slhpc23Arena = require('index'); // See note below*.
* var xxxSvc = new Slhpc23Arena.XxxApi(); // Allocate the API class we're going to use.
* var yyyModel = new Slhpc23Arena.Yyy(); // Construct a model instance.
* yyyModel.someProperty = 'someValue';
* ...
* var zzz = xxxSvc.doSomething(yyyModel); // Invoke the service.
* ...
* </pre>
* <em>*NOTE: For a top-level AMD script, use require(['index'], function(){...})
* and put the application logic within the callback function.</em>
* </p>
* <p>
* A non-AMD browser application (discouraged) might do something like this:
* <pre>
* var xxxSvc = new Slhpc23Arena.XxxApi(); // Allocate the API class we're going to use.
* var yyy = new Slhpc23Arena.Yyy(); // Construct a model instance.
* yyyModel.someProperty = 'someValue';
* ...
* var zzz = xxxSvc.doSomething(yyyModel); // Invoke the service.
* ...
* </pre>
* </p>
* @module index
* @version 1.0.0
*/
export {
    /**
     * The ApiClient constructor.
     * @property {module:ApiClient}
     */
    ApiClient,

    /**
     * The ActionErrorResponse model constructor.
     * @property {module:model/ActionErrorResponse}
     */
    ActionErrorResponse,

    /**
     * The BoolLikeString model constructor.
     * @property {module:model/BoolLikeString}
     */
    BoolLikeString,

    /**
     * The Card model constructor.
     * @property {module:model/Card}
     */
    Card,

    /**
     * The CardEffect model constructor.
     * @property {module:model/CardEffect}
     */
    CardEffect,

    /**
     * The CardEffectResponse model constructor.
     * @property {module:model/CardEffectResponse}
     */
    CardEffectResponse,

    /**
     * The CardEffectType model constructor.
     * @property {module:model/CardEffectType}
     */
    CardEffectType,

    /**
     * The CardOrNull model constructor.
     * @property {module:model/CardOrNull}
     */
    CardOrNull,

    /**
     * The CardSuit model constructor.
     * @property {module:model/CardSuit}
     */
    CardSuit,

    /**
     * The CardValue model constructor.
     * @property {module:model/CardValue}
     */
    CardValue,

    /**
     * The DeleteMatchRequest model constructor.
     * @property {module:model/DeleteMatchRequest}
     */
    DeleteMatchRequest,

    /**
     * The ErrorResponse model constructor.
     * @property {module:model/ErrorResponse}
     */
    ErrorResponse,

    /**
     * The HelloWorldResponse model constructor.
     * @property {module:model/HelloWorldResponse}
     */
    HelloWorldResponse,

    /**
     * The IStateDelta model constructor.
     * @property {module:model/IStateDelta}
     */
    IStateDelta,

    /**
     * The IStateDeltaStack model constructor.
     * @property {module:model/IStateDeltaStack}
     */
    IStateDeltaStack,

    /**
     * The IUserAction model constructor.
     * @property {module:model/IUserAction}
     */
    IUserAction,

    /**
     * The MatchActionType model constructor.
     * @property {module:model/MatchActionType}
     */
    MatchActionType,

    /**
     * The MatchCreateResponse model constructor.
     * @property {module:model/MatchCreateResponse}
     */
    MatchCreateResponse,

    /**
     * The MatchCreationParams model constructor.
     * @property {module:model/MatchCreationParams}
     */
    MatchCreationParams,

    /**
     * The MatchDTO model constructor.
     * @property {module:model/MatchDTO}
     */
    MatchDTO,

    /**
     * The MatchEventDTO model constructor.
     * @property {module:model/MatchEventDTO}
     */
    MatchEventDTO,

    /**
     * The MatchEventType model constructor.
     * @property {module:model/MatchEventType}
     */
    MatchEventType,

    /**
     * The MoveDTO model constructor.
     * @property {module:model/MoveDTO}
     */
    MoveDTO,

    /**
     * The PlayerDTO model constructor.
     * @property {module:model/PlayerDTO}
     */
    PlayerDTO,

    /**
     * The State model constructor.
     * @property {module:model/State}
     */
    State,

    /**
     * The WhoAmiIResponse model constructor.
     * @property {module:model/WhoAmiIResponse}
     */
    WhoAmiIResponse,

    /**
    * The DiagnosticApi service constructor.
    * @property {module:api/DiagnosticApi}
    */
    DiagnosticApi,

    /**
    * The GameApi service constructor.
    * @property {module:api/GameApi}
    */
    GameApi,

    /**
    * The PlayersApi service constructor.
    * @property {module:api/PlayersApi}
    */
    PlayersApi
};
