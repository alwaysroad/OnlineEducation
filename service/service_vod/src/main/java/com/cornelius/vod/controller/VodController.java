package com.cornelius.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteStreamRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.cornelius.commonutils.R;
import com.cornelius.servicebase.exceptionhandler.CorneliusException;
import com.cornelius.vod.service.VodService;
import com.cornelius.vod.utils.ConstantPropertiesUtil;
import com.cornelius.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file) {
        //返回上传视频id
        String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId", videoId);
    }

    //根据视频id删除视频
    @DeleteMapping("/removeAliVideo/{id}")
    public R removeAliVideo(@PathVariable String id) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();

            request.setVideoIds(id);
            client.getAcsResponse(request);

            return R.ok();
        } catch (Exception e) {
            throw new CorneliusException(20001,"删除视频失败");
        }
        // System.out.print("RequestId = " + response.getRequestId() + "\n");

    }

    //删除多个视频
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList) {
        vodService.removeMoreAliyVideo(videoList);

        return R.ok();
    }

}

