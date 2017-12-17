
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphTest {

    public void test(){
List<Presentation> presentations = PresentationUtil.loadPresentations();


        SparkConf conf = new SparkConf().setAppName("test").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        SQLContext sqlContext = new org.apache.spark.sql.SQLContext(sc);

        List<StructField> verFields = new ArrayList<StructField>();
        verFields.add(DataTypes.createStructField("name",DataTypes.StringType, true));



        List<StructField> EdgFields = new ArrayList<StructField>();
        EdgFields.add(DataTypes.createStructField("src",DataTypes.LongType,
                true));
        EdgFields.add(DataTypes.createStructField("dst",DataTypes.LongType,
                true));
        EdgFields.add(DataTypes.createStructField("relationType",DataTypes.StringType,
                true));

List<Row> testRows = new ArrayList<>();
        for (Presentation presentation : presentations) {
            testRows.add(RowFactory.create(presentation.getName()));
        }
        JavaRDD<Row> verRow = sc.parallelize(testRows);
        JavaRDD<Row> edgRow = sc.parallelize(Arrays.asList(
                RowFactory.create(101L,301L,"Colleague"),
                RowFactory.create(101L,401L,"Friends"),
                RowFactory.create(401L,201L,"Reports"),
                RowFactory.create(301L,201L,"Reports"),
                RowFactory.create(201L,101L,"Reports")));

        StructType verSchema = DataTypes.createStructType(verFields);
        StructType edgSchema = DataTypes.createStructType(EdgFields);
        Dataset<Row> verDF = sqlContext.createDataFrame(verRow, verSchema);
        Dataset<Row> edgDF = sqlContext.createDataFrame(edgRow, edgSchema);
        GraphFrame g = GraphFrame.apply(verDF,edgDF);
        g.vertices().show();

    }


}
