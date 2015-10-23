package parsers.jaxb.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Ticket {

    @XmlElement
    private int id;

    @XmlElement
    private int row;

    @XmlElement
    private int place;

    @XmlElement
    private int price;

    @XmlElement
    private Client client;

    @XmlElement
    private AnonimousClient anonimousClient;

    public Ticket(){}

    public Ticket(int id, int row, int place, int price, Client client, AnonimousClient anonimousClient) {
        this.id = id;
        this.row = row;
        this.place = place;
        this.price = price;
        this.client = client;
        this.anonimousClient = anonimousClient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public AnonimousClient getAnonimousClient() {
        return anonimousClient;
    }

    public void setAnonimousClient(AnonimousClient anonimousClient) {
        this.anonimousClient = anonimousClient;
    }
}
