
package at.racon.eamp.core.core_types_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceInterface complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceInterface">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.racon.at/eamp/core/core-types-2.0}Interface">
 *       &lt;sequence>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceInterface")
@XmlSeeAlso({
    WebServiceInterface.class,
    NativeServiceInterface.class
})
public class ServiceInterface
    extends Interface
{


}
