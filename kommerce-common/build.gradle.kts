plugins {
    kotlin("jvm")
}

val arrowVersion: String by project
val airomemVersion: String by project

val junitVersion: String by project

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.arrow-kt", "arrow-core", arrowVersion)
    implementation("pl.setblack", "airomem-core", airomemVersion)

    testImplementation("org.junit.jupiter", "junit-jupiter", junitVersion)
}