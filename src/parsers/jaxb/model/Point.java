package parsers.jaxb.model;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Point {

    @XmlElement
    private int id;

    @XmlElement
    private String country;

    @XmlElement
    private String city;

    @XmlAttribute
    private String image;

    public Point(){}

    public Point(int id, String country, String city, String image) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
