/* tslint:disable */
/* eslint-disable */
/**
 * The Automation Configuration API
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
  WorkspaceModel,
} from '../models/index';
import {
    WorkspaceModelFromJSON,
    WorkspaceModelToJSON,
} from '../models/index';

export interface CreateWorkspaceRequest {
    workspaceModel: Omit<WorkspaceModel, 'createdBy'|'createdDate'|'lastModifiedBy'|'lastModifiedDate'>;
}

export interface DeleteWorkspaceRequest {
    id: number;
}

export interface GetWorkspaceRequest {
    id: number;
}

export interface UpdateWorkspaceRequest {
    id: number;
    workspaceModel: Omit<WorkspaceModel, 'createdBy'|'createdDate'|'lastModifiedBy'|'lastModifiedDate'>;
}

/**
 * 
 */
export class WorkspaceApi extends runtime.BaseAPI {

    /**
     * Create a workspace event.
     * Create a new workspace
     */
    async createWorkspaceRaw(requestParameters: CreateWorkspaceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<WorkspaceModel>> {
        if (requestParameters['workspaceModel'] == null) {
            throw new runtime.RequiredError(
                'workspaceModel',
                'Required parameter "workspaceModel" was null or undefined when calling createWorkspace().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/workspaces`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: WorkspaceModelToJSON(requestParameters['workspaceModel']),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => WorkspaceModelFromJSON(jsonValue));
    }

    /**
     * Create a workspace event.
     * Create a new workspace
     */
    async createWorkspace(requestParameters: CreateWorkspaceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<WorkspaceModel> {
        const response = await this.createWorkspaceRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Delete a workspace.
     * Delete a workspace
     */
    async deleteWorkspaceRaw(requestParameters: DeleteWorkspaceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling deleteWorkspace().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/workspaces/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))),
            method: 'DELETE',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     * Delete a workspace.
     * Delete a workspace
     */
    async deleteWorkspace(requestParameters: DeleteWorkspaceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.deleteWorkspaceRaw(requestParameters, initOverrides);
    }

    /**
     * Get a workspace by id.
     * Get a workspace by id
     */
    async getWorkspaceRaw(requestParameters: GetWorkspaceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<WorkspaceModel>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling getWorkspace().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/workspaces/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => WorkspaceModelFromJSON(jsonValue));
    }

    /**
     * Get a workspace by id.
     * Get a workspace by id
     */
    async getWorkspace(requestParameters: GetWorkspaceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<WorkspaceModel> {
        const response = await this.getWorkspaceRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Get workspaces.
     * Get workspaces
     */
    async getWorkspacesRaw(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Array<WorkspaceModel>>> {
        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/workspaces`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(WorkspaceModelFromJSON));
    }

    /**
     * Get workspaces.
     * Get workspaces
     */
    async getWorkspaces(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Array<WorkspaceModel>> {
        const response = await this.getWorkspacesRaw(initOverrides);
        return await response.value();
    }

    /**
     * Update an existing workspace.
     * Update an existing workspace
     */
    async updateWorkspaceRaw(requestParameters: UpdateWorkspaceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<WorkspaceModel>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling updateWorkspace().'
            );
        }

        if (requestParameters['workspaceModel'] == null) {
            throw new runtime.RequiredError(
                'workspaceModel',
                'Required parameter "workspaceModel" was null or undefined when calling updateWorkspace().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/workspaces/{id}/projects`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))),
            method: 'PUT',
            headers: headerParameters,
            query: queryParameters,
            body: WorkspaceModelToJSON(requestParameters['workspaceModel']),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => WorkspaceModelFromJSON(jsonValue));
    }

    /**
     * Update an existing workspace.
     * Update an existing workspace
     */
    async updateWorkspace(requestParameters: UpdateWorkspaceRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<WorkspaceModel> {
        const response = await this.updateWorkspaceRaw(requestParameters, initOverrides);
        return await response.value();
    }

}
