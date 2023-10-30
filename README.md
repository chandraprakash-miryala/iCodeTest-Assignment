# iCodeTest-Assignment (Templatization of Org and Logins)
Web Application to add Organization with option to define fields for template, download template excel and upload template with data to show in table. Application is implemented in two parts:
1. RESTfull web services: API's build using spring boot are used for handling all the back end operations which includes storing data in h2 database
2. Front End: User interfaces designed and developed using Angular

#### Features
Create Organization with template fields <br>
Delete Organization <br>
Download template of an organization <br>
Upload template with user data to show in the table <br>

#### Deploy Application
To create a jar, run following command: 

`mvn clean install` this will create a jar at *backend/target/iCodeTestAssignment-0.0.1-SNAPSHOT.jar*

To deploy the jar, run the following command:

`java -jar backend/target/iCodeTestAssignment-0.0.1-SNAPSHOT.jar`

The application will be up at `http://localhost:8080`

#### Application screenshots
###### Organization List:
![image](https://github.com/chandraprakash-miryala/iCodeTest-Assignment/assets/40048528/9b7ac35d-0929-485f-8730-2f6b50ea7b7e)

###### Create Organization Template:
![image](https://github.com/chandraprakash-miryala/iCodeTest-Assignment/assets/40048528/2f9bc71b-5461-4d87-a704-79fbe6cad80e)

###### Organization Details after uploading template data:
![image](https://github.com/chandraprakash-miryala/iCodeTest-Assignment/assets/40048528/2882aed4-be53-4f6b-aa3c-df3288dd9b0e)

