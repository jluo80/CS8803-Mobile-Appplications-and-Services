# Amazing Gifter
===
![](https://github.com/jluo80/CS8803-Mobile-Appplications-and-Services/raw/master/app/src/main/res/drawable/amazing gifter banner.png)

### CS8803 Mobile Applications and Services Project - Summer semester 2016
#### Team: Tidehunters  
#### Members: Tongzhe Zhang, Chong Guo, Kai Lu, Jiahao Luo
#### Author：Jiahao Luo

## Abstract
===
This project is our team project for CS8803 in Summer semester, 2016 at Georgia Tech. This project requires us to develop two seperate platform and I am responsible for the Android development part of our application.

There are two primary goals of this class:
 - Build your expertise in the area of Mobile Apps and Services
 - Enhance your understanding of the business ecosystem

Our goal is to Analyze, specify, design, implement, document and demonstrate an application for buying train tickets at GTTrain.com. Basically this project involves the usage of classical methodology for database development. The system is using a relational DBMS(MySQL) that supports standard SQL queries.

## Overall Architecture
===
Our project’s overall architecture can be explained explicitly according to the following figure. Speaking generally, the architecture consists of three technical parts.
CS8803-Mobile-Appplications-and-Services/
![](https://github.com/jluo80/CS8803-Mobile-Appplications-and-Services/raw/master/architecture.PNG)

0. As to frontend part, it includes two major mainstream mobile phone platforms which are native Android and iOS. Those two frequently-used platforms cover most of the mobile application users which can help our business establish a wide range of user groups.

1. Since the core value of our project can be simply concluded as “gift crowdfunding”, our API implementation includes Ebay API, PayPal system and Facebook SDK. 

  - In the early stage of the project, we decided to choose facebook as our only user login method, because facebook has a large number of users which can help us save time to explore our users and share our application base on facebook’s social network. By using Facebook SDK, the application can fetch user’s basic information and share their initiating gift crowdfunding to their friends. Besides, as a gift crowdfunding application, its usage rate may be limited. People may use this application based on certain condition only, such as birthday, upcoming festivals etc. There is no need to establish our own login system for now. 

  - In addition, gift is another important component. Amazon is our first choice as gift product searching source. However, it has certain condition that we are not qualified currently which make us pivot to another large product trading online platform, ebay. As a lthough ebay may not be as regulatory as Amazon, it can also provide users a great gift searching result. Amazon and other electronic commerce platforms will be considered in the future plan. 

  - PayPal is selected as our payment system. Because it is a fast and safe way to pay online without sharing financial details. Besides, PayPal comes very naturally since we are using ebay as our gift platform.


2. The last part is our backend, Firebase. Firebase not only serves as database in our project, it also collects several services, for example, user authentication integrated with facebook login. Firebase database is a NoSQL database, the datas are stored as JSON file and we can save and retrieve the real-time datas through RESTful API. 

## Implementation Status
===
As the overall architecture mentioned above, we have been implemented most of the features to both frontend platform. Both Android and iOS platforms work fine with Facebook login, Ebay searching API and Firebase backend. However, as to payment system, we are still looking for suitable payment system that can help us complete the crowdfunding payment. Currently, the PayPal system we’ve been integrated to our application in both platform is just a mockup.

## Acknowledgements
===
Thank you for all the help and support provided by my teammates.
