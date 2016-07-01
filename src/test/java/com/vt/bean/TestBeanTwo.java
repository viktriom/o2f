package com.vt.bean;

import com.vt.o2f.annotations.DataFileMappedBean;
import com.vt.o2f.annotations.DataFileMappedField;

/**
 * Created by sonu on 28/06/16.
 */
@DataFileMappedBean(mappedToFileName = "tradeStatusData", mappedToFileType = ".csv")
public class TestBeanTwo {
    @DataFileMappedField(mappedToColumnName = "stockId", mappedToFieldType = "Integer")
    private Integer stockId;

    @DataFileMappedField(mappedToColumnName = "side", mappedToFieldType = "String")
    private String side;

    @DataFileMappedField(mappedToColumnName = "company", mappedToFieldType = "String")
    private String company;

    @DataFileMappedField(mappedToColumnName = "quantity", mappedToFieldType = "Integer")
    private Integer quantity;

    @DataFileMappedField(mappedToColumnName = "remainingQuantity", mappedToFieldType = "Integer")
    private Integer remainingQuantity;

    @DataFileMappedField(mappedToColumnName = "status", mappedToFieldType = "String")
    private String status;

    public TestBeanTwo(){

    }

    public TestBeanTwo(Integer stockId, String side, String company, Integer quantity, Integer remainingQuantity, String status) {
        this.stockId = stockId;
        this.side = side;
        this.company = company;
        this.quantity = quantity;
        this.remainingQuantity = remainingQuantity;
        this.status = status;
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

    public Integer getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TestBeanTwo{" +
                "stockId=" + stockId +
                ", side='" + side + '\'' +
                ", company='" + company + '\'' +
                ", quantity=" + quantity +
                ", remainingQuantity=" + remainingQuantity +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestBeanTwo that = (TestBeanTwo) o;

        if (stockId != null ? !stockId.equals(that.stockId) : that.stockId != null) return false;
        if (side != null ? !side.equals(that.side) : that.side != null) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (remainingQuantity != null ? !remainingQuantity.equals(that.remainingQuantity) : that.remainingQuantity != null)
            return false;
        return !(status != null ? !status.equals(that.status) : that.status != null);

    }

    @Override
    public int hashCode() {
        int result = stockId != null ? stockId.hashCode() : 0;
        result = 31 * result + (side != null ? side.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (remainingQuantity != null ? remainingQuantity.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
