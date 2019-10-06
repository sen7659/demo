package com.personal.demo.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.personal.demo.bean.Daily;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DailyDao extends BaseMapper<Daily> {

    public long queryCount(Daily daily);

    public List<Daily> query(Daily daily);

}
