dependencies {
    compileOnly("jakarta.servlet:jakarta.servlet-api")

    implementation("jakarta.validation:jakarta.validation-api")
    implementation("org.apache.commons:commons-lang3")
    implementation("org.slf4j:slf4j-api")
    implementation("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework.security:spring-security-core")
    implementation(project(":server:libs:platform:platform-rest:platform-rest-api"))
    implementation(project(":server:libs:platform:platform-user:platform-user-api"))

    testImplementation("org.springframework:spring-tx")
    testImplementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation(project(":server:libs:platform:platform-user:platform-user-service"))
    testImplementation(project(":server:libs:test:test-int-support"))
}
