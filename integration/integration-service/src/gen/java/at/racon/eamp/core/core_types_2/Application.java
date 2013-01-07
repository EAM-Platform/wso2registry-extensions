
package at.racon.eamp.core.core_types_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Application complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Application">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.racon.at/eamp/core/core-types-2.0}SoftwareArtifact">
 *       &lt;sequence>
 *         &lt;element name="shortName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="producer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sector" type="{http://www.racon.at/eamp/core/core-types-2.0}Sector"/>
 *         &lt;element name="businesDomain" type="{http://www.racon.at/eamp/core/core-types-2.0}BusinesDomain" minOccurs="0"/>
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
    "shortName",
    "producer",
    "sector",
    "businesDomain"
})
public class Application
    extends SoftwareArtifact
{

    @XmlElement(required = true)
    protected String shortName;
    @XmlElement(required = true, nillable = true)
    protected String producer;
    @XmlElement(required = true, nillable = true)
    protected Sector sector;
    protected BusinesDomain businesDomain;

    /**
     * Gets the value of the shortName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Sets the value of the shortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortName(String value) {
        this.shortName = value;
    }

    /**
     * Gets the value of the producer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProducer() {
        return producer;
    }

    /**
     * Sets the value of the producer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProducer(String value) {
        this.producer = value;
    }

    /**
     * Gets the value of the sector property.
     * 
     * @return
     *     possible object is
     *     {@link Sector }
     *     
     */
    public Sector getSector() {
        return sector;
    }

    /**
     * Sets the value of the sector property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sector }
     *     
     */
    public void setSector(Sector value) {
        this.sector = value;
    }

    /**
     * Gets the value of the businesDomain property.
     * 
     * @return
     *     possible object is
     *     {@link BusinesDomain }
     *     
     */
    public BusinesDomain getBusinesDomain() {
        return businesDomain;
    }

    /**
     * Sets the value of the businesDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinesDomain }
     *     
     */
    public void setBusinesDomain(BusinesDomain value) {
        this.businesDomain = value;
    }

}
