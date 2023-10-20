dependencies {
    implementation("org.springframework:spring-context")
    implementation("org.springframework.data:spring-data-relational")
    implementation(project(":server:libs:core:commons:commons-data"))
    implementation(project(":server:libs:core:commons:commons-util"))
    implementation(project(":server:libs:helios:helios-execution:helios-execution-api"))
    implementation(project(":server:libs:atlas:atlas-file-storage:atlas-file-storage-api"))

    testImplementation(project(":server:libs:atlas:atlas-execution:atlas-execution-repository:atlas-execution-repository-jdbc"))
    testImplementation(project(":server:libs:atlas:atlas-execution:atlas-execution-service"))
    testImplementation(project(":server:libs:configs:liquibase-config"))
    testImplementation(project(":server:libs:core:category:category-service"))
    testImplementation(project(":server:libs:core:tag:tag-service"))
    testImplementation(project(":server:libs:test:test-int-support"))
}