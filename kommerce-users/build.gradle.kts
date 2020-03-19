plugins {
    kotlin("jvm")
}

val arrowVersion: String by project
val commonsValidatorVersion: String by project
val junitVersion: String by project

dependencies {
    implementation(project(":kommerce-common"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.arrow-kt", "arrow-core", arrowVersion)
    implementation("commons-validator", "commons-validator", commonsValidatorVersion)

    testImplementation("org.junit.jupiter", "junit-jupiter", junitVersion)
}