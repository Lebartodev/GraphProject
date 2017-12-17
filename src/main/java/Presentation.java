import java.util.List;

public class Presentation {


    private int comments;
    private String description;
    private int duration;
    private String event;
    private long film_date;
    private int languages;
    private String main_speaker;
    private String name;
    private int num_speakers;
    private long published_date;
    private String ratings;
    private List<RelatedTalk> related_talks;
    private String speaker_occupation;
    private List<String> tags;
    private String title;
    private String url;
    private int views;

    public Presentation(int comments, String description, int duration, String event, long film_date, int languages, String main_speaker, String name, int num_speakers, long published_date, String ratings, List<RelatedTalk> related_talks, String speaker_occupation, List<String> tags, String title, String url, int views) {
        this.comments = comments;
        this.description = description;
        this.duration = duration;
        this.event = event;
        this.film_date = film_date;
        this.languages = languages;
        this.main_speaker = main_speaker;
        this.name = name;
        this.num_speakers = num_speakers;
        this.published_date = published_date;
        this.ratings = ratings;
        this.related_talks = related_talks;
        this.speaker_occupation = speaker_occupation;
        this.tags = tags;
        this.title = title;
        this.url = url;
        this.views = views;
    }
    //comments,description,duration,event,film_date,languages,main_speaker,name,num_speaker,published_date,ratings,related_talks,speaker_occupation,tags,title,url,views

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public long getFilm_date() {
        return film_date;
    }

    public void setFilm_date(long film_date) {
        this.film_date = film_date;
    }

    public int getLanguages() {
        return languages;
    }

    public void setLanguages(int languages) {
        this.languages = languages;
    }

    public String getMain_speaker() {
        return main_speaker;
    }

    public void setMain_speaker(String main_speaker) {
        this.main_speaker = main_speaker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum_speakers() {
        return num_speakers;
    }

    public void setNum_speakers(int num_speakers) {
        this.num_speakers = num_speakers;
    }

    public long getPublished_date() {
        return published_date;
    }

    public void setPublished_date(long published_date) {
        this.published_date = published_date;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public List<RelatedTalk> getRelated_talks() {
        return related_talks;
    }

    public void setRelated_talks(List<RelatedTalk> related_talks) {
        this.related_talks = related_talks;
    }

    public String getSpeaker_occupation() {
        return speaker_occupation;
    }

    public void setSpeaker_occupation(String speaker_occupation) {
        this.speaker_occupation = speaker_occupation;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
