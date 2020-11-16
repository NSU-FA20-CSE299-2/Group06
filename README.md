<p style="text-align: center;">&nbsp;</p>
<p style="text-align: center;">&nbsp;</p>
<p align="center"><strong><img src="https://media.dhakatribune.com/uploads/2016/11/nsulogo.jpg" alt="" width="307" height="172" /></strong></p>
<p align="center"><strong>North South University</strong></p>
<p align="center">Department of Electrical &amp; Computer Engineering</p>
<p align="center"><strong>Project Proposal</strong></p>
<p align="center"><strong>Group No</strong>: 06</p>
<p align="center"><strong>Fall 2020</strong></p>
<p align="center"><strong>Project Name</strong>: HELP ME APP</p>
<p align="center"><strong>Course No</strong>: CSE 299 <strong>Sec</strong><strong>:</strong> 2</p>
<p align="center"><strong>Faculty</strong>: Shaikh Shawon Arefin Shimon (Sas3)</p>
<p align="center"><strong><u>Member 1</u></strong><u>:</u></p>
<p align="center"><strong>Name</strong><strong>:</strong> Ferdous Zeaul Islam</p>
<p align="center"><strong>ID</strong><strong>:&nbsp; </strong>173 1136 042</p>
<p align="center"><strong>Email</strong><strong>:</strong> <a href="mailto:ferdous.islam@northsouth.edu">ferdous.islam@northsouth.edu</a></p>
<p align="center"><strong><u>Member 2</u></strong><strong><u>:</u></strong></p>
<p align="center"><strong>Name</strong><strong>:</strong> Rifat Islam</p>
<p align="center"><strong>ID</strong><strong>:&nbsp; </strong>173 1536 042</p>
<p align="center"><strong>Email</strong><strong>:</strong> <a href="mailto:rifat.islam173@northsouth.edu">rifat.islam173@northsouth.edu</a></p>
<p align="center"><strong>Git Repository</strong><strong>: </strong><a href="https://github.com/NSU-FA20-CSE299-2/Group06">https://github.com/NSU-FA20-CSE299-2/Group06</a></p>
<p align="center"><strong>Date Submitted</strong><strong>: </strong>11/11/2020</p>
<p><strong>&nbsp;</strong></p>
<p><strong>&nbsp;</strong></p>


<p><strong>INTRODUCTION</strong></p>
<p>A real-time emergency help seeking platform consisting of an Android App.</p>

<br>

<p><strong>MOTIVATION</strong></p>
<p>Personal security is a great concern in our country. Lack of trust in authority has forced people to either take matters in their own hands or seek help through gaining publicity in social media. In any case, there is almost never any instant help for the victim. Our app is a meager attempt to create such a platform that provides instant assistance to a person in distress through appropriate medium, while keeping a keen eye to prevent spread of false information.</p>

<br>

<p><strong>FEATURES</strong></p>
<ol>
<li><p>Signup with email or Facebook account & login,logout.</li>

<li><p>Assign emergency contacts. Two types of emergency contacts:
<ol type="a">
<li><strong>App users</strong>: Can be added by their username and phone number provided during sign up.
</li>
<li><strong>Non-app users</strong>: Non app users can also be added through their phone number.
</li>
</ol>
</p></li>

<li><p>An emergency help post can consist of four elements: 
<ol type="a">
<li><strong>Text</strong>:  predefined text, can be edited.
</li>
<li><strong>Location</strong>: Current latitude and longitude of the person, visible on map.
</li>
<li><strong>Photo</strong>: A photo clicked through the app, any human face detected in this photo will be blurred. (faces will be blurred to avoid false accusations, the platform is only meant for helping and not capturing or identifying criminals <strong>yet</strong>).
</li>
<li><strong>TimeStamp</strong>.
</li>
</ol>
</p></li>

