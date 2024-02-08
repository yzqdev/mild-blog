import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.springboot) 
    alias(libs.plugins.springDependency) 
    alias(libs.plugins.kapt)
    kotlin("jvm") version libs.versions.kotlin
    kotlin("plugin.spring") version  libs.versions.kotlin
}

group = "com.site.blog"
version = "1.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}


dependencies {
    compileOnly ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-mail")

    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    // https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-boot-starter
    implementation(libs.mybatisPlusBoot)
    implementation("io.pebbletemplates:pebble-spring-boot-starter:3.2.0")
    implementation(libs.bundles.utils)
    // https://mvnrepository.com/artifact/cn.hutool/hutool-all
    implementation(libs.hutool)
// https://mvnrepository.com/artifact/com.alibaba/druid-spring-boot-starter
    implementation("com.alibaba:druid-spring-boot-starter:1.2.15")
// https://mvnrepository.com/artifact/net.coobird/thumbnailator
    implementation("net.coobird:thumbnailator:0.4.17")
// https://mvnrepository.com/artifact/org.apache.commons/commons-text
    implementation("org.apache.commons:commons-text:1.10.0")
// https://mvnrepository.com/artifact/com.freewayso/image-combiner
    implementation("com.freewayso:image-combiner:2.3.7")
// https://mvnrepository.com/artifact/com.dtflys.forest/forest-spring-boot-starter
    implementation("com.dtflys.forest:forest-spring-boot-starter:1.5.28")


    implementation("com.auth0:java-jwt:4.3.0")

    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
