package uimap;

import org.openqa.selenium.By;

/**
 * UI Map for Common Objects
 */
public class Common {

	public By environmentselector;
	public By tabitem;
	public By menuitem;
	public By lookupicon;
	public By dialogSelect;
	public By dialogbutton;
	public By headerbutton;
	public By textarea;
	public By textbox;
	public By radiobutton;
	public By contentbutton;
	public By treeview;
	public By expand;
	public By dccdocumentsearch;
	public By documentsearch;
	public By checkbox;
	public By dialogtextbox;
	public By removebutton;
	public By listitem;
	public By dialogRadio;
	public By settingicon;
	public By link;
	public By popupradio;
	public By popudropdown;
	public By drag, reqtextbox, reqlookupicon,readonly,readonly2,reqDropDown,dialogtextarea;
	public By scrollLabel,btnDocType,drpdwnType,drpdwnSubType,searchDocFields,selectDocFields,
					selectLifecycle,selectDocDetails,selectMaterialIntent,selectAudience,selectDissemination,
					selectFinalForm,listBusinessOwner,listCoordinator,selectApprovalType,selectLanguage,
					btnComplete,drpdwnStartWorkflow,btnWorkflowOK,chkReviewers,
					lstApprover,txtApprover,lblApprover,lblReviewer,txtReviewer,lblProjectManager,
					txtProjectManager,rdoApproved, lblMedicalReviewer, txtMedicalReviewer, lblLegalReviewer, 
					 txtLegalReviewer, lblRegulatoryReviewer, txtRegulatoryReviewer, 
					 lblCommercialReviewer, txtCommercialReviewer, lblOtherReviewer, txtOtherReviewer,
					 lblOtherApprover, txtOtherApprover,rdoApprovedForUse,lblPreReviewer,txtPreReviewer,
					 lblCommOps,txtCommOps,lblEditorialReviewer,txtEditorialReviewer,lstReviewDeadline,
					 rdoWithdrawn,rdoHasMaterialCode,txtMaterialCode,txt4DigitCostCenter,
					 lblExaminer, txtExaminer,lblPlannedFirstUseDate,lblMedicalSignatory,
					 txtMedicalSignatory,rdoCoPayTerms,lstMaterialHistory,rdoPRCDerivative,txtContentCreator,
					 lblContentCreator,lblProposedExpDate,drpdwnReason;

