package businesscomponents;

import java.io.IOException;
import java.nio.channels.FileLock;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.cognizant.framework.FileLockMechanism;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;
import com.cognizant.framework.Util;

import decrypter.Password_Decrypter;
import supportlibraries.ScriptHelper;
import uimap.Common;

public class CommonFunctions extends CommonActionsAndFunctions {

	public CommonFunctions(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	private final long timeOutInSeconds = Long.parseLong(properties.getProperty("ObjectSyncTimeout"));
	public final long lngMinTimeOutInSeconds = Long.parseLong(properties.getProperty("MinObjectSyncTimeout"));
	public final long lngMaxTimeOutInSeconds = Long.parseLong(properties.getProperty("MaxObjectSyncTimeout"));
	public final long lngMinInvisibilityTimeOutInSeconds = Long
			.parseLong(properties.getProperty("MinInvisibilityTimeout"));
	public final long lngMaxInvisibilityTimeOutInSeconds = Long
			.parseLong(properties.getProperty("MaxInvisibilityTimeout"));
	private final long lngPagetimeOutInSeconds = Long.parseLong(properties.getProperty("PageLoadTimeout"));
	private final long lngTesttimeOutInSeconds = Long.parseLong(properties.getProperty("TestTimeOut"));
	public Eyes eyes;
	public EyesRunner runner = new ClassicRunner();

	/*
	 * Declaration of Variable
	 */
	String strStatusCheck = null;
	String strHeaderField = null;
	static String strDate = null;
	static String strrandomCharacter = null;
	static String strrandomNumber = null;
	String strDCCDocNumber = null;
	String strDocumentName = null;
	static String strStatus = null;
	String strWindowName = "";
	String strPageName = "";
	String strPopUpName = "";
	/*
	 * Keyword - To invoke application URL `
	 * 
	 * @param - No parameters
	 * 
	 * @return - No return
	 */

	public void launchApp1() {
		String strSheetName = "General_Data";
		String strFilePath = dataTable.datatablePath + Util.getFileSeparator() + dataTable.datatableName + ".xls";
		HSSFWorkbook wb = openExcelFile(strFilePath);
		wb.setForceFormulaRecalculation(true);
		HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
		Sheet sheet = getSheetFromXLSWorkbook(wb, strSheetName, strFilePath);
		int intColumnIndex = getColumnIndex(strFilePath, strSheetName, "TC_ID");
		int intUNColumnIndex = getColumnIndex(strFilePath, strSheetName, "User_Name");
		int intUNColumnType = getColumnIndex(strFilePath, strSheetName, "User_Type");
		ArrayList<String> listStrings = new ArrayList<String>();
		ArrayList<String> listStringExpected = new ArrayList<String>();
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			if (sheet.getRow(i) != null) {
				if (getCellValueAsString(wb, sheet.getRow(i).getCell(intColumnIndex))
						.equalsIgnoreCase(testparameters.getCurrentTestcase())) {
					listStrings.add(getCellValueAsString(wb, sheet.getRow(i).getCell(intUNColumnType)) + " : "
							+ getCellValueAsString(wb, sheet.getRow(i).getCell(intUNColumnIndex)));
					listStringExpected.add(getCellValueAsString(wb, sheet.getRow(i).getCell(intUNColumnType)) + " : "
							+ "<<" + getCellValueAsString(wb, sheet.getRow(i).getCell(intUNColumnType)) + " Name>>");
				}
			}
		}

		String strUserData = "";
		for (int i = 0; i < listStrings.size(); i++) {
			if (i == listStrings.size() - 1) {
				strUserData = strUserData + listStrings.get(i);
			} else {
				strUserData = strUserData + listStrings.get(i) + "<br>";

			}
		}
		String strUserDataExpected = "";
		for (int i = 0; i < listStringExpected.size(); i++) {
			if (i == listStringExpected.size() - 1) {
				strUserDataExpected = strUserDataExpected + listStringExpected.get(i);
			} else {
				strUserDataExpected = strUserDataExpected + listStringExpected.get(i) + "<br>";
			}
		}
		ALMFunctions.UpdateReportLogNSAndALMForPassStatus("Test Case Pre-Requesite",
				"Environment: <<Environment Name>><br>User details:<br>" + strUserDataExpected, "Environment: "
						+ dataTable.getData("General_Data", "Environment") + " <br>User details:<br>" + strUserData,
				false);

		driver.get(dataTable.getData("General_Data", "Application_URL"));
		driverUtil.waitUntilPageReadyStateComplete(lngPagetimeOutInSeconds, "Home");
		report.updateTestLog("Environment", "Test Envirnoment :" + dataTable.getData("General_Data", "Environment"),
				Status.DONE);
		report.updateTestLog("Invoke Application",
				"Invoke the application under test @ " + dataTable.getData("General_Data", "Application_URL"),
				Status.DONE);

	}

	/**
	 * 
	Method Name : launchApp
	Description    : To invoke application URL 
	Return Type  : No return value
	 */
	public void launchApp() {

		String strEnvironment = dataTable.getData("General_Data", "Environment");
		driver.get(dataTable.getData("General_Data", "Application_URL"));
		
		driverUtil.waitUntilPageReadyStateComplete(lngPagetimeOutInSeconds, "Home");
		
		report.updateTestLog("Check the environment under test", "Environment under test is: " + strEnvironment.toUpperCase() ,
				Status.DONE);
		report.updateTestLog("Launch the application under test in browser",
				"Invoke the application under test @ " + dataTable.getData("General_Data", "Application_URL"),
				Status.DONE);
		if (objectExists(Common.VeevatxtboxUserName, "isDisplayed", lngMinTimeOutInSeconds, "Login Page", "textbox",
				"Landing Page", false)) {
			ALMFunctions.UpdateReportLogAndALMForPassStatus("Verify if the application is launched",
					"User should be able to launch the application", "Application is launched and login page is displayed", true);
		} else {
			ALMFunctions.UpdateReportLogAndALMForFailStatus("Verify if the application is launched",
					"User should be able to launch the application", "Application is launched and login page is displayed", true);
		}

	}
	public void loginAndVerify1() {
		try {
			sendkeys(uimap.Common.VeevatxtboxUserName, timeOutInSeconds, dataTable.getData("General_Data", "User_Name"),
					"User Name", "Veeva Login");
			click(uimap.Common.VeevabtnContinue, timeOutInSeconds, "Continue", "Button", "Veeva Login", true);
			if (objectExists(uimap.Common.loginerror, "isDisplayed", lngMinTimeOutInSeconds, "last Login", "Label",
					"My Vault page", false)) {
				String StrLoginUserName = getText(uimap.Common.loginerror, timeOutInSeconds, "User name",
						"Veeva Login");
				ALMFunctions.ThrowException("Home", "User has to enter the user name", StrLoginUserName, true);
			}
			driverUtil.waitUntilElementEnabled(uimap.Common.VeevatxtboxPassword, lngMinTimeOutInSeconds, "Login",
					"User Name", "Loginpage");
			String strDecryptedPWD = decrypter.Password_Decrypter
					.decrypt(dataTable.getData("General_Data", "Encrypted_Password"));
			driver.findElement((uimap.Common.VeevatxtboxPassword)).sendKeys(strDecryptedPWD);
			click(uimap.Common.VeevabtnLogin, timeOutInSeconds, "Login", "Button", "Veeva Login", true);
			driverUtil.waitUntilPageReadyStateComplete(timeOutInSeconds, "My Vaults");
			if (objectExists(uimap.Common.loginerror, "isDisplayed", lngMinTimeOutInSeconds, "last Login", "Label",
					"My Vault page", false)) {
				String StrLoginUserName = getText(uimap.Common.loginerror, timeOutInSeconds, "Password", "Veeva Login");
				ALMFunctions.ThrowException("Home", "User should able to login", StrLoginUserName, true);
			}

			if (objectExists(uimap.Common.Veevathome, "isDisplayed", lngMinTimeOutInSeconds, "Home", "Label",
					"HomePage", false)) {
				ALMFunctions.UpdateReportLogAndALMForPassStatus(
						"Login to Veeva application using " + dataTable.getData("General_Data", "User_Type")
								+ " credentials.",
						dataTable.getData("General_Data", "User_Type")
								+ " should be able to login to Veeva application.",
						dataTable.getData("General_Data", "User_Name") + " is able to login to Veeva application.",
						true);
			} else {
				ALMFunctions.ThrowException("Home", "Home Page should displayed after login into application.",
						"Your login attempt has failed", true);
			}
		} catch (Exception ex) {
			report.updateTestLog("Login Error", ex.getMessage(), Status.FAIL);

			ALMFunctions.ThrowException("Login", "User should able to login into the application",
					"Error in login into the application", true);
		}
	}

	/**
	 * 
	Method Name : loginAndVerify
	Description    : To login to the IDM App
	 *       @throws InterruptedException
	Return Type  : No return value
	 */
	public void loginAndVerify() throws InterruptedException {
		String strDecryptedPWD = Password_Decrypter.decrypt(dataTable.getData("General_Data", "Encrypted_Password").trim());
		launchApp();
		
		sendkeys(uimap.Common.VeevatxtboxUserName, timeOutInSeconds, dataTable.getData("General_Data", "User_Name").trim(),
				"User Name", "IDM Login");
		click(uimap.Common.VeevabtnContinue, timeOutInSeconds, "Continue", "Button", "Veeva Login", true);
		sendkeys(uimap.Common.VeevatxtboxPassword, timeOutInSeconds, strDecryptedPWD, "Password", "IDM Login",false);
		report.updateTestLog("User should be able to enter encrypted password in the IDM Login page", "Encrypted Password is entered in the IDM Login page"
				, Status.DONE);
		//
		if (objectExists(uimap.Common.VeevabtnLogin, "isDisplayed", lngMinTimeOutInSeconds, "Login", "Button",
				"IDM Login Page", false)) {
			click(uimap.Common.VeevabtnLogin, timeOutInSeconds, "Login", "Button", "IDM Login", true);
		}

		
		if (objectExists(Common.Veevathome, "isDisplayed", lngMinTimeOutInSeconds, "Dashboard", "Label",
				"Landing Page", false)) {
			ALMFunctions.UpdateReportLogAndALMForPassStatus("Verify if the user is logged in to the application",
					"User should be able to login into the application",
					"User is logged in to the application", true);
		} else {
			ALMFunctions.UpdateReportLogAndALMForFailStatus("Verify if the user is logged in to the application",
					"User should be able to login into the application",
					"User isn't logged in to the application", true);
		}

	}
	
	/**
	 * 
	Method Name : waitUntilLoading
	Description    : To wait until the page is loaded
	 *       @param strPageName
	Return Type  : No return value
	 */
		public void waitUntilLoading(String strPageName) {
			//driverUtil.waitUntilElementInVisiblity(Common.Veevathome, "Loading Screen", "Img", strPageName);
		}
		
	public void environmentSelection() throws Exception {
		driverUtil.waitUntilElementEnabled(uimap.Common.LastLogin, timeOutInSeconds, "last Login", "Label",
				"My Vault page");
		environmentSelection(dataTable.getData("General_Data", "Environment"));
		driverUtil.waitUntilElementEnabled(uimap.Common.Veevathome, timeOutInSeconds, "Home", "Label", "HomePage");
	}

	public void errorHandling() {
		if (objectExists(uimap.Common.error, "isDisplayed", lngMinTimeOutInSeconds, "Error", "Required Field", "",
				false)) {
			List<WebElement> errorvalue = driver.getWebDriver().findElements(uimap.Common.error);
			List<String> listStrings = new ArrayList<String>();
			for (int i = 0; i < errorvalue.size(); i++) {
				listStrings.add(errorvalue.get(i).getText());
			}
			ALMFunctions.ThrowException("Error Message", "All Madatory field should be entered before save.",
					"The below mandatory fields are not entered by the user " + listStrings, true);
		} else {
			ALMFunctions.ThrowException("Error", "Form should be saved",
					"Error - in determining whether form is saved " + "or any error message is displayed", true);
		}
	}

	
	
	public void loginAsCoOrdinatorAndUploadDoc1() throws Exception {

		FillForm("Parameter", "Input_Parameters", "LoginAsCoOrdinator", "$", true, "Veeva Home");
		strDocumentName = getAttribute(uimap.Common.VeevaDoctext, timeOutInSeconds, "textContent", "Document Number",
				"Veeva UploadPage");
		String strDocName = StringUtils.substringBefore(strDocumentName, "(").trim();
		ALMFunctions.UpdateReportLogAndALMForPassStatus("Verify the creation of new document",
				"System should create a new document with DRAFT status.",
				"Document : " + strDocumentName + " is created with " + strStatus + " status.", true);
		dataTable.putData("Workflow", "Document_To_Search", strDocName);

		if (!dataTable.getData("Workflow", "Scenario_Type").isEmpty()) {
			switch (dataTable.getData("Workflow", "Scenario_Type").toLowerCase()) {
			case "agency":
				sendToContentCreator();
				break;
			case "manual":
				sendToBusinessOwner1();
				break;
			default:
				// report.updateTestLog(stepName, stepDescription, stepStatus);
				break;

			}
		}

	}

