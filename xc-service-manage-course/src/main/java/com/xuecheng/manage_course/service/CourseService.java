package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import com.xuecheng.manage_course.dao.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    TeachplanRepository teachplanRepository;

    @Autowired
    CourseBaseRepository courseBaseRepository;

    //查询课程计划
    public TeachplanNode findTeachplanList(String courseId){
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        return teachplanNode;
    }

    //添加课程计划
    public ResponseResult addTeachplan(Teachplan teachplan){
        if(teachplan == null ||
                StringUtils.isEmpty(teachplan.getCourseid())||
                StringUtils.isEmpty(teachplan.getPname())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        //取出课程ID和父节点ID
        String courseId = teachplan.getCourseid();
        String parentId = teachplan.getParentid();
        if(StringUtils.isEmpty(parentId)){
            //如果父结点为空则获取根结点
            parentId = this.getTeachplanRoot(courseId);
        }

        //取出父节点信息
        Optional<Teachplan> optional = teachplanRepository.findById(parentId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        Teachplan teachplanParent = optional.get();
        //设置父节点ID
        teachplan.setParentid(parentId);
        teachplan.setStatus("0");//状态：未发布
        //父节点级别
        String parentGrade = teachplanParent.getGrade();
        if(parentGrade.equals("1")){
            teachplan.setGrade("2");
        }
        if(parentGrade.equals("2")){
            teachplan.setGrade("3");
        }
        //设置课程ID
        teachplan.setCourseid(courseId);
        teachplanRepository.save(teachplan);
        return ResponseResult.SUCCESS();
    }

    public String getTeachplanRoot(String courseId){
        //校验课程id
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            return null;
        }
        CourseBase courseBase = optional.get();
        List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        if(teachplanList == null ||
            teachplanList.size() == 0) {
            //新增一个根结点
            Teachplan teachplanRoot = new Teachplan();
            teachplanRoot.setCourseid(courseId);
            teachplanRoot.setPname(courseBase.getName());
            teachplanRoot.setParentid("0");
            teachplanRoot.setGrade("1");//1级
            teachplanRoot.setStatus("0");//未发布
            teachplanRepository.save(teachplanRoot);
            return teachplanRoot.getId();
        }
        return teachplanList.get(0).getId();
    }
}
