package businesscomponents;

import com.cognizant.framework.Status;

import supportlibraries.ScriptHelper;
import uimap.Common;

public class ExaminerWorkflow extends CommonFunctions{

	public ExaminerWorkflow(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	
	private final long timeOutInSeconds = Long.parseLong(properties.getProperty("ObjectSyncTimeout"));
	private final long lngPagetimeOutInSeconds = Long.parseLong(properties.getProperty("PageLoadTimeout"));
	private final long lngMinTimeOutInSeconds = Long.parseLong(properties.getProperty("MinObjectSyncTimeout"));
	
	
	public void examinerWorkflow() throws Exception{
		
		String strWindowName=dataTable.getData("Examiners", "Window_Name");
		String strPageName=dataTable.getData("Examiners", "Page_name");
		String strPopUpName=dataTable.getData("Examiners", "Window_Name")+"PopUp";
		docNumberSearch("All Documents", dataTable.getData("Examiners", "Document_To_Search"), strPageName);
		if(objectExists(new Common("Accept").contentbutton, "isDisplayed", lngMinTimeOutInSeconds, 
				"Accept", "Button", strPageName, false)){
			contentButton("Accept", strPageName);
		}
		contentButton("Complete", strPageName);
		
		/*if(objectExists(new Common(strWindowName,"Capacity").dialogSelect, "isDisplayed", lngMinTimeOutInSeconds, 
				"Capacity", "DropDown", strPageName, false)){*/
		if(!dataTable.getData("Examiners", "Capacity").isEmpty()){
			if(objectExists(new Common(strWindowName,"Capacity").dialogSelect, "isDisplayed", lngMinTimeOutInSeconds, 
					"Capacity", "DropDown", strPageName, false)){
			dialogSelect("Capacity", dataTable.getData("Examiners", "Capacity"), strWindowName, strPopUpName);
			}else if(objectExists(new Common(strWindowName,"Capacity Examiners").dialogSelect, "isDisplayed", lngMinTimeOutInSeconds, 
					"Capacity Examiners", "DropDown", strPageName, false)){
				dialogSelect("Capacity Examiners", dataTable.getData("Examiners", "Capacity"), strWindowName, strPopUpName);	
				
			}else{
				report.updateTestLog("Check Dropdown", "No such dropdown found", Status.FAIL);
			}
			
		}
		
		popupradio(dataTable.getData("Examiners", "Review_verdict"),  strWindowName, strPopUpName);
		
		if(!dataTable.getData("Examiners", "User_Name").isEmpty()){
			
			dialogTextBox("User Name", dataTable.getData("Examiners", "User_Name"), strWindowName, strPageName);
			dialogTextBox("Password", dataTable.getData("Examiners", "Password"), strWindowName, strPageName);
		}
		ALMFunctions.Screenshot();
		driver.capture(strPageName);
		dialogButton ("Complete", strWindowName, strPopUpName);
		status(dataTable.getData("Examiners", "Status"), strPageName);
		
	}

	
	
	public void sendForExamination() throws Exception {

		strPageName = "Business Home Page";
	
		docNumberSearch("All Documents", dataTable.getData("Business", "Document_To_Search"), strPageName);
		if (objectExists(new Common("Complete").contentbutton, "isDisplayed", lngMinTimeOutInSeconds, "Complete",
				"Button", strPageName, false)) {
			contentButton("Complete", strPageName);

		}

		if ((dataTable.getData("Business", "Workflow").toLowerCase()).equalsIgnoreCase("Examination")) {
			String strMaterial = dataTable.getData("Business", "Material");
			popupradio(strMaterial, "Business Owner Quality Check", "Business Owner Quality Check PopUp");
			dialogButton("Complete","Business Owner Quality Check", "Business Owner Quality Check PopUp");

			String strWorkflow = dataTable.getData("Business", "Start_a_workflow");
			dialogSelect("Start a workflow", strWorkflow, "Start Next Workflow","Start Next Workflow PopUp");
			dialogButton("OK", "Start Next Workflow","Start Next Workflow PopUp");

		} 

		
		if ((dataTable.getData("Business", "Workflow").toLowerCase()).equalsIgnoreCase("Examination")) {
		strWindowName = "Send for Examination";
		dialogTextBox("Due Date", getTodayDate(), strWindowName, "Send for Examination popup");
		}

		usersType(dataTable.getData("Business", "Business_User"),strWindowName);
		usersType(dataTable.getData("Business", "Examiner"),strWindowName);
		ALMFunctions.Screenshot();
		driver.capture(strPageName);
		dialogButton("Start", strWindowName, strWindowName + "PopUp");
		status(dataTable.getData("Business", "Status"), strPageName);

	}

	
	
	
	
	
}
