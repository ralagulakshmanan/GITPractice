package businesscomponents;

import supportlibraries.ScriptHelper;
import uimap.Common;

public class ReviewWorkflow extends CommonFunctions{

	public ReviewWorkflow(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	private final long timeOutInSeconds = Long.parseLong(properties.getProperty("ObjectSyncTimeout"));
	private final long lngPagetimeOutInSeconds = Long.parseLong(properties.getProperty("PageLoadTimeout"));
	private final long lngMinTimeOutInSeconds = Long.parseLong(properties.getProperty("MinObjectSyncTimeout"));
	
	
	public void reviewersWorkflow() throws Exception{
		
		String strWindowName=dataTable.getData("Reviewers", "Window_Name");
		String strPageName=dataTable.getData("Reviewers", "Page_name");
		String strPopUpName=dataTable.getData("Reviewers", "Window_Name")+"PopUp";
		docNumberSearch("All Documents", dataTable.getData("Reviewers", "Document_To_Search"), strPageName);
		
		if(!dataTable.getData("Reviewers", "Only_Status_Check").toLowerCase().equals("yes")){
		if(objectExists(new Common("Accept").contentbutton, "isDisplayed", lngMinTimeOutInSeconds, 
				"Accept", "Button", strPageName, false)){
			contentButton("Accept", strPageName);
			
		}
		contentButton("Complete", strPageName);
		
		/*if(objectExists(new Common(strWindowName,"Capacity").dialogSelect, "isDisplayed", lngMinTimeOutInSeconds, 
				"Capacity", "DropDown", strPageName, false)){*/
		if(!dataTable.getData("Reviewers", "Capacity").isEmpty()){
			dialogSelect("Capacity", dataTable.getData("Reviewers", "Capacity"), strWindowName, strPopUpName);	
		}
		
		popupradio( dataTable.getData("Reviewers", "Review_verdict"),  strWindowName, strPopUpName);
		ALMFunctions.Screenshot();
		driver.capture(strPageName);
		dialogButton ("Complete", strWindowName, strPopUpName);
		status(dataTable.getData("Reviewers", "Status"), strPageName);
		
	}else{
		status(dataTable.getData("Reviewers", "Status"), strPageName);
		
	}
	}	


}
