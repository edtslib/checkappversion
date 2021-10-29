# CheckAppVersion

![SlidingButton](https://i.ibb.co/GCcGMwH/edtslibs.png)
## Setup
### Gradle

Add this to your project level `build.gradle`:
```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
Add this to your app `build.gradle`:
```groovy
dependencies {
    implementation 'com.github.edtslib:checkappversion:latest'
}
```

### Usage

- Create Class extend Application, and add to manifest android:name.
```xml
<application
    android:name=".App">
</application>
```

- On application oncreate, Initialize the checkappversion with call CheckAppVersion.init()

```kotlin
class App: Application() {
    override fun onCreate() {
        super.onCreate()

        CheckAppVersion.init(this, "http://172.18.185.110:32080/configuration/api2506/mobile/sy-app-version/get-version-key/ANDROID/",
            object : CheckAppVersionDelegate {
                override fun onLoading() {
                    Log.d("abah", "abah onLoading")
                }

                override fun onError(code: String?, message: String?) {
                    Log.d("abah", "abah onError $message")
                }

                override fun onUnAuthorize() {
                    Log.d("abah", "abah onUnAuthorize")
                }

                override fun onAppMustUpdate() {
                    Log.d("abah", "abah onAppMustUpdate")
                }

                override fun onAppOptionalUpdate() {
                    Log.d("abah", "abah onAppOptionalUpdate")
                }

                override fun onAppVersionLatest() {
                    Log.d("abah", "abah onAppVersionLatest")
                }
            })
    }
}
```

if you're already using Koin on your application, you can call init on your application using this method
```kotlin
    fun init(baseUrl: String, koin: KoinApplication,
             delegate: CheckAppVersionDelegate)
```

- To execute check version you can call this static method
-
```kotlin

    fun check(activity: FragmentActivity)
    
    // appVersion: if you already have version
    fun check(activity: FragmentActivity, appVersion: String)

```

### Api Response

Your api response must follow this format
```json
{"status":"01","message":"success","data":{"id":2,"description":"Force Update","version":"0.0.0","previousVersion":"3.6.0","deviceType":"ANDROID","forceUpdate":true},"timestamp":"2021-10-29T01:12:19.798+00:00"}
```

