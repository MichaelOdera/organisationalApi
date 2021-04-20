# Organisational Api

This project was generated with [JAVA](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html) version 11.

## Author
[Michael Ochieng' Odera](https://www.github.com/MichaelOdera)

## Date Published
17th February 2020


## Contributors
Michael Ochieng' Odera


## Contacts
In case of any bugs or improvements, contact me through email:michogelira@gmail.com or phone:+254733258286

## Development server
developed to heroku

## Code Beat Badge
[![codebeat badge](https://codebeat.co/badges/ad7668d7-1795-42a9-bfbb-8ec0d33fb03c)](https://codebeat.co/projects/github-com-michaelodera-organisationalapi-feature-branch)


## Bugs Encountered
There were no major bugs to be encountered and the console would prove to be most helpful in case of any need to log out the errors

## Technologies used
* Java11
* Gradle
* Spark Framework
* Maven
* Postgres
* SQL2O
*  json
* gson

## Installation
It is imperative that you install a JavaRuntimeEnvironment IDEA and Gradle dependencies else the system won't function as required.Knowledge of working with Java is also important.

## Description
The project allows one to enter data through various end points belonging to users, departments and event news, using json formats and implementing gson to convert the json objects to POJOs, and the POJOs fetched back to json objects with a get or post to view or send certain information to the database.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## API Routes
The following are the api routes for the above application: -

    | Method | model       |    End Points         |
    |------- |-----------  |---------------------- |
    |post    | user        |   /users/new          |
    |post    | department  |/departments/new       |
    |post    | news        |   /news/new           |
    |get     | user        |  /user/:id            |
    |get     | user        | /users                |
    |delete  | user        | /users/:id            |
    |get     | department  | /departments          |
    |get     | department  | /department/:id       |
    |delete  | department  |/department/:id        |
    |get     | user        |/departments/:id/users |
    |get     | news        | /news/:id             |
    |put     | news        | /news/:id             |
    |delete  | news        | /news/:id             |
    |delete  | news        | /news/delete/all      |
    |get     | news        | /news                 |
    |get     | news        |/departments/:id/news  |
    
## JSON Samples
### User
 ```json5
   {
    "userName" : "user254",
    "userCompanyPosition": "Secretary",
    "userCompanyRole":"Taking Notes",
    "departmentId": 3,
    "id":5
    }
```

### Department
```json5
    {
      "departmentName": "Managerial",
      "departmentDescription":"Management and company activity oversite",
      "employeesNumber": 15,
      "departmentId": 2
    }
```

### News

```json5
   {
     "newsTitle": "Sanitation",
     "newsContent":"Cleaning will be done on Mondays as from 7:30pm - 9:30pm",
     "departmentId": 3,
     "id":2
   }

```

    



Run `gradle run` for a dev server. Navigate to `http://localhost:4567/`. The app will not automatically change if you make any changes white the app is still live. A rerun of the command will be needed in this case.

## Code scaffolding

Run `netstat -pliten |grep java` to check for ports that are being used by java processes. You can also use `gradle run|build`.

## Build

Run `gradle build` to build the project. The `build.gradle` file is important for any of the required process dependencies to function properly.

## Running unit tests

Run `run test` to execute the unit tests on your local directory after git cloning or downloading.


## Further help

To get more help on the Spark 11 use go check out the [APACHE SPARK SITE](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html).

##  LICENSE
MIT [LICENSE](LICENSE)