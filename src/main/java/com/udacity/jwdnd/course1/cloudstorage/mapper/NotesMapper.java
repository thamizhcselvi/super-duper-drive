package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Insert("INSERT INTO NOTES (notetitle,notedescription,userId) VALUES (#{notetitle},#{notedescription},#{userId})")
    @Options(useGeneratedKeys = true,keyProperty = "noteid")
    int insert(Notes notes);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    public  Notes select(Integer noteid);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
     public List<Notes> getAllNotesById(Integer userId);

    @Update("UPDATE NOTES SET notetitle=#{notetitle},notedescription=#{notedescription},userId=#{userId} where noteid=#{noteid}")
    public void update(Notes notes);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    public void delete(Integer noteid);


}
