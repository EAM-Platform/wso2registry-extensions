
package at.racon.eamp.core.registryservice_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import at.racon.eamp.core.core_types_2.Artifact;


/**
 * <p>Java class for LoadArtifactResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoadArtifactResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="value" type="{http://www.racon.at/eamp/core/core-types-2.0}Artifact"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoadArtifactResponse", propOrder = {
    "value"
})
public class LoadArtifactResponse {

    @XmlElement(required = true)
    protected Artifact value;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link Artifact }
     *     
     */
    public Artifact getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link Artifact }
     *     
     */
    public void setValue(Artifact value) {
        this.value = value;
    }

}
