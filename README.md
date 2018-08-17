## 组件化实现方案

### app 配置
```
if (isModule) {
    apply plugin: rootProject.ext.plugin.com_android_library
} else {
    apply plugin: rootProject.ext.plugin.com_android_application
}

android {

    defaultConfig {

       // 配置组件applicationId
       if (!isModule) {
           applicationId rootProject.ext.appid.moudle_x5
       }

        // 配置日志开关, BuildConfig.class查看字段isLog
        buildConfigField(rootProject.ext.config.boolean, rootProject.ext.config.log, String.valueOf(isLog))
        // 配置组件开关, BuildConfig.class查看字段isModule
        buildConfigField(rootProject.ext.config.boolean, rootProject.ext.config.module, String.valueOf(isModule))

        // 配置资源文件
         sourceSets {
             main {
                 if (isComponent) {//如果moudle为组件则配置 AndroidManifest 和java代码主文件
                     manifest.srcFile 'src/main/moudle/AndroidManifest.xml'
                     java.srcDirs 'src/main/java','src/main/moudle/java'
                 } else {
                     manifest.srcFile 'src/main/AndroidManifest.xml'
                 }
             }
         }
    }
}

dependencies {

    if (isModule){
        // moudle是作为依赖, 允许导入
        implementation project(':moudle')
    }else{
        // moudle是作为组件, 禁止导入
    }
}
```
