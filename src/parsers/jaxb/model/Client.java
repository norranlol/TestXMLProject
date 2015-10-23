package parsers.jaxb.model;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Client {

    @XmlElement
    private int id;

    @XmlElement
    private String surname;

    @XmlElement
    private String name;

    @XmlElement
    private String patronymic;

    @XmlAttribute
    private int pasportSeries;

    @XmlAttribute
    private int pasportNumber;

    @XmlAttribute
    private String phoneNumber;

    @XmlAttribute
    private String eMail;

    @XmlAttribute
    private String address;

    public Client(){}

    public Client(int id, String surname, String name, String patronymic, int pasportSeries, int pasportNumber, String phoneNumber, String eMail, String address) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.pasportSeries = pasportSeries;
        this.pasportNumber = pasportNumber;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getPasportSeries() {
        return pasportSeries;
    }

    public void setPasportSeries(int pasportSeries) {
        this.pasportSeries = pasportSeries;
    }

    public int getPasportNumber() {
        return pasportNumber;
    }

    public void setPasportNumber(int pasportNumber) {
        this.pasportNumber = pasportNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
