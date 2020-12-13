# Practice

Apps created for as practice & to use in main app project.  

## SMSSender App

Codes/Documents/Links related to any practiced topics are to be included here.

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
