buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.4.1")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
