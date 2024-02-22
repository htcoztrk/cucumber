package jenkins;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostegreSql {
    String jdbcUrl = "jdbc:postgresql://localhost:5432/testDB";
    String username = "postgres";
    String password = "123123";

    public static int testPlanID;
    public static String testPlanName;
    public static String date;


    public static String formattedDuration;
    LocalDateTime startTime;

    LocalDateTime endTime;

    public Connection postgreConnect() throws ClassNotFoundException {


        Class.forName("org.postgresql.Driver");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            System.out.println("DB Connection Bağlantı hatası: " + e.getMessage());
        }

        System.out.println("*************** Baglantı Saglandi ***************");


        if (testPlanID == 0) {
            try {
                testPlanName=System.getProperty("testPlanName");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT MAX(\"TestPlanID\") AS \"MaxTestPlanID\"\n" +
                        "FROM public.\"testResult\"\n" +
                        "WHERE \"TestPlanName\" ='"+testPlanName+"'");
                if (resultSet.next()) {
                    testPlanID = resultSet.getInt(1) + 1;
                    System.out.println("*************** Guncel TestPlanID : " + testPlanID + " ***************");

                }
            } catch (SQLException e) {
                System.out.println("TestPlanID DB okuma hatası: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Bağlantı kapatma hatası: " + e.getMessage());
                }
            }
        }

        return connection;
    }

    public void getStartDate(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        date = myDateObj.format(dateFormat);
        startTime = myDateObj;
        System.out.println("Scenario running date: " + date);
        System.out.println("Scenario running startTime: " + startTime.format(timeFormat));
    }

    public void getStopDate(){
        LocalDateTime myDateObj2 = LocalDateTime.now();
        endTime = myDateObj2;
        Duration duration = Duration.between(startTime, endTime);
        long seconds = duration.getSeconds();
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        long millis = duration.toMillis() % 1000;
        formattedDuration = String.format("%02d:%02d:%02d", minutes, remainingSeconds, millis/20);
        System.out.println("Scenario duration: " + formattedDuration);
    }

    public void writeToDB(int TestPlanID,String TestPlanName,String Date,String TestSuite,String ScenarioName,int ScenarioResult,String ScenarioRunnigTime) throws ClassNotFoundException {
        Connection connection = postgreConnect();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();

                String deneme = "INSERT INTO public.\"testResult\"(\"TestPlanID\",\"TestPlanName\",\"TestDate\",\"TestSuite\",\"ScenarioName\", \"ScenarioResult\", \"ScenarioRunnigTime\") VALUES (";
                String deneme1 = "INSERT INTO public.\"testResult\"(\"TestPlanID\", \"TestPlanName\", \"TestDate\", \"TestSuite\", \"ScenarioName\", \"ScenarioResult\", \"ScenarioRunningTime\") VALUES ("+"01"+",'"+"NetworkService"+"','"+"2022-09-10"+"','"+"TestSuite"+"','"+"ScenarioName"+"','"+0+"','"+"00:02:03"+"')";

                System.out.println("*******************************");
                System.out.println(TestPlanID);
                System.out.println(TestPlanName);
                System.out.println(Date);
                System.out.println(TestSuite);
                System.out.println(ScenarioName);
                System.out.println(ScenarioResult);
                System.out.println(ScenarioRunnigTime);
                System.out.println("*******************************");


                //INSERT INTO public."testResult"("TestPlanID", "TestPlanName", "TestDate", "TestSuite", "ScenarioName", "ScenarioResult", "ScenarioRunnigTime")
                //VALUES(3,'DenemePlan','2022-09-22','TestSuitName','Scenarioo','0','01:01:01');
                statement.executeUpdate("INSERT INTO public.\"testResult\"(\"TestPlanID\", \"TestPlanName\", \"TestDate\", \"TestSuite\", \"ScenarioName\", \"ScenarioResult\", \"ScenarioRunningTime\") VALUES ("+TestPlanID+",'"+TestPlanName+"','"+Date+"','"+TestSuite+"','"+ScenarioName+"','"+0+"','"+ScenarioRunnigTime+"')");
                //statement.executeUpdate("INSERT INTO dbo.TestData(TestPlanName, TestPlanID, TestDate, TestSuite, ScenarioName, ScenarioResult, ScenarioRunnigTime) VALUES ("+"'s'"+", 25,  GETDATE(), 'Test Suite 1', 'Scenario 1', 0,'10:30:00')");

            } catch (SQLException e) {
                System.out.println("DB yazma hatası: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Bağlantı kapatma hatası: " + e.getMessage());
                }
            }
        }
    }

    public String testSuite(String dosyaYolu){
        int sonSlashIndex = dosyaYolu.lastIndexOf("/");
        String sadeceDosyaAdi = dosyaYolu.substring(sonSlashIndex + 1);

        if (sadeceDosyaAdi.endsWith(".feature")) {
            sadeceDosyaAdi = sadeceDosyaAdi.substring(0, sadeceDosyaAdi.length() - ".feature".length());
        }
        return sadeceDosyaAdi;
    }


}

