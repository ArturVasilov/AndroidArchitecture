-keepattributes Signature,Exceptions,InnerClasses,EnclosingMethod,*Annotation*

-keep public class ru.arturvasilov.stackexchangeclient.** {
    public static <fields>;
    public static <methods>;
    public <methods>;
    protected <methods>;
}

-keep public interface ru.arturvasilov.stackexchangeclient.** { *; }
-dontwarn ru.arturvasilov.stackexchangeclient.**

-dontnote **

# gson
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# support library
-keep class android.support.v4.** { *; }
-dontwarn android.support.v4.**

-keep class android.support.v7.** { *; }
-dontwarn android.support.v7.**

-keep class android.support.design.** { *; }
-dontwarn android.support.design.**

# Retrofit
-dontwarn okio.**
-dontwarn retrofit2.**

# okhttp
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**

# RxJava
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Retrolambda
-dontwarn java.lang.invoke.*