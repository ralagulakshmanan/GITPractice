package businesscomponents;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.cognizant.framework.Status;

import supportlibraries.ScriptHelper;
import uimap.notifications;

public class MailVerification extends CommonFunctions {

	public MailVerification(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}

	public final long lngPageLoadTimeOutInSeconds = Long.parseLong(properties.getProperty("MaxInvisibilityTimeout"));
	private final long lngTimeOutInSeconds = Long.parseLong(properties.getProperty("PageLoadTimeout"));
	private final long lngStaleTimeOutInSeconds = Long.parseLong(properties.getProperty("PageLoadTimeout"));
	private final long lngMaxTimeOutInSeconds = Long.parseLong(properties.getProperty("MaxObjectSyncTimeout"));
	/**
	 * Method to launch outlook URL and Redirect to Outlook homepage using SSO
	 */
	public void outlookLogin() {

		String strSheetName = "Notification";
		driver.get(dataTable.getData(strSheetName, "Application_URL"));
		report.updateTestLog("Environment", "TEST", Status.DONE);
		report.updateTestLog("Invoke Application",
				"Invoked the application under test @ " + dataTable.getData(strSheetName, "Application_URL"),
				Status.DONE);

		driverUtil.waitForPageLoad(lngPageLoadTimeOutInSeconds, "Outlook HomePage");

		if (objectExists(notifications.txtStaySigned, "isDisplayed", lngTimeOutInSeconds, "Agreement", "Pop upt",
				"Outlook Login Page", false)) {
			clickByJS(notifications.btnStaySignedNo, lngTimeOutInSeconds, "No", "button", "Outlook SIgn In Page", true);
		}
		driverUtil.waitForPageLoad(lngPageLoadTimeOutInSeconds, "Outlook HomePage");

	}

