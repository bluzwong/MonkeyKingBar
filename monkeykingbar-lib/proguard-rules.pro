# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\android-sdk-windows/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Monkey King Bar
-keep class **_MKB
-keepclasseswithmembernames class * {
    @com.github.bluzwong.monkeykingbar_lib.* <fields>;
}
-keep class com.github.bluzwong.monkeykingbar_lib.InjectExtra
-keep class com.github.bluzwong.monkeykingbar_lib.KeepState

-dontwarn sun.misc.**
-keep class sun.misc.** {*;}
-keep class * implements java.io.Serializable { *; }
-keep class com.esotericsoftware.** { *; }
-dontwarn com.esotericsoftware.**
-keep class de.javakaffee.kryoserializers.** { *; }
-dontwarn de.javakaffee.kryoserializers.**
