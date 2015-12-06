package com.qinyuan15.lottery.mvc.dao;

import org.apache.commons.lang3.BooleanUtils;

public class LotteryLot extends AbstractLot {
    private Integer serialNumber;
    private Boolean virtual;

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
    }

    public Boolean getVirtual() {
        return BooleanUtils.isTrue(virtual);
    }

    @Override
    public synchronized User getUser() {
        if (virtual) {
            return null;
        } else {
            return super.getUser();
        }
    }
}
