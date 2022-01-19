package com.group.exam.service.impl;

import com.group.exam.dao.MajorMapper;
import com.group.exam.dao.StudentMapper;
import com.group.exam.model.entity.Major;
import com.group.exam.model.entity.Student;
import com.group.exam.model.responseModel.ResponseModel;
import com.group.exam.model.responseModel.StudentInfoResponse;
import com.group.exam.service.intf.StudentServiceInterface;
import com.group.exam.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StudentService implements StudentServiceInterface {
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private MajorMapper majorMapper;

    @Override
    public ResponseModel getStudentInfo(String sID) {
        try {
            Student student = studentMapper.findByID(sID);
            Major major = majorMapper.findByID(student.getsMajorID());
            StudentInfoResponse response = new StudentInfoResponse(student, major);
            return ResponseUtil.success(response);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.error("未找到当前学生信息");
        }
    }
}
