plugins {
    kotlin("jvm") version "1.9.22"
    application
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.6")
    implementation("com.github.librepdf:openpdf:2.0.3")
    implementation("com.openhtmltopdf:openhtmltopdf-pdfbox:1.0.10")
    implementation("com.openhtmltopdf:openhtmltopdf-slf4j:1.0.10")
    implementation("com.itextpdf:kernel:8.0.2")
    implementation("com.itextpdf:io:8.0.2")
    implementation("com.itextpdf:hyph:8.0.2")
    implementation("com.itextpdf:layout:8.0.2")
    implementation("com.itextpdf:bouncy-castle-adapter:8.0.2")
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("MainKt")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
} 