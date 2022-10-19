import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.allopen") version "1.7.20"
    id("io.quarkus")

    id("org.openapi.generator") version "6.2.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("com.github.ben-manes.versions") version "0.43.0"
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("$quarkusPlatformGroupId:$quarkusPlatformArtifactId:$quarkusPlatformVersion"))
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("io.quarkus:quarkus-keycloak-authorization")
    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-jacoco")
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-mongodb-panache-kotlin")
    implementation("io.quarkus:quarkus-resteasy-reactive-kotlin-serialization")
    implementation("io.quarkus:quarkus-micrometer")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("io.quarkiverse.mockk:quarkus-junit5-mockk:1.1.1")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

group = "io.github.simonscholz"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    kotlinOptions.javaParameters = true
}

ktlint {
    filter {
        exclude { element -> element.file.path.contains("generated/") }
    }
    version.set("0.45.2")
    enableExperimentalRules.set(true)
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

// Creates a gradle task that generates java code from an Open API spec
fun generateOpenApiSpec(
    taskName: String,
    spec: String,
    pkg: String,
    versionSuffix: String,
) = tasks.register<GenerateTask>(taskName) {
    group = "Source Generation"
    description = "Generates kotlin classes from an Open API specification"

    verbose.set(false)
    generatorName.set("kotlin")
    inputSpec.set(spec)
    outputDir.set("$buildDir/generated")
    packageName.set(pkg)
    modelPackage.set(pkg)
    modelNameSuffix.set("${versionSuffix}DTO")
    generateModelTests.set(false)
    generateApiTests.set(false)
    configOptions.set(
        mapOf(
            "serializationLibrary" to "jackson",
            "enumPropertyNaming" to "UPPERCASE",
            "dateLibrary" to "java8",
            "bigDecimalAsString" to "true",
            "hideGenerationTimestamp" to "true",
            "useBeanValidation" to "false",
            "performBeanValidation" to "false",
            "openApiNullable" to "false",
        ),
    )
    globalProperties.set(
        // only generate model, because api is not supported
        mapOf("models" to "", "modelDocs" to "false"),
    )
}

generateOpenApiSpec(
    taskName = "generateConduitApi",
    spec = "$rootDir/api/server/conduit-api-v1.yml",
    pkg = "io.github.simonscholz.conduit.dto.v1",
    versionSuffix = "V1",
)

sourceSets {
    main {
        java {
            srcDir("$buildDir/generated/src/main/kotlin")
        }
    }
}

tasks.compileKotlin.configure {
    dependsOn(
        tasks.getByName("generateConduitApi"),
    )
}
