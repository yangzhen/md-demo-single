package com.md.demo.server.dal.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.md.demo.server.bean.entry.UserAccount;

/**
 * 
 * @author yangzhen
 *
 */
@MyBatisRepository
public interface UserAccountDAO {
    
    @Select("SELECT id, user_id, avaliable_balance FROM user_account WHERE user_id = #{userId}")
    public UserAccount findAccountByUserId(@Param("userId") int userId);
    
    @Update("UPDATE user_account SET avaliable_balance = avaliable_balance - #{money} WHERE user_id = #{userId} and avaliable_balance>=#{money}")
    public int removeMoney(@Param("userId") int userId, @Param("money") Double money);
    
    @Update("UPDATE user_account SET avaliable_balance = avaliable_balance + #{money} WHERE user_id = #{userId}")
    public int addMoney(@Param("userId") int userId, @Param("money") Double money);
    
}
