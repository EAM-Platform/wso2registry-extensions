
package at.racon.eamp.eamp_types_1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Subscription complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Subscription">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="consumer" type="{http://www.racon.at/eamp/eamp-types-1.0}ApplicationService"/>
 *         &lt;element name="provider" type="{http://www.racon.at/eamp/eamp-types-1.0}ApplicationService"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Subscription", propOrder = {
    "consumer",
    "provider"
})
public class Subscription {

    @XmlElement(required = true)
    protected ApplicationService consumer;
    @XmlElement(required = true)
    protected ApplicationService provider;

    /**
     * Gets the value of the consumer property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationService }
     *     
     */
    public ApplicationService getConsumer() {
        return consumer;
    }

    /**
     * Sets the value of the consumer property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationService }
     *     
     */
    public void setConsumer(ApplicationService value) {
        this.consumer = value;
    }

    /**
     * Gets the value of the provider property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationService }
     *     
     */
    public ApplicationService getProvider() {
        return provider;
    }

    /**
     * Sets the value of the provider property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationService }
     *     
     */
    public void setProvider(ApplicationService value) {
        this.provider = value;
    }

}
