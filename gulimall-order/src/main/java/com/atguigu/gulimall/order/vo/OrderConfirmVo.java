package com.atguigu.gulimall.order.vo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class OrderConfirmVo {
    List<MemberAddressVo> address;
    List<OrderItemVo> items;
    /**
     * 积分
     */
    Integer integration;
    @Setter @Getter
    Map<Long,Boolean> stocks;

    //防重复令牌
    String orderToken;
    public Integer getCount(){
        Integer i=0;
        if (items != null) {
            for (OrderItemVo item : items) {
                i+=item.getCount();
            }
        }

        return i;
    }

    public String getOrderToken() {
        return orderToken;
    }

    public void setOrderToken(String orderToken) {
        this.orderToken = orderToken;
    }



    public List<MemberAddressVo> getAddress() {
        return address;
    }

    public void setAddress(List<MemberAddressVo> address) {
        this.address = address;
    }

    public List<OrderItemVo> getItems() {
        return items;
    }

    public void setItems(List<OrderItemVo> items) {
        this.items = items;
    }

    public Integer getIntegration() {
        return integration;
    }

    public void setIntegration(Integer integration) {
        this.integration = integration;
    }

    public BigDecimal getTotal() {
        BigDecimal sum=new BigDecimal("0");
        if (items != null) {
            for (OrderItemVo item : items) {
                BigDecimal multiply = item.getPrice().multiply(new BigDecimal(item.getCount().toString()));
                sum=sum.add(multiply);
            }
        }
        return sum;
    }
    public BigDecimal getPayPrice() {
        return getTotal();
    }



}
