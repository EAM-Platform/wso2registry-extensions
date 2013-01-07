
package at.racon.eamp.eamp_types_1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import at.grz.jp.datatypes_2.Key;


/**
 * <p>Java class for Application complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Application">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.racon.at/eamp/eamp-types-1.0}Artifact">
 *       &lt;sequence>
 *         &lt;element name="domain" type="{http://www.grz.at/jp/DataTypes-2.5}Key"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Application", propOrder = {
    "domain"
})
public class Application
    extends Artifact
{

    @XmlElement(required = true)
    protected Key domain;

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link Key }
     *     
     */
    public Key getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link Key }
     *     
     */
    public void setDomain(Key value) {
        this.domain = value;
    }

}
