
package at.racon.eamp.eamp_types_1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import at.grz.jp.datatypes_2.Key;


/**
 * <p>Java class for Module complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Module">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.racon.at/eamp/eamp-types-1.0}Artifact">
 *       &lt;sequence>
 *         &lt;element name="application" type="{http://www.racon.at/eamp/eamp-types-1.0}Application"/>
 *         &lt;element name="tier" type="{http://www.grz.at/jp/DataTypes-2.5}Key"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Module", propOrder = {
    "application",
    "tier"
})
public class Module
    extends Artifact
{

    @XmlElement(required = true, nillable = true)
    protected Application application;
    @XmlElement(required = true)
    protected Key tier;

    /**
     * Gets the value of the application property.
     * 
     * @return
     *     possible object is
     *     {@link Application }
     *     
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Sets the value of the application property.
     * 
     * @param value
     *     allowed object is
     *     {@link Application }
     *     
     */
    public void setApplication(Application value) {
        this.application = value;
    }

    /**
     * Gets the value of the tier property.
     * 
     * @return
     *     possible object is
     *     {@link Key }
     *     
     */
    public Key getTier() {
        return tier;
    }

    /**
     * Sets the value of the tier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Key }
     *     
     */
    public void setTier(Key value) {
        this.tier = value;
    }

}