<li><p>The help post will be forwarded to four type of audience:
<ol type="a">
<li><strong>Emergency Contacts</strong>:  Assigned emergency contacts (explained in <strong>Feature-2</strong>) will receive the help seeking post. Both app-users and non-app-users will receive an SMS, app-users will also receive a notification from the app. The SMS will contain: poster’s location, phone number and a link. For app users the link will open the App and show the whole post, for non-app users this link will open a static web page containing all the elements(text, photo, location on map) provided by the poster.
</li>
<li><strong>Nearby People</strong>: Other app users who are nearby within a specific range, using device bluetooth/wifi signal. Receivers need to have mobile bluetooth/wifi turned on to detect the signal. This signal will contain only text and location. It is unusual to expect people will keep bluetooth/wifi turned on all the time so for this, specific time periods can be fixed from the app settings by the users. This feature does not require any internet connection.
</li>
<li><strong>User’s Facebook Profile</strong>: If an user has signed in with facebook and allowed the app to post on their facebook wall, emergency help seeking posts will be added to his/her facebook wall.
</li>
<li><strong>App Feed</strong>:The app itself has a feed of it’s own, all emergency help posts will be listed here. The feed is visible to all app users and can be filtered based on location & time.
</li>
</ol>
</p></li>

<li><p>Posts for help can be made instantly by pressing a custom sequence of volume up-down button (default settings is press volume-down key twice and volume-up once) and also shaking the device up and down. For these detection mechanisms to function “Distress mode” needs to be enabled from the App’s settings. Such instant help seeking posts are to contain only predefined text and location.
</p></li>

<li><p>Alternatively help seeking posts can be made with more details such as- attaching photos. Needless to say this type of posts cannot be generated instantly.
</p></li>

<li><p>Assigned emergency contacts (who must also be app users) can monitor an user’s status- location on map, sudden shutdown of phone, unusual occurrence detected using motion and audio sensors. For the monitoring to take place, the user has to explicitly select and grant permission to emergency contacts through the app. The monitoring is completely real-time, but doesn’t require the monitoring user to constantly hold his phone and check. In case of sudden phone shutdown or unusual occurrence detected by the app a notification will be sent to the monitoring user. This notification will keep showing up after specified time intervals until the monitoring user notices.
</p></li>

</ol>

<br>

<p><strong>SOCIAL BENEFITS</strong></p>
<ul>

<li>Reduce constant fear of personal security with an assurance that you can instantly reach out to an appropriate group of audience when in distress (<strong>Feature-5</strong>).
</li>

<li>A platform that encourages community based response to an emergency situation by reaching out to nearby people, sharing on facebook and a global app feed (<strong>Feature-4.ii, 4,iii, 4.iv</strong>).
</li>

<li>The global app feed <strong>(Feature-4.iv)</strong> lets users identify risky areas, from where help seeking posts are made more frequently.
</li>

<li>Photo attached to help seeking posts attract more attention, especially when reaching out to nearby people & sharing on facebook. Faces in these photos will be blurred to prevent false accusations.
</li>

<li>The platform is aimed at only to provide assistance to one in distress but not to accuse/catch criminals. We believe the latter is a far more complex task.
</li>

</ul>

<br>

<p><strong>TOOLS</strong></p>

<ol>

<li><em>Framework</em> <p>Android Studio with Java.</p></li>

<li><em>Database</em>
<p>Firebase real-time database & Firebase functions for backend triggers.</p>
<p>The real-time monitoring mentioned in <strong>Feature-7</strong> & app feed mentioned in <strong>Feature-4.iv</strong> is going to be implemented by the firebase realtime-database.
</p>
</li>

<li><em>Third Party Support</em>

<ul> <p>

<li><a href="https://developers.facebook.com/docs/android/">Facebook SDK for Android</a> (<strong>Feature 1 & 4.iii</strong>)</li>

<li><a href="https://developers.google.com/nearby/connections/overview">Google Nearby Connections API</a> (<strong>Feature 4.ii</strong>)</li>

<li><a href="https://developers.google.com/location-context/fused-location-provider">Google Fused Location Provider API</a> (<strong>Feature 3.ii</strong>)</li>

<li><a href="https://developers.google.com/maps/documentation/android-sdk/overview">Google Maps SDK</a> (<strong>Feature 3.ii</strong>)</li>

<li>API for face blur (to be decided) (<strong>Feature 3.iii</strong>)</li>

<li>API for phone shake gesture detection (to be decided) (<strong>Feature 5</strong>)</li>

</p></ul>

</li>

</ol>

<br>

<p><strong>Monetization</strong></p>
<ul>
<li>Initially we plan to create a prototype using the free version of the tools mentioned. Presenting the prototype in various competitions/hackathons we hope to collect appropriate fundings for launching the app.</li>
<li>If the idea is good enough we would also like to approach various charitable organizations for funding. However, we do not have a concrete plan on how and who to approach regarding this.</li>
<li>The app will also be linked with a patreon account. Users can donate if they want there.</li>
</ul>
