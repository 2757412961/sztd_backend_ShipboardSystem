package cn.edu.zju.gislab.SZTDService.controller;

import cn.edu.zju.gislab.SZTDService.po.Weathersite;
import cn.edu.zju.gislab.SZTDService.po.Weathersitenew;
import cn.edu.zju.gislab.SZTDService.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class SiteController {
    @Autowired
    private SiteService siteService;

    @RequestMapping("/querySiteNew")
    @ResponseBody
    public Weathersitenew querySiteNew(){
        return siteService.getSiteNew();
    }

    @RequestMapping("/querySiteHistory")
    public @ResponseBody List<Weathersite> querySiteHistory(Long startTime, Long endTime){
        Timestamp stTime = new Timestamp(startTime);
        Timestamp edTime = new Timestamp(endTime);
        List<Weathersite> weatherSiteList=siteService.getSiteHistory(stTime,edTime);
        return weatherSiteList;
    }

    @RequestMapping("/querySiteLast24")
    public @ResponseBody List<Weathersite> querySiteLast24(){
        List<Weathersite> weatherSiteList=siteService.getSiteLast24();
        return weatherSiteList;
    }

}
