
package at.grz.jp.datatypes_2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.grz.jp.datatypes_2 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Key_QNAME = new QName("http://www.grz.at/jp/DataTypes-2.5", "key");
    private final static QName _MonetaryAmount_QNAME = new QName("http://www.grz.at/jp/DataTypes-2.5", "monetaryAmount");
    private final static QName _MonetaryAmountCurrency_QNAME = new QName("http://www.grz.at/jp/DataTypes-2.5", "currency");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.grz.jp.datatypes_2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MonetaryAmount }
     * 
     */
    public MonetaryAmount createMonetaryAmount() {
        return new MonetaryAmount();
    }

    /**
     * Create an instance of {@link Key }
     * 
     */
    public Key createKey() {
        return new Key();
    }

    /**
     * Create an instance of {@link AnyType }
     * 
     */
    public AnyType createAnyType() {
        return new AnyType();
    }

    /**
     * Create an instance of {@link KeyList }
     * 
     */
    public KeyList createKeyList() {
        return new KeyList();
    }

    /**
     * Create an instance of {@link MonetaryAmountList }
     * 
     */
    public MonetaryAmountList createMonetaryAmountList() {
        return new MonetaryAmountList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Key }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.grz.at/jp/DataTypes-2.5", name = "key")
    public JAXBElement<Key> createKey(Key value) {
        return new JAXBElement<Key>(_Key_QNAME, Key.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MonetaryAmount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.grz.at/jp/DataTypes-2.5", name = "monetaryAmount")
    public JAXBElement<MonetaryAmount> createMonetaryAmount(MonetaryAmount value) {
        return new JAXBElement<MonetaryAmount>(_MonetaryAmount_QNAME, MonetaryAmount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.grz.at/jp/DataTypes-2.5", name = "currency", scope = MonetaryAmount.class)
    public JAXBElement<String> createMonetaryAmountCurrency(String value) {
        return new JAXBElement<String>(_MonetaryAmountCurrency_QNAME, String.class, MonetaryAmount.class, value);
    }

}
