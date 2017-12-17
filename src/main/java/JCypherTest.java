
import iot.jcypher.database.DBAccessFactory;
import iot.jcypher.database.DBProperties;
import iot.jcypher.database.DBType;
import iot.jcypher.database.IDBAccess;
import iot.jcypher.graph.GrLabel;
import iot.jcypher.graph.GrNode;
import iot.jcypher.graph.GrProperty;
import iot.jcypher.graph.GrRelation;
import iot.jcypher.graph.Graph;
import iot.jcypher.query.JcQuery;
import iot.jcypher.query.JcQueryResult;
import iot.jcypher.query.api.IClause;
import iot.jcypher.query.factories.clause.CREATE;
import iot.jcypher.query.factories.clause.MATCH;
import iot.jcypher.query.factories.clause.RETURN;
import iot.jcypher.query.result.JcError;
import iot.jcypher.query.values.JcNode;
import iot.jcypher.query.values.JcNumber;
import iot.jcypher.query.writer.Format;
import iot.jcypher.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class JCypherTest {
    private static IDBAccess dbAccess;
    public  void initDBConnection() {
        Properties props = new Properties();

        // properties for remote access and for embedded access
        // (not needed for in memory access)
        // have a look at the DBProperties interface
        // the appropriate database access class will pick the properties it needs
        props.setProperty(DBProperties.SERVER_ROOT_URI, "http://localhost:7474");


        dbAccess = DBAccessFactory.createDBAccess(DBType.IN_MEMORY, props);
    }

    public void queryNodeCount() {
        JcQuery query;
        JcQueryResult result;

        String queryTitle = "COUNT NODES";
        JcNode n = new JcNode("n");
        JcNumber nCount = new JcNumber("nCount");

        query = new JcQuery();
        query.setClauses(new IClause[] {
                MATCH.node(n),
                RETURN.count().value(n).AS(nCount)
        });
        /** map to CYPHER statements and map to JSON, print the mapping results to System.out.
         This will show what normally is created in the background when accessing a Neo4j database*/
        print(query, queryTitle, Format.PRETTY_3);

        /** execute the query against a Neo4j database */
        result = dbAccess.execute(query);
        if (result.hasErrors())
            printErrors(result);

        /** print the JSON representation of the query result */
        print(result, queryTitle);

        List<BigDecimal> nr = result.resultOf(nCount);
        System.out.println(nr.get(0));
        return;
    }


    private static void print(JcQuery query, String title, Format format) {
        System.out.println("QUERY: " + title + " --------------------");
        // map to Cypher
        String cypher = iot.jcypher.util.Util.toCypher(query, format);
        System.out.println("CYPHER --------------------");
        System.out.println(cypher);

        // map to JSON
        String json = iot.jcypher.util.Util.toJSON(query, format);
        System.out.println("");
        System.out.println("JSON   --------------------");
        System.out.println(json);

        System.out.println("");
    }

    /**
     * print the JSON representation of the query result
     * @param queryResult
     */
    private static void print(JcQueryResult queryResult, String title) {
        System.out.println("RESULT OF QUERY: " + title + " --------------------");
        String resultString = Util.writePretty(queryResult.getJsonResult());
        System.out.println(resultString);
    }

    private static void print(List<GrNode> nodes, boolean distinct) {
        List<Long> ids = new ArrayList<Long>();
        StringBuilder sb = new StringBuilder();
        boolean firstNode = true;
        for (GrNode node : nodes) {
            if (!ids.contains(node.getId()) || !distinct) {
                ids.add(node.getId());
                if (!firstNode)
                    sb.append("\n");
                else
                    firstNode = false;
                sb.append("---NODE---:\n");
                sb.append('[');
                sb.append(node.getId());
                sb.append(']');
                for (GrLabel label : node.getLabels()) {
                    sb.append(", ");
                    sb.append(label.getName());
                }
                sb.append("\n");
                boolean first = true;
                for (GrProperty prop : node.getProperties()) {
                    if (!first)
                        sb.append(", ");
                    else
                        first = false;
                    sb.append(prop.getName());
                    sb.append(" = ");
                    sb.append(prop.getValue());
                }
            }
        }
        System.out.println(sb.toString());
    }

    private static void printErrors(JcQueryResult result) {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------General Errors:");
        appendErrorList(result.getGeneralErrors(), sb);
        sb.append("\n---------------DB Errors:");
        appendErrorList(result.getDBErrors(), sb);
        sb.append("\n---------------end Errors:");
        String str = sb.toString();
        System.out.println("");
        System.out.println(str);
    }

    /**
     * print errors to System.out
     * @param result
     */
    private static void printErrors(List<JcError> errors) {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------Errors:");
        appendErrorList(errors, sb);
        sb.append("\n---------------end Errors:");
        String str = sb.toString();
        System.out.println("");
        System.out.println(str);
    }

    private static void appendErrorList(List<JcError> errors, StringBuilder sb) {
        int num = errors.size();
        for (int i = 0; i < num; i++) {
            JcError err = errors.get(i);
            sb.append('\n');
            if (i > 0) {
                sb.append("-------------------\n");
            }
            sb.append("codeOrType: ");
            sb.append(err.getCodeOrType());
            sb.append("\nmessage: ");
            sb.append(err.getMessage());
            if (err.getAdditionalInfo() != null) {
                sb.append("\nadditional info: ");
                sb.append(err.getAdditionalInfo());
            }
        }
    }



}
