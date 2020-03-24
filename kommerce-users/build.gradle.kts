plugins {
    kotlin("jvm")
    kotlin("kapt")
    jacoco
}

val arrowVersion: String by project
val commonsValidatorVersion: String by project
val moshiVersion: String by project
val jwtVersion: String by project
val jbcryptVersion: String by project

val junitVersion: String by project

dependencies {
    kapt("com.squareup.moshi", "moshi-kotlin-codegen", moshiVersion)

    implementation(project(":kommerce-common"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.arrow-kt", "arrow-core", arrowVersion)
    implementation("commons-validator", "commons-validator", commonsValidatorVersion)
    implementation("com.squareup.moshi", "moshi", moshiVersion)
    implementation("com.auth0", "java-jwt", jwtVersion)
    implementation("jbcrypt","jbcrypt", jbcryptVersion)

    testImplementation("org.junit.jupiter", "junit-jupiter", junitVersion)
}