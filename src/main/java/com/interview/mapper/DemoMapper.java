package com.interview.mapper;

import com.interview.pojo.entity.Demo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interview.service.MyService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qikun
 * @since 2025-03-11
 */
@Mapper
public interface DemoMapper extends BaseMapper<Demo> {

}
