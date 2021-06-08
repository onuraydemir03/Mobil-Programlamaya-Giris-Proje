package yildiz.edu.tr.onur.proje;

public class Combine {
    int id, hatItemID, glassesItemID, upperBodyItemID, lowerBodyItemID, shoeItemID;
    public Combine(int id, int hatItemID, int glassesItemID, int upperBodyItemID, int lowerBodyItemID, int shoeItemID) {
        this.id = id;
        this.hatItemID = hatItemID;
        this.glassesItemID = glassesItemID;
        this.upperBodyItemID = upperBodyItemID;
        this.lowerBodyItemID = lowerBodyItemID;
        this.shoeItemID = shoeItemID;
    }

    @Override
    public String toString() {
        return "Combine{" +
                "id=" + id +
                ", hatItemID=" + hatItemID +
                ", glassesItemID=" + glassesItemID +
                ", upperBodyItemID=" + upperBodyItemID +
                ", lowerBodyItemID=" + lowerBodyItemID +
                ", shoeItemID=" + shoeItemID +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHatItemID() {
        return hatItemID;
    }

    public void setHatItemID(int hatItemID) {
        this.hatItemID = hatItemID;
    }

    public int getGlassesItemID() {
        return glassesItemID;
    }

    public void setGlassesItemID(int glassesItemID) {
        this.glassesItemID = glassesItemID;
    }

    public int getUpperBodyItemID() {
        return upperBodyItemID;
    }

    public void setUpperBodyItemID(int upperBodyItemID) {
        this.upperBodyItemID = upperBodyItemID;
    }

    public int getLowerBodyItemID() {
        return lowerBodyItemID;
    }

    public void setLowerBodyItemID(int lowerBodyItemID) {
        this.lowerBodyItemID = lowerBodyItemID;
    }

    public int getShoeItemID() {
        return shoeItemID;
    }

    public void setShoeItemID(int shoeItemID) {
        this.shoeItemID = shoeItemID;
    }
}
