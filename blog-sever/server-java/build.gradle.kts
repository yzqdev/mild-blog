

plugins {
    java
    alias(libs.plugins.springboot) 
    alias(libs.plugins.springDependency) 
//    alias(libs.plugins.kapt)
//    kotlin("jvm") version libs.versions.kotlin
//    kotlin("plugin.spring") version  libs.versions.kotlin
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


    // https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-boot-starter
    implementation(libs.mybatisPlusBoot)
    implementation(libs.pebble)
    implementation(libs.bundles.utils)

// https://mvnrepository.com/artifact/com.alibaba/druid-spring-boot-starter
    implementation(libs.druidSpringboot)
// https://mvnrepository.com/artifact/net.coobird/thumbnailator
    implementation(libs.thumbnailator)

    implementation(libs.bundles.commons)
// https://mvnrepository.com/artifact/com.freewayso/image-combiner
    implementation(libs.imageCombiner)
// https://mvnrepository.com/artifact/com.dtflys.forest/forest-spring-boot-starter
    implementation(libs.forestBoot)


    implementation(libs.javaJwt)

    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}



tasks.withType<Test> {
    useJUnitPlatform()
}
