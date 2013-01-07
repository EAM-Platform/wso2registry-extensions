
package at.racon.eamp.core.core_types_2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Role.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Role">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ARCHITECT"/>
 *     &lt;enumeration value="ANALYST"/>
 *     &lt;enumeration value="PRODUCTMANAGER"/>
 *     &lt;enumeration value="PROJECTMANAGER"/>
 *     &lt;enumeration value="DESIGNER"/>
 *     &lt;enumeration value="DEVELOPER"/>
 *     &lt;enumeration value="TESTER"/>
 *     &lt;enumeration value="SYSTEMENGINEER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Role")
@XmlEnum
public enum Role {

    ARCHITECT,
    ANALYST,
    PRODUCTMANAGER,
    PROJECTMANAGER,
    DESIGNER,
    DEVELOPER,
    TESTER,
    SYSTEMENGINEER;

    public String value() {
        return name();
    }

    public static Role fromValue(String v) {
        return valueOf(v);
    }

}
