package bot.tool.records;

public class Record {
    public String title;
    public String description;
    public String type;

    private static String defaultType = "Другое";

    public Record() {

    }
    public Record(String title, String description) {
        this.title = title;
        this.description = description;
        this.type = defaultType;
    }
    public Record(String title, String description, String type) {
        this.title = title;
        this.description = description;
        this.type = type;
    }
    public Record(Record record) {
        this.title = record.title;
        this.description = record.description;
        this.type = record.type;
    }

    @Override
    public String toString() {
        return ("Title: " + title + "\nDescriptions: " + description + "\nType: " + type + "\n");
    }
}
