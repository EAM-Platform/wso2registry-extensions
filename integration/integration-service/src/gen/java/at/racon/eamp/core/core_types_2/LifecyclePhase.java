
package at.racon.eamp.core.core_types_2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LifecyclePhase.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LifecyclePhase">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DESIGN"/>
 *     &lt;enumeration value="DEVELOPMENT"/>
 *     &lt;enumeration value="TEST"/>
 *     &lt;enumeration value="RELEASED"/>
 *     &lt;enumeration value="PRODUCTION"/>
 *     &lt;enumeration value="DEPRECATED"/>
 *     &lt;enumeration value="END_OF_LIFE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LifecyclePhase")
@XmlEnum
public enum LifecyclePhase {

    DESIGN,
    DEVELOPMENT,
    TEST,
    RELEASED,
    PRODUCTION,
    DEPRECATED,
    END_OF_LIFE;

    public String value() {
        return name();
    }

    public static LifecyclePhase fromValue(String v) {
        return valueOf(v);
    }

}
