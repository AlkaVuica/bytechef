package com.bytechef.embedded.configuration.web.rest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * DeleteWorkflowNodeParameterRequestModel
 */

@JsonTypeName("deleteWorkflowNodeParameter_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-24T07:24:43.865568+02:00[Europe/Zagreb]", comments = "Generator version: 7.5.0")
public class DeleteWorkflowNodeParameterRequestModel {

  private Integer arrayIndex;

  private String name;

  private String path;

  private String workflowNodeName;

  public DeleteWorkflowNodeParameterRequestModel() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DeleteWorkflowNodeParameterRequestModel(String path, String workflowNodeName) {
    this.path = path;
    this.workflowNodeName = workflowNodeName;
  }

  public DeleteWorkflowNodeParameterRequestModel arrayIndex(Integer arrayIndex) {
    this.arrayIndex = arrayIndex;
    return this;
  }

  /**
   * The array index.
   * @return arrayIndex
  */
  
  @Schema(name = "arrayIndex", description = "The array index.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("arrayIndex")
  public Integer getArrayIndex() {
    return arrayIndex;
  }

  public void setArrayIndex(Integer arrayIndex) {
    this.arrayIndex = arrayIndex;
  }

  public DeleteWorkflowNodeParameterRequestModel name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The parameter name.
   * @return name
  */
  
  @Schema(name = "name", description = "The parameter name.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DeleteWorkflowNodeParameterRequestModel path(String path) {
    this.path = path;
    return this;
  }

  /**
   * The workflow node parameter path.
   * @return path
  */
  @NotNull 
  @Schema(name = "path", description = "The workflow node parameter path.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("path")
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public DeleteWorkflowNodeParameterRequestModel workflowNodeName(String workflowNodeName) {
    this.workflowNodeName = workflowNodeName;
    return this;
  }

  /**
   * The workflow node name.
   * @return workflowNodeName
  */
  @NotNull 
  @Schema(name = "workflowNodeName", description = "The workflow node name.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("workflowNodeName")
  public String getWorkflowNodeName() {
    return workflowNodeName;
  }

  public void setWorkflowNodeName(String workflowNodeName) {
    this.workflowNodeName = workflowNodeName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeleteWorkflowNodeParameterRequestModel deleteWorkflowNodeParameterRequest = (DeleteWorkflowNodeParameterRequestModel) o;
    return Objects.equals(this.arrayIndex, deleteWorkflowNodeParameterRequest.arrayIndex) &&
        Objects.equals(this.name, deleteWorkflowNodeParameterRequest.name) &&
        Objects.equals(this.path, deleteWorkflowNodeParameterRequest.path) &&
        Objects.equals(this.workflowNodeName, deleteWorkflowNodeParameterRequest.workflowNodeName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(arrayIndex, name, path, workflowNodeName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteWorkflowNodeParameterRequestModel {\n");
    sb.append("    arrayIndex: ").append(toIndentedString(arrayIndex)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    workflowNodeName: ").append(toIndentedString(workflowNodeName)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

