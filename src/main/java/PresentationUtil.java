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
                replaceRules(line,7);
                replaceRules(line,11);
                replaceRules(line,14);
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
    private static void replaceRules(String[] line, int index){
        line[index] = line[index].replaceAll("'s ", "s ");
        line[index] = line[index].replaceAll("'ve ", "ve ");
        line[index] = line[index].replaceAll("M'B", "MB");
        line[index] = line[index].replaceAll("'re ", "re ");
        line[index] = line[index].replaceAll("'ll ", "ll ");
        line[index] = line[index].replaceAll("'t ", "t ");
        line[index] = line[index].replaceAll("'m ", "m ");
        line[index] = line[index].replaceAll("Alzheimer's", "Alzheimers");
        line[index] = line[index].replaceAll("Parkinson's", "Parkinsons");
        line[index] = line[index].replaceAll("don't", "dont");
        line[index] = line[index].replaceAll("'s-eye", "s-eye");
        line[index] = line[index].replaceAll("won't", "wont");
        line[index] = line[index].replaceAll("kids' ", "kids ");



    }
}
