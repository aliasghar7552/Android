package Model;

/**
 * Created by Aliasghar on 7/18/2018.
 */

public class ModelViewBids {

    private String farmerCropsId, buyerId, quantity, unitRequest, unitPrice, totalPrice, bidPrice, bidStatus;


    public String getFarmerCropsId() {
        return farmerCropsId;
    }

    public void setFarmerCropsId(String farmerCropsId) {
        this.farmerCropsId = farmerCropsId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitRequest() {
        return unitRequest;
    }

    public void setUnitRequest(String unitRequest) {
        this.unitRequest = unitRequest;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(String bidStatus) {
        this.bidStatus = bidStatus;
    }
}
