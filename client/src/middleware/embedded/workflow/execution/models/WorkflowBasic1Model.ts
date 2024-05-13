/* tslint:disable */
/* eslint-disable */
/**
 * Embedded Execution API
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
/**
 * The blueprint that describe the execution of a job.
 * @export
 * @interface WorkflowBasic1Model
 */
export interface WorkflowBasic1Model {
    /**
     * The created by.
     * @type {string}
     * @memberof WorkflowBasic1Model
     */
    readonly createdBy?: string;
    /**
     * The created date.
     * @type {Date}
     * @memberof WorkflowBasic1Model
     */
    readonly createdDate?: Date;
    /**
     * The number of workflow connections
     * @type {number}
     * @memberof WorkflowBasic1Model
     */
    readonly connectionsCount?: number;
    /**
     * The description of a workflow.
     * @type {string}
     * @memberof WorkflowBasic1Model
     */
    description?: string;
    /**
     * The id of a workflow.
     * @type {string}
     * @memberof WorkflowBasic1Model
     */
    readonly id?: string;
    /**
     * The number of workflow inputs
     * @type {number}
     * @memberof WorkflowBasic1Model
     */
    readonly inputsCount?: number;
    /**
     * The descriptive name for the workflow
     * @type {string}
     * @memberof WorkflowBasic1Model
     */
    readonly label?: string;
    /**
     * The last modified by.
     * @type {string}
     * @memberof WorkflowBasic1Model
     */
    readonly lastModifiedBy?: string;
    /**
     * The last modified date.
     * @type {Date}
     * @memberof WorkflowBasic1Model
     */
    readonly lastModifiedDate?: Date;
    /**
     * Does this workflow have a manual trigger or not
     * @type {boolean}
     * @memberof WorkflowBasic1Model
     */
    readonly manualTrigger?: boolean;
    /**
     * 
     * @type {Array<string>}
     * @memberof WorkflowBasic1Model
     */
    readonly workflowTaskComponentNames?: Array<string>;
    /**
     * 
     * @type {Array<string>}
     * @memberof WorkflowBasic1Model
     */
    readonly workflowTriggerComponentNames?: Array<string>;
    /**
     * 
     * @type {number}
     * @memberof WorkflowBasic1Model
     */
    version?: number;
}

/**
 * Check if a given object implements the WorkflowBasic1Model interface.
 */
export function instanceOfWorkflowBasic1Model(value: object): boolean {
    return true;
}

export function WorkflowBasic1ModelFromJSON(json: any): WorkflowBasic1Model {
    return WorkflowBasic1ModelFromJSONTyped(json, false);
}

export function WorkflowBasic1ModelFromJSONTyped(json: any, ignoreDiscriminator: boolean): WorkflowBasic1Model {
    if (json == null) {
        return json;
    }
    return {
        
        'createdBy': json['createdBy'] == null ? undefined : json['createdBy'],
        'createdDate': json['createdDate'] == null ? undefined : (new Date(json['createdDate'])),
        'connectionsCount': json['connectionsCount'] == null ? undefined : json['connectionsCount'],
        'description': json['description'] == null ? undefined : json['description'],
        'id': json['id'] == null ? undefined : json['id'],
        'inputsCount': json['inputsCount'] == null ? undefined : json['inputsCount'],
        'label': json['label'] == null ? undefined : json['label'],
        'lastModifiedBy': json['lastModifiedBy'] == null ? undefined : json['lastModifiedBy'],
        'lastModifiedDate': json['lastModifiedDate'] == null ? undefined : (new Date(json['lastModifiedDate'])),
        'manualTrigger': json['manualTrigger'] == null ? undefined : json['manualTrigger'],
        'workflowTaskComponentNames': json['workflowTaskComponentNames'] == null ? undefined : json['workflowTaskComponentNames'],
        'workflowTriggerComponentNames': json['workflowTriggerComponentNames'] == null ? undefined : json['workflowTriggerComponentNames'],
        'version': json['__version'] == null ? undefined : json['__version'],
    };
}

export function WorkflowBasic1ModelToJSON(value?: Omit<WorkflowBasic1Model, 'createdBy'|'createdDate'|'connectionsCount'|'id'|'inputsCount'|'label'|'lastModifiedBy'|'lastModifiedDate'|'manualTrigger'|'workflowTaskComponentNames'|'workflowTriggerComponentNames'> | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'description': value['description'],
        '__version': value['version'],
    };
}

