
package at.racon.eamp.core.core_types_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Port complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Port">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.racon.at/eamp/core/core-types-2.0}Artifact">
 *       &lt;sequence>
 *         &lt;element name="module" type="{http://www.racon.at/eamp/core/core-types-2.0}Module"/>
 *         &lt;element name="portInterface" type="{http://www.racon.at/eamp/core/core-types-2.0}Interface" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="visibility" type="{http://www.racon.at/eamp/core/core-types-2.0}PortVisibility"/>
 *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="component" type="{http://www.racon.at/eamp/core/core-types-2.0}ContainedElement" minOccurs="0"/>
 *         &lt;element name="endpoints" type="{http://www.racon.at/eamp/core/core-types-2.0}EndpointList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Port", propOrder = {
    "module",
    "portInterface",
    "type",
    "visibility",
    "address",
    "component",
    "endpoints"
})
public class Port
    extends Artifact
{

    @XmlElement(required = true)
    protected Module module;
    protected Interface portInterface;
    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected PortVisibility visibility;
    @XmlElement(required = true)
    protected String address;
    protected ContainedElement component;
    protected EndpointList endpoints;

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
     * Gets the value of the portInterface property.
     * 
     * @return
     *     possible object is
     *     {@link Interface }
     *     
     */
    public Interface getPortInterface() {
        return portInterface;
    }

    /**
     * Sets the value of the portInterface property.
     * 
     * @param value
     *     allowed object is
     *     {@link Interface }
     *     
     */
    public void setPortInterface(Interface value) {
        this.portInterface = value;
    }

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
     * Gets the value of the visibility property.
     * 
     * @return
     *     possible object is
     *     {@link PortVisibility }
     *     
     */
    public PortVisibility getVisibility() {
        return visibility;
    }

    /**
     * Sets the value of the visibility property.
     * 
     * @param value
     *     allowed object is
     *     {@link PortVisibility }
     *     
     */
    public void setVisibility(PortVisibility value) {
        this.visibility = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the component property.
     * 
     * @return
     *     possible object is
     *     {@link ContainedElement }
     *     
     */
    public ContainedElement getComponent() {
        return component;
    }

    /**
     * Sets the value of the component property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContainedElement }
     *     
     */
    public void setComponent(ContainedElement value) {
        this.component = value;
    }

    /**
     * Gets the value of the endpoints property.
     * 
     * @return
     *     possible object is
     *     {@link EndpointList }
     *     
     */
    public EndpointList getEndpoints() {
        return endpoints;
    }

    /**
     * Sets the value of the endpoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link EndpointList }
     *     
     */
    public void setEndpoints(EndpointList value) {
        this.endpoints = value;
    }

}
