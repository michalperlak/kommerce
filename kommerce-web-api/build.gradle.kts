import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

val arrowVersion: String by project
val reactorNettyVersion: String by project
val junitVersion: String by project

dependencies {
    implementation(project(":kommerce-common"))
    implementation(project(":kommerce-users"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.arrow-kt", "arrow-core", arrowVersion)
    implementation("io.projectreactor.netty", "reactor-netty", reactorNettyVersion)

    testImplementation("org.junit.jupiter", "junit-jupiter", junitVersion)
}

val mainClass = "com.github.kommerce.KommerceWebApplicationKt"
application {
    mainClassName = mainClass
}

tasks.withType<ShadowJar>() {
    manifest {
        attributes["Main-Class"] = mainClass
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}