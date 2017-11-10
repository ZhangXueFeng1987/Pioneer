package com.pioneer.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.pioneer.mapper.po.MapImagesGeoLocationPO;
@Mapper
public interface MapImagesGeoLocationMapper {
    @Insert("INSERT INTO MAP_IMAGES_GEOLOCATION(UUID,POINT) VALUES(#{UUID},GeomFromText(#{POINT}))")
    public int insertGeolocationForUploadFile(@Param("POINT") String point,@Param("UUID") String UUID);
    
    @Results({
        @Result(property = "uuid", column = "UUID"),
        @Result(property = "point", column = "POINT")
      })
    @Select("SELECT UUID,ASTEXT(POINT) POINT FROM MAP_IMAGES_GEOLOCATION WHERE MBRWITHIN(POINT,ST_GEOMFROMTEXT(#{polygo}))")
    public ArrayList<MapImagesGeoLocationPO> selectGeolocationForUploadFile(@Param("polygo")String polygo);

    @Delete("DELETE FROM MAP_IMAGES_GEOLOCATION WHERE UUID=#{UUID}")
    public int deleteGeolocationForUploadFile(@Param("UUID") String UUID);
    
}
