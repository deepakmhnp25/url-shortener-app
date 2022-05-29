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

![alt text](https://github.com/deepakmhnp25/url-shortener-app/blob/master/screenshots/home%20page.PNG?raw=true)

***
#### 2. Service should let authenticated users create shortened URLs.
***
* Upon login user will be taken to a page where the user can input their long url and click the shorten button.
* The result showing the shortened url will get listed on the same screen. 

![alt text](https://github.com/deepakmhnp25/url-shortener-app/blob/master/screenshots/login.PNG?raw=true)

***
#### 3. Service should let any user use shortened URLs (e.g., follow redirects to original URLs).
***
* The shortened url that user created in the second step can be used anywhere regardless of the login status.
* This url does not have any authentication / authorization required.

![alt text](https://github.com/deepakmhnp25/url-shortener-app/blob/master/screenshots/shortened%20url.PNG?raw=true)

***

## Architecture and Technical stack
#### The design diagram

![alt text](https://github.com/deepakmhnp25/url-shortener-app/blob/master/screenshots/design%20diagram.PNG?raw=true)

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
