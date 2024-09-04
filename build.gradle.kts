// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.serialize) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.google.dagger.hilt) apply false
//    alias(libs.plugins.compose.compiler) apply false
}