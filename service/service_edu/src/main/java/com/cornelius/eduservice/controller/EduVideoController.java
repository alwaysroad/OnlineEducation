package com.cornelius.eduservice.controller;


import com.cornelius.commonutils.R;
import com.cornelius.eduservice.client.VodClient;
import com.cornelius.eduservice.entity.EduVideo;
import com.cornelius.eduservice.service.EduVideoService;
import com.cornelius.servicebase.exceptionhandler.CorneliusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author cornelius
 * @since 2020-10-08
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;
    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    // TODO 后面这个方法需要完善：删除小节时候，同时把里面视频删除
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        String videoSourceId = videoService.getById(id).getVideoSourceId();//根据小节id得到视频id
        if (!StringUtils.isEmpty(videoSourceId)) {
            //远程调用删除小节里的视频
            R result = vodClient.removeAliVideo(videoSourceId);
            if(!result.getSuccess()){
                throw new CorneliusException(20001,"熔断器。。。。。");
            }
        }

        videoService.removeById(id);
        return R.ok();
    }

    //修改小节 TODO

}

