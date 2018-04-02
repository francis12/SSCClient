package com.ssc.client;

import com.alibaba.fastjson.JSONObject;
import com.ssc.util.HttpUtil;
import com.ssc.util.LotteryUtil;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Map;

//任三
public class SSCClient extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    public void trigger(final TextArea textArea, final Stage primaryStage) {
        Runnable runnable = new Runnable() {
            public void run() {
                File file = new File("gen.txt");
                while (true) {
                    try {
                        String url = "http://114.116.9.72:8011/gen/getLatestGenPrize?lotteryCode=TCFFC";
                        String result = HttpUtil.doGet(url, "utf-8");
                        Map<String, Object> map  = JSONObject.parseObject(result, Map.class);
                        if ("200".equals(String.valueOf(map.get("code")))) {
                            String genPrize = (String) map.get("genPrize");

                            Integer wan = Integer.valueOf(String.valueOf(genPrize.charAt(0)));
                            Integer qian = Integer.valueOf(String.valueOf(genPrize.charAt(1)));

                            String normalNums = LotteryUtil.convertCha2Normal(LotteryUtil.genPy3NumStr(wan), LotteryUtil.genPy3NumStr(qian));
                            String output ="wanqian" + String.valueOf(map.get("no")) + ":" + genPrize + " zhuan( " +   normalNums +")zhuan";
                            FileUtils.writeStringToFile(file, output, false);
                            textArea.setText(result+output);

                        }
                        Thread.sleep(3*1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(runnable).start();
         /*
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);*/

    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("腾讯分分前2智能出号,稳定方案加qq:352560380");
        Group root = new Group();
        Scene scene = new Scene(root, 450, 200, Color.WHITE);
        int x = 100;
        int y = 100;

        Text text = new Text(x, y, "000 009");
        TextArea text00Area = new TextArea();
        text00Area.setText("等待中...");

        //text.setFill(Color.rgb(red, green, blue, .99));
        root.getChildren().add(text);
        root.getChildren().add(text00Area);

        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                System.out.print("监听到窗口关闭");
                System.exit(0);
            }
        });
        trigger(text00Area, primaryStage);

    }
}