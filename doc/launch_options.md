# Configuration options

##### GENERAL:  

	Following options are avalible for browser launch:  
	1. Use local browser, created by Selenide  
	2. Use browserstack devices    
	3. Generate mobile emulation

##### LOCAL BROWSER:  
  
	Goal: launch tests locally using chrome  
	  
	Usage: pass ...-DuseBS=false...  param   
	To use browser other than chrome use Selenide documentation:  
	https://github.com/selenide/selenide/wiki/How-Selenide-creates-WebDriver    
	
##### BROWSER STACK LAUNCH
	
	Goal: launch tests on real devices, using Browserstack API  
	  
	Usage: pass ...-DuseBS=true...  param to enable browserstack.    
	To set a device pass ...-Ddevice=DEVICE_NAME...     
	List of avalible devices is located under:   
	src/test/resources/BROWSERSTACK_DEVICES_CONFIG 
	To add / update devices config use Browserstack documentation:  
	https://www.browserstack.com/automate/capabilities    
		
	
##### BROWSERSTACK SETUP:  

_src/test/java/com/qa/common/Browserstack.java_  
	 
	Main browserstack setup to init and close driver 


_src/test/java/com/qa/common/MainConfig.java_
	Config map to hold browserstack configuraton  
	For more info regarding config use, see working with params doc.   
		
_src/test/resources/MainConfig.properties_  


	Browserstack configuration values.  
	IMPORTANT:   
	Keys:  
	bs_user=test  
	bs_key=test  
	Should be replaced with actual BS account values 
	
	
##### MOBULE EMULATION SETUP:  
Goal: launch tests locally using mobile emulation
Usage: pass ...-DmobileLocal=true...  param  
To set the mobile device type update mobileEmulationDevice key in: _src/test/resources/MainConfig.properties_


		   
	