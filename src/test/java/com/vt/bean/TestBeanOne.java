package com.vt.bean;

import com.vt.o2f.annotations.DataFileMappedBean;
import com.vt.o2f.annotations.DataFileMappedField;

/**
 * Created by sonu on 25/06/16.
 */
@DataFileMappedBean(mappedToFileName = "tradeData", mappedToFileType = ".csv")
public class TestBeanOne {

    @DataFileMappedField(mappedToColumnName = "stockId", mappedToFieldType = "Integer")
    private Integer stockId;

    @DataFileMappedField(mappedToColumnName = "side", mappedToFieldType = "String")
    private String side;

    @DataFileMappedField(mappedToColumnName = "company", mappedToFieldType = "String")
    private String company;

    @DataFileMappedField(mappedToColumnName = "quantity", mappedToFieldType = "Integer")
    private Integer quantity;

    public TestBeanOne(){

    }

    public TestBeanOne(int stockId, String side, String company, int quantity) {
        this.stockId = stockId;
        this.side = side;
        this.company = company;
        this.quantity = quantity;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return  stockId +
                "," + side + '\'' +
                "," + company + '\'' +
                "," + quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestBeanOne testBeanOne = (TestBeanOne) o;

        if (stockId != testBeanOne.stockId) return false;
        if (quantity != testBeanOne.quantity) return false;
        if (!side.equals(testBeanOne.side)) return false;
        return company.equals(testBeanOne.company);

    }

    @Override
    public int hashCode() {
        int result = stockId;
        result = 31 * result + side.hashCode();
        result = 31 * result + company.hashCode();
        result = 31 * result + quantity;
        return result;
    }
}
