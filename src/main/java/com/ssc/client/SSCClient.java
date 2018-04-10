package com.ssc.client;

import com.ssc.com.ssc.vo.UserInfo;
import com.ssc.service.PrizeFetchService;
import com.ssc.util.DateUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

//任三
public class SSCClient extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    public void trigger(String start, String end) {
        new PrizeFetchService().getPrizeDataByTimeInterval(start, end);
    }

    @Override
    public void start(final Stage primaryStage) {

        FlowPane root = new FlowPane();

        root.setHgap(10);
        root.setVgap(20);
        root.setPadding(new Insets(15,15,15,15));

        Label label1 = new Label();
        label1.setText("彩种:");
        root.getChildren().add(label1);

        ChoiceBox cz = new ChoiceBox(FXCollections.observableArrayList("加拿大30s"));
        cz.setPrefWidth(180);
        root.getChildren().add(cz);

        Label zhLabel = new Label();
        zhLabel.setText("账号:");
        root.getChildren().add(zhLabel);

        // TextField
        TextField zhTextField = new TextField("");
        zhTextField.setPrefWidth(180);
        root.getChildren().add(zhTextField);

        Label sqmLabel = new Label();
        sqmLabel.setText("开始时间:");
        root.getChildren().add(sqmLabel);

        // TextField
        TextField sqTextField = new TextField("");
        sqTextField.setPrefWidth(180);
        root.getChildren().add(sqTextField);

        Label endLabel = new Label();
        endLabel.setText("结束时间:");
        root.getChildren().add(endLabel);

        // TextField
        TextField endTextField = new TextField("");
        endTextField.setPrefWidth(180);
        root.getChildren().add(endTextField);

        String curTime = DateUtil.date2String(DateUtil.String2Date(DateUtil.date2String(new Date(), "yyyy-MM-dd HH"), "yyyy-MM-dd HH"), "yyyy-MM-dd HH:mm:ss");
        sqTextField.setText(curTime);
        endTextField.setText(curTime);
        cz.setValue("加拿大30s");
        // Button 1
        Button button1= new Button("开始下载数据");
        root.getChildren().add(button1);

        // TextField
/*        TextField textField = new TextField("Text Field");
        textField.setPrefWidth(110);
        root.getChildren().add(textField);*/

        button1.setOnAction(oa -> {
            String zh = zhTextField.getText();
            if (!"qq352560380".equals(zh)) {
                Alert _alert = new Alert(Alert.AlertType.WARNING);
                _alert.setTitle("信息");
                _alert.setHeaderText("账号校验失败");
                _alert.show();
            } else {
                String start = sqTextField.getText();
                String end = endTextField.getText();
                Alert _alert = new Alert(Alert.AlertType.INFORMATION);
                _alert.setTitle("信息");
                _alert.setHeaderText("账号校验通过,开始下载数据到当前文件夹下!");
                _alert.show();
                try {
                    Thread.sleep(3*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                _alert.close();

                UserInfo userInfo = new UserInfo();
                userInfo.setUserName(zhTextField.getText());
                //userInfo.setWebsiteName(cb.getValue().toString());
                userInfo.setAuthCode(sqTextField.getText());
                this.setUserInfo2File(userInfo);

                trigger(start, end);
            }
        });

        Scene scene = new Scene(root, 210, 360);

        primaryStage.setTitle("腾讯分分智能出号,稳定方案加qq:352560380");
        primaryStage.setScene(scene);

        UserInfo userInfo = this.getUserInfoFromFile();
        if (null != userInfo) {
            //cb.setValue(userInfo.getWebsiteName());
            zhTextField.setText(userInfo.getUserName());
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