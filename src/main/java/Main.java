import java.util.HashMap;

public class Main {


    public static void main(String... args) throws Exception {

        //DatabaseTest databaseTest = new DatabaseTest();
        //databaseTest.initDatabase( new SparkConf().setAppName("JavaTwitterHashTagJoinSentiments").setMaster("local[2]").set("spark.executor.memory","1g"));
        //JCypherTest test = new JCypherTest();
        //test.initDBConnection();
        //test.queryNodeCount();

        GraphTest graphTest = new GraphTest();
        graphTest.test();
    }
}
