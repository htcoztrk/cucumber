package stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import jenkins.PostegreSql;
import org.apache.commons.lang3.StringUtils;


public class BaseTest extends PostegreSql{

    public static String env;

    static PostegreSql postegreSql = new PostegreSql();

    @Before
    public static void beforeScenario( ) throws ClassNotFoundException {
        System.out.println("******* Senaryo Basliyooorr *******");



        env = System.getProperty("env");

        System.out.println(env+"*");

        if (env != null && env.equals("Jenkins")){
            System.out.println("******* Test Jenkins Üzerinde Çalışacak *******");
            System.out.println("Test Plan Name"+env);
            // sql.getStartDate();
            postegreSql.getStartDate();
            postegreSql.postgreConnect();
            System.out.println("****** *Test basliyor ******");
        }

        if (StringUtils.isEmpty(System.getProperty("key"))){

            System.out.println("*?*?*?*?*?"+System.getProperty("env")+"*?*?*?*?*?");
            System.out.println("*?*?*?*?*?"+System.getProperty("testPlanName")+"*?*?*?*?*?");
        }
        else {
            System.out.println("******* Test Local Ortamda Çalışacak *******");
        }
    }


    @After
    public static void afterScenario(Scenario scenario) throws ClassNotFoundException {
        System.out.println("******* Senaryo Bitti *******");


        if (env != null && env.equals("Jenkins")){
            System.out.println("******* Test sonuçları DB üzerine yazilacak *******");
            int testResult;
            if (scenario.isFailed()){
                testResult=1;
            }
            else {
                testResult=0;
            }

            postegreSql.getStopDate();
            String testPlanName =System.getProperty("testPlanName");
            System.out.println(testPlanName+"****???-***");
            System.out.println("TestPlanID : "+ PostegreSql.testPlanID);
            System.out.println("Date : "+PostegreSql.date);
            System.out.println("Test Suite : "+postegreSql.testSuite(scenario.getUri().toString()));
            System.out.println("Scenario Name : "+scenario.getName());
            System.out.println("Test Result : "+testResult);
            System.out.println("ScenarioRunning Time : "+PostegreSql.formattedDuration);


            postegreSql.writeToDB(PostegreSql.testPlanID,testPlanName,postegreSql.date, postegreSql.testSuite(scenario.getUri().toString()), scenario.getName(),testResult,"00:00:24");

        }
    }

}
