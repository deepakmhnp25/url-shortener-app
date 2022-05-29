# url-shortener-app
This is an application which will create short URLs (like Bitly, Rebrandly, etc.) for their original links.

## How to run the app

***
### Pre requisites : 
* Docker Engine (Dowload link : https://www.docker.com/products/docker-desktop/)

Steps : 
---
Execute the Below commands
* docker pull deepakmhnp25/urlshortener:0.1
* docker run -p8080:8080 deepakmhnp25/urlshortener:0.1
---
***
## Task and solution description
#### 1. Service should let users register a new account and authenticate themselves.
*** 
* Upon start up of the app, user may use http://localhost:8080 from your application and it will land the user on a home page where the user can register using email id and password.
* The regstered user may use the email id and password for login
* The UI is basic html.
* The passwords are Bcryptencoded  for safer storage.

<img src="C:\Users\deepa\Desktop\home page.PNG" width="500`"/>

***
#### 2. Service should let authenticated users create shortened URLs.
***
* Upon login user will be taken to a page where the user can input their long url and click the shorten button.
* The result showing the shortened url will get listed on the same screen. 

<img  height = "100" width="300" src="C:\Users\deepa\Desktop\login.PNG"/><img height="100" src="C:\Users\deepa\Desktop\shorten url.PNG"/>

***
#### 3. Service should let any user use shortened URLs (e.g., follow redirects to original URLs).
***
* The shortened url that user created in the second step can be used anywhere regardless of the login status.
* This url does not have any authentication / authorization required.

<img src="C:\Users\deepa\Desktop\shortened url.PNG"/>

***

## Architecture and Technical stack
#### The design diagram

<img src="C:\Users\deepa\Desktop\design diagram.PNG"/>

#### Tech Stack
***
* Spring Boot 
* Google Firestore datbase
* Spring Security
* Junit
* Spring test (integration test)
* Mockito
* Maven
* Docker
