package com.group.exam.dao;

import com.group.exam.model.entity.Major;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MajorMapper {
    @Select("SELECT * FROM major WHERE smajorid = #{id}")
    Major findByID(@Param("id") String ID);
}
