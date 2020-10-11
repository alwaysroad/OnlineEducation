package com.cornelius.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cornelius.eduservice.entity.EduSubject;
import com.cornelius.eduservice.entity.excel.SubjectData;
import com.cornelius.eduservice.entity.subject.OneSubject;
import com.cornelius.eduservice.entity.subject.TwoSubject;
import com.cornelius.eduservice.listener.SubjectExcelLinstener;
import com.cornelius.eduservice.mapper.EduSubjectMapper;
import com.cornelius.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author cornelius
 * @since 2020-10-07
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {

        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelLinstener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    //所有的课程分类，树形结构
    @Override
    public List<OneSubject> getAllOneTwoSubject() {


        //查询所有的一级分类  parent_id = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);//查询到的一级目录都放到oneSubjectList里面


        //查询所有的二级分类 parent_id !=0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperOne.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);//查询到的一级目录都放到oneSubjectList里面

        //创建一个List用于存放最后的数据
        ArrayList<OneSubject> finalList = new ArrayList<>(); //泛型为OneSubject 因为封装完成后一级目录下包含二级目录；也可以用Edusubject


        //封装一级分类
        //查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值，
        //封装到要求的list集合里面 List<OneSubject> finalList
        for (EduSubject eduSubject : oneSubjectList) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalList.add(oneSubject);

            //封装二级分类
            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每个一级分类的二级分类
            ArrayList<TwoSubject> twoSubjects = new ArrayList<>();

            //遍历查询出来的所有二级分类
            for (EduSubject twoSubject1 : twoSubjectList) {
                TwoSubject twoSubject = new TwoSubject();
                //判断二级分类parentid和一级分类id是否一样
                if(twoSubject1.getParentId().equals(eduSubject.getId())) {
                    //把twoSubject1值复制到twoSubjects里面，放到twoSubjects里面
                    BeanUtils.copyProperties(twoSubject1,twoSubject);
                    twoSubjects.add(twoSubject);
                }

            }
            //把一级下面所有二级分类放到一级分类里面
            oneSubject.setChildren(twoSubjects);
        }

        return finalList;
    }
}
