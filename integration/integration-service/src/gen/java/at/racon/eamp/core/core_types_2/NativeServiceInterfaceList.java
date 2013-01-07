
package at.racon.eamp.core.core_types_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NativeServiceInterfaceList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NativeServiceInterfaceList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nativeServiceInterface" type="{http://www.racon.at/eamp/core/core-types-2.0}NativeServiceInterface" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NativeServiceInterfaceList", propOrder = {
    "nativeServiceInterface"
})
public class NativeServiceInterfaceList {

    protected List<NativeServiceInterface> nativeServiceInterface;

    /**
     * Gets the value of the nativeServiceInterface property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nativeServiceInterface property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNativeServiceInterface().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NativeServiceInterface }
     * 
     * 
     */
    public List<NativeServiceInterface> getNativeServiceInterface() {
        if (nativeServiceInterface == null) {
            nativeServiceInterface = new ArrayList<NativeServiceInterface>();
        }
        return this.nativeServiceInterface;
    }

}
