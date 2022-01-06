package com.group.exam.service.intf;

import com.group.exam.model.entity.User;
import com.group.exam.model.requestModel.PasswordChangeRequest;
import com.group.exam.model.responseModel.ResponseModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpSession;

public interface UserServiceInterface extends UserDetailsService {
    boolean checkRole(User user, String role);
    ResponseModel changePassword(PasswordChangeRequest passwordChangeRequest);
}
