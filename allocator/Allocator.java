package allocator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.openqa.selenium.Platform;
import com.cognizant.framework.selenium.*;
import com.cognizant.framework.ExcelDataAccessforxlsm;
import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.IterationOptions;
import com.cognizant.framework.Settings;
import com.cognizant.framework.Util;

/**
 * Class to manage the batch execution of test scripts within the framework
 * 
 * @author Cognizant
 */
public class Allocator {
	private FrameworkParameters frameworkParameters = FrameworkParameters
			.getInstance();
	private Properties properties;
	public static Boolean booTestSummaryFlag = false;
	public static int intTestsPassed, intTestsFailed, intTestsAborted;
	private Boolean booUpdatePropertiesFlag = false;
	private Properties testSummary = new Properties();
	private Properties mobileProperties;
	private ResultSummaryManager resultSummaryManager = ResultSummaryManager.getInstance();
	private int intTotalTests;
	public static String strReportPath;

	/**
	 * The entry point of the test batch execution <br>
	 * Exits with a value of 0 if the test passes and 1 if the test fails
	 * 
	 * @param args
	 *            Command line arguments to the Allocator (Not applicable)
	 */
	public static void main(String[] args) {
		intTestsPassed = 0;
		intTestsFailed = 0;
		intTestsAborted = 0;
		Allocator allocator = new Allocator();
		allocator.driveBatchExecution();
	}

