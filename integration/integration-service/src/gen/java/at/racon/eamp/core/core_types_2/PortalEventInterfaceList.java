
package at.racon.eamp.core.core_types_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PortalEventInterfaceList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PortalEventInterfaceList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="portalEventInterface" type="{http://www.racon.at/eamp/core/core-types-2.0}PortalEventInterface" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PortalEventInterfaceList", propOrder = {
    "portalEventInterface"
})
public class PortalEventInterfaceList {

    protected List<PortalEventInterface> portalEventInterface;

    /**
     * Gets the value of the portalEventInterface property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the portalEventInterface property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPortalEventInterface().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PortalEventInterface }
     * 
     * 
     */
    public List<PortalEventInterface> getPortalEventInterface() {
        if (portalEventInterface == null) {
            portalEventInterface = new ArrayList<PortalEventInterface>();
        }
        return this.portalEventInterface;
    }

}
