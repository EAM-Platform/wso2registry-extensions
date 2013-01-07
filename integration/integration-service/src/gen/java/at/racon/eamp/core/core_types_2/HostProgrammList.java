
package at.racon.eamp.core.core_types_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostProgrammList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostProgrammList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="hostProgramm" type="{http://www.racon.at/eamp/core/core-types-2.0}HostProgramm" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostProgrammList", propOrder = {
    "hostProgramm"
})
public class HostProgrammList {

    protected List<HostProgramm> hostProgramm;

    /**
     * Gets the value of the hostProgramm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostProgramm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostProgramm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostProgramm }
     * 
     * 
     */
    public List<HostProgramm> getHostProgramm() {
        if (hostProgramm == null) {
            hostProgramm = new ArrayList<HostProgramm>();
        }
        return this.hostProgramm;
    }

}
