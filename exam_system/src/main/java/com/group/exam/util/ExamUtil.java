package com.group.exam.util;

import com.group.exam.model.cusEnum.ExamStatus;
import com.group.exam.model.cusEnum.QuestionType;
import com.group.exam.model.entity.Question;
import com.group.exam.model.responseModel.AutoMarkModel;

import java.util.Arrays;
import java.util.List;

public class ExamUtil {

    private static final String regex = "_#_ ";

    public static String StringList2String(List<String> list) {
        return String.join(regex,list);
    }

    public static List<String> String2StringList(String s) {
        return Arrays.asList(s.split(regex));
    }

    public static String Sec2String(int sec){
        int hours = sec / 3600;
        int minutes = (sec % 3600) / 60;
        int seconds = sec % 60;
        if(minutes == 0 && seconds == 0){
            return String.format("%d小时", hours);
        }else if(hours == 0 && seconds == 0){
            return String.format("%d分钟", minutes);
        }else if(hours == 0 && minutes == 0){
            return String.format("%d秒", seconds);
        }else {
            return String.format("%d小时%d分钟%d秒", hours, minutes, seconds);
        }
    }

    public static String Score2String(double score,int status){
        switch(ExamStatus.get(status)){
            case MARKED:
                return String.format("%.2f 分", score);
            case CANCELLED:
                return "取消";
            default:
                return "-";
        }
    }

    public static String Score2String(double score){
        return Score2String( score, ExamStatus.MARKED.getValue());
    }

    public static String Status2String(int status){
        return ExamStatus.get(status).getMsg();
    }


    /**
     * 简单实现 可能有BUG
     */
    public static String expandAnswer(Question question, List<String> answerList){
        if(question.getType() == QuestionType.TEXT)
            return answerList.get(0);
        StringBuilder sb = new StringBuilder();
        String answer;
        switch(question.getType()){
            case SINGLE:
                answer = answerList.get(0);
                sb.append(answer).append(".").append(question.getOptions().get(answer.charAt(0)-65));
                break;
            case MULTI:
                for(String x : answerList){
                    sb.append(x).append(".").append(question.getOptions().get(x.charAt(0)-65)).append("\n");
                }
                break;
            case JUDGE:
                answer = answerList.get(0);
                if (answer.equals("true")) {
                    sb.append("对");
                } else {
                    sb.append("错");
                }
                break;
            default:
                for(int i = 0; i< answerList.size();i++){
                    sb.append(i+1).append(".").append(answerList.get(i)).append("\n");
                }
            }


        return sb.toString();
    }

    public static AutoMarkModel AutoMark(QuestionType QuestionType, List<String> studentAnswer, List<String> trueAnswer, double score) {
        AutoMarkModel autoMark= new AutoMarkModel();
        autoMark.setCanAutoMark(true);
        switch(QuestionType) {
            case BLANK:
            case SINGLE:
            case JUDGE:
                autoMark.setResult(studentAnswer.get(0).equals(trueAnswer.get(0)));
                autoMark.setScore(score);
                break;
            case MULTI:
                for(String x : studentAnswer){
                    if(!trueAnswer.contains(x)){
                        autoMark.setResult(false);
                        autoMark.setScore(0);
                        return autoMark;
                    }
                }
                autoMark.setResult(true);
                autoMark.setScore(score);
                break;
            default: autoMark.setCanAutoMark(false);
        }
        return autoMark;
    }
}