	/**
	 * Method to Select folder in the outlook homepage
	 * 
	 * @param strFolderName
	 */
	public void selectFolder(String [] strFolderName) {
		click(new notifications(strFolderName[0]).folderExpand, lngTimeOutInSeconds, "Folder Name", "button",
				"Outlook Home Page", true);
		//driverUtil.waitForPageLoad(lngPageLoadTimeOutInSeconds, "WebMail Home");
	/*	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		click(new notifications(strFolderName[1]).folderName, lngTimeOutInSeconds, "Folder Name", "button",
				"Outlook Home Page", true);	
		driverUtil.waitForPageLoad(lngPageLoadTimeOutInSeconds, "WebMail Home");
	}

	/**
	 * Method to launch outlook, select folder and select the particular mail based
	 * on the mail subject and timestamp
	 * 
	 * @throws ParseException
	 */
//	public void gileadMailSelection() throws ParseException
//	{
//		String strSheetName = "Notification";
////		String strPageName = "Web Outlook Page";
//		String url = dataTable.getData(strSheetName, "Application_URL");
//		String strFolderName = dataTable.getData(strSheetName, "Folder_Name");
//		String strSubject = dataTable.getData(strSheetName, "Mail_Subject");
//		Long mailSentTimeInSeconds = Long.parseLong(dataTable.getData(strSheetName, "CounterMailSentTime"));
//		int bufferTime = 180;
//
//		outlookLogin();
//		selectFolder(strFolderName);
////		selectMailBySubjectAndTimestamp(strSubject, mailSentTimeInSeconds, bufferTime);
//		
////		ApttusOperations objApttus = new ApttusOperations(scriptHelper);
////		String mailBody = objApttus.tempAgreementName;
//		String mailBody = ApttusOperations.tempAgreementName;
//		selectMailBySubjectAndBody(strSubject, mailBody);
//	}

//	/**
//	 * Method to launch outlook, select folder and select the particular mail based on the mail subject and timestamp
//	 * @throws ParseException
//	 */
//	public void counterPartyMailSelection() throws ParseException {
//		String strSheetName = "Notification";
//		String strFolderName = dataTable.getData(strSheetName, "Folder_Name");
//		String strSubject = dataTable.getData(strSheetName, "Mail_Subject");
////		Long mailSentTimeInSeconds = Long.parseLong(dataTable.getData(strSheetName, "ActualMailSentTime"));
//		int bufferTime =1000;
//
//		
//		outlookLogin();
//		selectFolder(strFolderName);
////		selectMailBySubjectAndTimestamp(strSubject, mailSentTimeInSeconds, bufferTime);
//		
////		ApttusOperations objApttus = new ApttusOperations(scriptHelper);
////		String mailBody = objApttus.tempAgreementName;
//		String mailBody = ApttusOperations.tempAgreementName;
//		selectMailBySubjectAndBody(strSubject, mailBody);
//
//	}
//	

	/**
	 * Method to select a mail in folder with mail subject and content of mail body
	 * 
	 * @param strSubject : Subject of the Mail
	 * @param strBody    : Body/Content of the mail
	 */
	public void selectMailBySubject(String strSubject) {
		String strPageName = "Web Outlook Page";
		boolean blnSubjectFound = false;
		long startTime = System.currentTimeMillis();
		WebElement ele = null;
		boolean blnMailFound = false;

		int intlooponce = 1;
		By tableRowsLoc = new notifications(strSubject).tableRowsFilterWithSubject;
		if (driverUtil.waitUntilElementLocated(tableRowsLoc, lngStaleTimeOutInSeconds, "Table", "Rows", "WebMail Home",
				false)) {
			Do: do {
				List<WebElement> visibleMail = driver.getWebDriver().findElements(tableRowsLoc);
				if (intlooponce == 1) {
					for (WebElement eleVisibleMail : visibleMail) {

						new Actions(driver.getWebDriver()).moveToElement(eleVisibleMail).click().build().perform();
						driverUtil.waitForPageLoad(lngPageLoadTimeOutInSeconds, strPageName);
						boolean blnMailPresence = objectExists(new notifications(strSubject).mailSubject, "isDisplayed",
								lngStaleTimeOutInSeconds, "Mail Subject", "Text", strPageName, false);
						if (blnMailPresence) {

							ALMFunctions.UpdateReportLogAndALMForPassStatus("Verify if mail Received",
									"User should receive the mail", "Mail is received", true);
							blnMailFound = true;
							break Do;
						}

					}
				} else {

					for (WebElement eleVisibleMail : visibleMail) {
						new Actions(driver.getWebDriver()).moveToElement(eleVisibleMail).click().build().perform();
						boolean blnMailPresence = objectExists(new notifications(strSubject).mailSubject, "isDisplayed",
								lngStaleTimeOutInSeconds, "Mail Subject", "Text", strPageName, false);

						if (blnMailPresence) {
							ALMFunctions.UpdateReportLogAndALMForPassStatus("Verify if mail Received",
									"User should receive the mail", "Mail is received", true);
							blnMailFound = true;
							break Do;

						}

					}

				}
				intlooponce = intlooponce + 1;

			} while (!(blnSubjectFound));

			if (!blnMailFound) {
				ALMFunctions.ThrowException("Error", "Mails should be present in the Mail Box",
						"Mails with requied subject is not found", true);
			}
		}

	}

//	
//	/**
//	 * Method to select a mail in folder with mail subject in particular time range
//	 * @param strSubject, 
//	 * @param mailSentTimeInSeconds
//	 * @param bufferTime
//	 * @throws ParseException
//	 */
//	public void selectMailBySubjectAndTimestamp(String strSubject, Long mailSentTimeInSeconds, int bufferTime) throws ParseException
//	{
//		String strPageName = "Web Outlook Page";
//		boolean blnSubjectFound = false;
//		long startTime = System.currentTimeMillis();
//		WebElement ele = null;
//		boolean blnMailFound = false;
//		
//		
//		int intlooponce = 1;
//
//		By tableRowsLoc = new notifications(strSubject).tableRowsFilterWithSubject;
//		if (driverUtil.waitUntilElementLocated(tableRowsLoc, lngTimeOutInSeconds, "Table", "Rows", "WebMail Home", false,
//				false)) {
//			Do: do
//
//			{
//
//				List<WebElement> visibleMail = driver.getWebDriver().findElements(tableRowsLoc);
//				if (intlooponce == 1) {
//					for (WebElement eleVisibleMail : visibleMail) {
//
//						new Actions(driver.getWebDriver()).moveToElement(eleVisibleMail).click().build().perform();
//						String strMailTimeStamp = getText(uimap.notifications.timeStampOfMail, lngTimeOutInSeconds,
//								"Mail TimeStamp", strPageName).trim();
//						SimpleDateFormat sdfForStrDate = new SimpleDateFormat("EEE MM/dd/yyyy hh:mm a");
//						Date date = sdfForStrDate.parse(strMailTimeStamp);
//						Long mailReceivedTimeInSeconds = date.getTime() / 1000;
//
//
//						Long condition = mailSentTimeInSeconds - mailReceivedTimeInSeconds;
//						System.out.println(mailSentTimeInSeconds);
//						System.out.println(mailReceivedTimeInSeconds);
//						System.out.println(condition);
//						System.out.println(Math.abs(condition));
//
//						if (Math.abs(condition) < bufferTime) {
//
//							driver.capture();
//							ele = eleVisibleMail;
//							blnMailFound = true;
//							windowName.clear();
//							windowName.put("Window1", driver.getWindowHandle());
//							click(uimap.notifications.docusignLink, lngTimeOutInSeconds, "Docusign Link", "hyper Link",
//									"Mail", true);
//							manageAndSwitchNewWindow();
////							switchToWindowByTitleInMail(lngPageLoadTimeOutInSeconds,
////									dataTable.getData("TAP_Agreement", "DocuSignPageTitle"), "Docusign", true, true);
//							driverUtil.waitUntilElementVisible(uimap.Docusign.txtWelcomeGreeting, lngPageLoadTimeOutInSeconds, "Welcome Text", "Text", "Docusign Home Page", true, true);
//							String actualText = getText(uimap.Docusign.txtWelcomeGreeting, lngTimeOutInSeconds, "Welcome Text", "Docusign Home Page");
//							ALMFunctions.UpdateReportLogAndALMForPassStatus("Welcome Text", "Expected Value :"+actualText,"Actual Value :"+actualText , true);
//							
//							break Do;
//
//						}
//
//					}
//				} else {
//
//					for (WebElement eleVisibleMail : visibleMail) {
//						new Actions(driver.getWebDriver()).moveToElement(eleVisibleMail).click().build().perform();
//						String strMailTimeStamp = getText(uimap.notifications.timeStampOfMail, lngTimeOutInSeconds,
//								"Mail TimeStamp", strPageName).trim();
//						SimpleDateFormat sdfForStrDate = new SimpleDateFormat("EEE MM/dd/yyyy hh:mm a");
//						Date date = sdfForStrDate.parse(strMailTimeStamp);
//						Long mailReceivedTimeInSeconds = date.getTime() / 1000;
//						if (Math.abs(mailSentTimeInSeconds - mailReceivedTimeInSeconds) < bufferTime) {
//
//							ele = eleVisibleMail;
//							blnMailFound = true;
//							click(uimap.notifications.docusignLink, lngTimeOutInSeconds, "Docusign Link", "hyper Link",
//									"Mail", true);
//							switchToWindowByTitleInMail(lngPageLoadTimeOutInSeconds,
//									dataTable.getData("TAP_Agreement", "DocuSignPageTitle"), "Docusign", true, true);
//							By docusignWelcomeMsg = uimap.Docusign.txtWelcomeGreeting;
//							driverUtil.waitUntilElementVisible(docusignWelcomeMsg, 30, "Submission Confirmation", "Text", "Form Filling", true, true);
//							String actualDemo = getText(docusignWelcomeMsg, lngTimeOutInSeconds, "Submission Confirmation", "Form Filling");
//							ALMFunctions.UpdateReportLogAndALMForPassStatus("Welcome Msg", "Expected Value :"+actualDemo,"Actual Value :"+actualDemo , true);
//							
//							
//							
//							break Do;
//
//						}
//
//					}
//
//				}
//				intlooponce = intlooponce + 1;
//
//			} while (!(blnSubjectFound || !(System.currentTimeMillis() - startTime < mailNotificationTimeOut)));
//		
//		if(!blnMailFound) {
//			ALMFunctions.ThrowException("Mail Not Found", "Mails should be present in the home page", "Mails with requied Timestamp is not Found", true);
//		}
//
//		} else {
//			ALMFunctions.ThrowException("Error", "Mails should be present in the home page", "Mail box is empty", true);
//		}
//
//		
//	}
//	
//	public void switchViewPane(String strViewName, String strPageName) {
//		driverUtil.waitForPageLoad(lngPageLoadTimeOutInSeconds, strPageName);
//		boolean blnToolbarPresence = objectExists(uimap.notifications.btnViewPaneToolBar, "isDisplayed", lngMinObjectSyncTimeOut, "Switch View Pane", "Tool Bar", strPageName, false, false);
//		if(blnToolbarPresence){
//			click(new notifications(strViewName).btnInToolBar, lngTimeOutInSeconds, strViewName, "View", strPageName, true);
//			driverUtil.waitForPageLoad(lngPageLoadTimeOutInSeconds, strPageName);
//			driver.capture("Switching View Pane", "View Pane is Switched to \""+strViewName + "\"");
//		}else {
//			report.updateTestLog("Switching View Pane", "View Pane is not available for the current selected Folder", Status.DONE);
//		}
//	}
//	public boolean verifyMailSubjectFound(By locMailSubject, String steViewName, String steMailSubject, String strPageName) {
//		boolean blnMailSubject = false;
//		switchViewPane(steViewName, strPageName);
//		blnMailSubject = driverUtil.waitUntilElementLocated(locMailSubject, lngTimeOutInSeconds, steMailSubject, "Mails", strPageName, false,false);
//		if(blnMailSubject){
//			return blnMailSubject;
//		}else{
//		driver.navigate().refresh();
//		blnMailSubject = driverUtil.waitUntilElementLocated(locMailSubject, lngStaleTimeOutInSeconds, steMailSubject, "Mails", strPageName, false,false);
//		
//		return blnMailSubject;
//		}
//		
//	}
//	
//	/**
//	 * Method to select a mail in folder with mail subject and content of mail body
//	 * @param strSubject : Subject of the Mail
//	 * @param strBody : Body/Content of the mail
//	 */
//	public void selectMailBySubjectAndBodyDummy(String strSubject, String strUniqueIdentifierInBody, String strPageName)	{
//		boolean blnSubjectFound = false;
//		WebElement ele = null;
//		boolean blnMailFound = false;
//		
//		By tableRowsLoc = new notifications(strSubject).tableRowsFilterWithSubject;
//
//		blnSubjectFound = verifyMailSubjectFound(tableRowsLoc, "Focused", strSubject, strPageName);
//		if(!blnSubjectFound){
//			blnSubjectFound = verifyMailSubjectFound(tableRowsLoc, "Other", strSubject, strPageName);
//		}
//			
//		int intlooponce = 1;
//		long startTime = System.currentTimeMillis();
//		if (blnSubjectFound) {
//			Do: do
//			{
//				List<WebElement> visibleMails = driver.getWebDriver().findElements(tableRowsLoc);
//				if (intlooponce <= 2) {
//					for (WebElement eleVisibleMail : visibleMails) {
//
//						new Actions(driver.getWebDriver()).moveToElement(eleVisibleMail).click().build().perform();
//						driverUtil.waitForPageLoad(lngPageLoadTimeOutInSeconds, strPageName);
//						boolean blnMailPresence = objectExists(new notifications(strUniqueIdentifierInBody).mailBody, "isDisplayed", lngStaleTimeOutInSeconds, "Mail Body", "Unique Identifier Text", strPageName, false, false);
//						if (blnMailPresence) {
//							boolean blnMailSelected = driverUtil.WaitUntilElementAtributeText(eleVisibleMail, lngStaleTimeOutInSeconds, strSubject, "Mail Body", true, "aria-selected", "true", false);
//							if(blnMailSelected){
//							report.updateTestLog("Mail Selected : ", "Mail with Subject \""+strSubject+"\" is Selected", Status.PASS);	
//							ele = eleVisibleMail;
//							blnMailFound = true;
//							break Do;
//							}else {
//								report.updateTestLog("Mail Selection", "Unable to select Mail with Subject \""+strSubject+"\"", Status.FAIL);
//							}	
//						}
//
//					}
//				}
//				intlooponce = intlooponce + 1;
//				driver.navigate().refresh();
//
//			} while (!(blnSubjectFound || !(System.currentTimeMillis() - startTime < mailNotificationTimeOut)));
//		
//		if(!blnMailFound) {
//			ALMFunctions.ThrowException("Mail Not Found", "Mails should be present in the home page", "Mails with requied Timestamp is not Found", true);
//		}
//
//		} else {
//			ALMFunctions.ThrowException("Error", "Mails should be present in the Mail Box", "Mails with requied subject is not found", true);
//		}
//
//		
//	}
//
//	
//	
//	
//	
//	
	public void verifyMailReceived() {
		String strPageName = "Outlook Web Home Page";
		String strSheetName = "Notification";
//		String[] strMailBody = dataTable.getData(strSheetName, "Mail_Body").split("!");
		String strSubject = dataTable.getData(strSheetName, "Mail_Subject");
//		String strUniqueIdentifier = dataTable.getData(strSheetName, "UniqueIdentifier");
		String [] strFolderName = dataTable.getData(strSheetName, "Folder_Name").split(",");

		outlookLogin();
		selectFolder(strFolderName);
//		selectMailBySubjectAndBodyDummy(strSubject, strUniqueIdentifier, strPageName);
		selectMailBySubject(strSubject);

//		verifyMailReceived(strSubject, strMailBody, strUniqueIdentifier, strPageName);
	}
//	
//	
//	public boolean verifyMailReceived(String strExpectedMailSubject, String[] strBodyMessage, String strUniqueIdentifier, String strPageName) {
//		
//		String strActualMailSubject = "";
//		boolean blnRecordFound = true;
//		boolean blnBodyMessage = false;
//		String strExpectedbodyMsg = "";
//		String bodyMessage = ""; //TO DO:
//		
//		
//		strActualMailSubject = getAttributeValue(new notifications(strExpectedMailSubject).mailSubject, "title", lngTimeOutInSeconds, "Selected Mail Subject", "Mail Reading Pane");
//		
//		if(strActualMailSubject.contains(strExpectedMailSubject)){
//			report.updateTestLog("Verify Mail Subject",
//					"Expected : <br> Mail Subject should be displayed as \"" + strExpectedMailSubject
//							+ "\"<br>" + "<br>" + "Actual : <br>Mail Subject is displayed as \""
//							+ strActualMailSubject + "\"",
//					Status.PASS);
//		}else {
//			report.updateTestLog("Verify Mail Subject",
//					"Expected : <br> Mail Subject should be displayed as \"" + strExpectedMailSubject
//							+ "\"<br>" + "<br>" + "Actual : <br>Mail Subject is displayed as \""
//							+ strActualMailSubject + "\"",
//					Status.FAIL);
//		}
//		
//
//		int a = 0;
//		try {
//			
//			for(String msg : strBodyMessage)
//			{
//				boolean blnTextPresence = driverUtil.waitUntilElementLocated(new notifications(msg).mailBody, lngMinObjectSyncTimeOut, strActualMailSubject, strExpectedbodyMsg, strPageName, false, false);
//				strExpectedbodyMsg = strExpectedbodyMsg + msg + "\n";
//
//				if(blnTextPresence) {
//					a = a + 1;
//				}
//				
//			}
//			
//			
////			List<WebElement> elements = driver.getWebDriver().findElements(uimap.notifications.txtInMailBody);
////				for (WebElement ele : elements) {
////					String strValue = ele.getText();
////					bodyMessage = bodyMessage + strValue;
////					System.out.println(bodyMessage);
////			} 
////			if (blnRecordFound) {
////				for (String strMailbodyValue : strBodyMessage) {
////
////					if (bodyMessage.contains(strMailbodyValue)) {
////						strExpectedbodyMsg = strExpectedbodyMsg + strMailbodyValue;
////						blnRecordFound = true;
////						a = a + 1;
////					}
////				}
//
//				if (a == strBodyMessage.length) {
//					blnBodyMessage = true;
//					report.updateTestLog("Outlook notification", "Expected Outlook Notification : <br>" +"<br>"+ strExpectedbodyMsg +
//							 "<br>"+"<br>"+ "Actual Outlook Notification : <br>" +"<br>"+ bodyMessage, Status.PASS);
//
//				} else {
//
//					report.updateTestLog("Outlook notification", "Expected Outlook Notification : <br>" +"<br>"+ strExpectedbodyMsg +
//							 "<br>" +"<br>"+ "Actual Outlook Notification : <br>" +"<br>"+ bodyMessage, Status.FAIL);
//
//				}
////			}
//
//			return blnBodyMessage;
//		}catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return true;
//
//
//	}
//
//	
//	public void outlookLogOn() throws ParseException {
//		String strSheetName = "Notification";
//		String strPageName = "Web Outlook Page";
//		boolean blnSubjectFound = false;
//		long startTime = System.currentTimeMillis();
//		
//		String strFolderName = dataTable.getData(strSheetName, "Folder_Name");
//		String strSubject = dataTable.getData(strSheetName, "Mail_Subject");
//		Long mailSentTimeInSeconds = Long.parseLong(dataTable.getData(strSheetName, "MailSentTime"));
//		WebElement ele = null;
//		boolean blnMailFound = false;
//
//		System.out.println(dataTable.getData(strSheetName, "Application_URL"));
//		driver.get(dataTable.getData(strSheetName, "Application_URL"));
//		report.updateTestLog("Environment", "TEST", Status.DONE);
//		report.updateTestLog("Invoke Application",
//				"Invoked the application under test @ " + dataTable.getData(strSheetName, "Application_URL"), Status.DONE);
//		driverUtil.waitForPageLoad(lngPageLoadTimeOutInSeconds, "Login");
//
//		objectExists(notifications.txtStaySigned, "isDisplayed", lngTimeOutInSeconds, "Agreement", "Search Result",
//				"Apttus Home Page", false, false);
//		click(notifications.btnStaySignedNo, lngTimeOutInSeconds, "No", "button", "Outlook SIgn In Page", true);
//		click(new notifications(strFolderName).folderName, lngTimeOutInSeconds, "Folder Name", "button",
//				"Outlook Home Page", true);
//
//		driverUtil.waitUntilPageReadyStateComplete(lngPageLoadTimeOutInSeconds, "WebMail Home");
//
//		int intlooponce = 1;
//
//		By tableRowsLoc = new notifications(strSubject).tableRowsFilterWithSubject;
//		if (driverUtil.waitUntilElementLocated(tableRowsLoc, lngTimeOutInSeconds, "Table", "Rows", "WebMail Home", false,
//				false)) {
//			Do: do
//
//			{
//
//				List<WebElement> visibleMail = driver.getWebDriver().findElements(tableRowsLoc);
//				if (intlooponce == 1) {
//					for (WebElement eleVisibleMail : visibleMail) {
//
//						new Actions(driver.getWebDriver()).moveToElement(eleVisibleMail).click().build().perform();
//						String strMailTimeStamp = getText(uimap.notifications.timeStampOfMail, lngTimeOutInSeconds,
//								"Mail TimeStamp", strPageName).trim();
//						System.out.println(strMailTimeStamp);
//						SimpleDateFormat sdfForStrDate = new SimpleDateFormat("EEE MM/dd/yyyy hh:mm a");
//						Date date = sdfForStrDate.parse(strMailTimeStamp);
//						Long mailReceivedTimeInSeconds = date.getTime() / 1000;
//						Long condition = mailSentTimeInSeconds - mailReceivedTimeInSeconds;
//						if (Math.abs(condition) < 180) {
//
//							ele = eleVisibleMail;
//							blnMailFound = true;
//							click(uimap.notifications.docusignLink, lngTimeOutInSeconds, "Docusign Link", "hyper Link",
//									"Mail", true);
//							switchToWindowByTitleInMail(lngPageLoadTimeOutInSeconds,
//									dataTable.getData("TAP_Agreement", "DocuSignPageTitle"), "Docusign", true, true);
//							driverUtil.waitUntilElementVisible(uimap.Docusign.txtWelcomeGreeting, lngPageLoadTimeOutInSeconds, "Submission Confirmation", "Text", "Form Filling", true, true);
//							String actualText = getText(uimap.Docusign.txtWelcomeGreeting, lngTimeOutInSeconds, "Welcome Text", "Docusign Home Page");
//							ALMFunctions.UpdateReportLogAndALMForPassStatus("Welcome Text", "Expected Value :"+actualText,"Actual Value :"+actualText , true);
//							
//							break Do;
//
//						}
//
//					}
//				} else {
//
//					for (WebElement eleVisibleMail : visibleMail) {
//						new Actions(driver.getWebDriver()).moveToElement(eleVisibleMail).click().build().perform();
//						String strMailTimeStamp = getText(uimap.notifications.timeStampOfMail, lngTimeOutInSeconds,
//								"Mail TimeStamp", strPageName).trim();
//						SimpleDateFormat sdfForStrDate = new SimpleDateFormat("EEE MM/dd/yyyy hh:mm a");
//						Date date = sdfForStrDate.parse(strMailTimeStamp);
//						Long mailReceivedTimeInSeconds = date.getTime() / 1000;
//						if (Math.abs(mailSentTimeInSeconds - mailReceivedTimeInSeconds) < 120) {
//
//							ele = eleVisibleMail;
//							blnMailFound = true;
//							click(uimap.notifications.docusignLink, lngTimeOutInSeconds, "Docusign Link", "hyper Link",
//									"Mail", true);
//							switchToWindowByTitleInMail(lngPageLoadTimeOutInSeconds,
//									dataTable.getData("TAP_Agreement", "DocuSignPageTitle"), "Docusign", true, true);
//							By docusignWelcomeMsg = uimap.Docusign.txtWelcomeGreeting;
//							driverUtil.waitUntilElementVisible(docusignWelcomeMsg, 30, "Submission Confirmation", "Text", "Form Filling", true, true);
//							String actualDemo = getText(docusignWelcomeMsg, lngTimeOutInSeconds, "Submission Confirmation", "Form Filling");
//							ALMFunctions.UpdateReportLogAndALMForPassStatus("Welcome Msg", "Expected Value :"+actualDemo,"Actual Value :"+actualDemo , true);
//								
//							break Do;
//
//						}
//
//					}
//
//				}
//				intlooponce = intlooponce + 1;
//
//			} while (!(blnSubjectFound || !(System.currentTimeMillis() - startTime < mailNotificationTimeOut)));
//
//		} else {
//			ALMFunctions.ThrowException("Error", "Mails should be present in the home page", "Mail box is empty", true);
//		}
//
//		driverUtil.waitUntilPageReadyStateComplete(lngPageLoadTimeOutInSeconds, "DocuSign Home");
//	}
//
//	
//	
//
//	/**
//	 * @param lngPageTimeOutInSeconds
//	 * @param strWindowHandle
//	 * @param strWindowName
//	 * @param blnLog
//	 * @param blnThrowException
//	 */
//	public void switchToWindowByTitleInMail(long lngPageTimeOutInSeconds, String strWindowTitle, String strWindowName,
//			boolean blnLog, boolean blnThrowException) {
////		if (driverUtil.waitUntilWindowCountAvailable(2, strWindowName, lngPageTimeOutInSeconds, blnLog,
////				blnThrowException)) {
//			Set<String> handles = driver.getWindowHandles();
//			for (String windowHandle : handles) {
//				driver.switchTo().window(windowHandle);
//				
//				String strCurrentWindowTitle = driver.getTitle();
//				ALMFunctions.UpdateReportLogAndALMForPassStatus("Title:", "Expected Value :" + strWindowTitle,"Actual Value :"+strCurrentWindowTitle , true);
//				if (strCurrentWindowTitle.equalsIgnoreCase(strWindowTitle)) {
//					driver.manage().window().maximize();
//					break;
//				}
//			}
////		}
//	}
}
