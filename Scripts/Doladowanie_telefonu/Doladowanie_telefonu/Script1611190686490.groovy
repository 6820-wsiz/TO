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
import com.kms.katalon.core.util.KeywordUtil

// Visit Demobank login page and log in
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

// Test top-up
boolean missingRecipient = false
boolean missingAmount = false
boolean missingCheckbox = false
boolean isAmountBelow5 = false
boolean isAmountAbove150 = false
// Test top-up - Test recipient dropdown
if("${recipient}" && !"${recipient}".allWhitespace)
{
	assert WebUI.verifyOptionPresentByValue(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/select_wybierz telefon do doadowania       _5c7a19'),
		"${recipient}", false, 5, FailureHandling.OPTIONAL)
} else
{
	missingRecipient = true
}
WebUI.selectOptionByValue(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/select_wybierz telefon do doadowania       _5c7a19'),
	"${recipient}", true)

// Test top-up - Test ammount
if("${amount}" && !"${amount}".allWhitespace)
{
	assert "${amount}".isInteger()
	if("${amount}".toInteger() < 5)
	{
		isAmountBelow5 = true
	}
	if("${amount}".toInteger() > 150)
	{
		isAmountAbove150 = true
	}
} else
{
	missingAmount = true
}
WebUI.setText(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/input_kwota_widget_1_topup_amount'),
	"${amount}")

// Test top-up - Test checkbox
assert "${checkboxChecked}" == "true" || "${checkboxChecked}" == "false"
if ("${checkboxChecked}" == "true")
{
	WebUI.click(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/span'))
} else
{
	missingCheckbox = true
}

WebUI.delay(5)

// Test top-up
WebUI.click(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/button_doaduj telefon'))
WebUI.delay(5)

assert ((missingRecipient && WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/div_pole wymagane (1)'),
	5, FailureHandling.OPTIONAL)) ||
	(!missingRecipient))
assert ((missingAmount && WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/div_pole wymagane (2)'),
	5, FailureHandling.OPTIONAL)) ||
	(!missingAmount))
assert ((missingCheckbox && WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/div_pole wymagane (3)'),
	5, FailureHandling.OPTIONAL)) ||
	(!missingCheckbox))
assert ((isAmountBelow5 && WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/div_kwota musi by wiksza lub rwna 5'),
	5, FailureHandling.OPTIONAL)) ||
	(!isAmountBelow5))
assert ((isAmountAbove150 && WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Demobank - Bankowo Internetowa - Pulpit/div_kwota musi by mniejsza lub rwna 150'),
	5, FailureHandling.OPTIONAL)) ||
	(!isAmountAbove150))


WebUI.closeBrowser()
KeywordUtil.markPassed()
return null
