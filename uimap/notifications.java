package uimap;

import org.openqa.selenium.By;

/**
 * UI Map for Common Objects
 */
public class notifications {

	public By combolistCheckedOutDocs, folderName,folderExpand;
	public By tableRowsFilterWithSubject;
	public By mailBody;
	public By btnInToolBar;
	public By mailSubject;

	public static final By txtStaySigned = By.xpath("//div[text() = 'Stay signed in?']");
	public static final By btnStaySignedNo = By.xpath("//input[@value = 'No']");
	public static final By btnStaySignedYes = By.xpath("//input[@value = 'Yes']");
	public static final By btnViewPaneToolBar = By.xpath("//div[contains(@aria-label,'Message list')]//*[@role = 'toolbar']");
//	public static final By txtInMailBody = By.xpath("//*[@aria-label = 'Reading Pane']//*[@aria-label = 'Content pane']//*[@class = 'wide-content-host']//*[@aria-label = 'Email message']//*[@class = 'rps_e283']//*[string-length(text()) > 0]");
	public static final By txtInMailBody = By.xpath("//*[@aria-label = 'Reading Pane']//*[@aria-label = 'Content pane']//*[@class = 'wide-content-host']//*[@aria-label = 'Email message']//*[@class = 'rps_e283']");

	public static final By timeStampOfMail = By
			.xpath("(//div[@class = 'wide-content-host']//div//div[1]//div[3]//div[1]//div[2])[1]");
	public static final By docusignLink = By
			.xpath("//div[@class = 'wide-content-host']//span[contains(text(), 'REVIEW DOCUMENT')]//parent::a");
	public static final By tableRows = By.xpath(
			"//ancestor::div[contains(@tempid,'emailslistviewpanel')]//div[contains(@id,'aria') or @role='heading' and not(@style)]");
	

	public notifications(String strValue1) {
		folderExpand = By.xpath("(//div[@aria-label = 'Navigation pane']//span[text() = '" + strValue1 + "'])[last()]/..//button");
		folderName = By.xpath("//div[@aria-label = 'Navigation pane']//span[text() = '" + strValue1 + "']");
		tableRowsFilterWithSubject = By.xpath(
				"//div[contains(@aria-label,'Message list')]//div[@role = 'complementary']//div[contains(@aria-label,'" + strValue1 + "')]");
		
		mailBody = By.xpath("//div[@class = 'wide-content-host']//*[contains(text(), '" + strValue1 + "')]");
		btnInToolBar = By.xpath("//div[contains(@aria-label,'Message list')]//*[@role = 'toolbar']//*[@name = '" + strValue1 + "']");
		mailSubject = By.xpath("//*[@aria-label = 'Reading Pane']//*[@aria-label = 'Content pane']//*[contains(@title, '" + strValue1 + "') and @role = 'heading']");
	}

	public notifications(String strValue1, String strValue2) {
		combolistCheckedOutDocs = By.xpath("//*[contains(@class,'window-header-text') and contains(.,'" + strValue1
				+ "')]//ancestor::div[contains(@class,'window')"
				+ " and contains(@class,'component') and (not(contains(@class,'header')))]//*[contains(text(),'"
				+ strValue2 + "')]" + "//ancestor::div[@class='x-form-item ']//div[@qtip]");
	}

}
