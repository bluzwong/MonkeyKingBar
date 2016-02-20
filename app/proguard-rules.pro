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

## Monkey King Bar
#-keep class **_MKB
#-dontwarn sun.misc.**
#-keep class sun.misc.** {*;}
#-keep class * implements java.io.Serializable { *; }
#-keep class com.esotericsoftware.** { *; }
#-dontwarn com.esotericsoftware.**
#-keep class de.javakaffee.kryoserializers.** { *; }
#-dontwarn de.javakaffee.kryoserializers.**
# need manual avoid class name that have mkb annotation on fields
-keep class com.github.bluzwong.monkeykingbar.TestKotlinApt
#-keepnames class * {
#    @com.github.bluzwong.monkeykingbar_lib.** <fields>;
#}
#-keepclasseswithmembernames class * {
#    @com.github.bluzwong.monkeykingbar_lib.** <methods>;
#}
# common
-keepattributes SourceFile,LineNumberTable,Exceptions
-keepnames class * extends java.lang.Throwable

# kotlin
-dontwarn kotlin.**
-dontwarn org.w3c.dom.events.*
-dontwarn org.jetbrains.kotlin.di.InjectorForRuntimeDescriptorLoader


# ----------------------------------------
# Android Support Library
# ----------------------------------------
-dontwarn android.support.**
-keep class android.support.** { *; }

# ----------------------------------------
# Android and Java
# ----------------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class **.R$* {
  public static <fields>;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
