
package at.racon.eamp.core.core_types_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinesDomainList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinesDomainList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="businesDomain" type="{http://www.racon.at/eamp/core/core-types-2.0}BusinesDomain" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinesDomainList", propOrder = {
    "businesDomain"
})
public class BusinesDomainList {

    protected List<BusinesDomain> businesDomain;

    /**
     * Gets the value of the businesDomain property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the businesDomain property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBusinesDomain().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BusinesDomain }
     * 
     * 
     */
    public List<BusinesDomain> getBusinesDomain() {
        if (businesDomain == null) {
            businesDomain = new ArrayList<BusinesDomain>();
        }
        return this.businesDomain;
    }

}
