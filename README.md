# selenide-cucumber-allure-reports-java

Automation Testing Using Selenide API, BDD and Allure reports

Automation base is a behavior driven development (BDD) approach to write automation test script to test Web.  
The framework supports reporting with Allure reports
Selenide is a framework for test automation powered by Selenium WebDriver.
Official documentation: http://selenide.org/

Getting Started
-------------
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

Prerequisites
--------------
Maven  
Git

Installing
-------------
Clone the repo to get a working project

Running the tests - command line mode
-------------------
cd to project path  

mvn clean install test -U -Denv=pp1 "-Dcucumber.options=--tags @google --plugin io.qameta.allure.cucumberjvm.AllureCucumberJvm"

Running the tests - from IDE  
-------------------
Run [src/test/java/com/qa/automation/BaseTest.java](src/test/java/com/qa/automation/BaseTest.java)  as maven test


Documentation
-------------
* [Pass external parameters to test](doc/working_with_parameters.md)
* [Use allure reports](doc/allure_reports.md)
* [Bug template](doc/bug_report.md)
* [Feature request template](doc/feature_request.md)

Built With
-------------
* [Selenium](http://www.seleniumhq.org/) - The web framework to automate browsers
* [Selenide](http://selenide.org/) - Test framework over Selenium
* [Maven](https://maven.apache.org/) - Dependency Management
* [Cucumber](https://cucumber.io/) - Behavior Driven Development (BDD) library 
* [Allure reports](http://allure.qatools.ru/) - Reporting 

Contributing
-------------
Please read [CONTRIBUTING.md](doc/CONTRIBUTING.md) for details of the process for submitting pull requests.

Versioning
-------------
[SemVer](http://semver.org/) is in use for versioning.  

Authors
-------------
**Pavel Yampolsky**  - Skype: pavel.yampolsky.willhill Email: 2pavelya@gmail.com

License
-------------
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
