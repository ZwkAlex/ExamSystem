package com.group.exam.model.responseModel;


import com.group.exam.model.daoModel.CourseDao;
import com.group.exam.model.daoModel.ExamDao;
import com.group.exam.model.entity.Course;
import com.group.exam.model.entity.Exam;
import com.group.exam.util.ExamUtil;

public class StudentExamResponse {
    private String examID;
    private String courseName;
    private String description;
    private String duration;
    private String startDate;
    private String endDate;

    public StudentExamResponse(CourseDao course, ExamDao exam) {
        examID = exam.getExamID();
        courseName = course.getCourseName();
        description = course.getDescription();
        duration = ExamUtil.Sec2String(exam.getDuration());
        startDate = exam.getStartDate().toString();
        endDate = exam.getEndDate().toString();
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
