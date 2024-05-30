/*
 * Copyright 2023-present ByteChef Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytechef.platform.configuration.web.rest.error;

import com.bytechef.platform.configuration.exception.ConfigurationException;
import com.bytechef.platform.web.rest.error.AbstractResponseEntityExceptionHandler;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Ivica Cardic
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ConfigurationResponseEntityExceptionHandler extends AbstractResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationResponseEntityExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConfigurationException.class)
    @SuppressFBWarnings("BC_UNCONFIRMED_CAST")
    public ResponseEntity<ProblemDetail> handleConfigurationExceptionException(
        final ConfigurationException exception, final WebRequest request) {

        if (logger.isDebugEnabled()) {
            logger.debug(exception.getMessage(), exception);
        }

        return ResponseEntity
            .of(
                createProblemDetail(
                    exception.getCause() == null ? exception : (Exception) exception.getCause(), HttpStatus.BAD_REQUEST,
                    exception.getEntityClass(), exception.getErrorKey(), exception.getErrorMessageCode(),
                    exception.getErrorMessageArguments(), null, request))
            .build();
    }
}
