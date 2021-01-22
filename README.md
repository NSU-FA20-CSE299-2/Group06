<p style="text-align: center;">&nbsp;</p>
<p style="text-align: center;">&nbsp;</p>
<p align="center"><strong><img src="https://media.dhakatribune.com/uploads/2016/11/nsulogo.jpg" alt="" width="307" height="172" /></strong></p>
<p align="center"><strong>North South University</strong></p>
<p align="center">Department of Electrical &amp; Computer Engineering</p>
<p align="center"><strong>Project Report</strong></p>
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

<p><strong>PROPOSED FEATURES</strong></p>

<p>The features were extracted from the following use-case diagram,</p>

![use-case-diagram](Documentation/use_cases.png)

<p>The features are below,</p>

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

<p><strong>INCOMPLETE FEATURES</strong></p>

<p>Among the proposed features the following were not implemented-</p>

<ul>

<li>Add App users as emergency contacts (<strong>2i</strong>), only non-app users can be added as emergency users by their phone numbers.</li>

<li>Reaching nearby people when posted for help was not implemented (<strong>4ii</strong>) because of unexpected behaviour of the API used (see Issue #5)</li>

<li>Sign in with Facebook and post on Facebook feed (<strong>4iii</strong>).</li>

<li>Automatically triggered help posts by gesture (<strong>5</strong>).</li>

<li>Allowing emergency contacts to monitor in realtime (<strong>7</strong>)</li>

</ul>

<br>

<br>

<p><strong>PROJECT OVERVIEW</strong></p>

<p>Brief description of parts of the project- app, web and database, are added below with appropriate screenshots.</p>

<br>

<p><strong>Help Me App (Android)</strong></p>

<figure> <center> <img src="https://i.ibb.co/YyCZpcM/screenshot-2021-01-22-21-19-07-712.png"
     alt="ERR" width="60%"/>
     <figcaption>Signin Page</figcaption>
     </center></figure>
<br>
<figure> <center> <img src="https://i.ibb.co/m5MnrK8/screenshot-2021-01-22-21-19-12-82.png"
     alt="ERR" width="60%"/>
     <figcaption>Signup Page</figcaption>
     </center></figure>
<br>
<figure> <center> <img src="https://i.ibb.co/9w5YBZY/screenshot-2021-01-22-21-17-58-434.png"
     alt="ERR" width="60%"/>
     <figcaption>User Menu Page</figcaption>
     </center></figure>
<br>
<figure> <center> <img src="https://i.ibb.co/vPWmv1F/screenshot-2021-01-22-21-18-52-842.png"
     alt="ERR" width="60%"/>
     <figcaption>User Profile Page</figcaption>
     </center></figure>
<br>
<figure> <center> <img src="https://i.ibb.co/0Z0bB0Y/screenshot-2021-01-22-21-18-20-538.png"
     alt="ERR" width="60%"/>
     <figcaption>Emergency Contacts Page</figcaption>
     </center></figure>
<br>
<figure> <center> <img src="https://i.ibb.co/gdBzKC7/screenshot-2021-01-22-21-18-07-844.png"
     alt="ERR" width="60%"/>
     <figcaption>Help Post Page</figcaption>
     </center></figure>
<br>
<figure> <center> <img src="https://i.ibb.co/vdrLzGD/screenshot-2021-01-22-21-18-38-15.png"
     alt="ERR" width="60%"/>
     <figcaption>Help Feed Page</figcaption>
     </center></figure>
<br>
<figure> <center> <img src=" https://i.ibb.co/44TS2gd/screenshot-2021-01-22-21-18-03-343.png"
     alt="ERR" width="60%"/>
     <figcaption>Settings Page</figcaption>
     </center></figure>

<br>

<p><strong>Help Me Api (Express JS)</strong></p>

<p>The web APIs in this project are developed with Expressjs framework which is a very popular javascript backend framework. The main purposes of the APIs are: to interact with the database and to provide reverse geolocation data. The Web App is thoroughly dependent on this Web API project. </p>

<p><strong>Database Interaction: </strong>The major purpose of developing API is to interact with database service so that the Web App can send requests with query parameters (unique post id number in this case) and get the exact data from the database through API. This allows us to implement, integrate and set up the database SDK’s and services only one place which is in the API project. This helps to prevent using database services directly on the Web Appliaction, ensures security and to follow the best practices while developing softwares.</p>

<p><strong>Reverse Geolocation: </strong>Another significant purpose of developing API is to decode users GPS data (lattitude and longitude) and convert them into formatted addresses. We used “OpenCage Geocoder”, a free geocoding API service in our API project to pursue this formatted address feature. The end point takes a unique post id number as a parameter and fetches that post’s latitude and longitude from the database. Then the API sends a request to the third-party Geocoder API and receives the response. After receiving the response it filters the data and only stores the formatted address data to that specific post’s address field in the database.
</p>

<p><strong>API endpoints and functions: </strong></p>
<center>
<table> 
<tr>
<td> <b>Endpoints </b> </td>
<td> <b>Functions </b> </td>
</tr>
<tr>
<td>/</td>
<td>Shows API home </td>
</tr>
<tr>
<td>/devlog</td>
<td>Shows API logs</td>
</tr>
<tr>
<td>/post?pid={unique post id} </td>
<td>Sends help post as response</td>
</tr>
<tr>
<td>/trigger?pid={unique post id} </td>
<td>Uses geocoding services to fetch formatted address and place it into that post’s address field in the database</td>
</tr>
</table>
</center>

<br>

<p><strong>Help Me Web App</strong></p>
<p>The Web App project establishes the ‘Help Me’ App as a multiplatform application by adding new dimensions in the application. The Web App allows users to view ‘Help Posts’ without downloading or installing the application on their phone. The Web App comes with very simple features. It has only three pages in total and the whole application is backed by the Expressjs framework. For the front end we also used css, javascript, ajax and bootstrap framework. The Web App can be used from any browser and the application is also ‘mobile browser friendly’.</p>
<p><strong>App Lander Page: </strong>The app landing page is specifically made for promoting our mobile app. It describes the app's features and value proposition so that the users are enticed to click through, download, and install. This is a static web page, download options redirects to another page for further operations.</p>

<p> <center> <img src="https://i.ibb.co/NVsLNj2/Capture.jpg"
     alt="ERR" width="80%" /></center></p>

<p><strong>Help Post Page: </strong>This is the main page of the Web App that shows the exact ‘Help Post’ that user posted. The ‘Emergency Contact’ users will get the link of this page as a sms and will be directed into the page showing the full help post. This is the only dynamic web page of this application. If the android app fails to get the formatted address as the post address, the user can view the formatted address by clicking on the address field of the post. The users can also view the location on map by clicking the address field of the post. This will take them to the google map and they can view the location from native google map application or on the web map.</p>

<p><center><img src="https://i.ibb.co/MV8RK2k/2.png"
     alt="ERR" width="80%" /></center></p>

<p><strong>The Error Page: </strong>Users will be redirected to this static error page if they try to open an invalid ‘Help Post’ link which does not exist.</p>

<p><center><img src="https://i.ibb.co/dkN56pp/3.jpg"
     alt="ERR" width="80%" /> </center></p>

<br>

<p><strong>Database: </strong></p>
<p>For database service we used Google’s ‘Firebase Realtime Database’ as our project’s main database. Firebase realtime database is a cloud-hosted nosql database. It stores data as JSON and synchronized in realtime to every connected client.</p>
<p><strong>Database Structure: </strong></p>

<p><center><img src="https://i.ibb.co/Sn2dfCB/nosql-database-structure.png"
     alt="ERR" width="80%" /> </center></p>

<br>


<p><strong>TOOLS</strong></p>

<ol>

<li><em>Framework</em> <p>Android Studio with Java.</p></li>

<li><em>Database</em>
<p>Firebase real-time database was used for storing all relevant data of the project.</p>
<p>Firebase storage was used for storing photos taken.</p>
</li>

<li><em>Third Party Support</em>

<ul> <p>

<li><a href="https://developers.facebook.com/docs/android/">Facebook SDK for Android</a> (<strong>The SDK was not used</strong>)</li>

<li><a href="https://developers.google.com/nearby/connections/overview">Google Nearby Connections API</a> (<strong>Feature 4.ii</strong>)</li>

<li><a href="https://developers.google.com/location-context/fused-location-provider">Google Fused Location Provider API</a> (<strong>Feature 3.ii</strong>)</li>

<li><a href="https://developers.google.com/maps/documentation/android-sdk/overview">Google Maps SDK</a> (<strong>Was not required, default maps app was used to show location instead.</strong>)</li>

<li>API for face blur (<strong>Subsequent feature was not implemented</strong>)</li>

<li>API for phone shake gesture detection (<strong>Subsequent feature was not implemented</strong>)</li>

</p></ul>

</li>

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

<p><strong>PLANS FOR MONETIZATION</strong></p>
<ul>
<li>Initially we plan to create a prototype using the free version of the tools mentioned. Presenting the prototype in various competitions/hackathons we hope to collect appropriate fundings for launching the app.</li>
<li>If the idea is good enough we would also like to approach various charitable organizations for funding. However, we do not have a concrete plan on how and who to approach regarding this.</li>
<li>The app will also be linked with a patreon account. Users can donate if they want there.</li>
</ul>
