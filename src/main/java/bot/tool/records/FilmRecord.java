package bot.tool.records;

public class FilmRecord extends Record {

    {
        description = "";
        type = "";

    }

    public static class Theatre {
        public String theatre;
        public String[] sessions;
        public int[] prices;

        public Theatre(String theatre, String[] sessions, int[] prices) {
            this.theatre = theatre;
            this.sessions = sessions;
            this.prices = prices;
        }
    }

    public String cinema;
    public Theatre[] theatres;

    public FilmRecord() {
        super();
    }

    public FilmRecord(String cinema, Theatre[] theatres) {
        this.cinema = cinema;
        this.theatres = theatres;
    }

    public FilmRecord(String title, String description, String cinema, Theatre[] theatres) {
        super(title, description);
        this.cinema = cinema;
        this.theatres = theatres;
    }

    public FilmRecord(String title, String description, String type, String cinema, Theatre[] theatres) {
        super(title, description, type);
        this.cinema = cinema;
        this.theatres = theatres;
    }

    public FilmRecord(Record record, String cinema, Theatre[] theatres) {
        super(record);
        this.cinema = cinema;
        this.theatres = theatres;
    }

    public void addTheatre(Theatre theatre) {
        if(theatres == null) {
            theatres = new Theatre[1];
            theatres[0] = theatre;
            return;
        }
        Theatre[] tmp = new Theatre[theatres.length + 1];
        for(int i = 0; i < theatres.length; i++)
            tmp[i] = theatres[i];
        tmp[tmp.length-1] = theatre;
        theatres = tmp;
    }


}
