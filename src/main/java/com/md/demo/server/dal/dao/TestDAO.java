package com.md.demo.server.dal.dao;

import org.apache.ibatis.annotations.Param;

import com.md.demo.server.bean.entry.TestMap;

/**
 * 
 * TestDAO 
 * @author chenchao
 * @date Jul 14, 2015 2:45:05 PM
 *
 */
@MyBatisRepository
public interface TestDAO {

	public TestMap selectTest(@Param("id") int id);

}