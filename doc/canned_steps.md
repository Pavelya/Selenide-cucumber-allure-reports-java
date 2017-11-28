# Predefined Steps

selenide-cucumber-allure-reports-java comes with the following set of predefined steps.

## Navigation Steps

To open/close URL and to navigate between pages use following steps :

	Then User navigate to "([^\"]*)"
	Then User navigate forward
	Then User navigate back
	Then User refresh page

To switch between frames use following steps :	

	Then User switch to frame having index \"(.*?)
	Then User switch to frame having id \"(.*?)
	Then User switch to frame having name \"(.*?)
	Then User switch to frame having xpath \"(.*?)
	Then User switch to frame having css \"(.*?)
	Then User switch to main content
	
To interact with browser use following steps :    

	Then User resize browser window size to width (\d+) and height (\d+)
	Then User maximize browser window
	Then User close browser



Javascript Handling Steps
-------------------------
To handle javascript pop-up use following steps :

	Then User accept alert 
	Then User dismiss alert
	Then User take snapshot
	
Javascript Handling Steps
-------------------------
To take custom snapshot (snapshot on failure will be taken automatiicly) use following steps :

	Then User take snapshot
  
Configuration Steps
-------------------
To print testing configuration use following step :

	Then User print configuration