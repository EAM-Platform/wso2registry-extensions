
package at.racon.eamp.core.core_types_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserInterfaceComponentList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserInterfaceComponentList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userInterfaceComponent" type="{http://www.racon.at/eamp/core/core-types-2.0}UserInterfaceComponent" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserInterfaceComponentList", propOrder = {
    "userInterfaceComponent"
})
public class UserInterfaceComponentList {

    protected List<UserInterfaceComponent> userInterfaceComponent;

    /**
     * Gets the value of the userInterfaceComponent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userInterfaceComponent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserInterfaceComponent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserInterfaceComponent }
     * 
     * 
     */
    public List<UserInterfaceComponent> getUserInterfaceComponent() {
        if (userInterfaceComponent == null) {
            userInterfaceComponent = new ArrayList<UserInterfaceComponent>();
        }
        return this.userInterfaceComponent;
    }

}
