
package at.racon.eamp.dependencyqueryservice_1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import at.grz.jp.datatypes_2.Key;
import at.grz.jp.datatypes_2.KeyList;
import at.racon.eamp.eamp_types_1.Application;
import at.racon.eamp.eamp_types_1.ApplicationService;
import at.racon.eamp.eamp_types_1.Module;


/**
 * <p>Java class for QueryServiceDependeciesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryServiceDependeciesRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="domain" type="{http://www.grz.at/jp/DataTypes-2.5}KeyList" minOccurs="0"/>
 *         &lt;element name="role" type="{http://www.grz.at/jp/DataTypes-2.5}Key" minOccurs="0"/>
 *         &lt;element name="application" type="{http://www.racon.at/eamp/eamp-types-1.0}Application" minOccurs="0"/>
 *         &lt;element name="module" type="{http://www.racon.at/eamp/eamp-types-1.0}Module" minOccurs="0"/>
 *         &lt;element name="service" type="{http://www.racon.at/eamp/eamp-types-1.0}ApplicationService" minOccurs="0"/>
 *         &lt;element name="tier" type="{http://www.grz.at/jp/DataTypes-2.5}Key" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryServiceDependeciesRequest", propOrder = {
    "domain",
    "role",
    "application",
    "module",
    "service",
    "tier"
})
public class QueryServiceDependeciesRequest {

    @XmlElementRef(name = "domain", namespace = "http://www.racon.at/eamp/DependencyQueryService-1.0", type = JAXBElement.class, required = false)
    protected JAXBElement<KeyList> domain;
    @XmlElementRef(name = "role", namespace = "http://www.racon.at/eamp/DependencyQueryService-1.0", type = JAXBElement.class, required = false)
    protected JAXBElement<Key> role;
    @XmlElementRef(name = "application", namespace = "http://www.racon.at/eamp/DependencyQueryService-1.0", type = JAXBElement.class, required = false)
    protected JAXBElement<Application> application;
    @XmlElementRef(name = "module", namespace = "http://www.racon.at/eamp/DependencyQueryService-1.0", type = JAXBElement.class, required = false)
    protected JAXBElement<Module> module;
    @XmlElementRef(name = "service", namespace = "http://www.racon.at/eamp/DependencyQueryService-1.0", type = JAXBElement.class, required = false)
    protected JAXBElement<ApplicationService> service;
    protected Key tier;

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link KeyList }{@code >}
     *     
     */
    public JAXBElement<KeyList> getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link KeyList }{@code >}
     *     
     */
    public void setDomain(JAXBElement<KeyList> value) {
        this.domain = value;
    }

    /**
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Key }{@code >}
     *     
     */
    public JAXBElement<Key> getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Key }{@code >}
     *     
     */
    public void setRole(JAXBElement<Key> value) {
        this.role = value;
    }

    /**
     * Gets the value of the application property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Application }{@code >}
     *     
     */
    public JAXBElement<Application> getApplication() {
        return application;
    }

    /**
     * Sets the value of the application property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Application }{@code >}
     *     
     */
    public void setApplication(JAXBElement<Application> value) {
        this.application = value;
    }

    /**
     * Gets the value of the module property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Module }{@code >}
     *     
     */
    public JAXBElement<Module> getModule() {
        return module;
    }

    /**
     * Sets the value of the module property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Module }{@code >}
     *     
     */
    public void setModule(JAXBElement<Module> value) {
        this.module = value;
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ApplicationService }{@code >}
     *     
     */
    public JAXBElement<ApplicationService> getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ApplicationService }{@code >}
     *     
     */
    public void setService(JAXBElement<ApplicationService> value) {
        this.service = value;
    }

    /**
     * Gets the value of the tier property.
     * 
     * @return
     *     possible object is
     *     {@link Key }
     *     
     */
    public Key getTier() {
        return tier;
    }

    /**
     * Sets the value of the tier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Key }
     *     
     */
    public void setTier(Key value) {
        this.tier = value;
    }

}