	public void sendToContentCreator() {
		strWindowName = "Send to Content Creator";
		strPageName = "Upload File(2) Page";
		strPopUpName = "Send to Content Creator PopUP";
		try {
			settingIcon("Actions menu", strPageName);
			menuItem("Send to Content Creator", strPageName);
			String strAssignTo = dataTable.getData("Workflow", "Assign_To_Content");
			String[] strSplit = strAssignTo.split("!");
			dropdown(strSplit[0], strSplit[1], strWindowName, strPageName);

			dialogTextBox("Due Date", getTodayDate(), strWindowName, strPopUpName);
			ALMFunctions.Screenshot();
			driver.capture(strPageName);
			dialogButton("Start", strWindowName, strPopUpName);
			status("DRAFT", strPageName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendToBusinessOwner1() {
		strWindowName = "Send to Business Owner";
		strPageName = "Veeva document Page";
		strPopUpName = "Send to Business Owner PopUP";

		try {

			settingIcon("Actions menu", strPageName);
			menuItem(dataTable.getData("Workflow", "Type_of_Approver"), strPageName);
			String strAssignTo = dataTable.getData("Workflow", "Assign_To_business");
			String[] strSplit = strAssignTo.split("!");
			dropdown(strSplit[0], strSplit[1], strWindowName, strPopUpName);
			String strAssignTo2 = dataTable.getData("Workflow", "Assign_To_Coordinator");
			String[] strSplit2 = strAssignTo2.split("!");
			dropdown(strSplit2[0], strSplit2[1], strWindowName, strPopUpName);
			dialogTextBox("Due Date", getTodayDate(), strWindowName, strPopUpName);
			ALMFunctions.Screenshot();
			driver.capture(strPageName);
			dialogButton("Start", strWindowName, strPopUpName);
			status(dataTable.getData("Workflow", "Status"), strPageName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void agencyDocUpload() {
		strWindowName = "Upload Content";
		strPageName = "Agency Home page";
		strPopUpName = "Upload Content PopUP";
		docSearch("All Documents", dataTable.getData("Workflow", "Document_To_Search"), strPageName);
		selectFileUpload(dataTable.getData("Workflow", "Document_upload"), strPageName);
		contentButton("Complete", strPageName);
		dialogButton("Complete", strWindowName, strPopUpName);
		status("DRAFT", strPageName);
	}

	public void agencySendToBusinessOwner() throws Exception {
		String strWindowName = "Send to Business Owner";
		String strPageName = "Veeva Vault - Home - My Tasks";
		String strPopUpName = "Send to Business Owner PopUP";
		docSearch("All Documents", dataTable.getData("Workflow", "Document_To_Search"), strPageName);
		contentButton("Complete", strPageName);
		dialogButton("Complete", "Content Uploaded", "Content Uploaded Pop up");
		dialogSelect("Start a workflow", dataTable.getData("Workflow", "Type_of_Approver"), "Start Next Workflow",
				"Start Next Workflow PopUp");
		dialogButton("OK", "Start Next Workflow", "Start Next Workflow PopUp");

		String strAssignTo = dataTable.getData("Workflow", "Assign_To_business");
		String[] strSplit = strAssignTo.split("!");
		dropdown(strSplit[0], strSplit[1], strWindowName, strPopUpName);
		String strAssignTo2 = dataTable.getData("Workflow", "Assign_To_Coordinator");
		String[] strSplit2 = strAssignTo2.split("!");
		dropdown(strSplit2[0], strSplit2[1], strWindowName, strPopUpName);
		dialogTextBox("Due Date", getTodayDate(), strWindowName, strPopUpName);
		ALMFunctions.Screenshot();
		driver.capture(strPageName);
		dialogButton("Start", strWindowName, strPopUpName);
		status(dataTable.getData("Workflow", "Status"), strPageName);
	}

	public void sendToReviewOrApprove() throws Exception {

		strPageName = "Business Home Page";

		docNumberSearch("All Documents", dataTable.getData("Business", "Document_To_Search"), strPageName);
		if (objectExists(new Common("Complete").contentbutton, "isDisplayed", lngMinTimeOutInSeconds, "Complete",
				"Button", strPageName, false)) {
			contentButton("Complete", strPageName);

		}

		if ((dataTable.getData("Business", "Workflow").toLowerCase()).equalsIgnoreCase("review")) {
			String strMaterial = dataTable.getData("Business", "Material");
			popupradio(strMaterial, "Business Owner Quality Check", "Business Owner Quality Check PopUp");
			dialogButton("Complete", "Business Owner Quality Check", "Business Owner Quality Check PopUp");

			String strWorkflow = dataTable.getData("Business", "Start_a_workflow");
			dialogSelect("Start a workflow", strWorkflow, "Start Next Workflow", "Start Next Workflow PopUp");
			dialogButton("OK", "Start Next Workflow", "Start Next Workflow PopUp");

		} else if (dataTable.getData("Business", "Workflow").equalsIgnoreCase("approval")) {
			if (objectExists(new Common("Assess Review Outcome", "Complete").dialogbutton, "isDisplayed",
					lngMinTimeOutInSeconds, "Complete", "Button", strPageName, false)) {

				dialogButton("Complete", "Assess Review Outcome", "Assess Review Outcome PopUp");
				String strWorkflow = dataTable.getData("Business", "Start_a_workflow");
				dialogSelect("Start a workflow", strWorkflow, "Start Next Workflow", "Start Next Workflow PopUp");
				dialogButton("OK", "Start Next Workflow", "Start Next Workflow PopUp");
			} else if (objectExists(
					new Common("Business Owner Quality Check", "Ready for Review / Approval").popupradio, "isDisplayed",
					lngMinTimeOutInSeconds, "Ready for Review / Approval", "Radio", strPageName, false)) {
				String strMaterial = dataTable.getData("Business", "Material");
				popupradio(strMaterial, "Business Owner Quality Check", "Business Owner Quality Check PopUp");
				dialogButton("Complete", "Business Owner Quality Check", "Business Owner Quality Check PopUp");

				String strWorkflow = dataTable.getData("Business", "Start_a_workflow");
				dialogSelect("Start a workflow", strWorkflow, "Start Next Workflow", "Start Next Workflow PopUp");
				dialogButton("OK", "Start Next Workflow", "Start Next Workflow PopUp");
			}

			else {

				settingIcon("Actions menu", strPageName);
				if (objectExists(new Common("Send for Approval").menuitem, "isDisplayed", lngMinTimeOutInSeconds,
						"Send for Approval", "Menu", strPageName, false)) {
					menuItem("Send for Approval", strPageName);
				} else {
					menuItem("Send for Non-Final Item Approval", strPageName);
				}
			}

		}
		if ((dataTable.getData("Business", "Workflow").toLowerCase()).equalsIgnoreCase("review")) {
			strWindowName = "Send for Review";
			dialogTextBox("Review By", getTodayDate(), strWindowName, "Send for review popup");
		} else if ((dataTable.getData("Business", "Workflow").toLowerCase()).equalsIgnoreCase("approval")) {
			strWindowName = "Send for Approval";
			dialogTextBox("Approve By", getTodayDate(), strWindowName, "Send for Approval popup");
		}

		usersType(dataTable.getData("Business", "Medical_User"), strWindowName);
		usersType(dataTable.getData("Business", "Legal_User"), strWindowName);
		usersType(dataTable.getData("Business", "Regulatory_User"), strWindowName);
		usersType(dataTable.getData("Business", "Commerical_user"), strWindowName);
		usersType(dataTable.getData("Business", "Other_User"), strWindowName);

		if (dataTable.getData("Business", "Workflow").equalsIgnoreCase("review")) {

			if (!dataTable.getData("Business", "PreReview_User").isEmpty()) {
				usersType(dataTable.getData("Business", "PreReview_User"), strWindowName);
			}
		}

		if (dataTable.getData("Business", "Workflow").equals("Approval")) {
			usersType(dataTable.getData("Business", "Business_User"), strWindowName);
			dialogTextBox("Planned First Use Date", getTodayDate(), strWindowName, "Send for Approval popup");

		}
		ALMFunctions.UpdateReportLogAndALMForPassStatus("Enter users details",
				"User should able to enter the mandatory fields", "User is eneterd the mandatory fields", true);
		ALMFunctions.Screenshot();
		driver.capture(strPageName);
		dialogButton("Start", strWindowName, strWindowName + "PopUp");
		status(dataTable.getData("Business", "Status"), strPageName);

	}

	public void usersType(String strValue, String strWindowName) {
		String strlabel = "";
		String strValues = "";
		String[] strTypeofUser = strValue.split(";");
		for (int i = 0; i < strTypeofUser.length; i++) {

			String[] strInputs = strTypeofUser[i].split("!");
			for (int j = 0; j < strInputs.length; j++) {
				switch (StringUtils.substringBefore(strInputs[j], "=").toLowerCase()) {

				case "element label":
					strlabel = StringUtils.substringAfter(strInputs[j], "=");
					break;

				case "element value":
					strValues = StringUtils.substringAfter(strInputs[j], "=");
					break;

				default:
					ALMFunctions.ThrowException("Test Data", "Test Data must be provided in the pre-defined format",
							"Unhandled case " + StringUtils.substringBefore(strInputs[j], "="), false);
					break;
				}

			}
			if (strlabel.trim().length() > 0) {
				dropdown(strlabel, strValues, strWindowName, strWindowName + "PopUp");
			}

		}

	}

	public void reviewComplete() {

		try {
			strPageName = "Business page";
			docNumberSearch("All Documents", dataTable.getData("Reviewers", "Document_To_Search"), strPageName);
			contentButton("Complete", strPageName);
			dialogButton("Complete", "Assess Review Outcome", "Assess Review Outcome PopUp");

			dialogSelect("Start a workflow", "Send to Business Owner", "Start Next Workflow",
					"Start Next Workflow PopUp");
			dialogButton("OK", "Start Next Workflow", "Start Next Workflow PopUp");

			usersType(dataTable.getData("Reviewers", "Business_User"), "Send to Business Owner");
			dialogTextBox("Due Date", getTodayDate(), "Send to Business Owner", "Send to Business Owner popup");

			dialogButton("Start", "Send to Business Owner", "Send to Business Owner PopUp");
			status("IN BUSINESS OWNER REVIEW", strPageName);
			contentButton("Complete", strPageName);

			popupradio("Ready for Approval", "Business Owner Quality Check", "Business Owner Quality Check Popup");
			ALMFunctions.Screenshot();
			driver.capture(strPageName);
			dialogButton("Complete", "Business Owner Quality Check", "Business Owner Quality Check Popup");
			status("REVIEW COMPLETE", strPageName);

			// FillForm("Parameter", "Input_Parameters", "Review Complete", "$",
			// true, "Review Complete");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void approveComplete() {

		try {
			strPageName = "Business page";
			docNumberSearch("All Documents", dataTable.getData("Approvers", "Document_To_Search"), strPageName);
			contentButton("Complete", strPageName);
			dialogButton("Complete", "Assess Outcome", "Assess Outcome PopUp");
			dialogSelect("Start a workflow", "Send to Business Owner Final Check", "Start Next Workflow",
					"Start Next Workflow PopUp");
			dialogButton("OK", "Start Next Workflow", "Start Next Workflow PopUp");
			usersType(dataTable.getData("Approvers", "Business_User"), "Send to Business Owner");
			dialogTextBox("Due Date", getTodayDate(), "Send to Business Owner", "Send to Business Owner popup");
			dialogTextBox("Actual Date of First Use", getTodayDate(), "Send to Business Owner",
					"Send to Business Owner popup");
			ALMFunctions.Screenshot();
			driver.capture(strPageName);
			dialogButton("Start", "Send to Business Owner", "Send to Business Owner PopUp");
			status("APPROVED WITH MINOR CHANGES", strPageName);

			// FillForm("Parameter", "Input_Parameters", "Approve Complete",
			// "$", true, "Review Complete");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void busSubmissionApprove() throws Exception {
		strPageName = "Business Home Page";
		docNumberSearch("All Documents", dataTable.getData("Business", "Document_To_Search"), strPageName);
		contentButton("Complete", strPageName);

		dialogTextBox("Planned First Use Date", getTodayDate(), "Business Owner Final Check Task",
				"Business Owner Final Check pop up");
		dialogTextBox("Actual Date of First Use", getTodayDate(), "Business Owner Final Check Task",
				"Business Owner Final Check pop up");
		ALMFunctions.Screenshot();
		driver.capture(strPageName);
		dialogButton("Complete", "Business Owner Final Check Task", "Business Owner Final Check pop up");

		status("AWAITING RESPONSE FROM HEALTH AUTHORITY", strPageName);
	}

	public void businessComplete() throws Exception {

		strPageName = "Business Home Page";
		docNumberSearch("All Documents", dataTable.getData("Business", "Document_To_Search"), strPageName);
		if (objectExists(new Common("Complete").contentbutton, "isDisplayed", lngMinTimeOutInSeconds, "Complete",
				"Button", strPageName, false)) {
			contentButton("Complete", strPageName);

		}

		if (dataTable.getData("Business", "Workflow").equalsIgnoreCase("submission")) {
			if (objectExists(new Common("Business Owner Final Check", "Approved for Use").popupradio, "isDisplayed",
					lngMinTimeOutInSeconds, "Approved for Use", "Radio Button", strPageName, false)) {
				popupradio("Approved for Use", "Business Owner Final Check", "Business Owner Final Check Pop up");

			}

			dialogTextBox("Actual Date of First Use", getTodayDate(), "Business Owner Final Check",
					"Business Owner Final Check pop up");
			ALMFunctions.Screenshot();
			driver.capture(strPageName);
			dialogButton("Complete", "Business Owner Final Check", "Business Owner Final Check pop up");
			if (dataTable.getData("Business", "Workflow").equalsIgnoreCase("submission")) {
				status("APPROVAL COMPLETE", strPageName);
			} else {
				// status("APPROVED FOR USE", strPageName);
			}

		}
	}

	public void preReviewComplete() {
		strPageName = "Pre-Review Home Page";
		docNumberSearch("All Documents", dataTable.getData("Business", "Document_To_Search"), strPageName);
		if (objectExists(new Common("Complete").contentbutton, "isDisplayed", lngMinTimeOutInSeconds, "Complete",
				"Button", strPageName, false)) {
			contentButton("Complete", strPageName);

		}
		ALMFunctions.Screenshot();
		driver.capture(strPageName);
		dialogButton("Complete", "Pre-Review", "Review pop up");
		status("IN REVIEW", strPageName);

	}

	public void approveForUse() throws Exception {

		strPageName = "Business Home Page";
		docNumberSearch("All Documents", dataTable.getData("Business", "Document_To_Search"), strPageName);
		if (objectExists(new Common("Complete").contentbutton, "isDisplayed", lngMinTimeOutInSeconds, "Complete",
				"Button", strPageName, false)) {
			contentButton("Complete", strPageName);

		}

		if (dataTable.getData("Business", "Workflow").equalsIgnoreCase("approval")
				|| dataTable.getData("Business", "Workflow").equalsIgnoreCase("submission")) {
			if (objectExists(new Common("Business Owner Final Check", "Approved for Use").popupradio, "isDisplayed",
					lngMinTimeOutInSeconds, "Approved for Use", "Radio Button", strPageName, false)) {
				popupradio("Approved for Use", "Business Owner Final Check", "Business Owner Final Check Pop up");

			}

			dialogTextBox("Actual Date of First Use", getTodayDate(), "Business Owner Final Check",
					"Business Owner Final Check pop up");
			ALMFunctions.Screenshot();
			driver.capture(strPageName);
			dialogButton("Complete", "Business Owner Final Check", "Business Owner Final Check pop up");
			//dialogButton("Complete", "Print Task", "Print Task Check pop up");
			if (dataTable.getData("Business", "Workflow").equalsIgnoreCase("submission")) {
				status("APPROVAL COMPLETE", strPageName);
			} else {
				status("APPROVED FOR USE", strPageName);
			}

		}

		if (dataTable.getData("Business", "Workflow").equalsIgnoreCase("certification")) {

			if (objectExists(new Common("Print Sample Material", "Complete").dialogbutton, "isDisplayed",
					lngMinTimeOutInSeconds, "Complete", "Button", "Print Sample Material pop up", false)) {
				dialogButton("Complete", "Print Sample Material", "Print Sample Material pop up");
				status("CERTIFIED FOR PRODUCTION", strPageName);
				if (objectExists(new Common("Complete").contentbutton, "isDisplayed", lngMinTimeOutInSeconds,
						"Complete", "Button", strPageName, false)) {
					contentButton("Complete", strPageName);
				}
			}

		/*	dialogTextBox("Planned First Use Date", getTodayDate(), "Business Owner Final Check Task",
					"Business Owner Final Check pop up");
			dialogTextBox("Actual Date of First Use", getTodayDate(), "Business Owner Final Check Task",
					"Business Owner Final Check pop up");*/
			ALMFunctions.Screenshot();
			driver.capture(strPageName);
			//dialogButton("Complete", "Business Owner Final Check Task", "Business Owner Final Check pop up");
			dialogButton("Complete", "Print Task", "Print Task Check pop up");
			status("CERTIFIED FOR PRODUCTION", strPageName);

		}

		if (dataTable.getData("Business", "Workflow").equalsIgnoreCase("Examination")) {

			dialogTextBox("Planned First Use Date", getTodayDate(), "Business Owner Final Check",
					"Business Owner Final Check pop up");
			dialogTextBox("Actual Date of First Use", getTodayDate(), "Business Owner Final Check",
					"Business Owner Final Check pop up");
			ALMFunctions.Screenshot();
			driver.capture(strPageName);
			dialogButton("Complete", "Business Owner Final Check", "Business Owner Final Check pop up");
			//dialogButton("Complete", "Print Task", "Print Task Check pop up");
			status("APPROVED FOR USE", strPageName);
		}

	}

	public void fieldValidation() throws Exception {
		FillForm("Parameter", "Input_Parameters", "Field Validation", "$", true, "Business");

	}

	public void subtypeDropdownValidation(String strLabel, String strValue, String strWindowName, String strPageName) {
		String strListValues = "";
		boolean flag = false;
		WebElement eleSelect = driver.findElement(new Common(strWindowName, strLabel).dialogSelect);
		Select selectValue = new Select(eleSelect);
		List<WebElement> listSubtype = selectValue.getOptions();

		String[] subtypeList = strValue.split(":");

		for (int i = 1; i < listSubtype.size(); i++) {

			strListValues = listSubtype.get(i).getText();
			for (int j = 0; j < subtypeList.length; j++) {

				if (strListValues.equals(subtypeList[j])) {
					ALMFunctions.UpdateReportLogAndALMForPassStatus("Data validation for " + strLabel + " dropdown",
							"Expected " + strListValues, "Actual :" + subtypeList[j], true);
					flag = true;
					break;

				}

			}
			if (!flag) {
				ALMFunctions.UpdateReportLogAndALMForFailStatus("Dropdown Data validation for " + strLabel,
						"Expected " + strListValues, "No such value found in dropdown", true);
			}

		}

	}

	public void logout() {
		click(uimap.Common.userMenu, timeOutInSeconds, "User menu button", "Button", "Veeva Home Page", true);
		driverUtil.waitUntilAjaxLoadingComplete(lngPagetimeOutInSeconds, "logout Page");
		click(uimap.Common.btnLogout, timeOutInSeconds, "Logout button", "Button", "Veeva Home Page", true);
		ALMFunctions.UpdateReportLogAndALMForPassStatus("Logout the Veeva application",
				dataTable.getData("General_Data", "User_Type") + " should be able to logout the application",
				dataTable.getData("General_Data", "User_Name") + " is able logout the application", true);
		click(uimap.Common.switchUser, timeOutInSeconds, "Switch user", "Button", "Veeva user page", true);
	}

	public void exit() {

		click(uimap.Common.userMenu, timeOutInSeconds, "User menu button", "Button", "Veeva - All Documents", true);
		String strExit = getText(uimap.Common.loginUserName, timeOutInSeconds, "DocCtrlApproval",
				"DocCtrlApproval Home Page");
		click(uimap.Common.btnLogout, timeOutInSeconds, "Logout button", "Button", "Veeva - All Documents", true);

		ALMFunctions.UpdateReportLogAndALMForPassStatus("Logout the application after successful completeion",
				"User should be able to logout the application", strExit + " is able logout the application", true);
		click(uimap.Common.switchUser, timeOutInSeconds, "Switch user", "Button", "Veeva Vault", true);
	}

	public void metaDataValidation(String strvalue, String strPagename) {
		String strSection = "";
		String strlabel = "";
		String strSecuritytype = "";
		String strFieldType = "";

		String[] splitinputData = strvalue.split("!");

		for (int i = 0; i < splitinputData.length; i++) {

			if (splitinputData[i].trim().length() > 0) {

				String[] strInputs = splitinputData[i].split(":");
				for (int j = 0; j < strInputs.length; j++) {

					switch (StringUtils.substringBefore(strInputs[j], "~").toLowerCase()) {

					case "section name":
						strSection = StringUtils.substringAfter(strInputs[j], "~");
						break;
					case "label":
						strlabel = StringUtils.substringAfter(strInputs[j], "~");
						break;
					case "security type":
						strSecuritytype = StringUtils.substringAfter(strInputs[j], "~");
						break;
					case "field type":
						strFieldType = StringUtils.substringAfter(strInputs[j], "~");
						break;

					default:
						ALMFunctions.ThrowException("Test Data", "Test Data must be provided in the pre-defined format",
								"Unhandled case " + StringUtils.substringBefore(strInputs[j], "~"), false);
					}

				}

			}

			if (strlabel.trim().length() > 0) {
				switch (strSecuritytype.trim().toLowerCase()) {
				case "required":
					requiredfield(strSection, strlabel, strSecuritytype, strFieldType, strPagename);

					break;
				case "optional":
					optional(strSection, strlabel, strSecuritytype, strFieldType, strPagename);
					break;

				case "read only":
					readonly(strSection, strlabel, strSecuritytype, strFieldType, strPagename);
					break;

				}

			}
		}

	}

	public void requiredfield(String strSection, String strlabel, String strSecuritytype, String strFieldType,
			String strPageName) {
		// String strPageName="Upload File Step(2) page";
		treeView(strSection, strPageName);
		switch (strFieldType.toLowerCase()) {

		case "freetext":

			String strFreetext = getAttribute(new Common(strlabel).textarea, timeOutInSeconds, "className", strlabel,
					strPageName);
			if (strFreetext.contains("required")) {

				report.updateTestLog("Validate the " + strFieldType + " field is required",
						strlabel + " is an required field", Status.PASS);
			} else {
				report.updateTestLog("Validate the " + strFieldType + " field is non-mandatory",
						strlabel + " is an non-mandatory field", Status.FAIL);

			}
			break;
		case "dropdown":
			String strDropdown = getAttribute(new Common(strlabel).reqDropDown, timeOutInSeconds, "className", strlabel,
					strPageName);
			if (strDropdown.contains("required")) {

				report.updateTestLog("Validate the " + strFieldType + " field is required",
						strlabel + " is an required field", Status.PASS);
			} else {
				report.updateTestLog("Validate the " + strFieldType + " field is non-mandatory",
						strlabel + " is an non-mandatory field", Status.FAIL);

			}
			break;
		case "lookup":
			String strLookUp = getAttribute(new Common(strlabel).reqlookupicon, timeOutInSeconds, "className", strlabel,
					strPageName);
			if (strLookUp.contains("required")) {

				report.updateTestLog("Validate the " + strFieldType + " field is required",
						strlabel + " is an required field", Status.PASS);
			} else {
				report.updateTestLog("Validate the " + strFieldType + " field is non-mandatory",
						strlabel + " is an non-mandatory field", Status.FAIL);

			}
			break;

		case "textbox":
			String strTextBox = getAttribute(new Common(strlabel).textbox, timeOutInSeconds, "className", strlabel,
					strPageName);
			if (strTextBox.contains("required")) {

				report.updateTestLog("Validate the " + strFieldType + " field is required",
						strlabel + " is an required field", Status.PASS);
			} else {
				report.updateTestLog("Validate the " + strFieldType + " field is non-mandatory",
						strlabel + " is an non-mandatory field", Status.FAIL);

			}
			break;
		case "radio":
			String strRadio = getAttribute(new Common(strlabel).radiobutton, timeOutInSeconds, "className", strlabel,
					strPageName);
			if (strRadio.contains("required")) {

				report.updateTestLog("Validate the " + strFieldType + " field is required",
						strlabel + " is an required field", Status.PASS);
			} else {
				report.updateTestLog("Validate the " + strFieldType + " field is non-mandatory",
						strlabel + " is an non-mandatory field", Status.FAIL);

			}
			break;

		default:
			report.updateTestLog("Validate the " + strFieldType + " field is mandatory",
					"No such data found in the page: " + strlabel, Status.FAIL);

		}

	}

	public void readonly(String strSection, String strlabel, String strSecuritytype, String strFieldType,
			String strPageName) {
		treeView(strSection, strPageName);
		switch (strFieldType.toLowerCase()) {

		case "label":
			if (objectExists(new Common(strlabel).readonly2, "isDisplayed", lngMinTimeOutInSeconds, strlabel,
					strFieldType, strPageName, false)) {
				String strReadOnly = driver.getWebDriver().findElement(new Common(strlabel).readonly2)
						.getAttribute("tagName");
				if (strReadOnly.contains("LABEL")) {

					report.updateTestLog("Validate the " + strFieldType + " field is read only",
							strlabel + " is an read only field", Status.PASS);
				} else {
					report.updateTestLog("Validate the " + strFieldType + " field is editbale",
							strlabel + " is an editbale field", Status.FAIL);

				}

			} else {
				String strReadOnly = driver.getWebDriver().findElement(new Common(strlabel).readonly)
						.getAttribute("type");
				if (strReadOnly.contains("hidden")) {

					report.updateTestLog("Validate the " + strFieldType + " field is read only",
							strlabel + " is an read only field", Status.PASS);
				} else {
					report.updateTestLog("Validate the " + strFieldType + " field is editbale",
							strlabel + " is an editbale field", Status.FAIL);

				}
			}
			break;
		default:
			report.updateTestLog("Validate the " + strFieldType + " field is Read Only",
					"No such data found in the page: " + strlabel, Status.FAIL);

		}

	}

	public void optional(String strSection, String strlabel, String strSecuritytype, String strFieldType,
			String strPageName) {
		treeView(strSection, strPageName);
		switch (strFieldType.toLowerCase()) {

		case "textbox":
			String strTextBox = getAttribute(new Common(strlabel).textbox, timeOutInSeconds, "className", strlabel,
					strPageName);
			if (strTextBox.contains("editMode")) {

				report.updateTestLog("Validate the " + strFieldType + " field is optional",
						strlabel + " is an optional field", Status.PASS);
			} else {
				report.updateTestLog("Validate the " + strFieldType + " field is a mandatory or ready only",
						strlabel + " is an mandatory or read only field", Status.FAIL);

			}
			break;

		case "dropdown":
			String strDropdown = getAttribute(new Common(strlabel).reqDropDown, timeOutInSeconds, "className", strlabel,
					strPageName);
			if (strDropdown.contains("editMode")) {

				report.updateTestLog("Validate the " + strFieldType + " field is optional",
						strlabel + " is an required field", Status.PASS);
			} else {
				report.updateTestLog("Validate the " + strFieldType + " field is mandatory or read only field",
						strlabel + " is an mandatory or read only field", Status.FAIL);

			}
			break;
		case "radio":
			String strRadio = getAttribute(new Common(strlabel).radiobutton, timeOutInSeconds, "className", strlabel,
					strPageName);
			if (strRadio.contains("editMode")) {

				report.updateTestLog("Validate the " + strFieldType + " field is optional",
						strlabel + " is an required field", Status.PASS);
			} else {
				report.updateTestLog("Validate the " + strFieldType + " field is mandatory or read only field",
						strlabel + " is an mandatory or read only field", Status.FAIL);

			}
			break;

		default:
			report.updateTestLog("Validate the " + strFieldType + " field is optional",
					"No such data found in the page: " + strlabel, Status.FAIL);
		}
	}

	public void status(String strValue, String strPageName) {
		if (objectExists(uimap.Common.error, "isDisplayed", lngMinTimeOutInSeconds, "Error", "Required Field",
				strPageName, false)) {
			errorHandling();
		} else {
			if (objectExists(uimap.Common.lblStatus, "isDisplayed", timeOutInSeconds, "Status", "status label",
					"Status popup", false)) {
				driverUtil.waitUntilElementInVisible(uimap.Common.lblStatus, "Status", "status label",
						"Status popup");
			}

			if (objectExists(uimap.Common.VeevaStatus, "isDisplayed", timeOutInSeconds, strValue, "Status label",
					strPageName, false)) {
				strStatus = getText(uimap.Common.VeevaStatus, timeOutInSeconds, "Status", "Status check Page");
				if ((strValue.toUpperCase()).equals(strStatus)) {
					ALMFunctions.UpdateReportLogAndALMForPassStatus("Verify the status",
							"Status should be dispayed as " + strValue, "Status is dispayed as " + strStatus, true);
				} else {
					ALMFunctions.ThrowException("Verify the status",
							"Status should be dispayed as " + strStatus + " in " + strPageName,
							"Status is not displayed as expected in the " + strPageName + "<br>Expected: " + strValue
									+ " <br>Actual: " + strStatus,
							true);
				}
			} else {
				ALMFunctions.ThrowException("Status", "Status should be dispayed as expected in " + strPageName,
						"Status is not displayed as expected in the " + strPageName + "<br>Expected: " + strValue
								+ " <br>Actual: " + strStatus,
						true);
			}
		}
	}

	public void addInputToDcc(String strValue, String strPageName) throws Exception {
		boolean blnflag = false;
		driverUtil.waitUntilElementVisible(uimap.Common.Inputbox, timeOutInSeconds, "Search document", "inputbox",
				"DCC POPUP Page", false);
		sendkeys(uimap.Common.Inputbox, timeOutInSeconds, dataTable.getData("Workflow", "Document_To_Search"),
				"Inputbox", "DCC POPUP Page");
		clickByJS(uimap.Common.search, timeOutInSeconds, "Search", "button", "DCC POPUP Page", true);
		do {
			driverUtil.waitUntilElementEnabled(uimap.Common.Addbutton, timeOutInSeconds, "Add", "Button",
					"DCC POPUP Page");
			click(uimap.Common.Addbutton, timeOutInSeconds, "Add", "Button", "DCC POPUP Page", true);
			if (driverUtil.waitUntilElementEnabled(uimap.Common.VeevaDCC_Removeicon, timeOutInSeconds, "Remove",
					"button", "DCC POPUP Page")) {
				blnflag = true;
			}
		} while (!blnflag);
		ALMFunctions.Screenshot();
		click(uimap.Common.close, timeOutInSeconds, "Close", "Button", "DCC POPUP Page", false);
		driverUtil.waitUntilElementInVisible(uimap.Common.close, "Close", "Button", "DCC POPUP Page");
		driverUtil.waitUntilElementVisible(uimap.Common.VeevaDCC_Settingicon, timeOutInSeconds, "Settings", "Button",
				"Dcc home page", false);
		click(uimap.Common.VeevaDCC_Settingicon, timeOutInSeconds, "Settings", "Button", "Dcc home page", true);

	}
	
	
	public void lookupEnterValue(String strLabel, String strValue, String strPageName) {
		lookupIcon(strLabel, strPageName);
		sendkeys(uimap.Common.Inputbox, timeOutInSeconds, strValue, strLabel, strPageName);
		click(uimap.Common.search, timeOutInSeconds, "Textbox", "Button", strValue, true);
		click(uimap.Common.Addbutton, timeOutInSeconds, "Add Icon", "Button", strValue, true);
		if (objectExists(uimap.Common.close, "isDisplayed", lngMinTimeOutInSeconds, "close", "Button", strValue,
				false)) {
			click(uimap.Common.close, timeOutInSeconds, "close", "Button", strValue, true);
		}
	}

	public void selectFileToImport(String strFileName, String strPageName) {

		/*
		 * mouseOverandClick(Common.GVaulChoose, timeOutInSeconds, "Choose",
		 * "button", strPageName); String strBaseDir =
		 * System.getProperty("user.dir") + Util.getFileSeparator(); String
		 * strWindowName = ""; switch (testparameters.getBrowser().getValue()) {
		 * case "chrome": strWindowName = "Open"; break; default: break; }
		 * executeScript(true, strBaseDir + "autoIT" + Util.getFileSeparator() +
		 * "FileSelect.exe", strWindowName, (strBaseDir + "testDataFiles" +
		 * Util.getFileSeparator() + strFileName).toLowerCase());
		 */

		String strBaseDir = System.getProperty("user.dir") + Util.getFileSeparator();
		driver.findElement(By.xpath("//input[@name='inbox_uploaded_files']"))
				.sendKeys(strBaseDir + "testDataFiles" + Util.getFileSeparator() + strFileName);

	}

	public void selectFileUpload(String strFileName, String strPageName) {
		String strBaseDir = System.getProperty("user.dir") + Util.getFileSeparator();
		click(Common.fileupload, timeOutInSeconds, "File Upload", "button", strPageName, true);
		mouseOverandClick(Common.VeevaChoose, timeOutInSeconds, "Choose", "button", strPageName);
		String strWindowName = "";
		switch (testparameters.getBrowser().getValue()) {
		case "chrome":
			strWindowName = "Open";
			break;
		default:
			break;
		}
		executeScript(true, strBaseDir + "autoIT" + Util.getFileSeparator() + "FileSelect.exe", strWindowName,
				(strBaseDir + "testDataFiles" + Util.getFileSeparator() + strFileName).toLowerCase());
		driverUtil.waitUntilElementVisible(uimap.Common.fileupdloadDataverify, lngMinTimeOutInSeconds, strFileName,
				"Label", strPageName, true);
		String strDataVerify = getAttribute(uimap.Common.fileupdloadDataverify, lngMinTimeOutInSeconds, "textContent",
				"Text", strPageName);
		if (strDataVerify.equals(strFileName)) {
			report.updateTestLog("Verify the upload data", "File is uploaded", Status.PASS);

		} else {
			report.updateTestLog("Verify the upload data", "File is not uploaded", Status.FAIL);

		}

		click(new Common("Upload File", "OK").dialogbutton, lngMinTimeOutInSeconds, "OK", "Button", strPageName, true);
		if (objectExists(new Common("Upload File", "Continue").dialogbutton, "isDisplayed", lngMinTimeOutInSeconds,
				"Continue", "Button", strPageName, false)) {
			click(new Common("Upload File", "Continue").dialogbutton, lngMinTimeOutInSeconds, "Continue", "Button",
					strPageName, true);
		}
		driverUtil.waitUntilElementInVisible(new Common("Upload File", "OK").dialogbutton, "OK", "Button", strPageName);

	}

	public void dragAndDrop(String strValue, String strPageName) {
		uimap.Common draganddrop = new uimap.Common(strValue);
		draganddroppopup(draganddrop.drag, timeOutInSeconds, strValue, "Draganddrop", strPageName);

	}

	public void environmentSelection(String environment) {
		uimap.Common environmentselector = new uimap.Common(dataTable.getData("General_Data", "Environment"));
		ALMFunctions.Screenshot();
		click(environmentselector.environmentselector, timeOutInSeconds, "Gilead IT", "LinkText", "My Vaults", false);

	}

	public static String getTodayDate() throws Exception {
		try {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			strDate = dateFormat.format(date);
		} catch (Exception e) {
			throw e;
		}
		return strDate;
	}

	public static String getFutureDate() throws Exception {
		String Futuredate = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
			Date date = new Date();
			Calendar cl = Calendar.getInstance();
			cl.setTime(date);
			;
			cl.add(Calendar.DAY_OF_YEAR, 5);
			date = cl.getTime();
			Futuredate = df.format(date);
		} catch (Exception e) {
			throw e;
		}
		return Futuredate;
	}

	public void date(String strLabel, String strValue, String strPageName) throws Exception {
		uimap.Common dialogtextbox = new uimap.Common(strLabel);
		switch (strValue) {
		case "Current date":
			strValue = getTodayDate();
			sendkeys(dialogtextbox.dialogtextbox, timeOutInSeconds, strValue, "Dialog TextBox", strPageName);
			sendkeys(null, timeOutInSeconds, Keys.TAB, strValue, strPageName);
			break;
		case "Future date":
			strValue = getFutureDate();
			sendkeys(dialogtextbox.dialogtextbox, timeOutInSeconds, strValue, "Dialog TextBox", strPageName);
			sendkeys(null, timeOutInSeconds, Keys.TAB, strValue, strPageName);
			break;
		}
	}

	public void tabItem(String strValue, String strPageName) {
		uimap.Common tabitem = new uimap.Common(strValue);
		click(tabitem.tabitem, timeOutInSeconds, strValue, "Tab Item", strPageName, true);

	}

	public void menuItem(String strValue, String strPageName) {
		uimap.Common menuitem = new uimap.Common(strValue);
		driverUtil.waitUntilElementVisible(menuitem.menuitem, timeOutInSeconds, strValue, "Menu Item", strPageName,
				true);
		click(menuitem.menuitem, timeOutInSeconds, strValue, "Menu Item", strPageName, true);
	}

	public void lookupIcon(String strValue, String strPageName) {
		uimap.Common lookupicon = new uimap.Common(strValue);
		click(lookupicon.lookupicon, timeOutInSeconds, "Look Up Icon", strValue, strPageName, true);
	}

	public void dialogSelect(String strLabel, String strValue, String strWindowName, String strPageName)
			throws Exception {
		uimap.Common dialogSelect = new uimap.Common(strWindowName, strLabel);
		selectListItem(dialogSelect.dialogSelect, timeOutInSeconds, new String[] { strValue }, strLabel, strPageName,
				"Value");
	}

	public void dropdown(String strLabel, String strValue, String strPageName) {
		textBox(strLabel, strValue, strPageName);
		menuItem(strValue, strPageName);

	}

	public void dropdow(String strLabel, String strValue, String strWindowName, String strPageName) {

		String strCheck = StringUtils.substringBefore(strValue, ":");
		switch (strCheck) {
		case "UnCheck":
			checkBox(strCheck, strLabel, strWindowName, strPageName);
			break;
		default:
			String strVal = StringUtils.substringAfter(strValue, ":");
			removeButton(strVal, strPageName);
			dialogTextBox(strLabel, strVal, strWindowName, strPageName);
			menuItem(strVal, strPageName);
			break;
		}

	}

	public void dropdown(String strLabel, String strValue, String strWindowName, String strPageName) {
		removeButton(strLabel, strPageName);
		String[] Strsplit = strValue.split("_");

		for (int i = 0; i < Strsplit.length; i++) {
			String[] strVal = Strsplit[i].split(":");
			if ((strVal.length <= 1) && (!strVal[0].equals("UnCheck"))) {
				dialogTextBox(strLabel, strVal[0], strWindowName, strPageName);
				menuItem(strVal[0], strPageName);
				break;

			} else {
				switch (strVal[0]) {
				case "UnCheck":
					checkBox(strVal[0], strLabel, strWindowName, strPageName);
					break;
				default:

					uimap.Common dialogRadio = new uimap.Common(strLabel, strVal[1], strWindowName);
					if (objectExists(dialogRadio.dialogRadio, "isDisplayed", lngMinTimeOutInSeconds, strLabel, "Radio",
							strPageName, false)) {
						dialogRadio(strVal[1], strLabel, strWindowName, strPageName);
					}
					dialogTextBox(strLabel, strVal[2], strWindowName, strPageName);
					menuItem(strVal[2], strPageName);
					break;
				}

			}

		}

	}

	public void popudropdown(String strLabel, String strValue, String strWindowName, String strPageName)
			throws Exception {
		uimap.Common popudropdown = new uimap.Common(strLabel, strValue, strWindowName);
		selectListItem(popudropdown.popudropdown, timeOutInSeconds, new String[] { strValue }, strLabel, strPageName,
				"Value");
	}

	public void dialogButton(String strValue, String strWindowName, String strPageName) {
		uimap.Common dialogbutton = new uimap.Common(strWindowName, strValue);
		clickByJS(dialogbutton.dialogbutton, timeOutInSeconds, strValue, "Dialog Button", strPageName, true);
		if (objectExists(uimap.Common.lblStatus, "isEnabled", lngMinTimeOutInSeconds, "Status check", "Pop up",
				strPageName, false)) {
			driverUtil.waitUntilElementInVisible(uimap.Common.lblStatus, "Status check", "Pop up", strPageName);
		}

	}

	public void dialogTextBox(String strLabel, String strValue, String strWindowName, String strPageName) {
		uimap.Common dialogtextbox = new uimap.Common(strWindowName, strLabel);
		// pagescroll(dialogtextbox.dialogtextbox, strPageName);
		clickByJS(dialogtextbox.dialogtextbox, timeOutInSeconds, strValue, "Dialog TextBox", strPageName, false);
		clear(dialogtextbox.dialogtextbox, timeOutInSeconds, "Dialog TextBox", strPageName);
		sendkeys(dialogtextbox.dialogtextbox, timeOutInSeconds, strValue, "Dialog TextBox", strPageName);
		if (objectExists(Common.datePicker, "isDisplayed", lngMinTimeOutInSeconds, "Date Picker", "Window", strPageName,
				false)) {
			driver.findElement(dialogtextbox.dialogtextbox).sendKeys(Keys.ESCAPE);
			if (!driverUtil.WaitUntilElementAtributeContainsText(Common.datePicker, lngMinTimeOutInSeconds,
					"Date Picker", "Window", false, "style", "none")) {
				ALMFunctions.ThrowException("Error", "Date Picker should be closed",
						"Date Picker is not closed even upon entering the value.", true);
			}
		} else {
			// No need of implementation as this is common for both text box and
			// date picker
		}
	}

	public void dialogTextArea(String strLabel, String strValue, String strWindowName, String strPageName) {
		uimap.Common dialogtextarea = new uimap.Common(strWindowName, strLabel);
		clickByJS(dialogtextarea.dialogtextarea, timeOutInSeconds, strValue, "Dialog TextArea", strPageName, false);
		clear(dialogtextarea.dialogtextarea, timeOutInSeconds, "Dialog TextArea", strPageName);
		sendkeys(dialogtextarea.dialogtextarea, timeOutInSeconds, strValue, "Dialog TextArea", strPageName);

	}

	public void headerButton(String strValue, String strPageName) {
		switch (strValue) {
		case "All Documents":
			click(uimap.Common.searchbtn, timeOutInSeconds, strValue, "Search Button", strPageName, true);
			break;

		default:
			uimap.Common headerbutton = new uimap.Common(strValue);
			click(headerbutton.headerbutton, timeOutInSeconds, strValue, "Button", strPageName, true);
		}
	}

	public void textArea(String strLabel, String strValue, String strPageName) {
		uimap.Common textarea = new uimap.Common(strLabel);
		clear(textarea.textarea, timeOutInSeconds, strLabel, strPageName);
		sendkeys(textarea.textarea, timeOutInSeconds, strValue, strLabel, strPageName);
	}

	public void textBox(String strLabel, String strValue, String strPageName) {
		switch (strLabel) {
		case "All Documents":
			clear(uimap.Common.searchtext, timeOutInSeconds, strLabel, strPageName);
			sendkeys(uimap.Common.searchtext, timeOutInSeconds, strValue, strLabel, strPageName);
			break;
		default:
			uimap.Common textbox = new uimap.Common(strLabel);
			sendkeys(textbox.textbox, timeOutInSeconds, strValue, strLabel, strPageName);
			if (objectExists(Common.datePicker, "isDisplayed", lngMinTimeOutInSeconds, "Date Picker", "Window",
					strPageName, false)) {
				driver.findElement(textbox.textbox).sendKeys(Keys.ESCAPE);
				if (!driverUtil.WaitUntilElementAtributeContainsText(Common.datePicker, lngMinTimeOutInSeconds,
						"Date Picker", "Window", false, "style", "none")) {
					ALMFunctions.ThrowException("Error", "Date Picker should be closed",
							"Date Picker is not closed even upon entering the value.", true);
				}
			} else {
				// No need of implementation as this is common for both text box
				// and date picker
			}
		}
	}

	public void contentButton(String strValue, String strPageName) {
		uimap.Common contentbutton = new uimap.Common(strValue);
		if (strValue.equals("buttonsave")) {
			clickByJS(uimap.Common.VeevaSave, timeOutInSeconds, "Save", "Button", "Home Page", true);
		} else {
			click(contentbutton.contentbutton, timeOutInSeconds, strValue, "Button", strPageName, true);
		}

	}

	public void treeView(String strValue, String strPageName) {
		uimap.Common treeview = new uimap.Common(strValue);
		if (objectExists(treeview.treeview, "isDisplayed", timeOutInSeconds, strValue, "Tree View", strPageName,
				false)) {
			String treview = getValue(treeview.treeview, timeOutInSeconds, "data-icon", strPageName);
			if (!treview.equalsIgnoreCase("caret-down")) {
				click(treeview.treeview, timeOutInSeconds, strValue, "Tree View", strPageName, true);
			}
		} else {

			throw new FrameworkException("Error", "Tree View : " + strValue + " is not visible in the Page");
		}
	}

	public void expand(String strValue, String strPageName) {

		driverUtil.waitUntilAjaxLoadingComplete(lngPagetimeOutInSeconds, strPageName);
		uimap.Common expand = new uimap.Common(strValue);
		if (objectExists(expand.expand, "isEnabled", timeOutInSeconds, strValue, "Expand button", strPageName, false)) {
			String expandbtn = getValue(expand.expand, timeOutInSeconds, "data-icon", strPageName);
			if (!expandbtn.equalsIgnoreCase("caret-down")) {
				click(expand.expand, timeOutInSeconds, strValue, "Expand button", strPageName, true);
			}

		} else {
			throw new FrameworkException("Error", "Expand button is not visible in the Page");
		}
	}

	public void checkBox(String strValue, String strLabel, String strWindowName, String strPageName) {
		uimap.Common checkbox = new uimap.Common(strLabel, strValue, strWindowName);
		switch (strValue) {
		case "UnCheck":

			click(checkbox.checkbox, timeOutInSeconds, strLabel, strValue + " Checkbox", strPageName, true);
			break;
		default:

			ALMFunctions.UpdateReportLogAndALMForPassStatus("Checkbox", "Check box is already checked",
					"Check box is " + strLabel + " checked", true);
			break;
		}
	}

	public void removeButton(String strValue, String strPageName) {
		uimap.Common removebutton = new uimap.Common(strValue);
		if (objectExists(removebutton.removebutton, "isDisplayed", timeOutInSeconds, strValue, "textboxclear",
				strPageName, false)) {
			List<WebElement> textremove1 = driver.getWebDriver().findElements(removebutton.removebutton);
			int size = textremove1.size();
			for (int i = 0; i < size; i++) {
				textremove1.get(i).click();
			}
		}
	}

	public void docSearch(String strLabel, String strValue, String strPageName) {

		textBox(strLabel, strValue, strPageName);
		headerButton(strLabel, strPageName);
		documentSearch(strValue, strPageName);

	}

	public void docNumberSearch(String strLabel, String strValue, String strPageName) {

		textBox(strLabel, strValue, strPageName);
		// headerButton(strLabel, strPageName);
		menuItem(strValue, strPageName);

	}

	public void docNumberSearchAndClickLink(String strLabel, String strValue, String strPageName) {

		textBox(strLabel, strValue, strPageName);
		headerButton(strLabel, strPageName);
		// menuItem(strValue, strPageName);

	}

	public void documentSearch(String strValue, String strPageName) {
		driverUtil.waitUntilAjaxLoadingComplete(lngPagetimeOutInSeconds, strPageName);
		uimap.Common documentsearch = new uimap.Common(strValue);
		click(documentsearch.documentsearch, timeOutInSeconds, strValue, "Document Search", strPageName, true);
		driverUtil.waitUntilAjaxLoadingComplete(timeOutInSeconds, strPageName);
	}

	public void dialogRadio(String strValue, String strLabel, String strWindowName, String strPageName) {
		uimap.Common dialogRadio = new uimap.Common(strLabel, strValue, strWindowName);
		click(dialogRadio.dialogRadio, timeOutInSeconds, strValue, "Radio Button", strPageName, true);
	}

	public void popupradio(String strValue, String strWindowName, String strPageName) {
		uimap.Common popupradio = new uimap.Common(strWindowName, strValue);
		click(popupradio.popupradio, timeOutInSeconds, strValue, "Radio Button", strPageName, true);
		if (objectExists(uimap.Common.lblStatus, "isEnabled", timeOutInSeconds, "Status check", "Pop up", strPageName,
				false)) {
			driverUtil.waitUntilElementInVisible(uimap.Common.lblStatus, "Status check", "Pop up", strPageName);
		}
	}

	public void radioButton(String strValue, String strLabel, String strPageName) {

		uimap.Common radiobutton = new uimap.Common(strLabel, strValue);
		clickByJS(radiobutton.radiobutton, timeOutInSeconds, strLabel, strValue, strPageName, true);
	}

	public void dccDocumentSearch(String strValue, String strPageName) {
		driverUtil.waitUntilAjaxLoadingComplete(lngPagetimeOutInSeconds, strPageName);
		uimap.Common dccdocumentsearch = new uimap.Common(strValue);
		click(dccdocumentsearch.dccdocumentsearch, timeOutInSeconds, strValue, "Selected Document click", strPageName,
				true);
		driverUtil.waitUntilAjaxLoadingComplete(lngMinTimeOutInSeconds, strPageName);

	}

	public void listItem(String strValue, String strLabel, String strPageName) {
		uimap.Common listitem = new uimap.Common(strValue, strLabel);
		click(listitem.listitem, timeOutInSeconds, strValue, "List Item", strPageName, true);
	}

	public void link(String strValue, String strPageName) {
		uimap.Common link = new uimap.Common(strValue);
		click(link.link, timeOutInSeconds, strValue, "Link", strPageName, true);
	}

	public void settingIcon(String strValue, String strPageName) {
		uimap.Common settingicon = new uimap.Common(strValue);
		click(settingicon.settingicon, timeOutInSeconds, strValue, "Setting Icon", strPageName, true);
		driverUtil.waitUntilAjaxLoadingComplete(lngMinTimeOutInSeconds, strPageName);

	}

	/**
	 * Method to fill input form in any page
	 * 
	 * @param strParameters
	 *            - Parameters for the form
	 * @param strPageName
	 *            - Name of the page in which form exists
	 * @throws Exception
	 */
	public void FillForm(String strSheetName, String strColumnName, String strScenario, String strDelimiter,
			boolean includeDelimiter, String strPageName) throws Exception {

		String strParametersInFormat = "";
		FileLock inputDataFilelock = new FileLockMechanism(timeOutInSeconds).SetLockOnFile("GetData");
		if (inputDataFilelock != null) {
			synchronized (CommonFunctions.class) {
				strParametersInFormat = getConcatenatedStringFromExcel(strSheetName, strColumnName, strScenario,
						strDelimiter, includeDelimiter);
			}
			new FileLockMechanism(timeOutInSeconds).ReleaseLockOnFile(inputDataFilelock, "GetData");
		}
		String[] arrParametersSplitByRow = StringUtils.split(strParametersInFormat, "$");
		for (int i = 0; i < arrParametersSplitByRow.length; i++) {
			if (arrParametersSplitByRow[i].trim().length() > 0) {

				String strLabel = "";
				String strType = "";
				String strValue = "";
				String strWindowName = "";
				String[] arrCriteria = StringUtils.split(arrParametersSplitByRow[i], ";");
				for (int k = 0; k < arrCriteria.length; k++) {
					if (arrCriteria[k].trim().length() > 0) {
						switch (StringUtils.substringBefore(arrCriteria[k], "=").toLowerCase()) {
						case "element label":
							strLabel = StringUtils.substringAfter(arrCriteria[k], "=");
							break;
						case "element type":
							strType = StringUtils.substringAfter(arrCriteria[k], "=").toLowerCase();
							break;
						case "element value":
							strValue = StringUtils.substringAfter(arrCriteria[k], "=");
							break;
						case "page name":
							strPageName = StringUtils.substringAfter(arrCriteria[k], "=");
							break;
						case "window":
							strWindowName = StringUtils.substringAfter(arrCriteria[k], "=");
							break;
						default:
							ALMFunctions.ThrowException("Test Data",
									"Test Data must be provided in the pre-defined format",
									"Unhandled case " + StringUtils.substringBefore(arrCriteria[k], "="), false);
						}
					}
				}

				boolean blnWindowNameExists = strWindowName.trim().length() > 0;

				if (strValue.trim().length() > 0) {
					switch (strType.toLowerCase()) {
					case "date":
						date(strLabel, strValue, strPageName);
						break;
					case "tabitem":
						tabItem(strValue, strPageName);
						break;
					case "menuitem":
						menuItem(strValue, strPageName);
						break;
					case "lookupicon":
						lookupIcon(strValue, strPageName);
						break;
					case "dropdownselect":
						dialogSelect(strLabel, strValue, strWindowName, strPageName);
						break;
					case "dropdown":
						if (blnWindowNameExists) {
							dropdown(strLabel, strValue, strWindowName, strPageName);

						} else {
							dropdown(strLabel, strValue, strPageName);
						}
						break;

					case "button":

						if (blnWindowNameExists) {
							dialogButton(strValue, strWindowName, strPageName);
						} else {
							contentButton(strValue, strPageName);
						}

						break;

					case "textbox":
						if (blnWindowNameExists) {
							dialogTextBox(strLabel, strValue, strWindowName, strPageName);
						} else {
							textBox(strLabel, strValue, strPageName);
						}
						break;

					case "textarea":
						if (blnWindowNameExists) {
							dialogTextArea(strLabel, strValue, strWindowName, strPageName);
						} else {
							textArea(strLabel, strValue, strPageName);
						}

						break;

					case "headerbutton":
						headerButton(strValue, strPageName);
						break;

					case "treeview":
						treeView(strValue, strPageName);
						break;

					case "expand":
						expand(strValue, strPageName);
						break;

					case "checkbox":

						if (blnWindowNameExists) {
							checkBox(strValue, strLabel, strWindowName, strPageName);
						}
						break;
					case "screenshot":

						report.updateTestLog("Screen Shot", "Screen Shot Captured in " + strPageName,
								Status.SCREENSHOT);
						ALMFunctions.Screenshot();
						break;

					case "removebutton":
						removeButton(strValue, strPageName);
						break;

					case "documentsearch":
						docSearch(strLabel, strValue, strPageName);
						break;

					case "radiobutton":
						if (blnWindowNameExists) {
							if (strLabel.length() > 0) {
								dialogRadio(strValue, strLabel, strWindowName, strPageName);
							} else {
								popupradio(strValue, strWindowName, strPageName);
							}
						} else {
							radioButton(strValue, strLabel, strPageName);
						}
						break;

					case "dccdocumentsearch":
						dccDocumentSearch(strValue, strPageName);
						break;

					case "listitem":
						listItem(strValue, strLabel, strPageName);
						break;

					case "link":
						link(strValue, strPageName);
						break;

					case "lookupentervalue":
						lookupEnterValue(strLabel, strValue, strPageName);
						break;

					case "fileselect":
						selectFileToImport(strValue, strPageName);
						break;
					case "fileupload":
						selectFileUpload(strValue, strPageName);
						break;

					case "settingicon":
						settingIcon(strValue, strPageName);
						break;

					case "addinputtodcc":
						addInputToDcc(strValue, strPageName);
						break;

					case "dropdownvalidation":
						subtypeDropdownValidation(strLabel, strValue, strWindowName, strPageName);
						break;

					case "draganddrop":
						dragAndDrop(strValue, strPageName);
						break;

					case "fieldvalidation":
						metaDataValidation(strValue, strPageName);
						break;
						
					case "labelclickbyjs":
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						clickByJS(new Common(strLabel).scrollLabel, lngMinTimeOutInSeconds, strLabel, strLabel,	strPageName, false);
						break;
						
					case "labelclick":
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						click(new Common(strLabel).scrollLabel, lngMinTimeOutInSeconds, strLabel, strLabel,	strPageName, false);
						break;
						
					case "buttondoctype":
						By button = null;
						button = new uimap.Common(strLabel).btnDocType;
						pagescroll(button, strPageName);
						click(new Common(strLabel).btnDocType, lngMinTimeOutInSeconds, strLabel, strLabel,	strPageName, true);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "selectdoctype":
						uimap.Common strGroupType = new uimap.Common(strLabel);
						WebElement eleType = driver.getWebDriver().findElement(strGroupType.drpdwnType);
						driverUtil.waitUntilStalenessOfElement(eleType, strPageName);
						Select drpdwnType = new Select(eleType);
						drpdwnType.selectByVisibleText(strValue);
						break;
						
					case "selectdocsubtype":
						uimap.Common strSubType = new uimap.Common(strLabel);
						WebElement eleSubType = driver.getWebDriver().findElement(strSubType.drpdwnSubType);
						driverUtil.waitUntilStalenessOfElement(eleSubType, strPageName);
						Select drpdwnSubType = new Select(eleSubType);
						drpdwnSubType.selectByVisibleText(strValue);
						break;
					
					case "docupload":
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						mouseOverandClick(Common.btnBrowse, lngMaxInvisibilityTimeOutInSeconds,	"Select files to upload", "Upload icon", strPageName);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						UploadFileAutoIT(strValue);
						break;
						
					case "docname":
						 clear(Common.txtName, lngMinTimeOutInSeconds, strValue, strPageName);
						sendkeys(Common.txtName, lngMinTimeOutInSeconds, strValue,"Name", strPageName,true);
						break;
						
					case "docobjective":
						 clear(Common.txtObjective, lngMinTimeOutInSeconds, strValue, strPageName);
						sendkeys(Common.txtObjective, lngMinTimeOutInSeconds, strValue,"Objective", strPageName,true);
						break;
						
					case "selectdoclifecycle":
						click(Common.txtLifecycle, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						clear(Common.txtLifecycle, lngMinTimeOutInSeconds, strValue, strPageName);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						click(new Common(strValue).selectLifecycle, lngMaxInvisibilityTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "clickandselect":
						sendkeys(new Common(strLabel).searchDocFields, lngMinTimeOutInSeconds, strValue, "search box",
								strPageName);
						driverUtil.waitUntilStalenessOfElement(new Common(strLabel).searchDocFields, strPageName);
						clickByJS(new Common(strValue).selectDocFields, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						break;
						
					case "screenshots":
						driverUtil.waitForPageLoad(lngMaxTimeOutInSeconds, strPageName);
						ALMFunctions.UpdateReportLogAndALMForPassStatus("Capture the screenshot of the page:"+strPageName ,"Screenshot should be captured for the page: "+strPageName ,"Screenshot is captured for the page: "+strPageName , true);
						break;
						
					case "selectmaterialintent":
						  sendkeys(Common.txtMaterialIntent, lngMinTimeOutInSeconds, strValue,"Material Intent",strPageName,true);
						  driverUtil.waitUntilStalenessOfElement(Common.txtMaterialIntent,strPageName); 
						  clickByJS(new Common(strValue).selectMaterialIntent, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						break;
						
					case "selectaudience":
						sendkeys(Common.txtAudience, lngMinTimeOutInSeconds, strValue, "Audience",strPageName,true);
						driverUtil.waitUntilStalenessOfElement(Common.txtAudience, strPageName);
						clickByJS(new Common(strValue).selectAudience, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						break;
						
					case "selectdissemination":
						sendkeys(Common.txtDissemination, lngMinTimeOutInSeconds, strValue, "Method of Dissemination",strPageName,true);
						driverUtil.waitUntilStalenessOfElement(Common.txtDissemination, strPageName);
						clickByJS(new Common(strValue).selectDissemination, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						break;
						
					case "selectfinalform":
						  sendkeys(Common.txtFinalForm, lngMinTimeOutInSeconds, strValue,"Final Form",strPageName,true);
						  driverUtil.waitUntilStalenessOfElement(Common.txtFinalForm, strPageName);
						  clickByJS(new Common(strValue).selectFinalForm, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						break;
						
					case "scroll":
						pagescroll(new Common(strLabel).scrollLabel, strPageName);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "status":
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						String strActual = getText(Common.lblStatus,lngMinTimeOutInSeconds, strLabel, strPageName);
						String strExpected = strValue;
						if (strActual.equalsIgnoreCase(strExpected)) {
						ALMFunctions.UpdateReportLogAndALMForPassStatus("Verify the value displayed for the element:"+strLabel, "Value to be displayed for the element "+strLabel+" is:"+strExpected, "Value displayed for the element "+strLabel+" is:"+strActual, true);
						} else {
							ALMFunctions.UpdateReportLogAndALMForFailStatus("Verify the value displayed for the element:"+strLabel, "Value to be displayed for the element "+strLabel+" is:"+strExpected, "Value displayed for the element "+strLabel+" is:"+strActual, true);
						}
						break;
						
					case "getdocumentnumber":
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						String strDocNo = getText(Common.lblDocumentNo,lngMinTimeOutInSeconds, strLabel, strPageName);
						if (!strDocNo.equalsIgnoreCase("")) {
						ALMFunctions.UpdateReportLogAndALMForPassStatus("Retrieve the value displayed for the element:"+strLabel, "Value displayed for the element "+strLabel+" is:"+strDocNo, "Value displayed for the element "+strLabel+" is:"+strDocNo, true);
						} else {
							ALMFunctions.UpdateReportLogAndALMForFailStatus("Retrieve the value displayed for the element:"+strLabel, "Value to be displayed for the element "+strLabel+" is:"+strDocNo, "Value displayed for the element "+strLabel+" is:"+strDocNo, true);
						}
						dataTable.putData("ApplicationData", "DocumentNumber", strDocNo);
						break;
						
					case "searchbydocumentno":
						sendkeys(Common.txtDocumentNo, lngMinTimeOutInSeconds, strValue,"Search Document",strPageName,true);
						driverUtil.waitUntilStalenessOfElement(Common.txtDocumentNo,strPageName); 
						click(Common.btnSearch, lngMinTimeOutInSeconds, strLabel, "Advanced Search", strPageName, true);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						if (objectExists(Common.imgLayouts, "isDisplayed", lngMinTimeOutInSeconds, "Layout", "Button", strPageName, false)) {
     						  click(Common.imgLayouts, lngMinTimeOutInSeconds, strLabel, "Layouts",
     						  strPageName, false); driverUtil.waitForPageLoad(lngMinTimeOutInSeconds,
     						  strPageName); click(Common.lstCompactView, lngMinTimeOutInSeconds, strLabel,
     						  "Compact View", strPageName, false);
     						  driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						}						 
						click(Common.lnkDocumentNo, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "sendtobusinessowner":
						clickByJS(Common.btnActions, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						click(new Common(strLabel).scrollLabel, lngMinTimeOutInSeconds, strLabel, strLabel,	strPageName, true);
						break;
						
					case "fillbusinessowner":
						clickByJS(Common.lblBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						sendkeys(Common.txtBusinessOwner, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						driverUtil.waitUntilStalenessOfElement(new Common(strValue).listBusinessOwner, strPageName);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						click(new Common(strLabel).scrollLabel, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						break;
						
					case "fillcoordinator":
						clickByJS(Common.lblCoordinator, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						sendkeys(Common.txtCoordinator, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						clickByJS(new Common(strValue).listCoordinator, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						break;
						
					case "fillduedate":
						sendkeys(Common.txtDueDate, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						clickByJS(new Common(strLabel).scrollLabel, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						driverUtil.waitUntilStalenessOfElement(Common.txtDueDate, strPageName);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "poforreviewagency":
						sendkeys(Common.txtPOForReviewAgency, lngMinTimeOutInSeconds, strValue,"Purchase Order for Review Agency", strPageName,true);
						break;
						
					case "selectapprovaltype":
						sendkeys(new Common(strLabel).searchDocFields, lngMinTimeOutInSeconds, strValue, "Dropdown search", 	strPageName);
						  driverUtil.waitUntilStalenessOfElement(new Common(strLabel).searchDocFields,strPageName); 
						  clickByJS(new Common(strValue).selectApprovalType, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						break;
						
					case "selectlanguage":
						sendkeys(new Common(strLabel).searchDocFields, lngMinTimeOutInSeconds, strValue, "Dropdown search", 	strPageName);
						driverUtil.waitUntilStalenessOfElement(new Common(strLabel).searchDocFields,strPageName); 
						clickByJS(new Common(strValue).selectLanguage, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						break;
						
					case "clickcomplete":
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						click(new Common(strLabel).btnComplete, lngMinTimeOutInSeconds, strLabel, strLabel,	strPageName, true);
						driverUtil.waitUntilStalenessOfElement(new Common(strLabel).btnComplete, strPageName);
						break;
						
					case "workflowclickok":
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						click(new Common(strLabel).btnWorkflowOK, lngMinTimeOutInSeconds, strLabel, strLabel,	strPageName, true);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "sendrevieworapproval":
						uimap.Common strWorkflow = new uimap.Common(strLabel);
						WebElement eleWorkflow = driver.getWebDriver().findElement(strWorkflow.drpdwnStartWorkflow);
						driverUtil.waitUntilStalenessOfElement(eleWorkflow, strPageName);
						Select drpdwnStartWorkflow = new Select(eleWorkflow);
						drpdwnStartWorkflow.selectByVisibleText(strValue);
						break;
						
					case "fillreviewby":
						sendkeys(Common.txtDueDate, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						clickByJS(new Common(strLabel).scrollLabel, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						driverUtil.waitUntilStalenessOfElement(Common.txtDueDate, strPageName);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "fillapprover":
						clickByJS(new Common(strLabel).lblApprover, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						sendkeys(new Common(strLabel).txtApprover, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "fillreviewers":
						clickByJS(new Common(strLabel).lblReviewer, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						sendkeys(new Common(strLabel).txtReviewer, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "fillprojectmanager":
						clickByJS(new Common(strLabel).lblProjectManager, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						sendkeys(new Common(strLabel).txtProjectManager, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "selectcapacity":
						selectDropdownByValue(Common.drpdwnCapacity,strLabel, strValue, strPageName, true);
						/*
						 * WebElement eleCapacity=
						 * driver.getWebDriver().findElement(Common.drpdwnCapacity);
						 * driverUtil.waitUntilStalenessOfElement(eleCapacity, strPageName); Select
						 * drpdwnCapacity = new Select(eleCapacity);
						 * drpdwnCapacity.selectByVisibleText(strValue);
						 */
						break;
						
					case "clickapproved":
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						click(new Common(strLabel).rdoApproved, lngMinTimeOutInSeconds, strLabel, strLabel,	strPageName, true);
						driverUtil.waitUntilStalenessOfElement(new Common(strLabel).rdoApproved, strPageName);
						break;
						
					case "checkapprovers":
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						if(strValue.equalsIgnoreCase("Yes")) {
							clickByJS(new Common(strLabel).chkReviewers, lngMinTimeOutInSeconds, strLabel, strLabel,	strPageName, true);
						}
						driverUtil.waitUntilStalenessOfElement(new Common(strLabel).chkReviewers, strPageName);
						break;
						
					case "fillmedicalreviewer":
						clickByJS(new Common(strLabel).lblMedicalReviewer, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						sendkeys(new Common(strLabel).txtMedicalReviewer, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "filllegalreviewer":
						clickByJS(new Common(strLabel).lblLegalReviewer, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						sendkeys(new Common(strLabel).txtLegalReviewer, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						driverUtil.waitUntilStalenessOfElement(new Common(strValue).listBusinessOwner, strPageName);
				     	driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
				     	click(new Common(strLabel).scrollLabel, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						break;
						
					case "fillregulatoryreviewer":
						clickByJS(new Common(strLabel).lblRegulatoryReviewer, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						sendkeys(new Common(strLabel).txtRegulatoryReviewer, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "fillcommercialreviewer":
						clickByJS(new Common(strLabel).lblCommercialReviewer, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						sendkeys(new Common(strLabel).txtCommercialReviewer, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "fillotherreviewer":
						clickByJS(new Common(strLabel).lblOtherReviewer, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						sendkeys(new Common(strLabel).txtOtherReviewer, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "fillplannedfirstusedate":
						sendkeys(Common.txtPlannedFirstUseDate, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						click(new Common(strLabel).lblPlannedFirstUseDate, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						driverUtil.waitUntilStalenessOfElement(Common.txtPlannedFirstUseDate, strPageName);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "fillotherapprover":
						clickByJS(new Common(strLabel).lblOtherApprover, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						sendkeys(new Common(strLabel).txtOtherApprover, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "approvedforuse":
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						click(new Common(strLabel).rdoApprovedForUse, lngMinTimeOutInSeconds, strLabel, strLabel,	strPageName, true);
						driverUtil.waitUntilStalenessOfElement(new Common(strLabel).rdoApprovedForUse, strPageName);
						break;
						
					case "approverusername":
						approverUserName(strLabel,strValue,strPageName);
						break;
						
					case "approverpassword":
						approverPassword(strLabel,strValue,strPageName);
						break;
						
					case "checkexpirationdate":
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						String strExpirationDate = getAttribute(Common.txtExpirationDate,lngMinTimeOutInSeconds,"value", strLabel, strPageName);
						if (strExpirationDate.equalsIgnoreCase(strValue)) {
						ALMFunctions.UpdateReportLogAndALMForPassStatus("Retrieve the value displayed for the element:"+strLabel, "Value displayed for the element "+strLabel+" is:"+strValue, "Value displayed for the element "+strLabel+" is:"+strExpirationDate, true);
						} else {
							ALMFunctions.UpdateReportLogAndALMForFailStatus("Retrieve the value displayed for the element:"+strLabel, "Value to be displayed for the element "+strLabel+" is:"+strValue, "Value displayed for the element "+strLabel+" is:"+strExpirationDate, true);
						}
						break;
						
					case "checkplannedfirstusedate":
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						String strFirstUseDate = getAttribute(Common.txtPlannedFirstUseDate,lngMinTimeOutInSeconds, "value", strLabel, strPageName);
						if (strFirstUseDate.equalsIgnoreCase(strValue)) {
						ALMFunctions.UpdateReportLogAndALMForPassStatus("Retrieve the value displayed for the element:"+strLabel, "Value displayed for the element "+strLabel+" is:"+strValue, "Value displayed for the element "+strLabel+" is:"+strFirstUseDate, true);
						} else {
							ALMFunctions.UpdateReportLogAndALMForFailStatus("Retrieve the value displayed for the element:"+strLabel, "Value to be displayed for the element "+strLabel+" is:"+strValue, "Value displayed for the element "+strLabel+" is:"+strFirstUseDate, true);
						}
						break;
						
					case "fillprereviewer":
						clickByJS(new Common(strLabel).lblPreReviewer , lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						sendkeys(new Common(strLabel).txtPreReviewer, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "fillcommops":
						clickByJS(new Common(strLabel).lblCommOps , lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
						sendkeys(new Common(strLabel).txtCommOps, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
						break;
						
					case "filleditorialreviewer":
						 fillEditorialReviewer(strLabel, strValue, strPageName);
						break;
						
					case "fillmeetingdate":
						sendkeys(Common.txtMeetingDate, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
						break;
						
					case "selectreviewdeadline":
						sendkeys(new Common(strLabel).searchDocFields, lngMinTimeOutInSeconds, strValue,strLabel,strPageName);
						driverUtil.waitUntilStalenessOfElement(new Common(strLabel).searchDocFields, strPageName);
						clickByJS(new Common(strValue).lstReviewDeadline, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
						break;
						
					case "choosewithdrawn":
						chooseWithdrawn(strLabel,strValue,strPageName);
						break;
						
					case "withdrawalnotes":
						withdrawalNotes(strLabel,strValue,strPageName);
						break;
						
					case "neworrevise":
						 newOrRevise(strLabel,strValue,strPageName);
						 break;
						 
					case "priority":
						 choosePriority(strLabel, strValue, strPageName);
						 break;
						 
					case "hasmaterialcode":
						 hasMaterialCode(strLabel, strValue, strPageName);
						 break;
						 
					case "materialcode":
						 enterMaterialCode(strLabel, strValue, strPageName);
						 break;
						 
					case "copyrightconfirmation":
						 copyrightConfirmation(strLabel, strValue, strPageName);
						 break;
						 
					case "corporatelogo":
						 useOfCorportateLogo(strLabel, strValue, strPageName);
						 break;
						 
					case "4digitcostcenter":
						 enter4DigitCostCenter(strLabel, strValue, strPageName);
						 break;
						 
					case "fillexaminer":
						 fillExaminer(strLabel,strValue,strPageName);
						 break;
						 
					case "fillmedicalsignatory":
						 fillMedicalSignatory(strLabel,strValue,strPageName);
						 break;
						 
					case "copaytermsincluded":
						 copayTermsIncluded(strLabel,strValue,strPageName);
						 break;
						 
					case "selectmaterialhistory":
						 selectMaterialHistory(strLabel,strValue,strPageName);
						break;
						
					case "prcderivativematerial":
						 prcDerivativeMaterial(strLabel,strValue,strPageName);
						break;
						
					case "fillprcspecialist":
						 fillPRCSpecialist(strLabel,strValue,strPageName);
						break;
						
					case "prcapprovaltype":
						 prcApprovalType(strLabel,strValue,strPageName);
						break;
						
					case "fillcontentcreator":
						 fillContentCreator(strLabel, strValue, strPageName);
						 break;
						 
					case "fillproposedexpdate":
						proposedExpDate(strLabel,strValue,strPageName);
						break;
						
					case "makeacopy":
						 makeCopy(strLabel, strValue, strPageName);
						 break;
						 
					case "reasonforcopy":
						 selectDropdownByValue(new Common(strLabel).drpdwnReason, strLabel, strValue, strPageName, true);
						 break;
					}
				}
			}
		}
	}

	public String getConcatenatedStringFromExcel(String strSheetName, String strColumnName, String strScenario,
			String strDelimiter, boolean blnIncludeDelimiter) {
		String strFilePath = dataTable.datatablePath + Util.getFileSeparator() + dataTable.datatableName + ".xls";
		HSSFWorkbook wb = openExcelFile(strFilePath);
		wb.setForceFormulaRecalculation(true);
		HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
		Sheet sheet = getSheetFromXLSWorkbook(wb, strSheetName, strFilePath);
		String strValue = "";
		int intColumnIndexConcatFlag = getColumnIndex(strFilePath, strSheetName, "Concatenate_Flag");
		int intColumnIndex = getColumnIndex(strFilePath, strSheetName, strColumnName);
		int intStartRow = getStartRow(wb, strFilePath, strSheetName, sheet, strScenario);
		int intEndRow = getEndRow(wb, sheet, intStartRow, strSheetName);
		for (int i = intStartRow; i < intEndRow; i++) {
			if (sheet.getRow(i) != null) {
				if (getCellValueAsString(wb, sheet.getRow(i).getCell(intColumnIndexConcatFlag))
						.equalsIgnoreCase("yes")) {
					if (blnIncludeDelimiter) {
						if (i == intStartRow) {
							strValue = getCellValueAsString(wb, sheet.getRow(i).getCell(intColumnIndex)) + strDelimiter;
						} else if (i != intEndRow) {
							strValue = strValue + getCellValueAsString(wb, sheet.getRow(i).getCell(intColumnIndex))
									+ strDelimiter;
						} else {
							strValue = strValue + getCellValueAsString(wb, sheet.getRow(i).getCell(intColumnIndex));
						}
					} else {
						if (i == intStartRow) {
							strValue = getCellValueAsString(wb, sheet.getRow(i).getCell(intColumnIndex));
						} else {
							strValue = strValue + getCellValueAsString(wb, sheet.getRow(i).getCell(intColumnIndex));
						}
					}
				}
			}
		}
		try {
			wb.close();
		} catch (IOException e) {
			ALMFunctions.ThrowException("Excel Close", "Should be able to close excel file",
					"Below Exception is thrown when trying to " + "close excel file found in the path " + strFilePath
							+ "<br><br>" + e.getLocalizedMessage(),
					false);
		}
		return strValue;
	}

	public int getStartRow(HSSFWorkbook wb, String strFilePath, String strSheetName, Sheet sheet, String strScenario) {
		boolean blnRowFound = false;
		int intTCIDColumnIndex = getColumnIndex(strFilePath, strSheetName, "TC_ID");
		int intScenarioColumnIndex = getColumnIndex(strFilePath, strSheetName, "TC_Scenario");
		int intIterationColumnIndex = getColumnIndex(strFilePath, strSheetName, "Iteration");
		int intSubIterationColumnIndex = getColumnIndex(strFilePath, strSheetName, "SubIteration");
		for (int i = 1; i < sheet.getLastRowNum(); i++) {
			if (sheet.getRow(i) != null) {
				if (getCellValueAsString(wb, sheet.getRow(i).getCell(intTCIDColumnIndex))
						.equalsIgnoreCase(testparameters.getCurrentTestcase())
						&& getCellValueAsString(wb, sheet.getRow(i).getCell(intScenarioColumnIndex))
								.equalsIgnoreCase(strScenario)
						&& Integer.valueOf(getCellValueAsString(wb,
								sheet.getRow(i).getCell(intIterationColumnIndex))) == (dataTable.currentIteration)
						&& Integer.valueOf(getCellValueAsString(wb, sheet.getRow(i)
								.getCell(intSubIterationColumnIndex))) == (dataTable.currentSubIteration)) {
					blnRowFound = true;
					return i + 2;
				}
			}
		}
		if (!blnRowFound) {
			ALMFunctions.ThrowException("Test Data", "Test Data should be found in the sheet " + sheet,
					"Error - Test Data with " + "TC_ID as " + testparameters.getCurrentTestcase() + " , TC_Scenario as "
							+ strScenario + " , Iteration as " + dataTable.currentIteration + " , SubIteration as "
							+ dataTable.currentSubIteration + " does not exists in the sheet " + strSheetName,
					false);
		}
		return 0;
	}

	public int getEndRow(HSSFWorkbook wb, Sheet sheet, int intStartRow, String strSheetName) {
		boolean blnEnd = false;
		for (int i = intStartRow; i <= sheet.getLastRowNum(); i++) {
			if (sheet.getRow(i) != null) {
				if (getCellValueAsString(wb, sheet.getRow(i).getCell(0)).equalsIgnoreCase("end")) {
					blnEnd = true;
					return i;
				}
			}
		}
		if (!blnEnd) {
			ALMFunctions.ThrowException("Test Data", "Test Data with End Tag should be found in the sheet " + sheet,
					"Error - Test Data with " + "End Tag does not exists in the sheet " + strSheetName, false);
		}
		return 0;
	}
/**
 * 
 *
Method Name : chooseWithdrawn
Description : Withdraw document from Approved for Use status
 @param strLabel
 @param strValue
 @param strPageName
Return Type : void
 */
	public void chooseWithdrawn(String strLabel, String strValue, String strPageName) {
		driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
		click(new Common(strLabel).rdoWithdrawn, lngMinTimeOutInSeconds, strLabel, strLabel,	strPageName, true);
		driverUtil.waitUntilStalenessOfElement(new Common(strLabel).rdoWithdrawn, strPageName);
		}
	/**
	 * 
	 *
	Method Name : approverUserName
	Description : Enter approver user name
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void approverUserName(String strLabel, String strValue, String strPageName) {
	clickByJS(Common.txtApproverUserName, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
	sendkeys(Common.txtApproverUserName, lngMinTimeOutInSeconds, strValue,strLabel, strPageName,true);
	driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
	driverUtil.waitUntilStalenessOfElement(Common.txtApproverUserName, strPageName);
	click(new Common(strLabel).scrollLabel, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
	}
	
	/**
	 * 
	 *
	Method Name : approverPassword
	Description : Enter approver password
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void approverPassword(String strLabel, String strValue, String strPageName) {
	String strDecryptedPWD = Password_Decrypter.decrypt(strValue.trim());
	click(new Common(strLabel).scrollLabel, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
	click(Common.txtApproverPassword, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
	sendkeys(Common.txtApproverPassword, lngMinTimeOutInSeconds, strDecryptedPWD,strLabel, strPageName,false);
	driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
	driverUtil.waitUntilStalenessOfElement(Common.txtApproverPassword, strPageName);
	}
	
	/**
	 * 
	 *
	Method Name : withdrawalNotes
	Description : Enter withdrawal notes
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void withdrawalNotes(String strLabel, String strValue, String strPageName) {
		sendkeys(Common.txtWithdrawalNotes, lngMinTimeOutInSeconds, strValue,strLabel, strPageName,true);
	}
	
	/**
	 * 
	 *
	Method Name : newOrRevise
	Description : Select New or Revise field
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void newOrRevise(String strLabel, String strValue, String strPageName)  {
	 sendkeys(Common.txtNewOrRevise, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
	 driverUtil.waitUntilStalenessOfElement(Common.txtNewOrRevise,strPageName);
	 clickByJS(new Common(strValue).selectMaterialIntent, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
	}
	
	/**
	 * 
	 *
	Method Name : choosePriority
	Description : Select priority for the document
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void choosePriority(String strLabel, String strValue, String strPageName)  {
		click(Common.txtPriority, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
		clear(Common.txtPriority, lngMinTimeOutInSeconds, strValue, strPageName);
		driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
		click(new Common(strValue).selectLifecycle, lngMaxInvisibilityTimeOutInSeconds, strLabel, strLabel, strPageName, true);
		driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
		}
	/**
	 * 
	 *
	Method Name : hasMaterialCode
	Description : Choose if the document has material code
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void hasMaterialCode(String strLabel, String strValue, String strPageName)  {
		 driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
		click(new Common(strValue).rdoHasMaterialCode, lngMinTimeOutInSeconds, strLabel, strValue,	strPageName, true);
		driverUtil.waitUntilStalenessOfElement(new Common(strValue).rdoHasMaterialCode, strPageName);
		}
	/**
	 * 
	 *
	Method Name : enterMaterialCode
	Description : Fille the material code field
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void enterMaterialCode(String strLabel, String strValue, String strPageName)  {
		 sendkeys(new Common(strLabel).txtMaterialCode, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
		 driverUtil.waitUntilStalenessOfElement(new Common(strLabel).txtMaterialCode,strPageName);
		}
/**
 * 
 *
Method Name : copyrightConfirmation
Description : Fill copyright confirmation field
 @param strLabel
 @param strValue
 @param strPageName
Return Type : void
 */
	public void copyrightConfirmation(String strLabel, String strValue, String strPageName)  {
		 sendkeys(Common.txtCopyright, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
		 driverUtil.waitUntilStalenessOfElement(Common.txtCopyright,strPageName);
		 clickByJS(new Common(strValue).selectMaterialIntent, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
		}
	/**
	 * 
	 *
	Method Name : useOfCorportateLogo
	Description : Fill Use of Corportate Logo field
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void useOfCorportateLogo(String strLabel, String strValue, String strPageName)  {
		 sendkeys(Common.txtCorporateLogo, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
		 driverUtil.waitUntilStalenessOfElement(Common.txtCorporateLogo,strPageName);
		 clickByJS(new Common(strValue).selectMaterialIntent, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
		}
	
	/**
	 * 
	 *
	Method Name : enter4DigitCostCenter
	Description : 
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void enter4DigitCostCenter(String strLabel, String strValue, String strPageName)  {
		 sendkeys(new Common(strLabel).txt4DigitCostCenter, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
		 driverUtil.waitUntilStalenessOfElement(new Common(strLabel).txt4DigitCostCenter,strPageName);
		}
	
	/**
	 * 
	 *
	Method Name : fillExaminer
	Description : Enter examiner ID to the examiner approver field
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void fillExaminer(String strLabel, String strValue, String strPageName) {
		clickByJS(new Common(strLabel).lblExaminer, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
		sendkeys(new Common(strLabel).txtExaminer, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
		clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
		driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
	}
	
	/**
	 * 
	 *
	Method Name : fillMedicalSignatory
	Description : Enter medical signatory  ID to the approver field
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void fillMedicalSignatory(String strLabel, String strValue, String strPageName) {
			clickByJS(new Common(strLabel).lblMedicalSignatory, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
			sendkeys(new Common(strLabel).txtMedicalSignatory, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
			clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
			driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
		}
	
	/**
	 * 
	 *
	Method Name : copayTermsIncluded
	Description : Select Yes or no for Copay Terms included field
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void copayTermsIncluded(String strLabel, String strValue, String strPageName)  {
		 driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
		click(new Common(strValue).rdoCoPayTerms, lngMinTimeOutInSeconds, strLabel, strValue,	strPageName, true);
		driverUtil.waitUntilStalenessOfElement(new Common(strValue).rdoCoPayTerms, strPageName);
		}
	
	/**
	 * 
	 *
	Method Name : selectMaterialHistory
	Description : Choose Material History for the document
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void selectMaterialHistory(String strLabel, String strValue, String strPageName)  {
     	sendkeys(Common.txtMaterialHistory, lngMinTimeOutInSeconds, strValue, strLabel,strPageName,true);
     	driverUtil.waitUntilStalenessOfElement(Common.txtMaterialHistory, strPageName);
     	clickByJS(new Common(strValue).lstMaterialHistory, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
	}
	
	/**
	 * 
	 *
	Method Name : prcDerivativeMaterial
	Description : Select yes or no for PRC Derivative Material field
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void prcDerivativeMaterial(String strLabel, String strValue, String strPageName)  {
		 driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
		click(new Common(strValue).rdoPRCDerivative, lngMinTimeOutInSeconds, strLabel, strValue,	strPageName, true);
		driverUtil.waitUntilStalenessOfElement(new Common(strValue).rdoPRCDerivative, strPageName);
		}
	/**
	 * 
	 *
	Method Name : fillPRCSpecialist
	Description : Enter values for PRC Specialist
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void fillPRCSpecialist(String strLabel, String strValue, String strPageName)  {
     	clickByJS(Common.lblPRCSpecialist, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
     	sendkeys(Common.txtPRCSpecialist, lngMinTimeOutInSeconds, strValue,strLabel ,strPageName,true);
     	clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
     	driverUtil.waitUntilStalenessOfElement(new Common(strValue).listBusinessOwner, strPageName);
     	driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
     	click(new Common(strLabel).scrollLabel, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
	}
	/**
	 * 
	 *
	Method Name : fillEditorialReviewer
	Description : Enter values for Editorial/Editorial Reviewer
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void fillEditorialReviewer(String strLabel, String strValue, String strPageName)  {
			clickByJS(new Common(strLabel).lblEditorialReviewer , lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
			sendkeys(new Common(strLabel).txtEditorialReviewer, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
			clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
			driverUtil.waitUntilStalenessOfElement(new Common(strValue).listBusinessOwner, strPageName);
	     	driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
	     	click(new Common(strLabel).scrollLabel, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
		}
	
	/**
	 * 
	 *
	Method Name : prcApprovalType
	Description : Select PRC Approval Type
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void prcApprovalType(String strLabel, String strValue, String strPageName)  {
		 sendkeys(new Common(strLabel).searchDocFields, lngMinTimeOutInSeconds, strValue,strLabel,strPageName);
		driverUtil.waitUntilStalenessOfElement(new Common(strLabel).searchDocFields, strPageName);
		clickByJS(new Common(strValue).selectMaterialIntent, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
		}
	
	/**
	 * 
	 *
	Method Name : fillContentCreator
	Description : Enter values for Content creator
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void fillContentCreator(String strLabel, String strValue, String strPageName)  {
			clickByJS(new Common(strLabel).lblContentCreator , lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
			clear(new Common(strLabel).txtContentCreator, lngMinTimeOutInSeconds, strValue, strPageName);
			sendkeys(new Common(strLabel).txtContentCreator, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
			clickByJS(new Common(strValue).listBusinessOwner, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
			driverUtil.waitUntilStalenessOfElement(new Common(strValue).listBusinessOwner, strPageName);
	     	driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
	     	click(new Common(strLabel).scrollLabel, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
		}
	
	/**
	 * 
	 *
	Method Name : proposedExpDate
	Description : Fill Proposed Expiration date
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void proposedExpDate(String strLabel, String strValue, String strPageName)  {
	clear(Common.txtProposedExpDate, lngMinTimeOutInSeconds, strValue, strPageName);
	sendkeys(Common.txtProposedExpDate, lngMinTimeOutInSeconds, strValue,strLabel,strPageName,true);
	click(new Common(strLabel).lblProposedExpDate, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, false);
	driverUtil.waitUntilStalenessOfElement(Common.txtProposedExpDate, strPageName);
	driverUtil.waitForPageLoad(lngMinTimeOutInSeconds, strPageName);
	}
	
	/**
	 * 
	 *
	Method Name : makeACopy
	Description : Select Make A Copy option from All Actions
	 @param strLabel
	 @param strValue
	 @param strPageName
	Return Type : void
	 */
	public void makeCopy(String strLabel, String strValue, String strPageName)  {
		 pagescroll(Common.btnAllActions, strPageName);
		clickByJS(Common.btnAllActions, lngMinTimeOutInSeconds, strLabel, strLabel, strPageName, true);
		pagescroll(new Common(strLabel).scrollLabel, strPageName);
		click(new Common(strLabel).scrollLabel, lngMinTimeOutInSeconds, strLabel, strLabel,	strPageName, true);
	}
	
}
