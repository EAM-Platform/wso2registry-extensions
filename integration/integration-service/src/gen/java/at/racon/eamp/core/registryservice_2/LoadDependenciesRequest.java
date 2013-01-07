
package at.racon.eamp.core.registryservice_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import at.racon.eamp.core.core_types_2.DependencyRole;


/**
 * <p>Java class for LoadDependenciesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoadDependenciesRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dependencyRole" type="{http://www.racon.at/eamp/core/core-types-2.0}DependencyRole"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoadDependenciesRequest", propOrder = {
    "identifier",
    "dependencyRole"
})
public class LoadDependenciesRequest {

    @XmlElement(required = true)
    protected String identifier;
    @XmlElement(required = true)
    protected DependencyRole dependencyRole;

    /**
     * Gets the value of the identifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the value of the identifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifier(String value) {
        this.identifier = value;
    }

    /**
     * Gets the value of the dependencyRole property.
     * 
     * @return
     *     possible object is
     *     {@link DependencyRole }
     *     
     */
    public DependencyRole getDependencyRole() {
        return dependencyRole;
    }

    /**
     * Sets the value of the dependencyRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link DependencyRole }
     *     
     */
    public void setDependencyRole(DependencyRole value) {
        this.dependencyRole = value;
    }

}
