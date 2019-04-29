package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.common.util.FileUtil;
import com.library.bexam.common.util.PathUtil;
import com.library.bexam.util.ConvertResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 上传文件
 * @author caoqian
 * @date 20190109
 */
@CrossOrigin
@RestController
@RequestMapping("file")
public class FileUploadController {

    /**
     * 实现文件上传
     * @author caoqian
     * */
    @RequestMapping(value="fileUpload",method = RequestMethod.POST)
    @ResponseBody
    public Result fileUpload(@RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return ConvertResult.getParamErrorResult("上传的文件不能为空");
        }
        String fileName = file.getOriginalFilename();
        String path = System.getProperty("user.dir")+ File.separator+ PathUtil.PathType.UPLOAD.value() ;
        boolean result=false;
        try {
            //保存文件
            FileUtil.uploadFile(file.getBytes(), path, fileName);
            result=true;
        } catch (Exception e) {
            result=false;
        }
        return ConvertResult.getSuccessResult(result);
    }
}
