package parsers.jaxb.model;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AnonimousClient {

    @XmlElement
    private String companyName;

    @XmlElement
    private int employeerId;

    @XmlAttribute
    private String isSecured;

    public AnonimousClient(){}

    public AnonimousClient(String companyName, int employeerId, String isSecured) {
        this.companyName = companyName;
        this.employeerId = employeerId;
        this.isSecured = isSecured;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getEmployeerId() {
        return employeerId;
    }

    public void setEmployeerId(int employeerId) {
        this.employeerId = employeerId;
    }

    public String getIsSecured() {
        return isSecured;
    }

    public void setIsSecured(String isSecured) {
        this.isSecured = isSecured;
    }
}
