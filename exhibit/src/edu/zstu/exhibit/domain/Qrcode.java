package edu.zstu.exhibit.domain;

import java.math.BigDecimal;

/**
 * Created by aning on 16/6/4.
 */
public class Qrcode {
    private Integer qrcodeId;
    private String customName;
    private Integer productId;
    private String productBarcode;
    private String saleName;
    private long addTime;
    private String comment;


    private String productType;

    private String productCode;

    private String productSizeIn;

    private String productSizeOut;

    private String productSize;

    private String productMaterial;

    private String outframeCode;

    private BigDecimal unitPrice;

    private Integer productNumber;

    private BigDecimal productVolume;

    private String productPackage;

    private String productDescribe;

    private String sizeInUnit;
    private String sizeOutUnit;
    private String sizeUnit;
    private String priceUnit;

    public Integer getQrcodeId() {
        return qrcodeId;
    }

    public void setQrcodeId(Integer qrcodeId) {
        this.qrcodeId = qrcodeId;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }


    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductSizeIn() {
        return productSizeIn;
    }

    public void setProductSizeIn(String productSizeIn) {
        this.productSizeIn = productSizeIn;
    }

    public String getProductSizeOut() {
        return productSizeOut;
    }

    public void setProductSizeOut(String productSizeOut) {
        this.productSizeOut = productSizeOut;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductMaterial() {
        return productMaterial;
    }

    public void setProductMaterial(String productMaterial) {
        this.productMaterial = productMaterial;
    }

    public String getOutframeCode() {
        return outframeCode;
    }

    public void setOutframeCode(String outframeCode) {
        this.outframeCode = outframeCode;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    public BigDecimal getProductVolume() {
        return productVolume;
    }

    public void setProductVolume(BigDecimal productVolume) {
        this.productVolume = productVolume;
    }

    public String getProductPackage() {
        return productPackage;
    }

    public void setProductPackage(String productPackage) {
        this.productPackage = productPackage;
    }

    public String getProductDescribe() {
        return productDescribe;
    }

    public void setProductDescribe(String productDescribe) {
        this.productDescribe = productDescribe;
    }

    public String getSizeInUnit() {
        return sizeInUnit;
    }

    public void setSizeInUnit(String sizeInUnit) {
        this.sizeInUnit = sizeInUnit;
    }

    public String getSizeOutUnit() {
        return sizeOutUnit;
    }

    public void setSizeOutUnit(String sizeOutUnit) {
        this.sizeOutUnit = sizeOutUnit;
    }

    public String getSizeUnit() {
        return sizeUnit;
    }

    public void setSizeUnit(String sizeUnit) {
        this.sizeUnit = sizeUnit;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Qrcode{" +
                "qrcodeId=" + qrcodeId +
                ", customName='" + customName + '\'' +
                ", productId=" + productId +
                ", productBarcode='" + productBarcode + '\'' +
                ", saleName='" + saleName + '\'' +
                ", addTime=" + addTime +
                ", comment='" + comment + '\'' +
                ", productType='" + productType + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productSizeIn='" + productSizeIn + '\'' +
                ", productSizeOut='" + productSizeOut + '\'' +
                ", productSize='" + productSize + '\'' +
                ", productMaterial='" + productMaterial + '\'' +
                ", outframeCode='" + outframeCode + '\'' +
                ", unitPrice=" + unitPrice +
                ", productNumber=" + productNumber +
                ", productVolume=" + productVolume +
                ", productPackage='" + productPackage + '\'' +
                ", productDescribe='" + productDescribe + '\'' +
                ", sizeInUnit='" + sizeInUnit + '\'' +
                ", sizeOutUnit='" + sizeOutUnit + '\'' +
                ", sizeUnit='" + sizeUnit + '\'' +
                ", priceUnit='" + priceUnit + '\'' +
                '}';
    }
}
