
package at.racon.eamp.core.core_types_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StakeholderList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StakeholderList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="stakeholder" type="{http://www.racon.at/eamp/core/core-types-2.0}Stakeholder" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StakeholderList", propOrder = {
    "stakeholder"
})
public class StakeholderList {

    protected List<Stakeholder> stakeholder;

    /**
     * Gets the value of the stakeholder property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stakeholder property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStakeholder().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Stakeholder }
     * 
     * 
     */
    public List<Stakeholder> getStakeholder() {
        if (stakeholder == null) {
            stakeholder = new ArrayList<Stakeholder>();
        }
        return this.stakeholder;
    }

}
