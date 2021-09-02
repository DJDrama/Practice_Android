apply {
    from("$rootDir/library-build.gradle")
}
plugins {
    kotlin(KotlinPlugins.serialization) version Kotlin.version
}

dependencies {
    // dependencies which are not included in the parent build.gradle
    // but can be used inside "components" module
    "implementation"(project(Modules.heroDomain))

    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.android)
}