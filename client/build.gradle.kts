import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("org.openjfx.javafxplugin") version "0.0.10"
    application
}

group = "ru.itmo.se.prog.lab7"
version = "1.0-SNAPSHOT"

dependencies {
    val kotlinVersion = "1.8.10"
    val koinVersion = "3.2.2"
    implementation(project(":common"))
    testImplementation(kotlin("test"))
    implementation("org.postgresql:postgresql:42.5.4")
    implementation(kotlin("serialization", version = kotlinVersion))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
    implementation("org.reflections:reflections:0.10.2")
    implementation("io.insert-koin:koin-core:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion")
    implementation(kotlin("stdlib-jdk8"))

    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.openjfx:javafx-controls:19.0.2.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

repositories {
    mavenCentral()
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

javafx {
    version = "16"
    modules = listOf("javafx.controls", "javafx.fxml")
}