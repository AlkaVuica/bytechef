/* tslint:disable */
/* eslint-disable */
/**
 * The Platform Configuration API
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
  WorkflowNodeTestOutputModel,
} from '../models/index';
import {
    WorkflowNodeTestOutputModelFromJSON,
    WorkflowNodeTestOutputModelToJSON,
} from '../models/index';

export interface CreateWorkflowNodeTestOutputRequest {
    workflowId: string;
    workflowNodeName: string;
}

export interface GetWorkflowNodeTestOutputsRequest {
    workflowId: string;
}

export interface UploadSampleOutputRequest {
    workflowId: string;
    workflowNodeName: string;
    body: string;
}

/**
 * 
 */
export class WorkflowNodeTestOutputApi extends runtime.BaseAPI {

    /**
     * Create a workflow node test output.
     * Create a workflow node test output
     */
    async createWorkflowNodeTestOutputRaw(requestParameters: CreateWorkflowNodeTestOutputRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<WorkflowNodeTestOutputModel>> {
        if (requestParameters.workflowId === null || requestParameters.workflowId === undefined) {
            throw new runtime.RequiredError('workflowId','Required parameter requestParameters.workflowId was null or undefined when calling createWorkflowNodeTestOutput.');
        }

        if (requestParameters.workflowNodeName === null || requestParameters.workflowNodeName === undefined) {
            throw new runtime.RequiredError('workflowNodeName','Required parameter requestParameters.workflowNodeName was null or undefined when calling createWorkflowNodeTestOutput.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/workflow-node-test-outputs/{workflowId}/{workflowNodeName`.replace(`{${"workflowId"}}`, encodeURIComponent(String(requestParameters.workflowId))).replace(`{${"workflowNodeName"}}`, encodeURIComponent(String(requestParameters.workflowNodeName))),
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => WorkflowNodeTestOutputModelFromJSON(jsonValue));
    }

    /**
     * Create a workflow node test output.
     * Create a workflow node test output
     */
    async createWorkflowNodeTestOutput(requestParameters: CreateWorkflowNodeTestOutputRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<WorkflowNodeTestOutputModel> {
        const response = await this.createWorkflowNodeTestOutputRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Get a workflow node outputs.
     * Get a workflow node outputs
     */
    async getWorkflowNodeTestOutputsRaw(requestParameters: GetWorkflowNodeTestOutputsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Array<WorkflowNodeTestOutputModel>>> {
        if (requestParameters.workflowId === null || requestParameters.workflowId === undefined) {
            throw new runtime.RequiredError('workflowId','Required parameter requestParameters.workflowId was null or undefined when calling getWorkflowNodeTestOutputs.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/workflow-node-test-outputs/{workflowId}/{workflowNodeName`.replace(`{${"workflowId"}}`, encodeURIComponent(String(requestParameters.workflowId))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(WorkflowNodeTestOutputModelFromJSON));
    }

    /**
     * Get a workflow node outputs.
     * Get a workflow node outputs
     */
    async getWorkflowNodeTestOutputs(requestParameters: GetWorkflowNodeTestOutputsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Array<WorkflowNodeTestOutputModel>> {
        const response = await this.getWorkflowNodeTestOutputsRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Upload a sample output to create a workflow node test output.
     * Upload a sample output to create a workflow node test output
     */
    async uploadSampleOutputRaw(requestParameters: UploadSampleOutputRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<WorkflowNodeTestOutputModel>> {
        if (requestParameters.workflowId === null || requestParameters.workflowId === undefined) {
            throw new runtime.RequiredError('workflowId','Required parameter requestParameters.workflowId was null or undefined when calling uploadSampleOutput.');
        }

        if (requestParameters.workflowNodeName === null || requestParameters.workflowNodeName === undefined) {
            throw new runtime.RequiredError('workflowNodeName','Required parameter requestParameters.workflowNodeName was null or undefined when calling uploadSampleOutput.');
        }

        if (requestParameters.body === null || requestParameters.body === undefined) {
            throw new runtime.RequiredError('body','Required parameter requestParameters.body was null or undefined when calling uploadSampleOutput.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/workflow-node-test-outputs/{workflowId}/{workflowNodeName/sample-output`.replace(`{${"workflowId"}}`, encodeURIComponent(String(requestParameters.workflowId))).replace(`{${"workflowNodeName"}}`, encodeURIComponent(String(requestParameters.workflowNodeName))),
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: requestParameters.body as any,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => WorkflowNodeTestOutputModelFromJSON(jsonValue));
    }

    /**
     * Upload a sample output to create a workflow node test output.
     * Upload a sample output to create a workflow node test output
     */
    async uploadSampleOutput(requestParameters: UploadSampleOutputRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<WorkflowNodeTestOutputModel> {
        const response = await this.uploadSampleOutputRaw(requestParameters, initOverrides);
        return await response.value();
    }

}