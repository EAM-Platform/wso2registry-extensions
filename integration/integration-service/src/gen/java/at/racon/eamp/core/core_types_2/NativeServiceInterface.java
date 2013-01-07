
package at.racon.eamp.core.core_types_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NativeServiceInterface complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NativeServiceInterface">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.racon.at/eamp/core/core-types-2.0}ServiceInterface">
 *       &lt;sequence>
 *         &lt;element name="businessInterface" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="remoteInterface" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NativeServiceInterface", propOrder = {
    "businessInterface",
    "remoteInterface"
})
public class NativeServiceInterface
    extends ServiceInterface
{

    @XmlElement(required = true)
    protected String businessInterface;
    protected String remoteInterface;

    /**
     * Gets the value of the businessInterface property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessInterface() {
        return businessInterface;
    }

    /**
     * Sets the value of the businessInterface property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessInterface(String value) {
        this.businessInterface = value;
    }

    /**
     * Gets the value of the remoteInterface property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteInterface() {
        return remoteInterface;
    }

    /**
     * Sets the value of the remoteInterface property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteInterface(String value) {
        this.remoteInterface = value;
    }

}
