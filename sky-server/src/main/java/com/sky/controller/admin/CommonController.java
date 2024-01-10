package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    //工具类配置成bean以后，开启服务后会自动创建bean对象。使用自动注入注解去使用该创建好的对象
    @Autowired
    private AliOssUtil aliOssUtil;


    /**
     * 文件上传
     * @param file
     * @return
     */
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传：{}",file);

        //工具类需要的参数：1.文件的字节  2.文件名（UUID）
        try {
            //获得原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名的文件后缀，如.txt
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String finalObjectName = UUID.randomUUID().toString() + extension;
            //获取文件路径
            String filePath = aliOssUtil.upload(file.getBytes(),finalObjectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败：{}",e);
        }
        return null;
    }

}
