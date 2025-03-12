package com.interview.web.mapper;

import com.interview.web.pojo.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qikun
 * @since 2025-03-11
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}
