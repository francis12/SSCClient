package com.ssc.service;

import com.alibaba.fastjson.JSONObject;
import com.ssc.com.ssc.vo.UserInfo;
import com.ssc.util.HttpUtil;
import com.ssc.util.LotteryUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.Date;
import java.util.Map;


//任三
public class SSCService {

    //File file = new File("定位胆-千位.txt");
    //File zhong3File = new File("中3.txt");
    File wanDwdFile = new File("wan.txt");

    String type;
    public SSCService(String type) {
        this.type = type;
    }

    private boolean processLatestNo() throws Exception {
        String genNo = LotteryUtil.getNextNoByOnlineTime();
        System.out.print(new Date() + "开始获取第" + genNo + "期号码：");

        boolean isGetCur = false;
        boolean is2FetchNext = false;
        int retryCnt = 0;
        while (retryCnt <= 60 && isGetCur == false) {
            retryCnt++;
            String url = "http://114.115.161.72:8011/gen/getLatestGenPrize?lotteryCode=TCFFC&signCode=201613gn7ew&type="+ type+ "&no=" + genNo;
            //String url = "http://localhost:8011/gen/getLatestGenPrize?lotteryCode=TCFFC&signCode=201613gn7ew";
            String result = HttpUtil.doGet(url, "utf-8");
            Map<String, Object> map = JSONObject.parseObject(result, Map.class);

            String code = String.valueOf(map.get("code"));
            String no = String.valueOf(map.get("no"));
            if ("200".equals(code)
                    && genNo.equals(no)) {
                String genPrize = (String) map.get("genPrize");
                System.out.println(genPrize);
                String output = type + " - " + String.valueOf(map.get("no")) + ":" + " zhuan(" + genPrize + ")zhuan";
                String target = new String(output.getBytes("utf-8"), "utf-8");
                FileUtils.writeStringToFile(wanDwdFile, target, false);
//                Integer wan = Integer.valueOf(String.valueOf(genPrize.charAt(0)));
//                Integer qian = Integer.valueOf(String.valueOf(genPrize.charAt(1)));
//                Integer bai = Integer.valueOf(String.valueOf(genPrize.charAt(2)));
//                Integer shi = Integer.valueOf(String.valueOf(genPrize.charAt(3)));
//                Integer ge = Integer.valueOf(String.valueOf(genPrize.charAt(4)));

//                //定位胆
//                String qianGenStr = LotteryUtil.genPy3NumStr(qian);
//
//                String output = "第" + String.valueOf(map.get("no")) + "期:" + " zhuan(" + qianGenStr + ")zhuan";
//                FileUtils.writeStringToFile(file, output, false);
//
//                //中三
//                //String srcStr = LotteryUtil.genPyPost4NumStr(qian) + "*" + LotteryUtil.genPy4NumStr(bai) + "*" + LotteryUtil.genPy4NumStr(shi);
//                String zhong3NormalNums = LotteryUtil.convertCha3Normal(LotteryUtil.genPyPost4NumStr(qian), LotteryUtil.genPy4NumStr(bai), LotteryUtil.genPy4NumStr(shi));
//                String zhong3Output = "第" + String.valueOf(map.get("no")) + "期:" + " zhuan(" + zhong3NormalNums + ")zhuan";
//                FileUtils.writeStringToFile(zhong3File, zhong3Output, false);


                isGetCur = true;
            }

            Thread.sleep(1 * 1000);
        }

        if(isGetCur) {
            //每隔一秒检查 当期期数与取到期数不一致时取下一期
            while (!is2FetchNext) {
                String nextNo = LotteryUtil.getNextNoByOnlineTime();
                if (!nextNo.equals(genNo)) {
                    FileUtils.writeStringToFile(wanDwdFile, "waiting...", false);
                    //FileUtils.writeStringToFile(zhong3File, "waiting...", false);
                    is2FetchNext = true;
                }
                Thread.sleep(1 * 1000);
            }
        } else {
            FileUtils.writeStringToFile(wanDwdFile, "cur" + genNo + "  plan error", false);
            //FileUtils.writeStringToFile(zhong3File, "cur" + genNo + " plan error", false);
            return false;
        }
        return true;
    }


    public void processGenNumEnvent() {
        while (true) {
            try {
                processLatestNo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}