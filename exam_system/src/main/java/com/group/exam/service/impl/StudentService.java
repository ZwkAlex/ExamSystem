package com.group.exam.service.impl;

import com.group.exam.dao.StudentMapper;
import com.group.exam.model.entity.Student;
import com.group.exam.model.responseModel.ResponseModel;
import com.group.exam.service.intf.StudentServiceInterface;
import com.group.exam.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StudentService implements StudentServiceInterface {
    @Resource
    private StudentMapper studentMapper;

    @Override
    public ResponseModel getStudentInfo(String sID) {
        Student student = studentMapper.findByID(sID);
        return student != null?ResponseUtil.success(student):ResponseUtil.error();
    }
}
