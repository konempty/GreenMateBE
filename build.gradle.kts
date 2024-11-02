plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.noarg") version "2.0.21"
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "kr.kro.jayden_bin"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

noArg {
    annotation("jakarta.persistence.Entity")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3:3.1.1")
    implementation("com.azure:azure-ai-inference:1.0.0-beta.1")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    runtimeOnly("com.oracle.database.jdbc:ojdbc10:19.21.0.0")
    implementation("org.redisson:redisson-spring-boot-starter:3.33.0")
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.5.1")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.5.1")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:3.5.1")
    implementation("com.linecorp.kotlin-jdsl:hibernate-kotlin-jdsl-jakarta:2.2.1.RELEASE")
    implementation("org.hibernate.orm:hibernate-spatial:6.5.2.Final")

    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.1.1"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-parameter-store:3.1.1")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:4.1.3")

    implementation("org.apache.tika:tika-core:2.9.1")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.withType<Test> {
    useJUnitPlatform()
}
