package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtProductReturnOrder extends Auditable<EmployeeDetails> implements OldPresist<BtProductReturnOrder>,Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String manualId;

    String shippingMode;
    String shipToName;
    String trackingNumber;
    Date actualSendDate;

    String returnOrderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    Location source;

    @ManyToOne(fetch = FetchType.LAZY)
    Location destination;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    Float totalQuantityReturn;
    Float totalQuantityReceived;
    
    String DELIV;
	String REMARK_D;
	String INVOICE;
	String REMARK_I;
	String MBLNR_G;
	String MJAHR_G;
	String REMARK_G;

    boolean synced;
    
    

    public String getDELIV() {
		return DELIV;
	}

	public void setDELIV(String dELIV) {
		DELIV = dELIV;
	}

	public String getREMARK_D() {
		return REMARK_D;
	}

	public void setREMARK_D(String rEMARK_D) {
		REMARK_D = rEMARK_D;
	}

	public String getINVOICE() {
		return INVOICE;
	}

	public void setINVOICE(String iNVOICE) {
		INVOICE = iNVOICE;
	}

	public String getREMARK_I() {
		return REMARK_I;
	}

	public void setREMARK_I(String rEMARK_I) {
		REMARK_I = rEMARK_I;
	}

	public String getMBLNR_G() {
		return MBLNR_G;
	}

	public void setMBLNR_G(String mBLNR_G) {
		MBLNR_G = mBLNR_G;
	}

	public String getMJAHR_G() {
		return MJAHR_G;
	}

	public void setMJAHR_G(String mJAHR_G) {
		MJAHR_G = mJAHR_G;
	}

	public String getREMARK_G() {
		return REMARK_G;
	}

	public void setREMARK_G(String rEMARK_G) {
		REMARK_G = rEMARK_G;
	}

	public BtProductReturnOrder(Long id) {
        this.id = id;
    }

    public BtProductReturnOrder() {
    }

    

    public String getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(String shippingMode) {
        this.shippingMode = shippingMode;
    }

    public String getShipToName() {
        return shipToName;
    }

    public void setShipToName(String shipToName) {
        this.shipToName = shipToName;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Date getActualSendDate() {
        return actualSendDate;
    }

    public void setActualSendDate(Date actualSendDate) {
        this.actualSendDate = actualSendDate;
    }

    public String getReturnOrderStatus() {
        return returnOrderStatus;
    }

    public void setReturnOrderStatus(String returnOrderStatus) {
        this.returnOrderStatus = returnOrderStatus;
    }

    public Location getSource() {
        return source;
    }

    public void setSource(Location source) {
        this.source = source;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public Float getTotalQuantityReturn() {
        return totalQuantityReturn;
    }

    public void setTotalQuantityReturn(Float totalQuantityReturn) {
        this.totalQuantityReturn = totalQuantityReturn;
    }

    public Float getTotalQuantityReceived() {
        return totalQuantityReceived;
    }

    public void setTotalQuantityReceived(Float totalQuantityReceived) {
        this.totalQuantityReceived = totalQuantityReceived;
    }



    @Transient transient BtProductReturnOrder old;

    @PostLoad
    @Override
    public void old() {
        this.old=(BtProductReturnOrder)SerializationUtils.clone(this);
    }

    @Override
    public BtProductReturnOrder gOld() {
        
        return this.old;
    }

    @Override
    public Long getId() {
        // TODO Auto-generated method stub
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
        
    }

    @Override
    public EmployeeDetails getOwner() {
        
        return this.owner;
    }

    @Override
    public void setOwner(EmployeeDetails u) {
        this.owner=u;
        
    }

    @Override
    public String getManualId() {
        
        return this.manualId;
    }

    @Override
    public void setManualId(String manualId) {
        this.manualId=manualId;
    }

    @Override
    public String toString() {
        return ""+id;
    }

}
