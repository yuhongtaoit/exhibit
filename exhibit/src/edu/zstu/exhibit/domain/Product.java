package edu.zstu.exhibit.domain;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Stream;

public class Product {
    private Integer Id;

    private String productType;

    private String productBarcode;

    private String productCode;

    private String productSizeIn;

    private String productSizeOut;

    private String productSize;

    private String productMaterial;

    private String outframeCode;

    private long creatTime;

    private BigDecimal unitPrice;

    private Integer productNumber;

    private BigDecimal productVolume;

    private String productPackage;

    private String productDescribe;

    private String sizeInUnit;
    private String sizeOutUnit;
    private String sizeUnit;


    private String priceUnit;

    private InputStream productPicture;



    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
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

    public long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
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

    public InputStream getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(InputStream productPicture) {
        this.productPicture = productPicture;
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

    @Override
    public String toString() {
        return "Product{" +
                "Id=" + Id +
                ", productType='" + productType + '\'' +
                ", productBarcode='" + productBarcode + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productSizeIn='" + productSizeIn + '\'' +
                ", productSizeOut='" + productSizeOut + '\'' +
                ", productSize='" + productSize + '\'' +
                ", productMaterial='" + productMaterial + '\'' +
                ", outframeCode='" + outframeCode + '\'' +
                ", creatTime=" + creatTime +
                ", unitPrice=" + unitPrice +
                ", productNumber=" + productNumber +
                ", productVolume=" + productVolume +
                ", productPackage='" + productPackage + '\'' +
                ", productDescribe='" + productDescribe + '\'' +
                ", sizeInUnit='" + sizeInUnit + '\'' +
                ", sizeOutUnit='" + sizeOutUnit + '\'' +
                ", sizeUnit='" + sizeUnit + '\'' +
                ", priceUnit='" + priceUnit + '\'' +
                ", productPicture=" + productPicture +
                '}';
    }
}