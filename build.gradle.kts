import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
    id("org.jetbrains.dokka") version "1.9.10"
    jacoco
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
    application
}

group = "ie.setu"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation(kotlin("test"))
    // https://mvnrepository.com/artifact/io.github.microutils/kotlin-logging-jvm
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:2.0.9")
    // https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream
    // https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream
    implementation("com.thoughtworks.xstream:xstream:1.4.20")
// https://mvnrepository.com/artifact/org.codehaus.jettison/jettison
    implementation("org.codehaus.jettison:jettison:1.5.4")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-cbor
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-cbor:2.14.2")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.10")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}

tasks.jar {
    manifest.attributes["Main-Class"] = "MainKt"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar")
        }.map { zipTree(it) }
    })
}