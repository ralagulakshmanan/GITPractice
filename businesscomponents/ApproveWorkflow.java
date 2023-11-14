package businesscomponents;

import com.cognizant.framework.Status;

import supportlibraries.ScriptHelper;
import uimap.Common;

public class ApproveWorkflow extends CommonFunctions{

	public ApproveWorkflow(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	
	
	private final long lngMinTimeOutInSeconds = Long.parseLong(properties.getProperty("MinObjectSyncTimeout"));
	
	public void createDocument() throws Exception {
		FillForm("CreateDocument", "Input_Parameters", "Create Document", "$", true, "Veeva Vault");
	}

	public void sendToBOReview() throws Exception {
		FillForm("SendToBOReview", "Input_Parameters", "Send to Business Owner review", "$", true, "Veeva Vault");
	}
	
	public void completeBOReview() throws Exception {
		FillForm("CompleteBOReview", "Input_Parameters", "Business Owner Review Complete", "$", true, "Veeva Vault");
	}
	
	public void readyforReviewApproval() throws Exception{
		FillForm("Parameter", "Input_Parameters", "Ready for Review/Approval", "$", true, "Veeva Vault");
	}
	
	public void sendToApproval() throws Exception{
		FillForm("SendToApproval", "Input_Parameters", "Send document to Approval", "$", true, "Veeva Vault");
	}
	
	public void completeApproval() throws Exception{
		FillForm("CompleteApproval", "Input_Parameters", "Complete Document Approval", "$", true, "Veeva Vault");
	}
	
	public void sendToReview() throws Exception{
		FillForm("SendToReview", "Input_Parameters", "Send document to Review", "$", true, "Veeva Vault");
	}
	
	public void completeReview() throws Exception{
		FillForm("CompleteReview", "Input_Parameters", "Complete Document Review", "$", true, "Veeva Vault");
	}
	
	public void completePRCMeeting() throws Exception{
		FillForm("PRCMeetingComplete", "Input_Parameters", "Complete Document PRC Meeting", "$", true, "Veeva Vault");
	}
	
	public void addReviewers() throws Exception{
		FillForm("AddReviewers", "Input_Parameters", "Add reviewers as coordinator", "$", true, "Veeva Vault");
	}
	
	public void readyForApproval() throws Exception{
		FillForm("ReadyForApproval", "Input_Parameters", "Ready for Approval", "$", true, "Veeva Vault");
	}
	
	public void addApprovers() throws Exception{
		FillForm("AddApprovers", "Input_Parameters", "Add approvers", "$", true, "Veeva Vault");
	}
	
	public void completeApprovedForUse() throws Exception{
		FillForm("CompleteApproval", "Input_Parameters", "Complete approval", "$", true, "Veeva Vault");
	}
	
	public void finalCheckBO() throws Exception{
		FillForm("BOFinalCheck", "Input_Parameters", "Business Owner final check", "$", true, "Veeva Vault");
	}
	
	public void addPreReviewer() throws Exception{
		FillForm("AddPreReviewer", "Input_Parameters", "Add PreReviewer", "$", true, "Veeva Vault");
	}
	
	public void completePreReview() throws Exception{
		FillForm("CompletePreReview", "Input_Parameters", "Complete Pre-review", "$", true, "Veeva Vault");
	}
	
	public void sendToCommOps() throws Exception{
		FillForm("SendToCommOps", "Input_Parameters", "Send To Comm Ops", "$", true, "Veeva Vault");
	}
	
	public void sendForGRCReview() throws Exception{
		FillForm("SendForGRCReview", "Input_Parameters", "Send For GRC Review", "$", true, "Veeva Vault");
	}
	
	public void completeGRCReview() throws Exception{
		FillForm("CompleteGRCReview", "Input_Parameters", "Complete GRC Review", "$", true, "Veeva Vault");
	}
	
	public void reviewOutcome() throws Exception{
		FillForm("ReviewOutcome", "Input_Parameters", "Proceed with Review outcome", "$", true, "Veeva Vault");
	}
	
	public void commOpsAssessOutcome() throws Exception{
		FillForm("CommOpsAssessOutcome", "Input_Parameters", "Comm Ops Assess Outcome", "$", true, "Veeva Vault");
	}
	
	public void checkExpirationDates() throws Exception{
		FillForm("CheckExpirationDates", "Input_Parameters", "Check Expiration Dates", "$", true, "Veeva Vault");
	}
	
	public void expireDocument() throws Exception{
		FillForm("ExpireDocument", "Input_Parameters", "Expire document", "$", true, "Veeva Vault");
	}
	
	public void withdrawDocument() throws Exception{
		FillForm("WithdrawDocument", "Input_Parameters", "Withdraw document", "$", true, "Veeva Vault");
	}
	
	public void examinerApproval() throws Exception{
			FillForm("ExaminerApproval", "Input_Parameters", "Approve as examiner", "$", true, "Veeva Vault");
		}
	
	public void medSignatoryApproval() throws Exception{
			FillForm("MedSignatoryApproval", "Input_Parameters", "Approve as medical signatory", "$", true, "Veeva Vault");
		}
	
	public void editorialQC() throws Exception{
			FillForm("EditorialQC", "Input_Parameters", "Send to editorial QC", "$", true, "Veeva Vault");
		}
	
	public void editorialQCComplete() throws Exception{
			FillForm("EditorialQCComplete", "Input_Parameters", "Complete editorial QC", "$", true, "Veeva Vault");
		}
	
	public void acceptChanges() throws Exception{
			FillForm("AcceptChanges", "Input_Parameters", "Accept changes as content creator", "$", true, "Veeva Vault");
		}
	
	public void boVerifyChanges() throws Exception{
			FillForm("BOVerifyChanges", "Input_Parameters", "BO verifies the changes made", "$", true, "Veeva Vault");
		}
	
	public void prcValidateChanges() throws Exception{
			FillForm("PRCValidateChanges", "Input_Parameters", "PRC validates the changes", "$", true, "Veeva Vault");
		}
	
	public void continuedUseReview() throws Exception{
			FillForm("ContinuedUseReview", "Input_Parameters", "Continued use review", "$", true, "Veeva Vault");
		}
	
	public void reviewMeeting() throws Exception{
			FillForm("ReviewMeeting", "Input_Parameters", "Requires Continued Use Review Meeting", "$", true, "Veeva Vault");
		}
	
	public void prcApproval() throws Exception{
			FillForm("PRCApproval", "Input_Parameters", "PRC approves the continued use review", "$", true, "Veeva Vault");
		}
	
	public void makeACopy() throws Exception{
			FillForm("MakeACopy", "Input_Parameters", "Make a copy of existing document", "$", true, "Veeva Vault");
		}
	
	public void approveWorkflow() throws Exception{
		
		String strWindowName=dataTable.getData("Approvers", "Window_Name");
		String strPageName=dataTable.getData("Approvers", "Page_name");
		String strPopUpName=dataTable.getData("Approvers", "Window_Name")+"PopUp";
		docNumberSearch("All Documents", dataTable.getData("Approvers", "Document_To_Search"), strPageName);
		if(objectExists(new Common("Accept").contentbutton, "isDisplayed", lngMinTimeOutInSeconds,"Accept", "Button", strPageName, false)){
			contentButton("Accept", strPageName);
			
		}
		contentButton("Complete", strPageName);
		
		/*if(objectExists(new Common(strWindowName,"Capacity").dialogSelect, "isDisplayed", lngMinTimeOutInSeconds, 
				"Capacity", "DropDown", strPageName, false)){*/
		if(!dataTable.getData("Approvers", "Capacity").isEmpty()){
			if(objectExists(new Common(strWindowName,"Capacity").dialogSelect, "isDisplayed", lngMinTimeOutInSeconds, 
					"Capacity", "DropDown", strPageName, false)){
			dialogSelect("Capacity", dataTable.getData("Approvers", "Capacity"), strWindowName, strPopUpName);
			}else if(objectExists(new Common(strWindowName,"Capacity Approver").dialogSelect, "isDisplayed", lngMinTimeOutInSeconds, 
					"Capacity Approver", "DropDown", strPageName, false)){
				dialogSelect("Capacity Approver", dataTable.getData("Approvers", "Capacity"), strWindowName, strPopUpName);	
				
			}else{
				
				report.updateTestLog("Check Dropdown", "No such dropdown found", Status.FAIL);
			}
			
		}
		
		popupradio(dataTable.getData("Approvers", "Review_verdict"),  strWindowName, strPopUpName);
		
		if(!dataTable.getData("Approvers", "User_Name").isEmpty()){
			
			dialogTextBox("User Name", dataTable.getData("Approvers", "User_Name"), strWindowName, strPageName);
			dialogTextBox("Password", dataTable.getData("Approvers", "Password"), strWindowName, strPageName);
		}
		ALMFunctions.Screenshot();
		driver.capture(strPageName);
		dialogButton ("Complete", strWindowName, strPopUpName);
		status(dataTable.getData("Approvers", "Status"), strPageName);
		
	}
}
