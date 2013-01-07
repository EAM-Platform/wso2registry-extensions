
package at.racon.eamp.core.core_types_2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Tier.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Tier">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CLIENT"/>
 *     &lt;enumeration value="WEB"/>
 *     &lt;enumeration value="PROCESS"/>
 *     &lt;enumeration value="SERVICE"/>
 *     &lt;enumeration value="HOST"/>
 *     &lt;enumeration value="DATA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Tier")
@XmlEnum
public enum Tier {

    CLIENT,
    WEB,
    PROCESS,
    SERVICE,
    HOST,
    DATA;

    public String value() {
        return name();
    }

    public static Tier fromValue(String v) {
        return valueOf(v);
    }

}
