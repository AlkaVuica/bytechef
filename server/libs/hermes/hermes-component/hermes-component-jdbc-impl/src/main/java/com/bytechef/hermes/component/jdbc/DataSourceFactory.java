/*
 * Copyright 2021 <your company/name>.
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

package com.bytechef.hermes.component.jdbc;

import com.bytechef.hermes.component.ConnectionParameters;
import com.bytechef.hermes.component.jdbc.constants.JdbcConstants;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author Ivica Cardic
 */
@Component
public class DataSourceFactory {
    private final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    public DataSource getDataSource(
            ConnectionParameters connectionParameters, String databaseJdbcName, String jdbcDriverClassName) {
        Assert.notNull(connectionParameters, "connectionParameters cannot be null");
        Assert.notNull(databaseJdbcName, "databaseJdbcName cannot be null");
        Assert.notNull(jdbcDriverClassName, "jdbcDriverClassName cannot be null");

        String url = "jdbc:" + databaseJdbcName + "://"
                + connectionParameters.getParameter(JdbcConstants.HOST)
                + ":"
                + connectionParameters.getParameter(JdbcConstants.PORT)
                + "/"
                + connectionParameters.getParameter(JdbcConstants.DATABASE);
        String username = connectionParameters.getParameter(JdbcConstants.USERNAME);

        return dataSourceMap.computeIfAbsent(url + username, key -> {
            DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();

            dataSourceBuilder.driverClassName(jdbcDriverClassName);
            dataSourceBuilder.url(url);
            dataSourceBuilder.username(username);
            dataSourceBuilder.password(connectionParameters.getParameter(JdbcConstants.PASSWORD));

            return dataSourceBuilder.build();
        });
    }
}
