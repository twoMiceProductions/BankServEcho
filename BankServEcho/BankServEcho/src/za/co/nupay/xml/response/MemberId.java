//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.12 at 01:49:25 PM CAT 
//


package za.co.nupay.xml.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MemberId complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MemberId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MbrId" type="{urn:bsva:std:tech:xsd:rltm.810.001.001.01}Numeric6"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MemberId", propOrder = {
    "mbrId"
})
public class MemberId {

    @XmlElement(name = "MbrId")
    protected int mbrId;

    /**
     * Gets the value of the mbrId property.
     * 
     */
    public int getMbrId() {
        return mbrId;
    }

    /**
     * Sets the value of the mbrId property.
     * 
     */
    public void setMbrId(int value) {
        this.mbrId = value;
    }

}
