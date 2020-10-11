package com.cornelius.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cornelius.eduservice.entity.EduChapter;
import com.cornelius.eduservice.entity.EduVideo;
import com.cornelius.eduservice.entity.chapter.ChapterVo;
import com.cornelius.eduservice.entity.chapter.VideoVo;
import com.cornelius.eduservice.mapper.EduChapterMapper;
import com.cornelius.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cornelius.eduservice.service.EduVideoService;
import com.cornelius.servicebase.exceptionhandler.CorneliusException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author cornelius
 * @since 2020-10-08
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;


    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {


        //根据课程id首先查出所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);//查询到某个课程的所有章节

        //课程id查询出所有的小节
        QueryWrapper<EduVideo> wrapperVedio = new QueryWrapper<>();
        wrapperVedio.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVedio);


        List<ChapterVo> finalList = new ArrayList<>();

        //遍历查询章节list集合进行封装
        for (EduChapter eduChapter : eduChapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalList.add(chapterVo);

            List<VideoVo> objects = new ArrayList<>();//创建集合封装章节里面的小节
            //遍历查询小结list集合进行封装
            for (EduVideo eduVideo1 : eduVideoList) {
               VideoVo eduVideo = new VideoVo();
                if (eduVideo1.getChapterId().equals(eduChapter.getId())) {
                    BeanUtils.copyProperties(eduVideo1, eduVideo);
                    objects.add(eduVideo);
                }
            }
            //封装好的小节集合放到章节对象里面
            chapterVo.setChildren(objects);

        }




        return finalList;
    }

    ////删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterid章节id 查询小节表，如果查询数据，不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        //判断
        if(count >0) {//查询出小节，不进行删除
            throw new CorneliusException(20001,"不能删除");
        } else { //不能查询数据，进行删除
            //删除章节
            int result = baseMapper.deleteById(chapterId);
            //成功  1>0   0>0
            return result>0;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }

}
