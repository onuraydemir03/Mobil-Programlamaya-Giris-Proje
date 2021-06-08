package yildiz.edu.tr.onur.proje;

public class Event {
    private int id;
    private String name, type, date;
    private double l1, l2;

    public Event(int id, String name, String type, String date, double l1, double l2) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.l1 = l1;
        this.l2 = l2;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", l1=" + l1 +
                ", l2=" + l2 +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getL1() {
        return l1;
    }

    public void setL1(double l1) {
        this.l1 = l1;
    }

    public double getL2() {
        return l2;
    }

    public void setL2(double l2) {
        this.l2 = l2;
    }
}
