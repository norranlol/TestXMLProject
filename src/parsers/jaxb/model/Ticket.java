package parsers.jaxb.model;

import javax.xml.bind.annotation.*;

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
    private String status;

    @XmlElement
    private int price;

    @XmlAttribute
    private String position;

    @XmlElement
    private Client client;

    @XmlElement
    private AnonimousClient anonimousClient;

    public Ticket(){}

    public Ticket(int id, int row, int place, String status, int price, String position, Client client, AnonimousClient anonimousClient) {
        this.id = id;
        this.row = row;
        this.place = place;
        this.status = status;
        this.price = price;
        this.position = position;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
