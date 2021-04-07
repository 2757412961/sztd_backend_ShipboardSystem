package cn.edu.zju.gislab.SZTDService.service.impl;

import cn.edu.zju.gislab.SZTDService.mapper.AdcpinfonewMapper;
import cn.edu.zju.gislab.SZTDService.po.*;
import cn.edu.zju.gislab.SZTDService.service.ADCPInfoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ADCPInfoServiceImp implements ADCPInfoService {
    @Autowired
    private AdcpinfonewMapper adcpinfonewMapper;

    @Override
    public List<Adcpinfonew> getADCPInfoNew() {
        int[] ids = new int[7];
        for (int i = 0; i < 7; i++) {
            ids[i] = i + 1;
        }
        return getADCPInfoNew(ids);
    }

    @Override
    public List<Adcpinfonew> getADCPInfoNew(int[] ids) {
        List<Adcpinfonew> adcpinfonewList = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            Adcpinfonew adcpinfonew = adcpinfonewMapper.selectByPrimaryKey(ids[i]);
            adcpinfonewList.add(adcpinfonew);
        }
        return adcpinfonewList;
    }

    @Override
    public List<AdpcConf> getADCPInfoConf(String path) {
        FileSystemResource resource = new FileSystemResource(path);
        try {
            // 读取配置文件
            FileInputStream inputStream = new FileInputStream(resource.getFile());
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            String text = new String(bytes, Charset.forName("UTF-8"));
            List<AdpcConf> confs = JSONObject.parseArray(text, AdpcConf.class);
            return confs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


}
