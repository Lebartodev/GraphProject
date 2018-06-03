import org.neo4j.driver.v1.*;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTest {

    private Driver driver;

    public void initDatabase() {
        driver = GraphDatabase.driver("bolt://localhost:7687");

        Session session = driver.session();
        List<Presentation> presentations = new ArrayList<>();
        presentations = PresentationUtil.loadPresentations();
        for (Presentation presentation : presentations) {
            session.writeTransaction(transaction -> {
                //"CREATE (n:Person { name: 'Andres', title: 'Developer' })";
                String tran = "MERGE (p:Presentation {title: '" + presentation.getTitle().replaceAll("'", "")
                        + "', name: '" + presentation.getName().replaceAll("'", "")
                        + "', speaker: '" + presentation.getMain_speaker().replaceAll("'", "");


                for (RelatedTalk relatedTalk : presentation.getRelated_talks()) {
                    tran += "', related_" + presentation.getRelated_talks().indexOf(relatedTalk) + ": '" + relatedTalk.getTitle().replaceAll("'", "");
                }
                tran += "'}) return 'kek'";
                StatementResult result = transaction.run(tran);
                return result.single().get(0).asString();
            });
        }


    }

    public List<TwitterObj> getPeople() {
        driver = GraphDatabase.driver("bolt://localhost:7687");
        try (Session session = driver.session()) {
            return session.readTransaction(DatabaseTest::matchPersonNodes);
        }
    }

    private static List<TwitterObj> matchPersonNodes(Transaction tx) {
        List<TwitterObj> names = new ArrayList<>();
        StatementResult result = tx.run("MATCH (a) RETURN a");
        while (result.hasNext()) {
            names.add(new TwitterObj(result.next().get(0).get("id").asString()));
        }
        return names;
    }


}
