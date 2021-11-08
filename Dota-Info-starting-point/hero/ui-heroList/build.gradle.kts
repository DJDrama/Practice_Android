apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    // dependencies which are not included in the parent build.gradle
    // but can be used inside "components" module
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.heroDomain))
    "implementation"(project(Modules.heroInteractors))
    "implementation"(Coil.coil)


    "implementation"(Hilt.android)
    "kapt"(Hilt.compiler)

    "implementation"(SqlDelight.androidDriver)
}