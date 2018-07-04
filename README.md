# Helpers-Android
HTTP REST api call wrapper. It Initiates a new HTTP (REST) request with the given URL [and parameters in case of POST]"


## _The HttpHelper_

### Description
This is HTTP RESTFUL request wrapper. It allows you to make RESTFUL api calls asynchronously in your android apps. It Initiates a new HTTP (REST) request with the given URL [and parameters in case of POST]

### Requirements
#### Step 1:  Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

#### Step 2: Add the dependency
``` 
dependencies {
    implementation 'com.github.aweklin:helpers-android:0.1.0'
}
```

### Usage

In your Java class, import the package
```
import com.aweklin.helpers.HttpHelper;
```

Then, consume your service using the code below:
```
try {
    HttpHelper.Response sample = new HttpHelper().request(this, HttpHelper.RequestType.get, "yourapiurl", null, true, "Loading stuff, please wait...").get();
    if (sample.isSuccessful) {
        // do anything you want here        
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    } else {
        Toast.makeText(getApplicationContext(), sample.message, Toast.LENGTH_LONG).show();
    }
} catch (InterruptedException | ExecutionException e) {
    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
} catch (Exception e) {
    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
}
```
