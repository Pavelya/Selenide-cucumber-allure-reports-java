# Configuration options

##### GENERAL:  

	Configuration is set in following classes:  
	_MainConfig_  
	_TestEnvironmentConfig_  
	_BrowserStackDeviceConfig_  

##### MAIN CONFIG:  

_src/test/resources/MainConfig.properties_  

	Goal: to store configuration, not depends from environment (example: folderName)  
	  
	Usage: protected static MainConfig config = ConfigFactory.create(MainConfig.class); 
	String ALLURE_SNAPSHOTS_FOLDER = config.allure_screenshots_folder();  
	
##### TEST ENVIRONMENT CONFIG:
	
_src/test/resources/ANY_NAME.environment.properties_  

	Goal: pass parameters, related to specific environment  
	  
	Usage: to point to environment config file pass env param: mvn test  ...-Denv=ANY_NAME...  
	to get correct cofig:  private TestEnvConfig testConf = createTestEnvConfig();  
	open(testConf.googleUrl());  
	
##### BROWSERSTACK DEVICE CONFIG:  

_src/test/resources/BROWSERSTACK_DEVICES_CONFIG_  

	Goal: select device for test  
	
	Usage: to point to device config file pass env param: mvn test  ...--Ddevice=OS_X_Mojave_Safari...
		   
	