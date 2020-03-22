import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    kotlin("jvm") version "1.3.70" apply false
    jacoco
    id("com.github.kt3k.coveralls") version "2.8.4"
}

allprojects {
    group = "com.github.kommerce"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    plugins.withType<JacocoPlugin> {
        the<JacocoPluginExtension>().toolVersion = "0.8.3"

        val testTasks = tasks.withType<Test>()
        testTasks.configureEach {
            extensions.configure<JacocoTaskExtension> {
                includes?.add("com.github.kommerce.*")
            }
        }

        val jacocoReport by tasks.registering(JacocoReport::class)
        jacocoReport {
            val execFiles = files(testTasks)
                .filter { it.exists() && it.name.endsWith(".exec") }
            executionData(execFiles)
        }

        tasks.withType<JacocoReport>().configureEach {
            reports {
                html.isEnabled = true
                xml.isEnabled = false
            }
        }

        configure<SourceSetContainer> {
            val mainCode = get("main")
            jacocoReport {
                additionalSourceDirs.from(mainCode.allJava.srcDirs)
                sourceDirectories.from(mainCode.allSource.srcDirs)
                classDirectories.from(mainCode.output.asFileTree.matching {
                    exclude("module-info.class")
                })
            }
        }
    }
}

task<JacocoReport>("jacocoRootReport") {
    dependsOn(subprojects.map { it.tasks.withType<Test>() })
    dependsOn(subprojects.map { it.tasks.withType<JacocoReport>() })
    val sourceDirs = subprojects.flatMap { it.files("src/main/kotlin") }
    val outputDirs = subprojects.flatMap { it.files("build/classes/kotlin/main") }
    additionalSourceDirs.setFrom(sourceDirs)
    sourceDirectories.setFrom(sourceDirs)
    classDirectories.setFrom(outputDirs)
    executionData.setFrom(project.fileTree(".") {
        include("**/build/jacoco/test.exec")
    })
    reports {
        xml.isEnabled = true
        html.isEnabled = true
        html.destination = file("${buildDir}/reports/jacoco/html")
    }
}