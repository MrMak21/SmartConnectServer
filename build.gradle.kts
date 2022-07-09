import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
}

group = "gr.makris"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.projectlombok:lombok:1.18.20")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("com.google.code.gson:gson:2.8.6")

	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.retrofit2:converter-jackson:2.9.0")


	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.0")
	implementation("mysql:mysql-connector-java:8.0.29")

	implementation("org.springframework.boot:spring-boot-starter-security:2.7.1")
	implementation("com.auth0:java-jwt:4.0.0")

	implementation("org.springframework.session:spring-session-data-redis:2.7.0")

	implementation ("com.google.api-client:google-api-client:1.35.1")
	implementation ("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
	implementation ("com.google.apis:google-api-services-gmail:v1-rev20211108-1.32.1")


//	implementation ("jakarta.mail:jakarta.mail-api:2.1.0")

	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-mail
	implementation("org.springframework.boot:spring-boot-starter-mail:2.7.1")


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