	private void driveBatchExecution() {
		resultSummaryManager.setRelativePath();
		properties = Settings.getInstance();
		mobileProperties = Settings.getMobilePropertiesInstance();
		String runConfiguration;
		if (System.getProperty("RunConfiguration") != null) {
			runConfiguration = System.getProperty("RunConfiguration");
		} else {
			runConfiguration = properties.getProperty("RunConfiguration");
		}
		resultSummaryManager.initializeTestBatch(runConfiguration);

		int nThreads = Integer.parseInt(properties
				.getProperty("NumberOfThreads"));
		resultSummaryManager.initializeSummaryReport(nThreads);

		resultSummaryManager.setupErrorLog();
		
		strReportPath = resultSummaryManager.reportPath;

		int testBatchStatus = executeTestBatch(nThreads);

		resultSummaryManager.wrapUp(false);
		
		if(booUpdatePropertiesFlag)
		{
			putDataInPropertiesFile("TotalTests", String.valueOf(intTotalTests));
			SimpleDateFormat objDateFormat = new SimpleDateFormat("hh:mm:ss a");
			putDataInPropertiesFile("StartTime", objDateFormat.format(resultSummaryManager.overallStartTime));
			putDataInPropertiesFile("EndTime", objDateFormat.format(resultSummaryManager.overallEndTime));
			putDataInPropertiesFile("OverAllExecutionTime", resultSummaryManager.overAllExecutionTime());
			putDataInPropertiesFile("SummaryPath", resultSummaryManager.reportPath+"\\HTML Results\\Summary.Html");
			SavePropertiesFile();
		}
		
		resultSummaryManager.launchResultSummary();
		
		System.exit(testBatchStatus);
	}
	/***** When working with SeeTest/Perfecto Parellel  *****/
	/*private int executeTestBatch(int nThreads) {
        List<SeleniumTestParameters> testInstancesToRun = getRunInfo(frameworkParameters
                     .getRunConfiguration());
        ExecutorService parallelExecutor = Executors
                     .newFixedThreadPool(nThreads);
        ParallelRunner testRunner = null;
        int i=0;
  while(i<testInstancesToRun.size())
  {
         System.out.println("I:"+i);
         int size=i+nThreads;
         //System.out.println("First For");
         for(int currentTestInstance=size-nThreads;currentTestInstance<size;currentTestInstance++)
         {
        testRunner = new ParallelRunner(
                     testInstancesToRun.get(currentTestInstance));
          parallelExecutor.execute(testRunner);
         
         if(frameworkParameters.getStopExecution()) {
               break;
         }
         }

  parallelExecutor.shutdown();
  while(!parallelExecutor.isTerminated()) {
               try {
                     Thread.sleep(3000);
               } catch (InterruptedException e) {
                     e.printStackTrace();
               }
        } 
  System.out.println("Waitng for thread to stop");
  i=size;
  }
  if (testRunner == null) {
               return 0; // All tests flagged as "No" in the Run Manager
        } else {
               return testRunner.getTestBatchStatus();
        }
 }*/

	
	private int executeTestBatch(int nThreads) {
		intTotalTests = 0;
		List<SeleniumTestParameters> testInstancesToRun = getRunInfo(frameworkParameters
				.getRunConfiguration());
		intTotalTests = testInstancesToRun.size();
		ExecutorService parallelExecutor = Executors
				.newFixedThreadPool(nThreads);
		ParallelRunner testRunner = null;
		
		if(intTotalTests==0) booUpdatePropertiesFlag = false;

		for (int currentTestInstance = 0; currentTestInstance < testInstancesToRun
				.size(); currentTestInstance++) {
			testRunner = new ParallelRunner(
					testInstancesToRun.get(currentTestInstance));
			if((testInstancesToRun.get(currentTestInstance).getCurrentTestcase().equalsIgnoreCase("Test_Summary") && intTotalTests>1))
			{
				booTestSummaryFlag = true;
				booUpdatePropertiesFlag = true;
				intTotalTests--;
			}
			else if((testInstancesToRun.get(currentTestInstance).getCurrentTestcase().equalsIgnoreCase("Test_Summary") && intTotalTests==1))
			{
				booTestSummaryFlag = true;
				booUpdatePropertiesFlag = false;
			}
			else
			{
				booUpdatePropertiesFlag = true;
				parallelExecutor.execute(testRunner);
			}
			/*if (frameworkParameters.getStopExecution()) {
				break;
			}*/
		}

		parallelExecutor.shutdown();
		while (!parallelExecutor.isTerminated()) {
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(booUpdatePropertiesFlag) deleteTestSummaryPropFile();
		
		if (testRunner == null) {
			if(booUpdatePropertiesFlag)
			{
				putDataInPropertiesFile("PassedTestCasesCount",String.valueOf(0));
				putDataInPropertiesFile("FailedTestCasesCount",String.valueOf(0));
				putDataInPropertiesFile("AbortTestCasesCount",String.valueOf(0));
			}
			return 0; // All tests flagged as "No" in the Run Manager
		} else {
			if(booUpdatePropertiesFlag)
			{
				putDataInPropertiesFile("PassedTestCasesCount",String.valueOf(intTestsPassed));
				putDataInPropertiesFile("FailedTestCasesCount",String.valueOf(intTestsFailed));
				putDataInPropertiesFile("AbortTestCasesCount",String.valueOf(intTestsAborted));
			}
			return testRunner.getTestBatchStatus();
		}		
		
	}

	private List<SeleniumTestParameters> getRunInfo(String sheetName) {
		ExcelDataAccessforxlsm runManagerAccess = new ExcelDataAccessforxlsm(
				frameworkParameters.getRelativePath(), "Run Manager");
		runManagerAccess.setDatasheetName(sheetName);

		int nTestInstances = runManagerAccess.getLastRowNum();
		List<SeleniumTestParameters> testInstancesToRun = new ArrayList<SeleniumTestParameters>();

		for (int currentTestInstance = 1; currentTestInstance <= nTestInstances; currentTestInstance++) {
			String executeFlag = runManagerAccess.getValue(currentTestInstance,
					"Execute");

			if ("Yes".equalsIgnoreCase(executeFlag)) {
				String currentScenario = runManagerAccess.getValue(
						currentTestInstance, "TestScenario");
				String currentTestcase = runManagerAccess.getValue(
						currentTestInstance, "TestCase");
				SeleniumTestParameters testParameters = new SeleniumTestParameters(
						currentScenario, currentTestcase);

				testParameters.setCurrentTestInstance("Instance"
						+ runManagerAccess.getValue(currentTestInstance,
								"TestInstance"));
				testParameters.setCurrentTestDescription(runManagerAccess
						.getValue(currentTestInstance, "Description"));

				String iterationMode = runManagerAccess.getValue(
						currentTestInstance, "IterationMode");
				if (!"".equals(iterationMode)) {
					testParameters.setIterationMode(IterationOptions
							.valueOf(iterationMode));
				} else {
					testParameters
							.setIterationMode(IterationOptions.RUN_ALL_ITERATIONS);
				}

				String startIteration = runManagerAccess.getValue(
						currentTestInstance, "StartIteration");
				if (!"".equals(startIteration)) {
					testParameters.setStartIteration(Integer
							.parseInt(startIteration));
				}
				String endIteration = runManagerAccess.getValue(
						currentTestInstance, "EndIteration");
				if (!"".equals(endIteration)) {
					testParameters.setEndIteration(Integer
							.parseInt(endIteration));
				}

				String executionMode = runManagerAccess.getValue(
						currentTestInstance, "ExecutionMode");
				if (!"".equals(executionMode)) {
					testParameters.setExecutionMode(ExecutionMode
							.valueOf(executionMode));
				} else {
					testParameters.setExecutionMode(ExecutionMode
							.valueOf(properties
									.getProperty("DefaultExecutionMode")));
				}

				String toolName = runManagerAccess.getValue(
						currentTestInstance, "MobileToolName");
				if (!"".equals(toolName)) {
					testParameters.setMobileToolName(MobileToolName
							.valueOf(toolName));
				} else {
					testParameters.setMobileToolName(MobileToolName
							.valueOf(mobileProperties
									.getProperty("DefaultMobileToolName")));
				}

				String executionPlatform = runManagerAccess.getValue(
						currentTestInstance, "MobileExecutionPlatform");
				if (!"".equals(executionPlatform)) {
					testParameters
							.setMobileExecutionPlatform(MobileExecutionPlatform
									.valueOf(executionPlatform));
				} else {
					testParameters
							.setMobileExecutionPlatform(MobileExecutionPlatform.valueOf(mobileProperties
									.getProperty("DefaultMobileExecutionPlatform")));
				}

				String mobileOSVersion = runManagerAccess.getValue(
						currentTestInstance, "MobileOSVersion");
				if (!"".equals(mobileOSVersion)) {
					testParameters.setmobileOSVersion(mobileOSVersion);
				}

				String deviceName = runManagerAccess.getValue(
						currentTestInstance, "DeviceName");
				if (!"".equals(deviceName)) {
					testParameters.setDeviceName(deviceName);
				} else {
					testParameters.setDeviceName(mobileProperties
							.getProperty("DefaultDevice"));
				}

				String browser = runManagerAccess.getValue(currentTestInstance,
						"Browser");
				if (!"".equals(browser)) {
					testParameters.setBrowser(Browser.valueOf(browser));
					
				} else {
					testParameters.setBrowser(Browser.valueOf(properties
							.getProperty("DefaultBrowser")));
					
				}
				String browserVersion = runManagerAccess.getValue(
						currentTestInstance, "BrowserVersion");
				if (!"".equals(browserVersion)) {
					testParameters.setBrowserVersion(browserVersion);
				}
				String platform = runManagerAccess.getValue(
						currentTestInstance, "Platform");
				if (!"".equals(platform)) {
					testParameters.setPlatform(Platform.valueOf(platform));
				} else {
					testParameters.setPlatform(Platform.valueOf(properties
							.getProperty("DefaultPlatform")));
				}
				String seeTestPort = runManagerAccess.getValue(
                        currentTestInstance, "SeeTestPort");
				if (!"".equals(seeTestPort)) {
					testParameters.setSeeTestPort(seeTestPort);
				} else {
					testParameters.setSeeTestPort(properties
                                        .getProperty("SeeTestDefaultPort"));
				}
				testInstancesToRun.add(testParameters);
			}
		}

		return testInstancesToRun;
	}
	
	private void putDataInPropertiesFile(String Property, String Value)
	{
		testSummary.setProperty(Property, Value);
	}
	
	private void SavePropertiesFile()
	{
		File FileTestSummaryProp = new File(System.getProperty("user.dir")+Util.getFileSeparator()+"//Test Summary.properties");
		try {
			testSummary.store(new FileWriter(FileTestSummaryProp),"Test Summary Details");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error - in saving the properties file");
		}
	}
	
	private void deleteTestSummaryPropFile()
	{
		File FileTestSummaryProp = new File(System.getProperty("user.dir")+Util.getFileSeparator()+"//Test Summary.properties");
		
		if(FileTestSummaryProp.exists()) FileTestSummaryProp.delete();
	}
	
}