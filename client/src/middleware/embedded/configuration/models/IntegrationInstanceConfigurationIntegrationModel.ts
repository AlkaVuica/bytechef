/* tslint:disable */
/* eslint-disable */
/**
 * The Embedded Configuration API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { mapValues } from '../runtime';
import type { IntegrationStatusModel } from './IntegrationStatusModel';
import {
    IntegrationStatusModelFromJSON,
    IntegrationStatusModelFromJSONTyped,
    IntegrationStatusModelToJSON,
} from './IntegrationStatusModel';

/**
 * 
 * @export
 * @interface IntegrationInstanceConfigurationIntegrationModel
 */
export interface IntegrationInstanceConfigurationIntegrationModel {
    /**
     * If multiple instances of an integration are allowed or not.
     * @type {boolean}
     * @memberof IntegrationInstanceConfigurationIntegrationModel
     */
    allowMultipleInstances: boolean;
    /**
     * The name of the integration's component.
     * @type {string}
     * @memberof IntegrationInstanceConfigurationIntegrationModel
     */
    componentName: string;
    /**
     * The version of the integration's component.
     * @type {number}
     * @memberof IntegrationInstanceConfigurationIntegrationModel
     */
    componentVersion: number;
    /**
     * The created by.
     * @type {string}
     * @memberof IntegrationInstanceConfigurationIntegrationModel
     */
    readonly createdBy?: string;
    /**
     * The created date.
     * @type {Date}
     * @memberof IntegrationInstanceConfigurationIntegrationModel
     */
    readonly createdDate?: Date;
    /**
     * The description of an integration.
     * @type {string}
     * @memberof IntegrationInstanceConfigurationIntegrationModel
     */
    description?: string;
    /**
     * The id of an integration.
     * @type {number}
     * @memberof IntegrationInstanceConfigurationIntegrationModel
     */
    readonly id?: number;
    /**
     * The version of an integration.
     * @type {number}
     * @memberof IntegrationInstanceConfigurationIntegrationModel
     */
    readonly integrationVersion?: number;
    /**
     * The last modified by.
     * @type {string}
     * @memberof IntegrationInstanceConfigurationIntegrationModel
     */
    readonly lastModifiedBy?: string;
    /**
     * The last modified date.
     * @type {Date}
     * @memberof IntegrationInstanceConfigurationIntegrationModel
     */
    readonly lastModifiedDate?: Date;
    /**
     * The published date.
     * @type {Date}
     * @memberof IntegrationInstanceConfigurationIntegrationModel
     */
    publishedDate?: Date;
    /**
     * 
     * @type {IntegrationStatusModel}
     * @memberof IntegrationInstanceConfigurationIntegrationModel
     */
    status?: IntegrationStatusModel;
}

/**
 * Check if a given object implements the IntegrationInstanceConfigurationIntegrationModel interface.
 */
export function instanceOfIntegrationInstanceConfigurationIntegrationModel(value: object): boolean {
    if (!('allowMultipleInstances' in value)) return false;
    if (!('componentName' in value)) return false;
    if (!('componentVersion' in value)) return false;
    return true;
}

export function IntegrationInstanceConfigurationIntegrationModelFromJSON(json: any): IntegrationInstanceConfigurationIntegrationModel {
    return IntegrationInstanceConfigurationIntegrationModelFromJSONTyped(json, false);
}

export function IntegrationInstanceConfigurationIntegrationModelFromJSONTyped(json: any, ignoreDiscriminator: boolean): IntegrationInstanceConfigurationIntegrationModel {
    if (json == null) {
        return json;
    }
    return {
        
        'allowMultipleInstances': json['allowMultipleInstances'],
        'componentName': json['componentName'],
        'componentVersion': json['componentVersion'],
        'createdBy': json['createdBy'] == null ? undefined : json['createdBy'],
        'createdDate': json['createdDate'] == null ? undefined : (new Date(json['createdDate'])),
        'description': json['description'] == null ? undefined : json['description'],
        'id': json['id'] == null ? undefined : json['id'],
        'integrationVersion': json['integrationVersion'] == null ? undefined : json['integrationVersion'],
        'lastModifiedBy': json['lastModifiedBy'] == null ? undefined : json['lastModifiedBy'],
        'lastModifiedDate': json['lastModifiedDate'] == null ? undefined : (new Date(json['lastModifiedDate'])),
        'publishedDate': json['publishedDate'] == null ? undefined : (new Date(json['publishedDate'])),
        'status': json['status'] == null ? undefined : IntegrationStatusModelFromJSON(json['status']),
    };
}

export function IntegrationInstanceConfigurationIntegrationModelToJSON(value?: IntegrationInstanceConfigurationIntegrationModel | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'allowMultipleInstances': value['allowMultipleInstances'],
        'componentName': value['componentName'],
        'componentVersion': value['componentVersion'],
        'description': value['description'],
        'publishedDate': value['publishedDate'] == null ? undefined : ((value['publishedDate']).toISOString()),
        'status': IntegrationStatusModelToJSON(value['status']),
    };
}

