import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.util.KeywordUtil

// Visit Demobank login page
WebUI.openBrowser('')
WebUI.navigateToUrl("${baseUrl}" + "${loginUrl}")
WebUI.waitForPageLoad(5)

// Test user ID
WebUI.setText(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Logowanie/input_identyfikator_login_id'), 
    "${userName}")
if("${userName}".length() < 8)
{
	assert !WebUI.verifyElementClickable(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Logowanie/button_dalej'),
		FailureHandling.OPTIONAL)
	WebUI.closeBrowser()
	KeywordUtil.markPassed()
	return null
}
WebUI.click(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Logowanie/button_dalej'))
WebUI.delay(5)

// Test password
//WebUI.setEncryptedText(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Logowanie/input_haso_haslo'), 
WebUI.setText(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Logowanie/input_haso_haslo'),
    "${password}")
if("${password}".length() < 8)
{
	assert !WebUI.verifyElementClickable(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Logowanie/button_zaloguj si'),
		FailureHandling.OPTIONAL)
	WebUI.closeBrowser()
	KeywordUtil.markPassed()
	return null
}
WebUI.click(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Logowanie/button_zaloguj si'))
WebUI.waitForPageLoad(5)

// Test transfer
boolean isAnyFieldEmpty = false
// Test transfer - Test recipient dropdown
if("${quickRecipientID}" && !"${quickRecipientID}".allWhitespace)
{
	assert WebUI.verifyOptionPresentByValue(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/select_wybierz odbiorc przelewu            _029a71'),
		"${quickRecipientID}", false, 5, FailureHandling.OPTIONAL)
} else
{
	isAnyFieldEmpty = true
}
WebUI.selectOptionByValue(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/select_wybierz odbiorc przelewu            _029a71'),
	"${quickRecipientID}", true)

// Test transfer - Test ammount
if("${amount}" && !"${amount}".allWhitespace)
{
	assert "${amount}".isFloat()
} else
{
	isAnyFieldEmpty = true
}
WebUI.setText(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/input_kwota_widget_1_transfer_amount'),
	"${amount}".replace(".", ","))

// Test transfer - Test transfer title
if(!"${transferTitle}" || "${transferTitle}".allWhitespace)
{
	isAnyFieldEmpty = true
}
WebUI.setText(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/input_tytuem_widget_1_transfer_title'),
	"${transferTitle}")

WebUI.delay(5)

// Test transfer
WebUI.click(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/button_wykonaj'))
WebUI.delay(5)

assert ((isAnyFieldEmpty && WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/div_pole wymagane'),
	5, FailureHandling.OPTIONAL)) ||
	(!isAnyFieldEmpty && !WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/div_pole wymagane'),
	5, FailureHandling.OPTIONAL)))

WebUI.closeBrowser()
KeywordUtil.markPassed()
return null

