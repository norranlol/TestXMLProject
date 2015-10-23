package parsers.jaxb.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement (name="flights"/*, namespace = "http://www.airport.ru"*/)
@XmlAccessorType(XmlAccessType.FIELD)
public class Flights {

    @XmlElement(name = "flight")
    private List<Flight> flightList;

    public Flights(){}

    public Flights(List<Flight> flightList) {
        this.flightList = flightList;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
    }
}
