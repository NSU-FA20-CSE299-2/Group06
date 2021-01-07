# Practice

Apps created for practice and later to use in main app project.  

## SMSSender App

<table>
  <tr>
    <th>Project Name</th>
    <th>Description</th>
    <th>Output on Android Device</th>
  </tr>
  <tr>
    <td>SMS Sender</td>
    <td>This project allows user to send sms without using the in-build messenger application.</td>
    <td><img width="400" src="https://i.ibb.co/84YC2Mw/Screenshot-2020-12-06-17-03-08.png">  </td>
  </tr>
</table>

## FirebaseSDK App

- Download google-services.json file from firebase admin->settings and copy inside project's 'app' folder

- Add to app's build.gradle,  

```Gradle
dependencies {

    ...

    // Import the BoM for the Firebase platform
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation platform('com.google.firebase:firebase-bom:26.1.1')
    // Declare the dependency for the Realtime Database library
    implementation 'com.google.firebase:firebase-database'
    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries
    // Declare the dependency for the Firebase Authentication library
    implementation 'com.google.firebase:firebase-auth'

}

// ADD THIS AT THE BOTTOM
apply plugin: 'com.google.gms.google-services'
```  

- Add to project's build.gradle,

```Gradle
dependencies {
        
        ...

        classpath 'com.google.gms:google-services:4.3.4'

}
```

## FusedLocationAPIAdapter App

Add to Manifest.xml,  

```Xml
<manifest...>

<uses-permission android:name="android.permission, ACCESS_FINE_LOCATION"/>

...

</manifest>
```

Add to app's build.gradle,  

```Gradle
dependencies {

    ...

    // permissions library
    implementation 'com.karumi:dexter:6.2.2'

    // fused location api
    implementation 'com.google.android.gms:play-services-location:17.1.0'

}
```  

## ImageUpload App  

- Add to Manifest.xml,

```Xml

...
<manifest ...>

    ...

    <uses-feature android:name="android.hardware.camera"/>

    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
        android:maxSdkVersion="18" />

    <application ...>

        ...

        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="com.nsu.group06.cse299.sec02.imageupload.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths"></meta-data>
        </provider>
        
        ...

    </application>

    ...

</manifest>
```  

- Add to res/xml/file_paths.xml,  
  
```Xml
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">

    <external-path
        name="external"
        path="." />
    <external-files-path
        name="external_files"
        path="." />
    <cache-path
        name="cache"
        path="." />
    <external-cache-path
        name="external_cache"
        path="." />
    <files-path
        name="files"
        path="." />

</paths>
```  

- Download google-services.json file from firebase admin->settings and copy inside project's 'app' folder  

- Add to app's build.gradle,  

```Gradle  
dependencies {
    
    ...

    // Import the BoM for the Firebase platform
    implementation platform('com.google.firebase:firebase-bom:26.2.0')

    // Declare the dependency for the Cloud Storage library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation 'com.google.firebase:firebase-storage'
}

// ADD THIS AT THE BOTTOM
apply plugin: 'com.google.gms.google-services'
```

- Add to project's build.gradle,  

```Gradle
dependencies {
        
        ...

        classpath 'com.google.gms:google-services:4.3.4'
    }
```
