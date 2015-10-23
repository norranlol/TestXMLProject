package parsers.jaxb.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Plane {

    @XmlElement
    private int id;

    @XmlElement
    private String model;

    @XmlElement
    private int year;

    @XmlElement
    private int countOfRows;

    @XmlElement
    private int placesInRow;

    public Plane(){}

    public Plane(int id, String model, int year, int countOfRows, int placesInRow) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.countOfRows = countOfRows;
        this.placesInRow = placesInRow;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCountOfRows() {
        return countOfRows;
    }

    public void setCountOfRows(int countOfRows) {
        this.countOfRows = countOfRows;
    }

    public int getPlacesInRow() {
        return placesInRow;
    }

    public void setPlacesInRow(int placesInRow) {
        this.placesInRow = placesInRow;
    }
}
