import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    application
}

group = "me.stpro"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("net.roz.ApplicationMainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:1.6.7")
    implementation("io.ktor:ktor-server:1.6.7")
    implementation("io.ktor:ktor-server-netty:1.6.7")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.0")
    implementation("ch.qos.logback:logback-classic:1.2.10")
    implementation("io.ktor:ktor-auth:1.6.7")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}