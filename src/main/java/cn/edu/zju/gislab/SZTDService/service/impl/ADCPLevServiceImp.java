package cn.edu.zju.gislab.SZTDService.service.impl;

import cn.edu.zju.gislab.SZTDService.mapper.AdcplevMapper;
import cn.edu.zju.gislab.SZTDService.mapper.AdcplevnewMapper;
import cn.edu.zju.gislab.SZTDService.po.Adcplev;
import cn.edu.zju.gislab.SZTDService.po.AdcplevExample;
import cn.edu.zju.gislab.SZTDService.po.Adcplevnew;
import cn.edu.zju.gislab.SZTDService.po.AdcplevnewExample;
import cn.edu.zju.gislab.SZTDService.service.ADCPLevService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ADCPLevServiceImp implements ADCPLevService {
    @Autowired
    private AdcplevnewMapper adcplevnewMapper;
    @Autowired
    private AdcplevMapper adcplevMapper;

    @Override
    public ArrayList<List<Adcplevnew>> getADCPLevNew() {
        int[] ids = new int[7];
        for (int i = 0; i < 7; i++) {
            ids[i] = i + 1;
        }
        return getADCPLevNew(ids);
    }

    @Override
    public ArrayList<List<Adcplevnew>> getADCPLevNew(int[] ids) {
        ArrayList<List<Adcplevnew>> adcplevnewListAll = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            AdcplevnewExample adcplevnewExample = new AdcplevnewExample();
            AdcplevnewExample.Criteria criteria = adcplevnewExample.createCriteria();
            criteria.andAidEqualTo(ids[i]);
//            criteria.andLevNotEqualTo(0);
            adcplevnewExample.setOrderByClause("lev asc");
            List<Adcplevnew> adcplevnewList = adcplevnewMapper.selectByExample(adcplevnewExample);
            adcplevnewListAll.add(adcplevnewList);
        }
        return adcplevnewListAll;
    }

    @Override
    public List<List<Adcplev>> getADCPLevHistory(int id, Timestamp timeBefore, Timestamp timeNow, int timeInterval) {
        AdcplevExample adcplevExample = new AdcplevExample();
        AdcplevExample.Criteria criteria = adcplevExample.createCriteria();
        criteria.andAidEqualTo(id);
        criteria.andLevEqualTo(1);
        criteria.andDtBetween(timeBefore, timeNow);
        adcplevExample.setOrderByClause("dt asc");
        List<Timestamp> timestampList = new ArrayList<>();
        List<Adcplev> adcplevList = adcplevMapper.selectByExample(adcplevExample);
        for (int i = 0; i < adcplevList.size(); i++) {
            timestampList.add(adcplevList.get(i).getDt());
        }

        List<List<Adcplev>> adcplevListAll = new ArrayList<>();
        for (int i = 0; i < timestampList.size(); i++) {
            AdcplevExample adcplevExample2 = new AdcplevExample();
            AdcplevExample.Criteria criteria2 = adcplevExample2.createCriteria();
            criteria2.andAidEqualTo(id);
//            criteria2.andLevNotEqualTo(0);
            criteria2.andDtEqualTo(timestampList.get(i));
            adcplevExample2.setOrderByClause("lev asc");
            List<Adcplev> adcplevList2 = adcplevMapper.selectByExample(adcplevExample2);
            adcplevListAll.add(adcplevList2);
        }

        List<List<Adcplev>> resultList = new ArrayList<>();
        int interval = timeInterval;
        for (int i = 0; i < adcplevListAll.size(); i = i + interval) {
            resultList.add(adcplevListAll.get(i));
        }
        return resultList;
    }

    @Override
    public List<List<Adcplev>> getADCPLevForecastAll(int id, Timestamp startTime, Timestamp endTime) {

        int[] lev = new int[8];
        lev[0] = 5;
        lev[1] = 10;
        lev[2] = 15;
        lev[3] = 20;
        lev[4] = 25;
        lev[5] = 30;
        lev[6] = 35;
        lev[7] = 40;

        List<List<Adcplev>> adcplevListAll = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            AdcplevExample adcplevExample = new AdcplevExample();
            AdcplevExample.Criteria criteria = adcplevExample.createCriteria();
            criteria.andAidEqualTo(id);
            criteria.andDtBetween(startTime, endTime);
            criteria.andLevEqualTo(lev[i]);
            adcplevExample.setOrderByClause("dt asc");
            List<Adcplev> adcplevList = adcplevMapper.selectByExample(adcplevExample);


            if (adcplevList.size() <= 1) {
                Adcplev adcplev = new Adcplev();
                adcplevList = new ArrayList<>();

                adcplev.setAid(id);
                adcplev.setDt(startTime);
                adcplev.setLev(lev[i]);
                adcplev.setU(0.);
                adcplev.setV(0.);
                adcplev.setVeloc(0.);

                adcplevList.add(adcplev);
                adcplevList.add(adcplev);
                adcplevListAll.add(adcplevList);
            } else if (adcplevList.size() < 50) {
                adcplevListAll.add(adcplevList);
            } else {
                List<Adcplev> resultList = new ArrayList<>();
                int interval = adcplevList.size() / 30;
                for (int j = 0; j < adcplevList.size(); j = j + interval) {
                    resultList.add(adcplevList.get(j));
                }
                adcplevListAll.add(resultList);
            }

        }

        return adcplevListAll;
    }

    @Override
    public List<List<Adcplev>> getADCPLevTenMAvg(int[] ids, Timestamp startTime, Timestamp endTime) {
        List<List<Adcplev>> adcpLevList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            AdcplevExample adcplevExample = new AdcplevExample();
            AdcplevExample.Criteria criteria = adcplevExample.createCriteria();
            criteria.andAidEqualTo(ids[i]);
            criteria.andDtBetween(startTime, endTime);
            criteria.andLevEqualTo(0);
            adcplevExample.setOrderByClause("dt asc");
            List<Adcplev> adcplevListSite = adcplevMapper.selectByExample(adcplevExample);

            if (adcplevListSite.size() <= 1) {
                Adcplev adcplev = new Adcplev();
                adcplevListSite = new ArrayList<>();

                adcplev.setAid(ids[i]);
                adcplev.setDt(startTime);
                adcplev.setLev(0);
                adcplev.setU(0.);
                adcplev.setV(0.);
                adcplev.setVeloc(0.);
                adcplevListSite.add(adcplev);
                adcplevListSite.add(adcplev);
                adcpLevList.add(adcplevListSite);
            } else if (adcplevListSite.size() < 33) {
                adcpLevList.add(adcplevListSite);
            } else {
                List<Adcplev> resultList = new ArrayList<>();
                int interval = adcplevListSite.size() / 30;
                for (int j = 0; j < adcplevListSite.size(); j = j + interval) {
                    resultList.add(adcplevListSite.get(j));
                }
                adcpLevList.add(resultList);
            }
        }

        return adcpLevList;
    }


}
