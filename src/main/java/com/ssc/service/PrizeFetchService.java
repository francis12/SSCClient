package com.ssc.service;

import com.alibaba.fastjson.JSONObject;
import com.ssc.com.ssc.vo.PrizeVo;
import com.ssc.util.DateUtil;
import com.ssc.util.ExcelUtil;
import com.ssc.util.HttpUtil;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

public class PrizeFetchService {

    String can30sUrl = "https://www.canadasuperdraw.com/api/history.php?lotteryid=29&date=";

    public List<PrizeVo> getPrizeDataByTime(String time) {
        List<PrizeVo> prizeVoList = new ArrayList<>();

        try {
            String url = can30sUrl + time.replace(" ", "%20");
            String result = HttpUtil.doGet(url,"utf-8");

            //String result = FileUtils.readFileToString(new File("d://testFile.txt"));
            Map<String, Object> jsonResult = JSONObject.parseObject(result, Map.class);

            for (Map.Entry<String, Object> entry : jsonResult.entrySet()) {
                try {
                    PrizeVo prizeVo = new PrizeVo();
                    String no = entry.getKey();
                    Map<String,Object> itemMap = JSONObject.parseObject(entry.getValue().toString(), Map.class);
                    String prize = String.valueOf(itemMap.get("result"));
                    String openTime = String.valueOf(itemMap.get("openTime"));

                    prizeVo.setNo(no);
                    prizeVo.setPrize(prize);
                    prizeVo.setTime(openTime);
                    prizeVoList.add(prizeVo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        Collections.sort(prizeVoList, new Comparator<PrizeVo>() {
            @Override
            public int compare(PrizeVo o1, PrizeVo o2) {

                String no1 = o1.getNo();
                String no2 = o2.getNo();
                return no1.compareTo(no2);
            }
        });
        return prizeVoList;
    }
    public String convert2ChinaNoFromCanadaNo(String originNo) {
        if(StringUtils.isEmpty(originNo)) {
            return "";
        }
        return null;
    }
    //2018-4-2 01:00:00
    public void getPrizeDataByTimeInterval(String start, String end) {

        Date startDate = DateUtil.String2Date(start, "yyyy-MM-dd HH:mm:ss");
        Date endDate = DateUtil.String2Date(end, "yyyy-MM-dd HH:mm:ss");

        List<PrizeVo> allResult = new ArrayList<>();
        //转换成加拿大时间
        while (startDate.compareTo(endDate) <= 0) {
            List<PrizeVo> prizeVoList = this.getPrizeDataByTime(DateUtil.date2String(startDate, "yyyy-MM-dd HH:mm:ss"));
            allResult.addAll(prizeVoList);
            startDate = DateUtil.addHourss(1, startDate);
        }
        ExcelUtil.writeCanada30SExcel(allResult, "加拿大30s开奖数据" + DateUtil.date2String(new Date(),"yyyyMMddHHmmss") + ".xls");
    }
}
