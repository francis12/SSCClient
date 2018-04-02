package com.ssc.client;

import com.alibaba.fastjson.JSONObject;
import com.ssc.com.ssc.vo.UserInfo;
import com.ssc.util.DateUtil;
import com.ssc.util.HttpUtil;
import com.ssc.util.LotteryUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Map;

//任三
public class SSCClient extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    public void trigger(final TextField textArea, final Stage primaryStage) {
        Runnable runnable = new Runnable() {
            public void run() {
                File file = new File("gen.txt");
                while (true) {
                    try {
                        String genNo = LotteryUtil.getNextNoByOnlineTime();
                        String url = "http://114.116.9.72:8011/gen/getLatestGenPrize?lotteryCode=TCFFC";
                        String result = HttpUtil.doGet(url, "utf-8");
                        Map<String, Object> map  = JSONObject.parseObject(result, Map.class);

                        if ("200".equals(String.valueOf(map.get("code")))
                                && genNo.equals(String.valueOf(map.get("no")))) {
                            String genPrize = (String) map.get("genPrize");

                            Integer wan = Integer.valueOf(String.valueOf(genPrize.charAt(0)));
                            Integer qian = Integer.valueOf(String.valueOf(genPrize.charAt(1)));

                            String normalNums = LotteryUtil.convertCha2Normal(LotteryUtil.genPy3NumStr(wan), LotteryUtil.genPy3NumStr(qian));
                            String output ="wanqian" + String.valueOf(map.get("no")) + ":" + genPrize + " zhuan( " +   normalNums +")zhuan";
                            FileUtils.writeStringToFile(file, output, false);
                            textArea.setText(result+output);

                            //当期期数与取到期数不一致时取下一期
                            boolean is2FetchNext = false;
                            while (!is2FetchNext) {
                                String nextNo = LotteryUtil.getNextNoByOnlineTime();
                                if (!nextNo.equals(genNo)) {
                                    FileUtils.writeStringToFile(file, "waiting...", false);
                                    is2FetchNext = true;
                                }
                                Thread.sleep(1*1000);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(runnable).start();
    }

    @Override
    public void start(final Stage primaryStage) {

        FlowPane root = new FlowPane();

        root.setHgap(10);
        root.setVgap(20);
        root.setPadding(new Insets(15,15,15,15));

        Label label1 = new Label();
        label1.setText("选择平台:");
        root.getChildren().add(label1);

        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("198", "杏彩", "钱汇"));
        root.getChildren().add(cb);


        Label zhLabel = new Label();
        zhLabel.setText("账号:");
        root.getChildren().add(zhLabel);

        // TextField
        TextField zhTextField = new TextField("");
        zhTextField.setPrefWidth(120);
        root.getChildren().add(zhTextField);

        Label sqmLabel = new Label();
        sqmLabel.setText("授权码:");
        root.getChildren().add(sqmLabel);

        // TextField
        TextField sqTextField = new TextField("");
        sqTextField.setPrefWidth(120);
        root.getChildren().add(sqTextField);

        // Button 1
        Button button1= new Button("开始计划");
        root.getChildren().add(button1);


        // TextField
        TextField textField = new TextField("Text Field");
        textField.setPrefWidth(110);
        root.getChildren().add(textField);

        button1.setOnAction(oa -> {
            String zh = zhTextField.getText();
            if (!"test".equals(zh)) {
                Alert _alert = new Alert(Alert.AlertType.WARNING);
                _alert.setTitle("信息");
                _alert.setHeaderText("账号失败校验");
                _alert.show();
            } else {
                trigger(textField, primaryStage);
                Alert _alert = new Alert(Alert.AlertType.INFORMATION);
                _alert.setTitle("信息");
                _alert.setHeaderText("账号校验通过,开始生成计划");
                _alert.show();
                button1.setDisable(true);

                UserInfo userInfo = new UserInfo();
                userInfo.setUserName(zhTextField.getText());
                userInfo.setWebsiteName(cb.getValue().toString());
                userInfo.setAuthCode(sqTextField.getText());
                this.setUserInfo2File(userInfo);
            }
        });

        Scene scene = new Scene(root, 150, 250);

        primaryStage.setTitle("腾讯分分前2智能出号,稳定方案加qq:352560380");
        primaryStage.setScene(scene);

        UserInfo userInfo = this.getUserInfoFromFile();
        if (null != userInfo) {
            cb.setValue(userInfo.getWebsiteName());
            zhTextField.setText(userInfo.getUserName());
            sqTextField.setText(userInfo.getAuthCode());
        }

        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                System.out.print("监听到窗口关闭");
                System.exit(0);
            }
        });

    }

    //    弹出一个信息对话框
    public void f_alert_informationDialog(String p_header, String p_message){
/*        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle("信息");
        _alert.setHeaderText(p_header);
        _alert.setContentText(p_message);
        _alert.initOwner(d_stage);
        _alert.show();*/
    }

    public UserInfo getUserInfoFromFile() {
        UserInfo userInfo = null;
        try {
            FileInputStream fis = new FileInputStream("userinfo");
            ObjectInputStream ois = new ObjectInputStream(fis);
            userInfo = (UserInfo)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }
    public void setUserInfo2File(UserInfo userInfo) {
        try {
            FileOutputStream fos = new FileOutputStream("userinfo");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}