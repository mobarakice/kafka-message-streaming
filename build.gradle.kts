plugins {
    java
    alias(libs.plugins.spring.framework)
    alias(libs.plugins.spring.dependency.management)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        implementation(rootProject.libs.starter.web)
        implementation(rootProject.libs.starter.data.jpa)
        implementation(rootProject.libs.starter.validation)
        implementation(rootProject.libs.lombok)
        annotationProcessor(rootProject.libs.lombok)
        testImplementation(rootProject.libs.starter.test)
        testImplementation(rootProject.libs.lombok)
        testRuntimeOnly(rootProject.libs.junit.platform.launcher)
        implementation ("org.springframework.kafka:spring-kafka")
        runtimeOnly ("com.h2database:h2")
    }

    tasks.named<Test>("test") {
        useJUnitPlatform()
    }
}
