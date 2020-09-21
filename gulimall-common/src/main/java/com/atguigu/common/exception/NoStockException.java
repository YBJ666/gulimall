package com.atguigu.common.exception;

import lombok.Getter;
import lombok.Setter;

public class NoStockException extends RuntimeException{
    @Setter @Getter
    private Long skuId;
    public NoStockException(String skuId){
        super("商品："+skuId+"库存不足");
    }

}
