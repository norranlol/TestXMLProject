package parsers.jaxb.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Route {

    @XmlElement
    private int id;

    @XmlElement
    private String title;

    @XmlElement
    private Point pointOfDeparture;

    @XmlElement
    private Point pointOfArrival;

    @XmlElement
    private String cipher;

    @XmlElement
    private int distance;

    public Route(){}

    public Route(int id, String title, Point pointOfDeparture, Point pointOfArrival, String cipher, int distance) {
        this.id = id;
        this.title = title;
        this.pointOfDeparture = pointOfDeparture;
        this.pointOfArrival = pointOfArrival;
        this.cipher = cipher;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Point getPointOfDeparture() {
        return pointOfDeparture;
    }

    public void setPointOfDeparture(Point pointOfDeparture) {
        this.pointOfDeparture = pointOfDeparture;
    }

    public Point getPointOfArrival() {
        return pointOfArrival;
    }

    public void setPointOfArrival(Point pointOfArrival) {
        this.pointOfArrival = pointOfArrival;
    }

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
