package com.ssc.client;

import com.ssc.com.ssc.vo.UserInfo;
import com.ssc.service.SSCService;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;


//任三
public class SSCClient extends Application{

    File file = new File("定位胆-千位.txt");
    File zhong3File = new File("中3.txt");

    public static void main(String[] args) {
        launch(args);
    }

    public void trigger(final TextField textArea, final Stage primaryStage, String type) {
        Runnable runnable = new Runnable() {
            public void run() {
                new SSCService(type).processGenNumEnvent();
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

       /* Label label1 = new Label();
        label1.setText("选择计划:");
        root.getChildren().add(label1);

        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("万位大小", "万位单双"));
        root.getChildren().add(cb);*/

        Label jhLabel = new Label();
        jhLabel.setText("计划id:");
        root.getChildren().add(jhLabel);

        // TextField
        TextField jhTextField = new TextField("");
        jhTextField.setPrefWidth(120);
        root.getChildren().add(jhTextField);

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


       /// TextField
        TextField textField = new TextField("Text Field");
        textField.setPrefWidth(110);
        textField.setDisable(true);
        root.getChildren().add(textField);

        button1.setOnAction(oa -> {
            String zh = zhTextField.getText();
            String type = jhTextField.getText();
            if (!"test".equals(zh)) {
                Alert _alert = new Alert(Alert.AlertType.WARNING);
                _alert.setTitle("信息");
                _alert.setHeaderText("账号失败校验");
                _alert.show();
            } else {
                trigger(textField, primaryStage, type);
                Alert _alert = new Alert(Alert.AlertType.INFORMATION);
                _alert.setTitle("信息");
                _alert.setHeaderText("账号校验通过,开始生成计划");
                _alert.show();
                button1.setDisable(true);

                UserInfo userInfo = new UserInfo();
                userInfo.setUserName(zhTextField.getText());
                //userInfo.setWebsiteName(cb.getValue().toString());
                userInfo.setAuthCode(sqTextField.getText());
                this.setUserInfo2File(userInfo);
            }
        });

        Scene scene = new Scene(root, 600, 100);

        primaryStage.setTitle("腾讯分分智能出号系统,联系qq:");
        primaryStage.setScene(scene);

        UserInfo userInfo = this.getUserInfoFromFile();
        if (null != userInfo) {
            //cb.setValue(userInfo.getWebsiteName());
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