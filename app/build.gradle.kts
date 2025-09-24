plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.dom.milestoneprogressview"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        targetSdk = 36
        // Library: add consumer rules (create this file at module root)
//        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            // no special config
        }
    }

    buildFeatures {
        // Library modules usually don't need BuildConfig
        buildConfig = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    // Include sources jar for publishing
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

// Publishing coordinates for JitPack or other Maven repos
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.domm-pixel" // TODO: replace with your GitHub ID
                artifactId = "milestone-progressview"
                version = "0.1.0"

                pom {
                    name.set("Milestone Progress View")
                    description.set("A customizable milestone-based progress view for Android.")
                    url.set("https://github.com/domm-pixel/milestone_progress_view")
                    licenses {
                        license {
                            name.set("Apache-2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0")
                        }
                    }
                    scm {
                        url.set("https://github.com/domm-pixel/milestone_progress_view")
                        connection.set("scm:git:https://github.com/domm-pixel/milestone_progress_view.git")
                        developerConnection.set("scm:git:ssh://github.com:domm-pixel/milestone_progress_view.git")
                    }
                    developers {
                        developer {
                            id.set("domm-pixel")
                            name.set("YOUR_NAME")
                        }
                    }
                }
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}