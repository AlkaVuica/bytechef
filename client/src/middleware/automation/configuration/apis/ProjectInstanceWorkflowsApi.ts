/* tslint:disable */
/* eslint-disable */
/**
 * Automation Project API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


import * as runtime from '../runtime';
import type {
  CreateProjectInstanceWorkflowJob200ResponseModel,
} from '../models';
import {
    CreateProjectInstanceWorkflowJob200ResponseModelFromJSON,
    CreateProjectInstanceWorkflowJob200ResponseModelToJSON,
} from '../models';

export interface CreateProjectInstanceWorkflowJobRequest {
    id: number;
    workflowId: string;
}

export interface EnableProjectInstanceWorkflowRequest {
    id: number;
    workflowId: string;
    enable: boolean;
}

/**
 * 
 */
export class ProjectInstanceWorkflowsApi extends runtime.BaseAPI {

    /**
     * Create a request for running a new job.
     * Create a request for running a new job
     */
    async createProjectInstanceWorkflowJobRaw(requestParameters: CreateProjectInstanceWorkflowJobRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<CreateProjectInstanceWorkflowJob200ResponseModel>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling createProjectInstanceWorkflowJob.');
        }

        if (requestParameters.workflowId === null || requestParameters.workflowId === undefined) {
            throw new runtime.RequiredError('workflowId','Required parameter requestParameters.workflowId was null or undefined when calling createProjectInstanceWorkflowJob.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/project-instances/{id}/workflows/{workflowId}/jobs`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))).replace(`{${"workflowId"}}`, encodeURIComponent(String(requestParameters.workflowId))),
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => CreateProjectInstanceWorkflowJob200ResponseModelFromJSON(jsonValue));
    }

    /**
     * Create a request for running a new job.
     * Create a request for running a new job
     */
    async createProjectInstanceWorkflowJob(requestParameters: CreateProjectInstanceWorkflowJobRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<CreateProjectInstanceWorkflowJob200ResponseModel> {
        const response = await this.createProjectInstanceWorkflowJobRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Enable/disable a workflow of a project instance.
     * Enable/disable a workflow of a project instance
     */
    async enableProjectInstanceWorkflowRaw(requestParameters: EnableProjectInstanceWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters.id === null || requestParameters.id === undefined) {
            throw new runtime.RequiredError('id','Required parameter requestParameters.id was null or undefined when calling enableProjectInstanceWorkflow.');
        }

        if (requestParameters.workflowId === null || requestParameters.workflowId === undefined) {
            throw new runtime.RequiredError('workflowId','Required parameter requestParameters.workflowId was null or undefined when calling enableProjectInstanceWorkflow.');
        }

        if (requestParameters.enable === null || requestParameters.enable === undefined) {
            throw new runtime.RequiredError('enable','Required parameter requestParameters.enable was null or undefined when calling enableProjectInstanceWorkflow.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/project-instances/{id}/workflows/{workflowId}/enable/{enable}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters.id))).replace(`{${"workflowId"}}`, encodeURIComponent(String(requestParameters.workflowId))).replace(`{${"enable"}}`, encodeURIComponent(String(requestParameters.enable))),
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     * Enable/disable a workflow of a project instance.
     * Enable/disable a workflow of a project instance
     */
    async enableProjectInstanceWorkflow(requestParameters: EnableProjectInstanceWorkflowRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.enableProjectInstanceWorkflowRaw(requestParameters, initOverrides);
    }

}