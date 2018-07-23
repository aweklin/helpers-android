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
    implementation 'com.github.aweklin:helpers-android:0.1.4'
}
```

### Usage

In your Java class, import the package
```
import com.aweklin.helpers.HttpHelper;
```

Then, consume your service using the code below:
###### GET
```
try {
    List<NameValuePair> headers = new ArrayList<>(1);
    headers.add(new BasicNameValuePair("Authorization", "your-api-authorization-key"));
    HttpHelper.Response response = new HttpHelper().request(this, HttpHelper.RequestType.get, "your_api_url", headers, null, true, "Loading stuff, please wait...").get();
    if (response.isSuccessful) {
        // do anything you want here        
        Toast.makeText(getApplicationContext(), "Get was successful", Toast.LENGTH_LONG).show();
    } else {
        Toast.makeText(getApplicationContext(), response.message, Toast.LENGTH_LONG).show();
    }
} catch (InterruptedException | ExecutionException e) {
    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
} catch (Exception e) {
    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
}
```
###### POST
```
try {
    String parameters = "{\"name\": \"test\"}";
    HttpHelper.Response response = new HttpHelper().request(this, HttpHelper.RequestType.post, "your_api_url", null, parameters, true, "Processing...").get();
    if (response.isSuccessful) {
        final JSONObject data = new JSONObject(response.data.toString());
        Toast.makeText(this, "Post was successful!", Toast.LENGTH_LONG).show();
    } else {
        Toast.makeText(this, response.message, Toast.LENGTH_LONG).show();
    }
} catch (InterruptedException | ExecutionException e) {
    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
} catch (Exception e) {
    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
}
```
###### PARAMETERS
Below is the parameters for the above requests (GET and POST) in the order demonnstrated
```
Context context
RequestType requestType     // enum RequestType { get, post }
String url
List<NameValuePair> headers
String parameters           // example: "{\"name\": \"test\"}"
Boolean showLoader
String loadingText
```

### Change Log
##### v0.1.3:   2018-07-23
> Added support for API level 19 by changing the minSdkVersion in the gradle file.

##### v0.1.2:   2018-07-05
> Added socket and connection timeout.

##### v0.1.1:   2018-07-04
> Added new **headers** parameters to the request method. This will enable you pass custom header keys/values as required by your API.

##### v0.1.0:   2018-07-04
> First release
