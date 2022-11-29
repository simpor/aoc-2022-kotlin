plugins {
    kotlin("jvm") version "1.7.21"
}

repositories {
    mavenCentral()
}

tasks {
//    sourceSets {
//        main {
//            java.srcDirs("src")
//        }
//    }

    wrapper {
        gradleVersion = "7.5.1"
    }
}

repositories {
    mavenCentral()
}
val kotestVersion = "5.5.4"

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
