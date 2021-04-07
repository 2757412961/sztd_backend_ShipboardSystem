package cn.edu.zju.gislab.SZTDService.service.impl;

import cn.edu.zju.gislab.SZTDService.mapper.WeathersiteMapper;
import cn.edu.zju.gislab.SZTDService.mapper.WeathersitenewMapper;
import cn.edu.zju.gislab.SZTDService.po.Weathersite;
import cn.edu.zju.gislab.SZTDService.po.WeathersiteExample;
import cn.edu.zju.gislab.SZTDService.po.Weathersitenew;
import cn.edu.zju.gislab.SZTDService.po.WeathersitenewExample;
import cn.edu.zju.gislab.SZTDService.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

public class SiteServiceImp implements SiteService {
    @Autowired
    private WeathersiteMapper weathersiteMapper;
    @Autowired
    private WeathersitenewMapper weathersitenewMapper;

    @Override
    public Weathersitenew getSiteNew() {
        WeathersitenewExample weathersitenewExample = new WeathersitenewExample();
        List<Weathersitenew> weathersitenewList = weathersitenewMapper.selectByExample(weathersitenewExample);
        if(weathersitenewList.size()!=0)
            return weathersitenewList.get(0);
        else
            return null;
    }

    @Override
    public List<Weathersite> getSiteHistory(Timestamp startTime, Timestamp endTime) {
        WeathersiteExample weathersiteExample = new WeathersiteExample();
        WeathersiteExample.Criteria criteria = weathersiteExample.createCriteria();
        criteria.andDtBetween(startTime,endTime);
        weathersiteExample.setOrderByClause("dt asc");
        List<Weathersite> weathersiteList = weathersiteMapper.selectByExample(weathersiteExample);
        if(weathersiteList.size()<750)
            return weathersiteList;
        else
        {
            List<Weathersite> resultList = new ArrayList<>();
            int interval = weathersiteList.size()/40;
            for(int i=0;i<weathersiteList.size();i=i+interval)
            {
                resultList.add(weathersiteList.get(i));
            }
            return resultList;
        }

    }

    @Override
    public List<Weathersite> getSiteLast24() {
        Weathersitenew weathersitenew = getSiteNew();
        Timestamp endTime = weathersitenew.getDt();
        Timestamp startTime = new Timestamp(endTime.getTime() - 24*60*60*1000);
        WeathersiteExample weathersiteExample = new WeathersiteExample();
        WeathersiteExample.Criteria criteria = weathersiteExample.createCriteria();
        criteria.andDtBetween(startTime,endTime);
        weathersiteExample.setOrderByClause("dt asc");
        List<Weathersite> weathersiteList = weathersiteMapper.selectByExample(weathersiteExample);
        if(weathersiteList.size()<730)
            return weathersiteList;
        else
        {
            List<Weathersite> resultList = new ArrayList<>();
            int interval = weathersiteList.size()/40;
            for(int i=0;i<weathersiteList.size();i=i+interval)
            {
                resultList.add(weathersiteList.get(i));
            }
            return resultList;
        }
    }
}
