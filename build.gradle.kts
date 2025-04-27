// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

buildscript {
    // 1. Declare repositórios para o classpath do build
    repositories {
        google()
        mavenCentral()  // necessária para io.objectbox :contentReference[oaicite:2]{index=2}
    }

    // 2. Defina a versão do plugin (pode vir de extra ou versão fixa)
    val objectboxVersion by extra("4.2.0") // consulte https://central.sonatype.com/artifact/io.objectbox/objectbox-gradle-plugin :contentReference[oaicite:3]{index=3}

    // 3. Adicione a dependência de classpath
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("io.objectbox:objectbox-gradle-plugin:$objectboxVersion") // agora com versão :contentReference[oaicite:4]{index=4}
    }
}