package Network;

import UI.LoginWindow;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 网络连接类
 *
 * 负责与服务器建立网络连接和数据通信
 */
public class Network {

    // Socket 对象及其 IO流对象
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    // 日期时间对象
    Date date;
    // 日期时间的格式
    SimpleDateFormat formatter;


    /**
     * 初始化网络配置，并与服务器建立连接
     */
    public Network() {
        try {
            // 服务器的IP地址和开放的相应端口号
            socket = new Socket("49.232.218.125", 6000);
            // 获取网络连接中的 IO流
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            // 实例化 日期时间对象
            date = new Date();
            //定义日期时间格式
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        } catch (IOException e) {
            System.out.println("Network() Error!");
            close();
            e.printStackTrace();
        }
    }


    /**
     * 接收来自服务器的数据
     * 方法会根据接收到的数据流中标识位，确定是账户数据还是聊天消息
     * @param LW LoginWindow 类的对象。账户消息使用
     * @param ta1 TODO 介绍
     */
    public void receive(LoginWindow LW, JTextArea ta1) {
        // 以匿名类的方式开启线程接收数据，防止接收方法堵塞程序
        new Thread(() -> {
            try {
                // 存放接收到的字节流
                byte[] data = new byte[1024 * 128];
                // 用于分割字节流
                int i;
                // 如果接收到的字节流长度不为 -1（代表这个流有点东西）
                while ((i = inputStream.read(data)) != -1) {
                    // 截取 字节流中标识位
                    switch (data[0]) {
                        // 标识位是 1，此流返回的是账户数据
                        case 1:
                            // 服务器返回的是 int 类型的 账户ID号
                            LW.NetCallback( Integer.parseInt(new String(data, 1, i)) );
                            break;
                        // 标识位是 2，此流返回的是聊天信息
                        case 2:
                            // 截取 发送此消息的账户名长度
                            int userNameLength = Integer.parseInt(new String(data, 1, 1));
                            // 截取 账户名
                            String userName = new String(data, 2, userNameLength);
                            // 截取 消息发送的时间
                            String userDatetime = new String(data, 2 + userNameLength, 16);
                            // 截取 聊天消息
                            String userMassage = new String(data, 2 + userNameLength + 16, i - 2 - userNameLength - 16);
                            // TODO 数据分开处理，还是这样打包到一起处理
                            ta1.append(userDatetime + "  " + userName + ": " + userMassage+"\n");
                    }
                }

            } catch (IOException e) {
                System.out.println("Network.receive() Error!");
                close();
                e.printStackTrace();
            }
        }).start();
    }


    /**
     * 向服务器发送关于账户的数据
     * @param purpose 数据的意图
     * @param unit  账户名长度
     * @param userName  账户名
     * @param Password  密码
     */
    public void sendAccount(String purpose, String unit, String userName, String Password) {
        try {
            // 将 数据的意图、账户名长度、账户名、密码，打包成 utf-8 格式发送
            outputStream.write((purpose + unit + userName + Password).getBytes(StandardCharsets.UTF_8));
            // 刷新缓存流
            outputStream.flush();

        } catch (IOException e) {
            // TODO 注释，关闭流
            System.out.println("Network.sendAccount() Error!");
            close();
            e.printStackTrace();
        }
    }


    /**
     * 向服务器发送关于聊天消息的数据
     * @param userName 发送此聊天消息的账户名
     * @param message 聊天消息的内容
     */
    public void sendMessage(String userName, String message) {
        try {
            // 将 账户名长度，账户名，聊天消息发送的时间，聊天消息，打包成 utf-8 格式发送
            outputStream.write( ( String.valueOf(userName.length()) +userName + formatter.format(date) + message).getBytes(StandardCharsets.UTF_8) );
            // 刷新缓存流
            outputStream.flush();

        } catch (IOException e) {
            System.out.println("Network.sendMessage() Error!");
            close();
            e.printStackTrace();
        }
    }


    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException ce) {
            System.out.println("Network.close() Error!");
            ce.printStackTrace();
        }
    }


}