# Practice

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
