package businesscomponents;

import com.cognizant.framework.Status;

import supportlibraries.ScriptHelper;
import uimap.Common;

public class Submission extends CommonFunctions{

	public Submission(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	
	
	private final long lngMinTimeOutInSeconds = Long.parseLong(properties.getProperty("MinObjectSyncTimeout"));
	
	
	public void submissionWorkflow() throws Exception{
		
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
	public void sendToRevieworSubmission() throws Exception {

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

		} else if (dataTable.getData("Business", "Workflow").equalsIgnoreCase("Submission")) {
			strWindowName = "Send for Approval";
			
			if(!dataTable.getData("Business", "Material").isEmpty()){
				String strMaterial = dataTable.getData("Business", "Material");
				popupradio(strMaterial, "Business Owner Quality Check", "Business Owner Quality Check PopUp");
				dialogButton("Complete", "Business Owner Quality Check", "Business Owner Quality Check PopUp");
			}
			
			if (objectExists(new Common("Assess Review Outcome", "Complete").dialogbutton, "isDisplayed",
					lngMinTimeOutInSeconds, "Complete", "Button", strPageName, false)) {

				dialogButton("Complete", "Assess Review Outcome", "Assess Review Outcome PopUp");
				String strWorkflow = dataTable.getData("Business", "Start_a_workflow");
				dialogSelect("Start a workflow", strWorkflow, "Start Next Workflow", "Start Next Workflow PopUp");
				dialogButton("OK", "Start Next Workflow", "Start Next Workflow PopUp");
			} else {

				String strWorkflow = dataTable.getData("Business", "Start_a_workflow");
				dialogSelect("Start a workflow", strWorkflow, "Start Next Workflow", "Start Next Workflow PopUp");
				dialogButton("OK", "Start Next Workflow", "Start Next Workflow PopUp");
			}

		}
		if ((dataTable.getData("Business", "Workflow").toLowerCase()).equalsIgnoreCase("review")) {

			dialogTextBox("Review By", getTodayDate(), strWindowName, "Send for review popup");
		}else if((dataTable.getData("Business", "Workflow").toLowerCase()).equalsIgnoreCase("submission")){
			dialogTextBox("Approve By", getTodayDate(), strWindowName, "Send for review popup");
			
		}
	
		
			usersType(dataTable.getData("Business", "Medical_User"), strWindowName);
			usersType(dataTable.getData("Business", "Legal_User"), strWindowName);
			usersType(dataTable.getData("Business", "Regulatory_User"), strWindowName);
			usersType(dataTable.getData("Business", "Commerical_user"), strWindowName);
			usersType(dataTable.getData("Business", "Other_User"), strWindowName);
			
			if (dataTable.getData("Business", "Workflow").equalsIgnoreCase("submission")) {
				usersType(dataTable.getData("Business", "Business_User"), strWindowName);
				usersType(dataTable.getData("Business", "Submission_User"), strWindowName);
				dialogTextBox("Planned First Use Date", getTodayDate(), strWindowName, "Send for Approval popup");

			}
			ALMFunctions.Screenshot();
			driver.capture(strPageName);
		dialogButton("Start", strWindowName, strWindowName + "PopUp");
		status(dataTable.getData("Business", "Status"), strPageName);

	}
	
public void submittoHealthAuthority() throws Exception{
	FillForm("Parameter", "Input_Parameters", "SubmittoHealthAuthority", "$", true, "Gvault Home");
	
}
}
