package com.cornelius.eduservice.service;

import com.cornelius.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cornelius.eduservice.entity.vo.CourseInfoVo;
import com.cornelius.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author cornelius
 * @since 2020-10-08
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);
}
