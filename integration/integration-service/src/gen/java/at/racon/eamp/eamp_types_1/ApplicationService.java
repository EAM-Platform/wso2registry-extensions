
package at.racon.eamp.eamp_types_1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import at.grz.jp.datatypes_2.Key;


/**
 * <p>Java class for ApplicationService complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicationService">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.racon.at/eamp/eamp-types-1.0}Artifact">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://www.grz.at/jp/DataTypes-2.5}Key"/>
 *         &lt;element name="module" type="{http://www.racon.at/eamp/eamp-types-1.0}Module"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicationService", propOrder = {
    "type",
    "module"
})
public class ApplicationService
    extends Artifact
{

    @XmlElement(required = true)
    protected Key type;
    @XmlElement(required = true)
    protected Module module;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link Key }
     *     
     */
    public Key getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link Key }
     *     
     */
    public void setType(Key value) {
        this.type = value;
    }

    /**
     * Gets the value of the module property.
     * 
     * @return
     *     possible object is
     *     {@link Module }
     *     
     */
    public Module getModule() {
        return module;
    }

    /**
     * Sets the value of the module property.
     * 
     * @param value
     *     allowed object is
     *     {@link Module }
     *     
     */
    public void setModule(Module value) {
        this.module = value;
    }

}
