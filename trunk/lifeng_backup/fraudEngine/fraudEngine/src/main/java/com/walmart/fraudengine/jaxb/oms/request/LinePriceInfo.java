//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b26-ea3 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.07.28 at 03:34:40 PM CST 
//


package com.walmart.fraudengine.jaxb.oms.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.walmart.fraudengine.jaxb.oms.request.LinePriceInfo;


/**
 * <p>Java class for LinePriceInfo element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="LinePriceInfo">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;attribute name="LineTotal" type="{http://www.w3.org/2001/XMLSchema}double" />
 *         &lt;attribute name="UnitPrice" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "LinePriceInfo")
public class LinePriceInfo {

    @XmlAttribute(name = "LineTotal")
    protected Double lineTotal;
    @XmlAttribute(name = "UnitPrice")
    protected Double unitPrice;

    /**
     * Gets the value of the lineTotal property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLineTotal() {
        return lineTotal;
    }

    /**
     * Sets the value of the lineTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLineTotal(Double value) {
        this.lineTotal = value;
    }

    /**
     * Gets the value of the unitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the value of the unitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUnitPrice(Double value) {
        this.unitPrice = value;
    }

}
