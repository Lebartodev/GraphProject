
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.graphframes.GraphFrame;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Relationship;

import java.util.ArrayList;
import java.util.List;
import static org.apache.spark.sql.functions.*;
public class GraphTest {
    private Driver driver;


    public void test() {
        List<PresentationFromNeo4j> presentations = getPresentations();
        List<Relationship> relationships = getRelations();
        SparkConf conf = new SparkConf().setAppName("test").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        SQLContext sqlContext = new org.apache.spark.sql.SQLContext(sc);

        List<StructField> verFields = new ArrayList<StructField>();
        verFields.add(DataTypes.createStructField("id", DataTypes.LongType, true));
        verFields.add(DataTypes.createStructField("name", DataTypes.StringType, true));
        verFields.add(DataTypes.createStructField("title", DataTypes.StringType, true));
        verFields.add(DataTypes.createStructField("related_0", DataTypes.StringType, true));
        verFields.add(DataTypes.createStructField("related_1", DataTypes.StringType, true));
        verFields.add(DataTypes.createStructField("related_2", DataTypes.StringType, true));
        verFields.add(DataTypes.createStructField("related_3", DataTypes.StringType, true));
        verFields.add(DataTypes.createStructField("related_4", DataTypes.StringType, true));
        verFields.add(DataTypes.createStructField("related_5", DataTypes.StringType, true));
        verFields.add(DataTypes.createStructField("related_6", DataTypes.StringType, true));

        List<StructField> EdgFields = new ArrayList<StructField>();
        EdgFields.add(DataTypes.createStructField("src", DataTypes.LongType,
                true));
        EdgFields.add(DataTypes.createStructField("dst", DataTypes.LongType,
                true));
        EdgFields.add(DataTypes.createStructField("relationType", DataTypes.StringType,
                true));

        List<Row> testRows = new ArrayList<>();
        for (PresentationFromNeo4j presentation : presentations) {
            testRows.add(RowFactory.create(presentation.getId(),
                    presentation.getName(),
                    presentation.getTitle(),
                    presentation.getRelated_0(), presentation.getRelated_1(),
                    presentation.getRelated_2(), presentation.getRelated_3(),
                    presentation.getRelated_4(), presentation.getRelated_5(),
                    presentation.getRelated_6()));
        }
        JavaRDD<Row> verRow = sc.parallelize(testRows);
        List<Row> testEdgRows = new ArrayList<>();
        for (Relationship relationship : relationships) {
            testEdgRows.add(RowFactory.create(relationship.startNodeId(), relationship.endNodeId(), relationship.type()));
        }
        JavaRDD<Row> edgRow = sc.parallelize(testEdgRows);

        StructType verSchema = DataTypes.createStructType(verFields);
        StructType edgSchema = DataTypes.createStructType(EdgFields);
        Dataset<Row> verDF = sqlContext.createDataFrame(verRow, verSchema);
        Dataset<Row> edgDF = sqlContext.createDataFrame(edgRow, edgSchema);
        GraphFrame g = GraphFrame.apply(verDF, edgDF);
        g.vertices().show();

        GraphFrame prRes = g.pageRank().maxIter(10).resetProbability(0.15).run();
        prRes.vertices().orderBy(desc("pagerank")).show();

    }

    public List<PresentationFromNeo4j> getPresentations() {
        driver = GraphDatabase.driver("bolt://localhost:7687");
        try (Session session = driver.session()) {
            return session.readTransaction(this::matchPersonNodes);
        }
    }

    public List<PresentationFromNeo4j> matchPersonNodes(Transaction tx) {
        List<PresentationFromNeo4j> names = new ArrayList<>();
        StatementResult result = tx.run("MATCH (n) RETURN n;");
        while (result.hasNext()) {

            Record record = result.next();
            PresentationFromNeo4j presentationFromNeo4j = new PresentationFromNeo4j(record.get(0).asNode().id(), record.get(0).get("name").asString(),
                    record.get(0).get("title").asString());
            try {
                presentationFromNeo4j.setRelated_0(record.get(0).get("related_0").asString());
                presentationFromNeo4j.setRelated_1(record.get(0).get("related_1").asString());
                presentationFromNeo4j.setRelated_2(record.get(0).get("related_2").asString());
                presentationFromNeo4j.setRelated_3(record.get(0).get("related_3").asString());
                presentationFromNeo4j.setRelated_4(record.get(0).get("related_4").asString());
                presentationFromNeo4j.setRelated_5(record.get(0).get("related_5").asString());
                presentationFromNeo4j.setRelated_6(record.get(0).get("related_6").asString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            names.add(presentationFromNeo4j);
        }
        return names;
    }

    public List<Relationship> getRelations() {
        driver = GraphDatabase.driver("bolt://localhost:7687");
        try (Session session = driver.session()) {
            return session.readTransaction(this::matchRelations);
        }
    }

    public List<Relationship> matchRelations(Transaction tx) {
        List<Relationship> names = new ArrayList<>();
        StatementResult result = tx.run("MATCH (n)-[r]->(m) RETURN r;");
        while (result.hasNext()) {

            Record record = result.next();
            Relationship d = record.get("r").asRelationship();
            names.add(d);

        }
        return names;
    }


}
