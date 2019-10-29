package com.fitgoal.dao.mybatis.mappers;

import com.fitgoal.dao.domain.UserDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {

    @Insert("Insert into users(email, password, link, active) values (#{email}, #{password}, #{link}, #{active})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(UserDto userDto);

    @Select("Select * from users where id = #{userId}")
    UserDto getById(Long userId);

    @Select("Select * from users where email = #{userEmail}")
    UserDto findByEmail(String userEmail);

    @Select("Select * from users where link = #{userLink}")
    UserDto findByLink(String userLink);

    @Update("Update users set email = #{email}, password = #{password}, link = #{link}, active = #{active} where id = #{id}")
    void update(UserDto userDto);
}
