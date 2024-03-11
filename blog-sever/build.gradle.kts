allprojects {
    // 将构建文件统一输出到项目根目录下的 build 文件夹
    layout.buildDirectory = File(rootDir, "build/${path.replace(':', '/')}")
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}



