import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PresentationUtil {

    public static List<Presentation> loadPresentations() {
        List<Presentation> presentations = new ArrayList<>();
        String csvFile = "./data.csv";
        Gson gson = new GsonBuilder().create();

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                line[11] = line[11].replaceAll("'s ", "s ");
                line[11] = line[11].replaceAll("'ve ", "ve ");
                line[11] = line[11].replaceAll("M'B", "MB");

                System.out.println(line[11]);
                List<RelatedTalk> relatedTalks = gson.fromJson(line[11], new TypeToken<List<RelatedTalk>>() {
                }.getType());
                List<String> tags = gson.fromJson(line[13], new TypeToken<List<String>>() {
                }.getType());
                System.out.println(relatedTalks.get(0).getTitle());

                presentations.add(new Presentation(Integer.parseInt(line[0]), line[1], Integer.parseInt(line[2]), line[3],
                        Long.parseLong(line[4]), Integer.parseInt(line[5]), line[6], line[7],
                        Integer.parseInt(line[8]), Long.parseLong(line[9]), line[10], relatedTalks, line[12], tags, line[14], line[15], Integer.parseInt(line[16])));

//                System.out.println("Country [id= " + line[0] + ", code= " + line[1] + " , name=" + line[2] + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return presentations;
    }
}
