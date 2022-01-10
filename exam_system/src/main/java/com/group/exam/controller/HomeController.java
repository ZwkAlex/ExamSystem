package com.group.exam.controller;

import com.group.exam.model.responseModel.ResponseModel;
import com.group.exam.model.entity.Student;
import com.group.exam.service.impl.ExamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 *  date:2021/12/23
 *  author:
 */
@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Resource
    private ExamService examService;




}
