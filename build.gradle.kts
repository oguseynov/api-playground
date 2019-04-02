import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
    id("org.jmailen.kotlinter") version "1.22.0"
}

group = "com.test.api-playground"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    withType<Test> {
        useJUnitPlatform()

        systemProperty("kotlintest.tags.include", System.getProperty("kotlintest.tags.include"))
        systemProperty("kotlintest.tags.exclude", System.getProperty("kotlintest.tags.exclude"))

        testLogging {
            showStandardStreams = true
            showStackTraces = true
            exceptionFormat = FULL
            events = mutableSetOf(PASSED, FAILED, SKIPPED, STANDARD_OUT, STANDARD_ERROR)
        }

        dependsOn("cleanTest")
    }
}

dependencies {
    val kotlintestVersion = "3.3.1"
    val javafakerVersion = "0.17.2"
    val slf4jSimpleVersion = "1.7.25"
    val fuelVersion = "2.0.1"

    implementation(kotlin("stdlib-jdk8"))
    testImplementation("io.kotlintest:kotlintest-runner-junit5:$kotlintestVersion")
    testImplementation("com.github.javafaker:javafaker:$javafakerVersion")
    testImplementation("org.slf4j:slf4j-simple:$slf4jSimpleVersion")
    testImplementation("com.github.kittinunf.fuel:fuel:$fuelVersion")
    testImplementation("com.github.kittinunf.fuel:fuel-jackson:$fuelVersion")
}
