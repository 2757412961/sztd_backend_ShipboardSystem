package cn.edu.zju.gislab.SZTDService.mapper;

import cn.edu.zju.gislab.SZTDService.po.Weathersite;
import cn.edu.zju.gislab.SZTDService.po.WeathersiteExample;
import java.sql.Timestamp;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WeathersiteMapper {
    int countByExample(WeathersiteExample example);

    int deleteByExample(WeathersiteExample example);

    int deleteByPrimaryKey(Timestamp dt);

    int insert(Weathersite record);

    int insertSelective(Weathersite record);

    List<Weathersite> selectByExample(WeathersiteExample example);

    Weathersite selectByPrimaryKey(Timestamp dt);

    int updateByExampleSelective(@Param("record") Weathersite record, @Param("example") WeathersiteExample example);

    int updateByExample(@Param("record") Weathersite record, @Param("example") WeathersiteExample example);

    int updateByPrimaryKeySelective(Weathersite record);

    int updateByPrimaryKey(Weathersite record);
}