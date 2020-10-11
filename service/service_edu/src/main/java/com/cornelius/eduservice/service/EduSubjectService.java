package com.cornelius.eduservice.service;

import com.cornelius.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cornelius.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author cornelius
 * @since 2020-10-07
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
