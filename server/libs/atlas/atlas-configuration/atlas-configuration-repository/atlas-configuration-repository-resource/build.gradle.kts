dependencies {
    implementation("org.springframework:spring-core")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation(project(":server:libs:atlas:atlas-configuration:atlas-configuration-repository:atlas-configuration-repository-api"))
    implementation(project(":server:libs:core:commons:commons-util"))
}