package parsers.jaxb.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.util.List;

@XmlRootElement (name="flight", namespace = "http://www.airport.ru")
@XmlAccessorType(XmlAccessType.FIELD)
public class Flight {

    @XmlElement
    private int id;

    @XmlElement
    private XMLGregorianCalendar timeOfDeparture;

    @XmlElement
    private XMLGregorianCalendar timeOfArrival;

    @XmlElement(name = "pilot")
    private List<Pilot> pilotList;

    @XmlElement
    private Plane plane;

    @XmlElement
    private Route route;

    @XmlElement(name = "ticket")
    private List<Ticket> ticketList;

    public Flight(){}

    public Flight(int id, XMLGregorianCalendar timeOfDeparture, XMLGregorianCalendar timeOfArrival, List<Pilot> pilotList, Route route, List<Ticket> ticketList) {
        this.id = id;
        this.timeOfDeparture = timeOfDeparture;
        this.timeOfArrival = timeOfArrival;
        this.pilotList = pilotList;
        this.route = route;
        this.ticketList = ticketList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public XMLGregorianCalendar getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public void setTimeOfDeparture(XMLGregorianCalendar timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }

    public XMLGregorianCalendar getTimeOfArrival() {
        return timeOfArrival;
    }

    public void setTimeOfArrival(XMLGregorianCalendar timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }

    public List<Pilot> getPilotList() {
        return pilotList;
    }

    public void setPilotList(List<Pilot> pilotList) {
        this.pilotList = pilotList;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
}
