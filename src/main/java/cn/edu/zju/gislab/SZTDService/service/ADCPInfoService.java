package cn.edu.zju.gislab.SZTDService.service;

import cn.edu.zju.gislab.SZTDService.po.Adcpinfonew;
import cn.edu.zju.gislab.SZTDService.po.AdpcConf;

import java.util.ArrayList;
import java.util.List;

public interface ADCPInfoService {
    List<Adcpinfonew> getADCPInfoNew();
    List<Adcpinfonew> getADCPInfoNew(int[] ids);
    List<AdpcConf> getADCPInfoConf(String path);
}
