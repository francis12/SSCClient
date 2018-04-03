package com.ssc.service;

import com.ssc.util.DateUtil;
import com.ssc.util.HttpUtil;

import java.util.Date;

public class PrizeFetchService {

    String can30sUrl = "https://www.canadasuperdraw.com/api/history.php?lotteryid=29&date=";

    public String getPrizeDataByTime(Date time) {

        //转换成加拿大时间
        Date date = DateUtil.addHourss(12, time);
        String timeParam = "2018-4-2 01:00:00";
        String result = HttpUtil.doGet(can30sUrl,"utf-8");

        return null;
    }

    public void getPrizeDataByTimeInterval(Date start, Date end) {

        //转换成加拿大时间
        while (start.compareTo(end) <= 0) {
            this.getPrizeDataByTime(start);
            start = DateUtil.addHourss(1, start);
        }
    }

}
