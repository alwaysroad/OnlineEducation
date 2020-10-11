package com.cornelius.eduservice.service;

import com.cornelius.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cornelius.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author cornelius
 * @since 2020-10-08
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
