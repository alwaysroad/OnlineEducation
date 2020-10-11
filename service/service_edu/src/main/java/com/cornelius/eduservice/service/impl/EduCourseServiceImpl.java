package com.cornelius.eduservice.service.impl;

import com.cornelius.eduservice.entity.EduCourse;
import com.cornelius.eduservice.entity.EduCourseDescription;
import com.cornelius.eduservice.entity.vo.CourseInfoVo;
import com.cornelius.eduservice.entity.vo.CoursePublishVo;
import com.cornelius.eduservice.mapper.EduCourseMapper;
import com.cornelius.eduservice.service.EduChapterService;
import com.cornelius.eduservice.service.EduCourseDescriptionService;
import com.cornelius.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cornelius.eduservice.service.EduVideoService;
import com.cornelius.servicebase.exceptionhandler.CorneliusException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author cornelius
 * @since 2020-10-08
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;//用谁就注入谁
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduChapterService chapterService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表里添加课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new CorneliusException(2000,"添加课程失败");
        }
        //获取到添加课程的id
        String cid = eduCourse.getId();

        //向description表里添加描述信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        //BeanUtils.copyProperties(courseInfoVo.getDescription(),courseDescription);
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);//设置描述id为课程id  这样表就建立了一对一的联系
        courseDescriptionService.save(courseDescription);

        return cid;
    }

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2 查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0) {
            throw new CorneliusException(20001,"修改课程信息失败");
        }

        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    //删除课程
    @Override
    public void removeCourse(String courseId) {
        //根据课程id删除小节
        videoService.removeVideoByCourseId(courseId);

        //根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);

        //根据课程id删除描述
        courseDescriptionService.removeById(courseId);//描述和课程一对一的关系，直接删除即可

        //根据课程id删除课程本身
        int i= baseMapper.deleteById(courseId);
        if (i == 0) {
            throw new CorneliusException(20001,"删除失败");
        }
    }

}
