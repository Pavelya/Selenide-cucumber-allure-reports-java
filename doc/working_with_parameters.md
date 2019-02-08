# Pass parameters from config files

##### Two types of configuration files are enabled in project:

_src/test/resources/MainConfig.properties_  

	Goal: to store configuration, not depends from environment (example: folderName)  
	  
	Usage: protected static MainConfig config = ConfigFactory.create(MainConfig.class); 
	String ALLURE_SNAPSHOTS_FOLDER = config.allure_screenshots_folder();  
	
_src/test/resources/ANY_NAME.environment.properties_  

	Goal: pass parameters, related to specific environment  
	  
	Usage: to point to environment config file pass env param: mvn test  "-Dbrowser=chrome" "-Denv=ANY_NAME"...  
	to get correct cofig: System.getProperty("ANY_NAME").toLowerCase();  
	String preProdUrl = getEnvironmentProperty("preProdUrl");  
		   
	