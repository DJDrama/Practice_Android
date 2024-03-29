apply {
    from("$rootDir/library-build.gradle")
}

plugins {
    kotlin(KotlinPlugins.serialization) version Kotlin.version
}

dependencies {
    // dependencies which are not included in the parent build.gradle
    // but can be used inside "components" module
    "implementation"(project(Modules.heroDataSource))
    "implementation"(project(Modules.heroDomain))

    "implementation"(Ktor.ktorClientMock)
    "implementation"(Ktor.clientSerialization)
}