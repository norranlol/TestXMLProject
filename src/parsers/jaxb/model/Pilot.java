package parsers.jaxb.model;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Pilot {

    @XmlElement
    private int id;

    @XmlElement
    private String surname;

    @XmlElement
    private String name;

    @XmlElement
    private String patronymic;

    @XmlElement
    private int category;

    @XmlAttribute
    private String certificateNumber;

    @XmlAttribute
    private String phoneNumber;

    @XmlAttribute
    private String eMail;

    @XmlAttribute
    private String address;

    public Pilot(){}

    public Pilot(int id, String surname, String name, String patronymic, int category, String certificateNumber, String phoneNumber, String eMail, String address) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.category = category;
        this.certificateNumber = certificateNumber;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
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
