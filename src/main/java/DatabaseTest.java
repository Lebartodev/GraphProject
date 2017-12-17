import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;
import org.neo4j.driver.v1.*;
import scala.Tuple2;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DatabaseTest {

    private Driver driver;

    public void initDatabase(SparkConf sparkConf) throws InterruptedException {
        driver = GraphDatabase.driver("bolt://localhost:7687");
        System.setProperty("twitter4j.oauth.consumerKey", "KNXXK9PhQHMIGzDELXCu30kKH");
        System.setProperty("twitter4j.oauth.consumerSecret", "PjYcaCevO1nb8KzylZCQESagsaDTEsfayFmeklDw7n2FKCAfgF");
        System.setProperty("twitter4j.oauth.accessToken", "777021529511124992-AOd7moI6YyKz071v9Kgo7yby323QvjZ");
        System.setProperty("twitter4j.oauth.accessTokenSecret", "gAxCxAifTwIV5kzTa21kqHEcFKsrXQtstzdf53edDcDHm");
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, new Duration(2000));
        JavaReceiverInputDStream<Status> stream = TwitterUtils.createStream(jssc);

        JavaDStream<Tuple2<String, List<String>>> actData = stream.map((Function<Status, Tuple2<String, List<String>>>) status -> {
            String username = status.getUser().getScreenName().replaceAll("_", "UNDERSCORE");
            List<String> ments = new ArrayList<>();

            for (UserMentionEntity userMentionEntity : status.getUserMentionEntities()) {
                ments.add(userMentionEntity.getScreenName().replaceAll("_", "UNDERSCORE"));
            }

            return new Tuple2(username, ments);
        });


        actData.foreachRDD(tuple2JavaRDD ->
                tuple2JavaRDD.collect().forEach(x -> x._2.forEach((Consumer<String>) target -> {
                    Session session = driver.session();
                    session.writeTransaction(transaction -> {
                        StatementResult result = transaction.run("MERGE (p:twi" + x._1 + " {id:'twi" + x._1 + "'}) MERGE (d:twi" + target + " {id: 'twi" + target + "'}) CREATE UNIQUE (p)-[r:rel {weight: 0}]-(d) RETURN 'pizdec'");
                        return result.single().get(0).asString();
                    });
                })));

        jssc.start();
        jssc.awaitTermination();


    }

    public List<String> getPeople()
    {
        try ( Session session = driver.session() )
        {
            return session.readTransaction(DatabaseTest::matchPersonNodes);
        }
    }

    private static List<String> matchPersonNodes( Transaction tx )
    {
        List<String> names = new ArrayList<>();
        StatementResult result = tx.run( "MATCH (a:Battle) RETURN a.name ORDER BY a.name" );
        while ( result.hasNext() )
        {
            names.add( result.next().get( 0 ).asString() );
        }
        return names;
    }


}
