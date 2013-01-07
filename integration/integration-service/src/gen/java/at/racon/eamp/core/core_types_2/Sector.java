
package at.racon.eamp.core.core_types_2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Sector.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Sector">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HARMONISIERT"/>
 *     &lt;enumeration value="SIENA"/>
 *     &lt;enumeration value="TOKIO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Sector")
@XmlEnum
public enum Sector {

    HARMONISIERT,
    SIENA,
    TOKIO;

    public String value() {
        return name();
    }

    public static Sector fromValue(String v) {
        return valueOf(v);
    }

}
