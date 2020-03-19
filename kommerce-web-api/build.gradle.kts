plugins {
    kotlin("jvm")
}

val junitVersion: String by project

dependencies {
    implementation(project(":kommerce-common"))
    implementation(project(":kommerce-users"))
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.junit.jupiter", "junit-jupiter", junitVersion)
}