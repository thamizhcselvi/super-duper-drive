package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Insert("INSERT INTO CREDENTIALS (url,username,key,password,userId) VALUES (#{url},#{username},#{key},#{password},#{userId})")
    @Options(useGeneratedKeys = true,keyProperty = "credentialid")
    int insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    public Credential select(Integer credentialid);

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    public List<Credential> getAllCredsById(Integer userId);

    @Update("UPDATE CREDENTIALS set url=#{url},username=#{username},password=#{password}, key=#{key} where credentialid=#{credentialid}")
    public int update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    public int deleteById(Integer credentialid);

}
