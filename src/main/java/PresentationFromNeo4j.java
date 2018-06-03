public class PresentationFromNeo4j {
    private long id;
    private String name;
    private String title;
    private String speaker;
    private String related_0;
    private String related_1;
    private String related_2;
    private String related_3;
    private String related_4;
    private String related_5;
    private String related_6;

    public PresentationFromNeo4j(long id, String name, String title, String speaker) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.speaker = speaker;
    }
    public PresentationFromNeo4j(long id, String name) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.speaker = speaker;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelated_0() {
        return related_0;
    }

    public void setRelated_0(String related_0) {
        this.related_0 = related_0;
    }

    public String getRelated_1() {
        return related_1;
    }

    public void setRelated_1(String related_1) {
        this.related_1 = related_1;
    }

    public String getRelated_2() {
        return related_2;
    }

    public void setRelated_2(String related_2) {
        this.related_2 = related_2;
    }

    public String getRelated_3() {
        return related_3;
    }

    public void setRelated_3(String related_3) {
        this.related_3 = related_3;
    }

    public String getRelated_4() {
        return related_4;
    }

    public void setRelated_4(String related_4) {
        this.related_4 = related_4;
    }

    public String getRelated_5() {
        return related_5;
    }

    public void setRelated_5(String related_5) {
        this.related_5 = related_5;
    }

    public String getRelated_6() {
        return related_6;
    }

    public void setRelated_6(String related_6) {
        this.related_6 = related_6;
    }
}
