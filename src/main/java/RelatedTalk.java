public class RelatedTalk {


    private int id;
    private String hero;
    private String speaker;
    private String title;
    private int duration;
    private String slug;
    private int viewed_count;

    public RelatedTalk(int id, String hero, String speaker, String title, int duration, String slug, int viewed_count) {
        this.id = id;
        this.hero = hero;
        this.speaker = speaker;
        this.title = title;
        this.duration = duration;
        this.slug = slug;
        this.viewed_count = viewed_count;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getViewed_count() {
        return viewed_count;
    }

    public void setViewed_count(int viewed_count) {
        this.viewed_count = viewed_count;
    }
}
