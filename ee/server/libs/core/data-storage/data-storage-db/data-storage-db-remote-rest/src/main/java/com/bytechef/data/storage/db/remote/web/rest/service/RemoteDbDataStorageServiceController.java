/*
 * Copyright 2023-present ByteChef Inc.
 *
 * Licensed under the ByteChef Enterprise license (the "Enterprise License");
 * you may not use this file except in compliance with the Enterprise License.
 */

package com.bytechef.data.storage.db.remote.web.rest.service;

import com.bytechef.commons.util.OptionalUtils;
import com.bytechef.data.storage.db.service.DbDataStorageService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version ee
 *
 * @author Ivica Cardic
 */
@Hidden
@RestController
@RequestMapping("/remote/db-data-storage-service")
public class RemoteDbDataStorageServiceController {

    private final DbDataStorageService dataStorageService;

    @SuppressFBWarnings("EI")
    public RemoteDbDataStorageServiceController(DbDataStorageService dataStorageService) {
        this.dataStorageService = dataStorageService;
    }

    @RequestMapping(
        method = RequestMethod.GET,
        value = "/fetch-value/{context}/{scope}/{scopeId}/{key}",
        consumes = {
            "application/json"
        })
    public ResponseEntity<Object> fetchValue(
        @PathVariable String context, @PathVariable int scope, @PathVariable long scopeId,
        @PathVariable String key) {

        return ResponseEntity.ok(
            OptionalUtils.orElse(dataStorageService.fetch(context, scope, scopeId, key), null));
    }

    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/save/{context}/{scope}/{scopeId}/{key}",
        consumes = {
            "application/json"
        })
    public ResponseEntity<Void> save(
        @PathVariable String context, @PathVariable int scope, @PathVariable long scopeId,
        @PathVariable String key, @RequestBody Object data) {

        dataStorageService.put(context, scope, scopeId, key, data);

        return ResponseEntity.noContent()
            .build();
    }
}