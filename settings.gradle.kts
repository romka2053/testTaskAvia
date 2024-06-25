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
    }
}

rootProject.name = "TIcketsAirTest"
include(":app")
include(":featuresearch")
include(":featuresearch:mainsearch")
include(":core")
include(":entity")
include(":data")
include(":domain")
include(":featuresearch:search")
include(":featuresearch:alltickets")
include(":featuremap")
include(":featurehotels")
include(":featuresubscriptions")
include(":featureprofile")
include(":featuresearch:emptyfragment")
