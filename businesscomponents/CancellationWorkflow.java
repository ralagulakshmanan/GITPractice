package businesscomponents;

import supportlibraries.ScriptHelper;
import uimap.Common;

public class CancellationWorkflow extends CommonFunctions{

	public CancellationWorkflow(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	
	
	private final long lngMinTimeOutInSeconds = Long.parseLong(properties.getProperty("MinObjectSyncTimeout"));
	
	
	public void cancelMaterial() {
		
		String strPageName="Document Homepage";
		String strWindowName="Cancel material";
		docNumberSearch("All Documents", dataTable.getData("Business", "Document_To_Search"), "Document Homepage");
		settingIcon("Actions menu", strPageName);
		menuItem("Cancel material", strPageName);
		dialogTextArea("Cancellation Notes", "Test", strWindowName, strPageName);
		ALMFunctions.Screenshot();
		driver.capture(strPageName);
		dialogButton("Start", strWindowName, strPopUpName);
		status(dataTable.getData("Business", "Status"), strPageName);	
	}
	
	
	public void cancelWorkflow() {
		
		String strPageName="Document Homepage";
		String strWindowName="Cancel Workflow";
		String strButtonName="Continue";
		docNumberSearch("All Documents", dataTable.getData("Business", "Document_To_Search"), "Document Homepage");
		click(uimap.Common.ShowActiveWorkflow, lngMinTimeOutInSeconds, "Show Active Workflow", "Link", strPageName, true);
		click(uimap.Common.settingIconActiveWorkFlow, lngMinTimeOutInSeconds, "Settings Icon", "Link", strPageName, true);
		click(uimap.Common.CancelWorkflow, lngMinTimeOutInSeconds, "cancelWorkflow", "Link", strPageName, true);
		if (objectExists(new Common(strWindowName, "Continue").dialogbutton, "isDisplayed", lngMinTimeOutInSeconds, "Complete",
				"Button", strPageName, false)) {
			ALMFunctions.Screenshot();
			driver.capture(strPageName);
			dialogButton(strButtonName, strWindowName, strPageName);
		}
		status(dataTable.getData("Business", "Status"), strPageName);
	}

	
	
	}
	
	

