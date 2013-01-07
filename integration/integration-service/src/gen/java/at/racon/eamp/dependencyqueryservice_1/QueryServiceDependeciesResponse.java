
package at.racon.eamp.dependencyqueryservice_1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import at.racon.eamp.eamp_types_1.SubscriptionList;


/**
 * <p>Java class for QueryServiceDependeciesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryServiceDependeciesResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="value" type="{http://www.racon.at/eamp/eamp-types-1.0}SubscriptionList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryServiceDependeciesResponse", propOrder = {
    "value"
})
public class QueryServiceDependeciesResponse {

    protected SubscriptionList value;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link SubscriptionList }
     *     
     */
    public SubscriptionList getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscriptionList }
     *     
     */
    public void setValue(SubscriptionList value) {
        this.value = value;
    }

}
