package bot.tool.records;

public class CoursesRecord extends Record {
    public String date;
    public String place;
    public String ref;

    {description = "";}


    public CoursesRecord(String date, String place, String ref) {
        super();
        this.date = date;
        this.place = place;
        this.ref = ref;
    }

    public CoursesRecord(String title, String type, String date, String place, String ref) {
        super(title, "", type);
        this.date = date;
        this.place = place;
        this.ref = ref;
    }

    public CoursesRecord(Record record, String date, String place, String ref) {
        super(record);
        this.date = date;
        this.place = place;
        this.ref = ref;
    }

    @Override
    public String toString() {
        return (super.toString() + "Date: " + date + "\nPlace" + place + "\nRef: " + ref + "\n");
    }


}
