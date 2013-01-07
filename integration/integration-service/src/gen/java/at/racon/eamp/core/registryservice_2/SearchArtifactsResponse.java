
package at.racon.eamp.core.registryservice_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import at.racon.eamp.core.core_types_2.ArtifactList;


/**
 * <p>Java class for SearchArtifactsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchArtifactsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="value" type="{http://www.racon.at/eamp/core/core-types-2.0}ArtifactList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchArtifactsResponse", propOrder = {
    "value"
})
public class SearchArtifactsResponse {

    protected ArtifactList value;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link ArtifactList }
     *     
     */
    public ArtifactList getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArtifactList }
     *     
     */
    public void setValue(ArtifactList value) {
        this.value = value;
    }

}
