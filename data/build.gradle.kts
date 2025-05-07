plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.ksp)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}


dependencies {
    implementation(project(":domain"))
    testImplementation(project(":test:unit"))
    
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.hilt.core)

    //test
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.testing)
}


