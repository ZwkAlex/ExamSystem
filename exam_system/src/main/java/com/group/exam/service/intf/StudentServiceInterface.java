package com.group.exam.service.intf;

import com.group.exam.model.responseModel.ResponseModel;

public interface StudentServiceInterface {
    ResponseModel getStudentInfo(String sID);
}
