package com.atguigu.gulimall.product.vo;

import com.atguigu.gulimall.product.entity.SkuImagesEntity;
import com.atguigu.gulimall.product.entity.SkuInfoEntity;
import com.atguigu.gulimall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

@Data
public class SkuItemVo {
    boolean hasStock = false;
     SkuInfoEntity info;//√
     List<SkuImagesEntity> images;//√
     List<SkuItemSaleAttrVo> saleAttr;
     SpuInfoDescEntity desp;//√
     List<SpuItemAttrGroupVo> groupAttrs;//√
     SeckillInfoVo seckillInfo;


}


