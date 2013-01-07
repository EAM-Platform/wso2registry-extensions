
package at.racon.eamp.core.core_types_2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DependencyRole.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DependencyRole">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PROVIDER"/>
 *     &lt;enumeration value="CONSUMER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DependencyRole")
@XmlEnum
public enum DependencyRole {

    PROVIDER,
    CONSUMER;

    public String value() {
        return name();
    }

    public static DependencyRole fromValue(String v) {
        return valueOf(v);
    }

}
