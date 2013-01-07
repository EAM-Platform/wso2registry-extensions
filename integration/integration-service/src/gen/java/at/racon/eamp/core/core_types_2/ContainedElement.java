
package at.racon.eamp.core.core_types_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContainedElement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContainedElement">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.racon.at/eamp/core/core-types-2.0}SoftwareArtifact">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="module" type="{http://www.racon.at/eamp/core/core-types-2.0}Module"/>
 *         &lt;element name="providedInterfaces" type="{http://www.racon.at/eamp/core/core-types-2.0}InterfaceList" minOccurs="0"/>
 *         &lt;element name="consumedInformations" type="{http://www.racon.at/eamp/core/core-types-2.0}InformationObjectList" minOccurs="0"/>
 *         &lt;element name="consumedPorts" type="{http://www.racon.at/eamp/core/core-types-2.0}PortList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContainedElement", propOrder = {
    "type",
    "module",
    "providedInterfaces",
    "consumedInformations",
    "consumedPorts"
})
@XmlSeeAlso({
    HostProgramm.class,
    UserInterfaceComponent.class,
    Service.class
})
public class ContainedElement
    extends SoftwareArtifact
{

    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected Module module;
    protected InterfaceList providedInterfaces;
    protected InformationObjectList consumedInformations;
    protected PortList consumedPorts;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
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

    /**
     * Gets the value of the providedInterfaces property.
     * 
     * @return
     *     possible object is
     *     {@link InterfaceList }
     *     
     */
    public InterfaceList getProvidedInterfaces() {
        return providedInterfaces;
    }

    /**
     * Sets the value of the providedInterfaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link InterfaceList }
     *     
     */
    public void setProvidedInterfaces(InterfaceList value) {
        this.providedInterfaces = value;
    }

    /**
     * Gets the value of the consumedInformations property.
     * 
     * @return
     *     possible object is
     *     {@link InformationObjectList }
     *     
     */
    public InformationObjectList getConsumedInformations() {
        return consumedInformations;
    }

    /**
     * Sets the value of the consumedInformations property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformationObjectList }
     *     
     */
    public void setConsumedInformations(InformationObjectList value) {
        this.consumedInformations = value;
    }

    /**
     * Gets the value of the consumedPorts property.
     * 
     * @return
     *     possible object is
     *     {@link PortList }
     *     
     */
    public PortList getConsumedPorts() {
        return consumedPorts;
    }

    /**
     * Sets the value of the consumedPorts property.
     * 
     * @param value
     *     allowed object is
     *     {@link PortList }
     *     
     */
    public void setConsumedPorts(PortList value) {
        this.consumedPorts = value;
    }

}
