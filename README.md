# Milestone Progress View (Android)

Customizable milestone-based progress view for Android.  
Shows labeled milestones along a progress bar with active/complete states.

![platform](https://img.shields.io/badge/platform-android-brightgreen)
![kotlin](https://img.shields.io/badge/kotlin-≥1.9-blue)
![license](https://img.shields.io/badge/license-Apache--2.0-lightgrey)

## Gradle (via JitPack)

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
  }
}

// module build.gradle.kts
dependencies {
  implementation("com.github.domm-pixel:milestone_progress_view:0.1.0")
}