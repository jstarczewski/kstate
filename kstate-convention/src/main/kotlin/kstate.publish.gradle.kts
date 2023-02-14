import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`
import org.gradle.kotlin.dsl.signing
import java.util.*

plugins {
    `maven-publish`
    signing
}

ext["signing.keyId"] = null
ext["signing.password"] = null
ext["signing.secretKeyRingFile"] = null
ext["ossrhUsername"] = null
ext["ossrhPassword"] = null

publishing {
    repositories {
        mavenLocal()
    }
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    secretPropsFile.reader().use {
        Properties().apply {
            load(it)
        }
    }.onEach { (name, value) ->
        ext[name.toString()] = value
    }

} else {
    ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
    ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
    ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
    ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
    ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
}

fun getExtraString(name: String) = ext[name]?.toString()

publishing {
    repositories {
        maven {
            name = "sonatype"
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = getExtraString("ossrhUsername")
                password = getExtraString("ossrhPassword")
            }
        }
        publications.withType<MavenPublication> {
            artifact(javadocJar.get())
            pom {
                name.set("Kstate-Core")
                description.set("State management utility for Compose and SwiftUI Kotlin Multiplatform Mobile apps")
                url.set("https://github.com/jstarczewski/kstate")

                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }
                }
                developers {
                    developer {
                        id.set("jstarczewski")
                        name.set("Jan Starczewski")
                        email.set("jan.a.starczewski@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/jstarczewski/kstate")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications)
}