	public Common(String strLabel) {

		environmentselector = By.xpath(
				"//*[@id='pageBody']//descendant::*[not(@id='hiddenElements')]//*[@class='tilesBody']//*[normalize-space(text())=\""
						+ strLabel
						+ "\"]//ancestor-or-self::*[contains(@class,'widget_header')]//span[contains(@class,'text')]");
		tabitem = By.xpath(
				".//*[@id='pageBody']//descendant::*[not(@id='hiddenElements')]//*[contains(@id,'TabGroup')]//*[normalize-space(text())=\""
						+ strLabel + "\"]//ancestor-or-self::*[contains(@class,'tabBarItem')]");
		menuitem = By.xpath("//*[contains(@class,'menu')]//*[normalize-space(.)=\"" + strLabel
				+ "\"]//ancestor-or-self::*[contains(@class,'menuItem') or @role='menuitem' or contains(@class,'menu-item')]");
		lookupicon = By.xpath(
				".//*[@id='pageBody']//descendant::*[not(@id='hiddenElements')]//descendant::*[@id='body']//*[normalize-space(translate(text(), '*', ''))=\""
						+ strLabel
						+ "\"]//ancestor::*[contains(@class,'Container') or contains(@class,'upload_form')][1]//descendant::*[contains(@class,'icon_button')]");

		headerbutton = By.xpath(
				".//*[@id='pageBody']//descendant::*[not(@id='hiddenElements')]//*[normalize-space(text())=\"" + strLabel
						+ "\"]//ancestor::*[contains(@class,'btn') and (local-name()='a' or local-name()='button')]");
		textarea = By.xpath(
				"//*[@id='pageBody']//descendant::*[not(@id='hiddenElements')]//descendant::*[@id='body']//*[normalize-space(translate(text(), '*', ''))=\""
						+ strLabel
						+ "\"]//ancestor-or-self::*[contains(@class,'inputContainer') or contains(@class,'field')]//descendant::textarea");
		textbox = By.xpath("//*[@id='pageBody']//descendant::*[not(@id='hiddenElements')]//descendant::*[@id='body']//*[normalize-space(text())=\""
				+ strLabel
				+ "\"]//ancestor-or-self::*[contains(@class,'inputContainer') or contains(@class,'field') ]//descendant::input[@type='text' or @role='textbox']");
		
		contentbutton = By.xpath(
				"//*[@id='pageBody']//descendant::*[(not(@id='hiddenElements')) and contains(@class,'page')]//descendant::*[@id='body']//*[normalize-space(text())=\""
						+ strLabel+ "\"]");
		treeview = By.xpath(
				"//*[@id='pageBody']//descendant::*[(not(@id='hiddenElements')) and contains(@class,'page')]//descendant::*[@id='body']//*[normalize-space(text())=\""
						+ strLabel
						+ "\"]//ancestor::*[contains(@class,'Section')]//descendant::*[contains(@class,'svg')][1]");
		expand = By.xpath(
				"//*[@id='pageBody']//descendant::*[(not(@id='hiddenElements')) and contains(@class,'page')]//descendant::*[@id='body']//*[normalize-space(text())=\""
						+ strLabel
						+ "\"]//ancestor::*[contains(@class,'expanderTarget') or contains(@class,'gridview_header')]//descendant::*[@role='img']");

		removebutton = By.xpath(
				"//*[contains(@class,'dialog-content') and contains(@class,'widget-content')]//*[normalize-space(translate(text(), '*', ''))=\""
						+ strLabel
						+ "\"]//ancestor::*[contains(@class,'fieldRow') or contains(@class,'container')]//descendant::*[contains(@class,'container')]//descendant::*[contains(@class,'removeItem')]//*[@role='img']");
		documentsearch = By.xpath("//a[contains(.,\"" + strLabel
				+ "\") and (contains(@class,'doc_title') or contains(@class,'data-name'))]");
		settingicon = By.xpath("//*[contains(@class,'menu')][@title=\"" + strLabel + "\"]");
		link = By.xpath(
				"//*[@id='pageBody']//descendant::*[(not(@id='hiddenElements')) and contains(@class,'page')]//descendant::*[@id='body']//*[normalize-space(text())=\""
						+ strLabel + "\" and contains(@class,'link')]");
		drag = By.xpath("//*[text()=\"" + strLabel
				+ "\"]//ancestor::*[contains(@class,'titlebar') and contains(@class,'widget-header')]");

		dccdocumentsearch = By.xpath("//*[contains(@class,'gridView')]//descendant::*[text()=\"" + strLabel + "\"]");
		reqDropDown= By.xpath("//*[@id='pageBody']//descendant::*[not(@id='hiddenElements')]//descendant::*[@id='body']//*[normalize-space(text())=\""
				+ strLabel
				+ "\"]//ancestor-or-self::*[contains(@class,'inputContainer') or contains(@class,'field') ]//descendant::div[contains(@title,\"" + strLabel + "\")]");
		reqlookupicon = By.xpath(
				".//*[@id='pageBody']//descendant::*[not(@id='hiddenElements')]//descendant::*[@id='body']//*[normalize-space(translate(text(), '*', ''))=\""
						+ strLabel
						+ "\"]//ancestor::*[contains(@class,'Container')][1]//descendant::*[contains(@title,\"" + strLabel + "\")]");
	readonly=By.xpath("//*[@id='pageBody']//descendant::*[not(@id='hiddenElements')]//descendant::*[@id='body']"
			+ "//*[normalize-space(text())=\"" + strLabel + "\"]"
			+ "//ancestor-or-self::*[contains(@class,'inputContainer') or contains(@class,'field') ]//descendant::input[@disabled='disabled' or @type='hidden']");
	
	readonly2=By.xpath("//*[@id='pageBody']//descendant::*[not(@id='hiddenElements')]//descendant::*[@id='body']"
			+ "//*[normalize-space(text())=\"" + strLabel + "\"]"
			+ "//ancestor-or-self::*[contains(@class,'inputContainer') or contains(@class,'field') ]//descendant::label[@id='documentType']");
	
	scrollLabel = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]");
	btnDocType = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*//descendant::*[contains(@class,'binoculars')]");
	drpdwnType = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]/descendant::*[contains(@id,'TypeSelect')]");
	drpdwnSubType = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]/descendant::*[contains(@id,'SubType')]");
	searchDocFields = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*//*[contains(@class,'multiItemSelectInput')]");
	selectDocFields = By.xpath("//*[contains(@class,'ui-menu ui-widget ui-widget-content ui-autocomplete ui-front vv_vof_lookup_panel vv-action-menu-overlay')]//descendant::*[text()='All']//following-sibling::*/descendant::em[text()[normalize-space()=\"" + strLabel + "\"]]");
	selectLifecycle= By.xpath("//*[contains(@class,'ui-menu ui-widget ui-widget-content ui-autocomplete ui-front vv_multi_item_select_ul vv_vof_lookup_panel')]//descendant::*[text()[normalize-space()=\"" + strLabel + "\"]]");
	selectMaterialIntent= By.xpath("//*[contains(@class,'ui-menu ui-widget ui-widget-content ui-autocomplete ui-front vv_multi_item_select_ul vv_vof_lookup_panel')]//descendant::a[@title='"+strLabel+"']/strong");
	selectAudience= By.xpath("//*[contains(@class,'ui-menu ui-widget ui-widget-content ui-autocomplete ui-front vv_multi_item_select_ul vv_vof_lookup_panel')]//descendant::a[@title='"+strLabel+"']/strong");
	selectDissemination= By.xpath("//*[contains(@class,'ui-menu ui-widget ui-widget-content ui-autocomplete ui-front vv_multi_item_select_ul vv_vof_lookup_panel')]//descendant::a[@title='"+strLabel+"']/strong");
	selectFinalForm= By.xpath("//*[contains(@class,'ui-menu ui-widget ui-widget-content ui-autocomplete ui-front vv_multi_item_select_ul vv_vof_lookup_panel')]//descendant::a[@title='"+strLabel+"']/strong");
	listBusinessOwner = By.xpath("//*[contains(@class,'ui-menu ui-widget ui-widget-content ui-autocomplete ui-front vv_vof_lookup_panel vv-action-menu-overlay')]//descendant::*[text()='All']//following-sibling::*/descendant::strong[text()[normalize-space()=\"" + strLabel + "\"]]");
    listCoordinator = By.xpath("//*[contains(@class,'ui-menu ui-widget ui-widget-content ui-autocomplete ui-front vv_vof_lookup_panel vv-action-menu-overlay')]//descendant::*[text()='All']//following-sibling::*/descendant::strong[text()[normalize-space()=\"" + strLabel + "\"]]");
    selectApprovalType= By.xpath("//*[contains(@class,'ui-menu ui-widget ui-widget-content ui-autocomplete ui-front vv_multi_item_select_ul vv_vof_lookup_panel')]//descendant::a[@title='"+strLabel+"']/strong");
    selectLanguage= By.xpath("//*[contains(@class,'ui-menu ui-widget ui-widget-content ui-autocomplete ui-front vv_multi_item_select_ul vv_vof_lookup_panel')]//descendant::a[@title='"+strLabel+"']/strong");
    btnComplete = By.xpath("//*[text()[normalize-space()='*Required to proceed']]//following-sibling::*//descendant::*[text()[normalize-space()=\"" + strLabel + "\"]]");
    drpdwnStartWorkflow = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//following-sibling::*[contains(@class,'nextWorkflow')]");
    btnWorkflowOK= By.xpath("//*[text()[normalize-space()='Take no further action']]//following-sibling::*[contains(@class,'button') or text()[normalize-space()=\"" + strLabel +"\"]]");
    chkReviewers = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//preceding-sibling::*[contains(@type,'checkbox')]");
    lblApprover = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'Approver')]//descendant::*[text()='Start typing to see a list of users...']");
    txtApprover = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'Approver')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    lblReviewer = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'Reviewer')]//descendant::*[text()='Start typing to see a list of users...']");
    txtReviewer = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'Reviewer')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    lblProjectManager = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'projectManager')]//descendant::*[text()='Start typing to see a list of users...']");
    txtProjectManager = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'projectManager')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    rdoApproved = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//preceding-sibling::*[contains(@nextlifecyclestate,'approved')]");
    lblMedicalReviewer = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'medical')]//descendant::*[text()='Start typing to see a list of users...']");
    txtMedicalReviewer= By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'medical')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    lblLegalReviewer = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'legalBusinessConduct')]//descendant::*[text()='Start typing to see a list of users...']");
    txtLegalReviewer= By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'legalBusinessConduct')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    lblRegulatoryReviewer = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'regulatory')]//descendant::*[text()='Start typing to see a list of users...']");
    txtRegulatoryReviewer = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'regulatory')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    lblCommercialReviewer = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'commercial')]//descendant::*[text()='Start typing to see a list of users...']");
    txtCommercialReviewer = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'commercial')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    lblOtherReviewer = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'Reviewer')]//descendant::*[text()='Start typing to see a list of users...']");
    txtOtherReviewer = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'Reviewer')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    lblOtherApprover = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'Approver')]//descendant::*[text()='Start typing to see a list of users...']");
    txtOtherApprover = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'Approver')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    rdoApprovedForUse = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//preceding-sibling::*[contains(@type,'radio')]");
    lblPreReviewer= By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'preReviewer')]//descendant::*[text()='Start typing to see a list of users...']");
    txtPreReviewer = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'preReviewer')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    lblCommOps = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'gileadCoordinator')]//descendant::*[text()='Start typing to see a list of users...']");
    txtCommOps = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'gileadCoordinator')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    lblEditorialReviewer = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'editorial')]//descendant::*[text()='Start typing to see a list of users...']");
    txtEditorialReviewer = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'editorial')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    lstReviewDeadline = selectApprovalType= By.xpath("//*[contains(@class,'ui-menu ui-widget ui-widget-content ui-autocomplete ui-front vv_multi_item_select_ul vv_vof_lookup_panel')]//descendant::a[@title='"+strLabel+"']/strong");
    rdoWithdrawn = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//preceding-sibling::*[contains(@nextlifecyclestate,'withdrawn')]");
    rdoHasMaterialCode = By.xpath("//*[@attrkey='hasMaterialCode']//following-sibling::div//descendant::*[contains(text(),'"+strLabel+"')]");
    txtMaterialCode = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]/parent::*//following-sibling::*//descendant::*[contains(@class,'materialCode')]");
    txt4DigitCostCenter = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]/parent::*/following-sibling::*/descendant::*[contains(@class,'4DigitCostCenter')]");
    lblExaminer = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'examiner')]//descendant::*[text()='Start typing to see a list of users...']");
    txtExaminer = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'examiner')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    lblPlannedFirstUseDate = By.xpath("//*[contains(@attrkey,'plannedFirstUseDate')]//descendant::*[text()[normalize-space()=\"" + strLabel + "\"]]");
    lblMedicalSignatory = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'medicalSignatory')]//descendant::*[text()='Start typing to see a list of users...']");
    txtMedicalSignatory  = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'medicalSignatory')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    rdoCoPayTerms = By.xpath("//*[@attrkey='areCoPayTermsConditionsInclude']//following-sibling::div//descendant::*[contains(text(),'"+strLabel+"')]");
    lstMaterialHistory = By.xpath("//*[contains(@class,'ui-menu ui-widget ui-widget-content ui-autocomplete ui-front vv_multi_item_select_ul vv_vof_lookup_panel')]//descendant::a[@title='"+strLabel+"']/strong");
    rdoPRCDerivative  = By.xpath("//*[@attrkey='pRCDerivativeMaterial']//following-sibling::div//descendant::*[contains(text(),'"+strLabel+"')]");
    lblContentCreator = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'contentCreator')]//descendant::*[text()='Start typing to see a list of users...']");
    txtContentCreator = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]//parent::*/following-sibling::*[contains(@data-role-key,'contentCreator')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    lblProposedExpDate = By.xpath("//*[contains(@attrkey,'proposedExpirationDate')]//descendant::*[text()[normalize-space()=\"" + strLabel + "\"]]");
    drpdwnReason = By.xpath("//*[text()[normalize-space()=\"" + strLabel + "\"]]/parent::*/following-sibling::*//descendant::*[contains(@name,'copyReason')]");
}

	public Common(String strLabel, String strLabel2) {

		dialogSelect = By.xpath("//*[text()=\"" + strLabel
				+ "\"]//ancestor::*[contains(@class,'dialog') and contains(@class,'widget-content')]//select[ancestor::*[normalize-space(text())=\""
				+ strLabel2
				+ "\"] or ancestor::*[contains(@class,'container')]//preceding-sibling::*//*[normalize-space(text())=\""
				+ strLabel2 + "\"] or preceding-sibling::*[normalize-space(text())=\"" + strLabel2 + "\"]]");
		radiobutton = By
				.xpath("//*[@id='pageBody']//descendant::*[not(@id='hiddenElements')]//*[normalize-space(translate(text(), '*', ''))=\""
						+ strLabel
						+ "\"]//ancestor-or-self::*[contains(@class,'inputContainer') or contains(@class,'field')]//descendant::*[text()=\""
						+ strLabel2 + "\"]/preceding-sibling::input[1]");
		dccdocumentsearch = By.xpath("//*[text()=\"" + strLabel
				+ "\"]//ancestor::*[contains(@class,'gridView')]//descendant::*[text()=\"" + strLabel2 + "\"]");
		listitem = By.xpath("//*[text()=\"" + strLabel
				+ "\"]//ancestor-or-self::*[contains(@class,'widget-content')]//*[contains(@class,'menu') and text()=\""
				+ strLabel2 + "\"]/following::*[contains(@class,'menu-item')]");
		dialogbutton = By.xpath("//*[normalize-space(text())=\"" + strLabel
				+ "\"]//ancestor::*[contains(@class,'dialog') and contains(@class,'widget-content')]//descendant::*[normalize-space(text())=\""
				+ strLabel2
				+ "\"]//ancestor::*[contains(@class,'button') and (local-name()='a' or local-name()='button')]");
		dialogtextbox = By.xpath("//*[normalize-space(text())=\"" + strLabel
				+ "\"]//ancestor::*[contains(@class,'dialog') and contains(@class,'widget-content')]//descendant::*[normalize-space(translate(text(), '*', ''))=\""
				+ strLabel2
				+ "\"]//ancestor::*[contains(@class,'fieldRow') or contains(@class,'form_container')or contains(@class,'inputContainer')][1]//descendant::input[not(@type='hidden') and not(@type='radio') and @role='textbox' or @type='text']");
		dialogtextarea = By.xpath("//*[normalize-space(text())=\"" + strLabel
				+ "\"]//ancestor::*[contains(@class,'dialog') and contains(@class,'widget-content')]//descendant::*[normalize-space(translate(text(), '*', ''))=\""
				+ strLabel2
				+ "\"]//ancestor::*[contains(@class,'fieldRow') or contains(@class,'form_container')or contains(@class,'inputContainer')][1]//descendant::textarea");
		
		
		
		popupradio = By.xpath("//*[text()=\"" + strLabel
				+ "\"]//ancestor::*[contains(@class,'dialog') and contains(@class,'widget-content')]//descendant::*[normalize-space(translate(text(), '*', ''))=\""
				+ strLabel2 + "\"]/preceding-sibling::input[@type='radio']");

	}

	public Common(String strLabel, String strValue, String strWindow) {
		dialogRadio = By.xpath("//*[text()=\"" + strWindow
				+ "\"]//ancestor::*[contains(@class,'dialog') and contains(@class,'widget-content')]//descendant::*[normalize-space(translate(text(), '*', ''))=\""
				+ strLabel + "\"]//ancestor::*[contains(@class,'container')]//descendant::*[text()=\"" + strValue
				+ "\"]//ancestor::*[contains(@class,'radioForm')]//input[@type='radio']");

		checkbox = By.xpath("//*[text()=\"" + strWindow
				+ "\"]//ancestor::*[contains(@class,'dialog') and contains(@class,'widget-content')]//descendant::*[normalize-space(translate(text(), '*', ''))=\""
				+ strLabel
				+ "\"]//ancestor::*[contains(@class,'fieldRow') or contains(@class,'container')]//descendant::*[contains(@class,'container')]//input[@type='checkbox']");
	}

	public static final By searchtext = By.xpath(
			"//*[@id='pageBody']//descendant::*[(not(@id='hiddenElements')) and (@id='top_nav')]//descendant::input[@role='textbox']");
	public static final By searchbtn = By.xpath(
			"//*[@id='pageBody']//descendant::*[(not(@id='hiddenElements')) and (@id='top_nav')]//descendant::button[contains(@id,'search_main')]");
	public static final By settingIcon = By.xpath("//*[contains(@class,'menu')][@title='Actions menu']");
	public static final By ActionsMenu = By.xpath("//*[contains(@class,'Actions')][@title='Actions menu']");
	public static final By Inputbox = By.xpath(
			"//*[contains(@class,'widget-content') and contains(@class,'dialog')]//descendant::input[contains(@class,'searchInput')]");
	public static final By search = By.xpath(
			"//*[contains(@class,'widget-content') and contains(@class,'dialog')]//descendant::*[contains(@class,'searchbar')]//descendant::*[contains(@class,'button') and not(contains(@class,'clearButton'))]");
	public static final By Addbutton = By.xpath(
			"//*[contains(@class,'widget-content') and contains(@class,'dialog')]//descendant::*[contains(@class,'searchBody') or contains(@class,'listContainer')]//descendant::*[contains(@class,'add')]");
	public static final By VeevatxtboxUserName = By.xpath("//*[contains(@id,'username')]");
	public static final By VeevatxtboxPassword = By.xpath("//*[contains(@id,'password')]");
	public static final By VeevabtnContinue = By.name("continue");
	public static final By VeevabtnLogin = By.name("login");
	public static final By VeevaSave = By.xpath("//*[contains(@class,'bottomButtonBar')]//*[contains(@class,'save')]");
	public static final By VeevaDoctext = By.xpath("//*[contains(@class,'docHeaderTitle')]");
	public static final By VeevaNameNumber = By.xpath(
			"//*[text()='Name']//ancestor::*[contains(@class,'field')]//descendant::*[contains(@class,'Field')]");
	public static final By VeevaDCC_Removeicon = By.xpath("//button[contains(@class,'remove')]");
	public static final By close = By.xpath(
			"//*[contains(@class,'widget-content') and contains(@class,'dialog')]//descendant::*[contains(@class,'body_buttons')]//descendant::*[(text()='Close')]");
	public static final By VeevaDCC_Settingicon = By
			.xpath("//*[contains(@class,'dropdown-button')]//button[@type='button']");
	public static final By VeevaChoose = By.xpath("//input[@title='Choose a file' or contains(@id,'inboxFileChooser')]");
	public static final By fileupload= By.xpath("//a[@title='Upload File']");
	public static final By fileupdloadDataverify= By.xpath("//*[contains(@class,'fileUploadText')]");
	public static final By error = By.xpath(
			"//*[(not(@id='hiddenElements'))]//*[@class='error']//ancestor::*[contains(@class,'container') or contains(@class,'inputContainer') or contains(@class,'field')]//descendant::*[(contains(@class,'dialog_label') and not(contains(@class,'container'))) or (normalize-space(translate(text(), '*', '')) and not(@class='error'))]");
	public static final By loginerror = By.xpath("//*[contains(@class,'login-error-text')]");
	public static final By userMenu = By.xpath("//*[contains(@class,'vv-navbar-dropdown vv-navbar-userprofile')]");
	public static final By switchUser = By.xpath("//*[text()='Switch user']");
	public static final By btnLogout = By.xpath("//*[text()[normalize-space()='Log out']]");
	public static final By VeevaStatus = By
			.xpath("//*[contains(@class,'docStatus') or contains(@class,'status-label')]");
	public static final By LastLogin = By.xpath(
			"//*[contains(text(),'Gilead IT')]//ancestor::div[contains(@class,'top')]/following-sibling::div//*[@class='lastLogin']");
	public static final By VeevaWait = By.xpath("//*[contains(text(),'Task Assignment Date')]");
	public static final By Veevathome = By.xpath("//*[contains(@alt,'Home')]");
	public static final By datePicker = By.xpath(".//*[contains(@id,'datepicker')]");
	public static final By loginUserName = By
			.xpath("//*[contains(@class,'menu-container')]//descendant::*[contains(@class,'username')]");
	public static final By ShowActiveWorkflow= By.xpath("//*[@title='Show Active Workflow']//a");
	public static final By CancelWorkflow= By.xpath("//*[text()='Cancel Workflow']//ancestor::li[@role='menuitem']");
    public static final By settingIconActiveWorkFlow = By.xpath("//*[contains(@class,'Actions')][@title='Actions menu']");
    public static final By btnBrowse = By.xpath("//*[contains(@class,'select') and contains(@class,'file') and contains(@class,'icon')]//descendant::*[contains(@class,'uploadIcon')]");
    public static final By txtName = By.xpath("//*[contains(@class,'dpPropTarget_name')]");
    public static final By txtObjective = By.xpath("//*[contains(@class,'dpPropTarget_objective')]");
    public static final By drpdwnLifecycle = By.xpath("//*[contains(@class,'dpPropTarget_lifecycle')]");
    public static final By txtLifecycle = By.xpath("//*[contains(@class,'dpPropTarget_lifecycle')]/parent::*//following-sibling::*/descendant::*[contains(@class,'SelectInput')]");
    public static final By txtMaterialIntent =  By.xpath("//*[@attrkey='materialIntent']//following-sibling::div//descendant::*[contains(@class,'multiItemSelectInput')]");
    public static final By txtAudience =  By.xpath("//*[@attrkey='audience']//following-sibling::div//descendant::*[contains(@class,'multiItemSelectInput')]");
    public static final By txtDissemination = By.xpath("//*[@attrkey='methodOfDissemination']//following-sibling::div//descendant::*[contains(@class,'multiItemSelectInput')]");
    public static final By txtFinalForm = By.xpath("//*[@attrkey='finalForm']//following-sibling::div//descendant::*[contains(@class,'multiItemSelectInput')]");
    public static final By lblStatus = By.xpath("//*[@attrkey='status']");
    public static final By lblDocumentNo = By.xpath("//*[@attrkey='DocumentNumber']");
    public static final By txtDocumentNo = By.xpath("//*[contains(@id,'search_main_box')]");
    public static final By btnSearch = By.xpath("//*[contains(@id,'search_main_button')]");
    public static final By lnkDocumentNo = By.xpath("//*[contains(@class,'doc_link_large') or contains(@class,'doc_title_link')]");
    public static final By btnActions = By.xpath("//*[@data-icon='workflow' and contains(@class,'vv-action-bar')]");
    public static final By lblBusinessOwner = By.xpath("//*[text()='Business Owner']//parent::div/following-sibling::div[contains(@data-role-key,'businessOwner')]//descendant::*[text()='Start typing to see a list of users...']");
    public static final By lblPRCSpecialist =   By.xpath("//*[text()='PRC Specialist']//parent::div/following-sibling::div[contains(@data-role-key,'pRCSpecialist')]//descendant::*[text()='Start typing to see a list of users...']");
    public static final By txtPRCSpecialist =   By.xpath("//*[text()='PRC Specialist']//parent::div/following-sibling::div[contains(@data-role-key,'pRCSpecialist')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    public static final By lblCoordinator = By.xpath("//*[text()='Coordinator']//parent::div/following-sibling::div[contains(@data-role-key,'gileadCoordinator')]//descendant::*[text()='Start typing to see a list of users...']");
    public static final By txtDueDate = By.xpath("//*[contains(@name,'date-dueDate') or contains(@name,'date-editorialDueDate') or contains(@name,'date-actualDateOfFirstUse')]");
    public static final By txtPlannedFirstUseDate= By.xpath("//*[contains(@name,'plannedFirstUseDate')]");
    public static final By txtBusinessOwner = By.xpath("//*[text()='Business Owner']//parent::div/following-sibling::div[contains(@data-role-key,'businessOwner')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    public static final By txtCoordinator = By.xpath("//*[text()='Coordinator']//parent::div/following-sibling::div[contains(@data-role-key,'gileadCoordinator')]//descendant::*[contains(@class,'multiItemSelectInput')]");
    public static final By lstBusinessOwner = By.xpath("//*[contains(@class,'ui-menu ui-widget ui-widget-content ui-autocomplete ui-front vv_vof_lookup_panel vv-action-menu-overlay')]//descendant::*[text()='All']//following-sibling::*/descendant::strong");
    public static final By lstCoordinator = By.xpath("//*[contains(@class,'ui-menu ui-widget ui-widget-content ui-autocomplete ui-front vv_vof_lookup_panel vv-action-menu-overlay')]//descendant::*[text()='All']//following-sibling::*/descendant::strong[text()=\"george.gilead.coordinator@sb-gilead.com\"]");
    public static final By txtPOForReviewAgency = By.xpath("//*[contains(@class,'dpPropTarget_purchaseOrderForReviewAgency')]");
    public static final By drpdwnCapacity = By.xpath("//*[contains(@id,'capacity') or contains(@name,'capacity')]");
    public static final By txtApproverUserName = By.xpath("//*[contains(@id,'username') or contains(@name,'username')]");
	public static final By txtApproverPassword = By.xpath("//*[contains(@id,'mask-password')]");
	public static final By txtExpirationDate = By.xpath("//*[contains(@name,'expirationDate')]");
	public static final By txtProposedExpDate = By.xpath("//*[contains(@name,'proposedExpirationDate')]");
	public static final By txtMeetingDate = By.xpath("//*[contains(@name,'meetingDate')]");
	public static final By txtWithdrawalNotes = By.xpath("//*[contains(@name,'withdrawalNotes')]");
	public static final By txtNewOrRevise = By.xpath("//*[@attrkey='newOrRevise']//following-sibling::div//descendant::*[contains(@class,'multiItemSelectInput')]");
	public static final By txtPriority = By.xpath("//*[@attrkey='priority']//following-sibling::div//descendant::*[contains(@class,'multiItemSelectInput')]");
	public static final By txtCopyright = By.xpath("//*[@attrkey='copyrightConfirmation']//following-sibling::div//descendant::*[contains(@class,'multiItemSelectInput')]");
	public static final By txtCorporateLogo = By.xpath("//*[@attrkey='useOfCorporateLogo']//following-sibling::div//descendant::*[contains(@class,'multiItemSelectInput')]");
	public static final By imgLayouts = By.xpath("//*[contains(@data-icon,'align')]");
	public static final By lstCompactView = By.xpath("//*[contains(@data-value,'COMPACT')]");
	public static final By txtMaterialHistory = By.xpath("//*[@attrkey='materialHistory']//following-sibling::div//descendant::*[contains(@class,'multiItemSelectInput')]");
	public static final By btnAllActions = By.xpath("//*[contains(@class,'DropdownMenu')]//descendant::*[contains(@data-icon,'ellipsis-h')]");
}
