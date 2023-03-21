import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.9"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

allprojects {
    ext {
        set("springdoc_version", "1.6.14")
        set("mapstruct_version", "1.5.3.Final")
        set("log4jdbc_version", "1.16")
    }
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    /**
     * rest doc
     */
    implementation("org.springdoc:springdoc-openapi-ui:${project.ext.get("springdoc_version")}")
    implementation("org.springdoc:springdoc-openapi-kotlin:${project.ext.get("springdoc_version")}")
    implementation("org.springdoc:springdoc-openapi-webmvc-core:${project.ext.get("springdoc_version")}")

    /**
     * object mapper
     */
    implementation("org.mapstruct:mapstruct:${project.ext.get("mapstruct_version")}")
    implementation("org.mapstruct:mapstruct-processor:${project.ext.get("mapstruct_version")}")

    /**
     * logging
     */
    implementation("org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:${project.ext.get("log4jdbc_version")}")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
