# Selenide-cucumber-allure-reports-java
[![License](https://img.shields.io/github/license/pavelya/Selenide-cucumber-allure-reports-java?style=for-the-badge)](https://github.com/Pavelya/Cypress-allure-reports-typescript)
[![GitHub issues](https://img.shields.io/github/issues/pavelya/Selenide-cucumber-allure-reports-java?style=for-the-badge)](https://github.com/Pavelya/Cypress-allure-reports-typescript/issues)

Automation Testing using BrowserStack, Selenide API, Cucumber and Allure reports

Behavior Driven Development (BDD) approach to write automation test scripts.  

Getting Started
-------------
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

Prerequisites
--------------  
[Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) - Getting Started - Installing Git  
[Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) - Maven in 5 Minutes  

How to install this project
-------------

```bash
git clone https://github.com/Pavelya/Selenide-cucumber-allure-reports-java.git  

```
Running the tests - command line mode
-------------------
```bash
cd to project path   

Run with BrowserStack  
mvn clean install test -U -DuseBS=true -Denv=prod -Dbs_local=false -Ddevice=OS_X_Catalina_Safari "-Dcucumber.options=--tags @google --plugin io.qameta.allure.cucumberjvm.AllureCucumberJvm"  
  
Run locally with headless browser   
mvn clean install test -U "-Dcucumber.options=--tags @google --plugin io.qameta.allure.cucumberjvm.AllureCucumberJvm" 

Run locally with UI 
mvn clean install test -U -Dheadless=false "-Dcucumber.options=--tags @google --plugin io.qameta.allure.cucumberjvm.AllureCucumberJvm" 

Run locally with mobile emulaiton
mvn clean install test -U -Dheadless=false -DmobileLocal=true "-Dcucumber.options=--tags @google --plugin io.qameta.allure.cucumberjvm.AllureCucumberJvm" 

```
Parameters usage

| Variable    | Description                    | Example    | Default   |
| ----------  | ------------------------------ | -----------| --------- |
| useBS       | Launch test using BrowserStack | `true`     | `false`   |
| bs_local    | Use BrowsserStack local config | `true`     | `true`    |  
| device      | Specify BrowserStack device    | `iPhone_XS`|  N/A      |
| env         | Environment config to be used  | `dev`      | `prod`    |
| headless    | Use headless mode              | `true`     | `true`    |
| mobileLocal | Use mobile emulation           | `true`     | `false`   |

Running the tests - from IDE  
-------------------
```bash
Run [src/test/java/com/qa/automation/BaseTest.java](src/test/java/com/qa/automation/BaseTest.java)  as maven test

```

Documentation
-------------
* [Launch options](doc/launch_options.md)
* [Pass external parameters to test](doc/working_with_parameters.md)
* [Use allure reports](doc/allure_reports.md)
* [Bug template](doc/bug_report.md)
* [Feature request template](doc/feature_request.md)

Built With
-------------
* [Browserstack](https://www.browserstack.com/) - Cloud web and mobile testing platform  
* [Selenium](http://www.seleniumhq.org/) - Portable framework for testing web applications
* [Selenide](http://selenide.org/) - Framework for test automation powered by Selenium WebDriver
* [Maven](https://maven.apache.org/) - Dependency Management
* [Cucumber](https://cucumber.io/) - Behavior Driven Development (BDD) library 
* [Allure reports](http://allure.qatools.ru/) - Framework designed to create test execution reports
* [Owner API](http://owner.aeonbits.org/) - Java properties management

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

Thanks  
-------------  
Huge kudos to wonderful software that allowed to create this project:

[![BrowserStack](https://www.browserstack.com/images/mail/browserstack-logo-footer.png)](https://www.browserstack.com)  
[![Selenide](https://selenide.org/images/selenide-logo-big.png)](https://selenide.org)  
[![Allure](https://avatars3.githubusercontent.com/u/5879127?s=200&v=4)](https://github.com/allure-framework/allure2)  
[![Cucumber](https://raw.githubusercontent.com/cucumber-ltd/brand/master/images/png/notm/cucumber-black/cucumber-black-128.png)](https://cucumber.io/)
[![Maven](https://maven.apache.org/images/maven-logo-black-on-white.png)](https://maven.apache.org/)
