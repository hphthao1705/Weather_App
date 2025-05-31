pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        //for all version have 'SNAPSHOT'
        maven {
            url = uri("https://androidx.dev/snapshots/builds/latest/artifacts/repository")
        }
    }
}

rootProject.name = "Weather_App"
include(":app")
 