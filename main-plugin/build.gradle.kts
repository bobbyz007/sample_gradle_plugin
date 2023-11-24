plugins {
    java
    `java-gradle-plugin`
    id("maven-publish")
}

/**
 * 实际上这些切面配置（横跨多个项目）应该抽取到脚本插件中，官方不推荐类似allprojects/subprojects这种cross configuration。
 * 此处为了简单罗列在此。
 */
group = "com.example.gradle"
version = "1.0.0"
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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
            implementationClass = "com.example.gradle.collection.SubPlugin"
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
