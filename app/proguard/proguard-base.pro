# 基于 sdk/tools/proguard/proguard-android-optimize.txt 修改
# 指定混淆时采用的算法
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
# 代码混淆的压缩比例，值介于0-7，默认5
-optimizationpasses 5
# 优化时允许访问并修改有修饰符的类及类的成员
-allowaccessmodification
# 关闭预校验(作用于Java平台，Android不需要，去掉可加快混淆)
-dontpreverify
# 混淆后类型都为小写
-dontusemixedcaseclassnames
# 不跳过非公共的库的类
-dontskipnonpubliclibraryclasses
# 混淆时记录日志
-verbose
# 忽略警告
-ignorewarnings
# 关闭代码优化
-dontoptimize
# 关闭混淆
#-dontobfuscate


# 不要删除无用代码
-dontshrink

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 避免混淆注解、内部类、泛型、匿名类
-keepattributes *Annotation*,InnerClasses,Signature,EnclosingMethod

# 不混淆本地方法
-keepclasseswithmembernames class * {
    native <methods>;
}

# 不混淆 Activity 在 XML 布局所设置的 onClick 属性值
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# 不混淆枚举类
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 不混淆 Parcelable 子类
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# 不混淆 Serializable 子类
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 保持资源文件R不被混淆
-keepclassmembers class **.R$* {
    *;
}

# 保持资源文件R不被混淆
-keepclassmembers class **.R$* {
   public static <fields>;
}

# 不混淆 WebView 设置的 JS 接口的方法名
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Android默认保留指令区
-keep public class * extends android.view.View                      # 保持自定义试图类不被混淆
-keep public class * extends android.app.Fragment                   # 保持哪些类不被混淆
-keep public class * extends android.app.Activity                   # 保持哪些类不被混淆
-keep public class * extends android.app.Application                # 保持哪些类不被混淆
-keep public class * extends android.app.Service                    # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver      # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider        # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper   # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference          # 保持哪些类不被混淆


# 保持自定义控件类不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Webview混淆指令区
-keepclassmembers class android.webkit.WebView {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String);
}

# 框架默认保留指令区
-keep public class * extends com.android.architecture.domain.navigation.ANavigation
-keep public class * extends com.android.architecture.domain.navigation.ANavigationAction
-keep public class * extends com.android.architecture.domain.transaction.ATransaction
-keep public class * extends com.android.architecture.domain.transaction.AAction
