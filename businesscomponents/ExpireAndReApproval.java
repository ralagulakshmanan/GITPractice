package businesscomponents;

import com.cognizant.framework.Status;

import supportlibraries.ScriptHelper;
import uimap.Common;

public class ExpireAndReApproval extends CommonFunctions {

	public ExpireAndReApproval(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	private final long lngMinTimeOutInSeconds = Long.parseLong(properties.getProperty("MinObjectSyncTimeout"));
	private final long timeOutInSeconds = Long.parseLong(properties.getProperty("ObjectSyncTimeout"));
	
	public void expireAndReapproval() throws Exception  {

		if (dataTable.getData("Expire&Re-Approve", "Workflow").equalsIgnoreCase("Expire")) 
		{
			docNumberSearch("All Documents", dataTable.getData("Expire&Re-Approve", "Document_To_Search"),
					"Business user Home page");
			settingIcon("Actions menu", "Business user Home page");
			menuItem(dataTable.getData("Expire&Re-Approve", "Type_of_Approver"), "Business user Home page");
			ALMFunctions.Screenshot();
			driver.capture(strPageName);
			dialogButton("Start", "Expire material", "Expire material popup");
			status(dataTable.getData("Expire&Re-Approve", "Status"), "Business user Home page");
		}
		else if (dataTable.getData("Expire&Re-Approve", "Workflow").equalsIgnoreCase("Reapproval")) 
		{
			docNumberSearch("All Documents", dataTable.getData("Expire&Re-Approve", "Document_To_Search"),
					"Business user Home page");
			settingIcon("Actions menu", "Business user Home page");
			menuItem(dataTable.getData("Expire&Re-Approve", "Type_of_Approver"), "Business user Home page");
			dialogTextBox("Reapprove By", getTodayDate(), "Send for Reapproval", "Send for Reapproval popup");
			usersType(dataTable.getData("Expire&Re-Approve", "Approver_User"),"Send for Reapproval");
			usersType(dataTable.getData("Expire&Re-Approve", "Business_Owner"),"Send for Reapproval");
			ALMFunctions.Screenshot();
			driver.capture(strPageName);
			dialogButton("Start", "Send for Reapproval", "Send for Reapproval Popup");
			status(dataTable.getData("Expire&Re-Approve", "Status"), "Business user Home page");

		}
	}
	public void approveForUseORInApproval() throws Exception{
		strPageName = "Business Home Page";
		docNumberSearch("All Documents", dataTable.getData("Expire&Re-Approve", "Document_To_Search"), strPageName);
		if (objectExists(new Common("Complete").contentbutton, "isDisplayed", lngMinTimeOutInSeconds, "Complete",
				"Button", strPageName, false)) {
			contentButton("Complete", strPageName);

		}
	
		popupradio("Approved for Continued Use", "Business Owner Final Check", "Business Owner Final Check Pop up");
		dialogTextBox("Expiration Date", getTodayDate(), "Business Owner Final Check",
					"Business Owner Final Check pop up");
		ALMFunctions.Screenshot();
		driver.capture(strPageName);
		dialogButton("Complete", "Business Owner Final Check", "Business Owner Final Check pop up");
		status("APPROVED FOR USE", "Status check Page");
		/*String strStatus = getText(uimap.Common.GVaulStatus, timeOutInSeconds, "Status", "Status check Page");
		 
		 switch(strStatus){
		 
		 case "APPROVED FOR USE":
			 status("APPROVED FOR USE", "Status check Page");
			break;
		 case "IN REAPPROVAL":
			 status(dataTable.getData("Expire&Re-Approve", "Status"), "Status check Page");
			break;
		default:
			report.updateTestLog("Status Check", "No such status is found in the page", Status.FAIL);
			break;*/
			
		 //}		
		
	}

}
