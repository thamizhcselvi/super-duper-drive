package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename,filepath,contenttype,filesize,filedata,userId) VALUES (#{filename},#{filepath},#{contenttype},#{filesize},#{filedata},#{userId})")
    @Options(useGeneratedKeys = true,keyProperty = "fileId")
    int insert(Files files);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    public List<Files> getAllFilesById(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    public Files select(Integer fileId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    public boolean deleteById(String fileId);

}
