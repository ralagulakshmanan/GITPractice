package businesscomponents;

import com.cognizant.framework.Status;

import supportlibraries.ScriptHelper;
import uimap.Common;

public class CertificationWorkflow extends CommonFunctions{

	public CertificationWorkflow(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	
	
	private final long lngMinTimeOutInSeconds = Long.parseLong(properties.getProperty("MinObjectSyncTimeout"));
	
	
	public void certificationWorkflow() throws Exception{
		
		String strWindowName=dataTable.getData("Certification", "Window_Name");
		String strPageName=dataTable.getData("Certification", "Page_name");
		String strPopUpName=dataTable.getData("Certification", "Window_Name")+"PopUp";
		docNumberSearch("All Documents", dataTable.getData("Certification", "Document_To_Search"), strPageName);
		if(objectExists(new Common("Accept").contentbutton, "isDisplayed", lngMinTimeOutInSeconds, 
				"Accept", "Button", strPageName, false)){
			contentButton("Accept", strPageName);
			
		}
		contentButton("Complete", strPageName);
		
		if(!dataTable.getData("Certification", "Capacity").isEmpty()){
			if(objectExists(new Common(strWindowName,"Capacity").dialogSelect, "isDisplayed", lngMinTimeOutInSeconds, 
					"Capacity", "DropDown", strPageName, false)){
			dialogSelect("Capacity", dataTable.getData("Certification", "Capacity"), strWindowName, strPopUpName);
			}else if(objectExists(new Common(strWindowName,"Capacity Approver").dialogSelect, "isDisplayed", lngMinTimeOutInSeconds, 
					"Capacity Approver", "DropDown", strPageName, false)){
				dialogSelect("Capacity Approver", dataTable.getData("Certification", "Capacity"), strWindowName, strPopUpName);	
				
			}else{
				
				report.updateTestLog("Check Dropdown", "No such dropdown found", Status.FAIL);
			}
			
		}
		
		popupradio(dataTable.getData("Certification", "Review_verdict"),  strWindowName, strPopUpName);
		
		if(!dataTable.getData("Certification", "User_Name").isEmpty()){
			
			dialogTextBox("User Name", dataTable.getData("Certification", "User_Name"), strWindowName, strPageName);
			dialogTextBox("Password", dataTable.getData("Certification", "Password"), strWindowName, strPageName);
		}
		
		dialogButton ("Complete", strWindowName, strPopUpName);
		status(dataTable.getData("Certification", "Status"), strPageName);
		
	}
	public void sendToReviewOrCertification() throws Exception {

		strPageName = "Business Home Page";

		docNumberSearch("All Documents", dataTable.getData("Business", "Document_To_Search"), strPageName);
		if (objectExists(new Common("Complete").contentbutton, "isDisplayed", lngMinTimeOutInSeconds, "Complete",
				"Button", strPageName, false)) {
			contentButton("Complete", strPageName);

		}

		if ((dataTable.getData("Business", "Workflow").toLowerCase()).equalsIgnoreCase("review")) {
			strWindowName = "Send for Review";
			String strMaterial = dataTable.getData("Business", "Material");
			popupradio(strMaterial, "Business Owner Quality Check", "Business Owner Quality Check PopUp");
			dialogButton("Complete", "Business Owner Quality Check", "Business Owner Quality Check PopUp");

			String strWorkflow = dataTable.getData("Business", "Start_a_workflow");
			dialogSelect("Start a workflow", strWorkflow, "Start Next Workflow", "Start Next Workflow PopUp");
			dialogButton("OK", "Start Next Workflow", "Start Next Workflow PopUp");

		} else if (dataTable.getData("Business", "Workflow").equalsIgnoreCase("Certification")) {
			strWindowName = "Send for Certification";
			if (objectExists(new Common("Assess Review Outcome", "Complete").dialogbutton, "isDisplayed",
					lngMinTimeOutInSeconds, "Complete", "Button", strPageName, false)) {

				dialogButton("Complete", "Assess Review Outcome", "Assess Review Outcome PopUp");
				String strWorkflow = dataTable.getData("Business", "Start_a_workflow");
				dialogSelect("Start a workflow", strWorkflow, "Start Next Workflow", "Start Next Workflow PopUp");
				dialogButton("OK", "Start Next Workflow", "Start Next Workflow PopUp");
			} else {

				settingIcon("Actions menu", strPageName);
				menuItem("Send for Certification", strPageName);
			}

		}
		if ((dataTable.getData("Business", "Workflow").toLowerCase()).equalsIgnoreCase("review")) {

			dialogTextBox("Review By", getTodayDate(), strWindowName, "Send for review popup");
		}

		if ((dataTable.getData("Business", "Workflow").toLowerCase()).equalsIgnoreCase("review")) {
			usersType(dataTable.getData("Business", "Medical_User"), strWindowName);
			usersType(dataTable.getData("Business", "Legal_User"), strWindowName);
			usersType(dataTable.getData("Business", "Regulatory_User"), strWindowName);
			usersType(dataTable.getData("Business", "Commerical_user"), strWindowName);
			usersType(dataTable.getData("Business", "Other_User"), strWindowName);
			usersType(dataTable.getData("Business", "PreReviewer_User"), strWindowName);
		} else {

			usersType(dataTable.getData("Business", "Medical_Signatory"), strWindowName);
			usersType(dataTable.getData("Business", "Functional_Area_Signatory"), strWindowName);
			usersType(dataTable.getData("Business", "Hard_Copy_Certifier"), strWindowName);
			
			if(!dataTable.getData("Business", "Submission_User").isEmpty()){
				usersType(dataTable.getData("Business", "Submission_User"), strWindowName);		
			}
			usersType(dataTable.getData("Business", "Business_User"), strWindowName);
			dialogTextBox("Due Date", getTodayDate(), strWindowName, strPopUpName);
		}
		
		
		ALMFunctions.Screenshot();
		driver.capture(strPageName);

		dialogButton("Start", strWindowName, strWindowName + "PopUp");
		status(dataTable.getData("Business", "Status"), strPageName);

	}
	
	
}
