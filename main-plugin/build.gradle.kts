import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    java
    `java-gradle-plugin`
    id("maven-publish")
    // 会间接引人java-gradle-plugin,org.jetbrains.kotlin.jvm等插件
    // 如果需要使用kotlin语言编写插件，推荐引入此插件。
    `kotlin-dsl`
}

/**
 * 实际上这些切面配置（横跨多个项目）应该抽取到脚本插件中，官方不推荐类似allprojects/subprojects这种cross configuration。
 * 此处为了简单罗列在此。
 */
group = "com.example.gradle"
version = "1.0.0"
// 推荐使用toolchain，而不是sourceCompatibility和targetCompatibility
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
kotlin {
    compilerOptions.jvmTarget = JvmTarget.JVM_17
    compilerOptions.languageVersion = KotlinVersion.DEFAULT
}

repositories {
    maven("https://maven.aliyun.com/repository/public/")
    maven("https://mirrors.huaweicloud.com/repository/maven/")
    // 或者公司本地仓库
    mavenCentral()
}

gradlePlugin {
    plugins {
        // 插件id与实现类的对应关系会写入 jar包中 META-INF/gradle-plugins目录下的文件中
        create("mainPlugin") {
            id = "com.example.gradle.main-plugin"
            implementationClass = "com.example.gradle.MainPlugin"
        }
        create("subPlugin") {
            id = "com.example.gradle.sub-plugin"
            implementationClass = "com.example.gradle.SubPlugin"
        }
        create("codeLinePlugin") {
            id = "com.example.gradle.codeline-plugin"
            implementationClass = "com.example.gradle.CodeLinesCounterPlugin"
        }
    }
}

dependencies {
    // implementation(libs.bundles.utilLib)
    // implementation(gradleApi())
}

/**
 * java-gradle-plugin检测到maven-publish插件时，会自动创建main publication
 * 和多个marker publication（每个插件实现类对应一个）
 *
 * publishToMavenLocal发布到本地maven不需要配置
 */
//publishing {
//    repositories {
//        maven {
//            url = uri(layout.buildDirectory.dir("maven-repo"))
//        }
//    }
//}
