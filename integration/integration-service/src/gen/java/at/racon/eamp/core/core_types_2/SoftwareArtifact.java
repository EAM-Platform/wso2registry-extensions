
package at.racon.eamp.core.core_types_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SoftwareArtifact complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SoftwareArtifact">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.racon.at/eamp/core/core-types-2.0}Artifact">
 *       &lt;sequence>
 *         &lt;element name="stakeholders" type="{http://www.racon.at/eamp/core/core-types-2.0}StakeholderList" minOccurs="0"/>
 *         &lt;element name="documents" type="{http://www.racon.at/eamp/core/core-types-2.0}DocumentList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoftwareArtifact", propOrder = {
    "stakeholders",
    "documents"
})
@XmlSeeAlso({
    Application.class,
    Module.class,
    InformationObject.class,
    Interface.class,
    ContainedElement.class
})
public class SoftwareArtifact
    extends Artifact
{

    protected StakeholderList stakeholders;
    protected DocumentList documents;

    /**
     * Gets the value of the stakeholders property.
     * 
     * @return
     *     possible object is
     *     {@link StakeholderList }
     *     
     */
    public StakeholderList getStakeholders() {
        return stakeholders;
    }

    /**
     * Sets the value of the stakeholders property.
     * 
     * @param value
     *     allowed object is
     *     {@link StakeholderList }
     *     
     */
    public void setStakeholders(StakeholderList value) {
        this.stakeholders = value;
    }

    /**
     * Gets the value of the documents property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentList }
     *     
     */
    public DocumentList getDocuments() {
        return documents;
    }

    /**
     * Sets the value of the documents property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentList }
     *     
     */
    public void setDocuments(DocumentList value) {
        this.documents = value;
    }

}
