package com.sky.controller.admin;

import com.sky.constant.StatusConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@Api(tags = "商店相关接口")
@Slf4j
@RequestMapping("/admin/shop")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置商店营业状态
     * @param status
     * @return
     */
    @ApiOperation("设置商店营业状态")
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status){
        log.info("设置商店营业状态为：{}",status == StatusConstant.ENABLE ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set("SHOP_STATUS",status);
        log.info("商店状态已设置为：{}", redisTemplate.opsForValue().get("SHOP_STATUS"));
        return Result.success(status);
    }

    @ApiOperation("获取商店营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        System.out.println("商店状态码："+status);
//        log.info("获取商店营业状态为：{}",status == StatusConstant.ENABLE ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
