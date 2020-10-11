package com.cornelius.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cornelius.commonutils.R;
import com.cornelius.eduservice.entity.EduTeacher;
import com.cornelius.eduservice.entity.vo.TeacherQuery;
import com.cornelius.eduservice.service.EduTeacherService;
import com.cornelius.servicebase.exceptionhandler.CorneliusException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author cornelius
 * @since 2020-10-04
 */
@RestController
/*
* 1:Controller 交给Spring管理
* 2：返回Json数据
* */
@Api("讲师管理")
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {


    //Controller调用Service层的方法
    @Autowired
    private EduTeacherService teacherService;

    //查询所有老师的数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        //调用service的方法实现查询所有教师
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);//链式编程
    }


    //讲师逻辑删除
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable String id) {
        boolean remove = teacherService.removeById(id);
        if (remove) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //分页查询讲师的方法
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageListTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,@PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //调用方法实现分页
        //调用方法的时候，底层封装，把分页的所有数据都封装到pageTeacher对象里面
        teacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
   /*
   方法一

   Map map = new HashMap<>();
        map.put("total",total);
        map.put("row",records);
        return R.ok().data(map);*/

        //方法二
        return R.ok().data("total",total).data("row",records);
    }

    //条件查询带分页的方法
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery){

       /* * 1:RequestBody:使用json传递数据，把json数据封装到对应的对象里面去
        * 2：ResponseBody：返回数据，返回json数据
        * @RequestBody(required = false) TeacherQuery teacherQuery:teacherQuery可以为空
        *
        * */

        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        
        //创建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，不为空就进行拼接
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name",name);//姓名的模糊查询
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);//等于
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);//大于
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);//小于
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        teacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("row",records);

    }

    //添加讲师
    @ApiOperation("添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        }else {
            return R.error();
        }

        //使用自定义异常
/*
           try {
            int i = 10/0;
        }catch ( Exception e){
            throw new CorneliusException(5000,"执行了自定义的异常");
        }
*/
    }


    //根据id进行查询
    @ApiOperation("根据Id查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //讲师修改
    @ApiOperation("修改讲师")
    @PostMapping("updateTeacher")
    public  R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean update = teacherService.updateById(eduTeacher);
        if(update){
            return R.ok();
        }else {
            return R.error();
        }
    }


}

