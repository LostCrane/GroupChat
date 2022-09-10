package Network;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 关于聊天消息的网络连接
 */
public class NetworkMessage {
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    // 日期时间对象
    Date date;
    // 日期时间的格式
    SimpleDateFormat formatter;


    /**
     * 初始化网络配置，同时等待网络的连接
     */
    public NetworkMessage() {
        try {
            // TODO
            socket = new Socket("49.232.218.125"/*"127.0.0.1"*/, 6200);

            System.out.println("Connect Success!");
            // 获取网络连接中的IO流
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

        }catch (IOException e) {
            System.out.println("Send Fail!");
            try {
                if (socket != null) {
                    socket.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch ( IOException io_e) {
                io_e.printStackTrace();
            }
            e.printStackTrace();
        }
    }


    /**
     * 接收网络的数据，并存入数据库中
     */
    public void Receive(JTextArea ta1) {
        new Thread(() -> {
            try {
                // 存放接收到的字节流
                byte[] data = new byte[1024 * 128];
                // 用于分割字节流
                int i;
                // 如果接收到的字节流长度不为 -1（代表这个流有点东西）
                while ((i = inputStream.read(data)) != -1) {
                    // 截取字节流的标识位，获取发送此消息的账户名长度
                    int userNameLength = Integer.parseInt(new String(data, 0, 1));
                    // 截取字节流中的账户名
                    String userName = new String(data, 1, userNameLength);
                    // 截取字节流中的消息发送时间
                    String userDatetime = new String(data, 1 + userNameLength, 16);
                    // 截取字节流中的消息
                    String userMassage = new String(data, 1 + userNameLength + 16, i - 1 - userNameLength - 16);
                    // TODO
                    ta1.append(userDatetime + "  " + userName + ": " + userMassage+"\n");
                }

            } catch (IOException e) {
                System.out.println("Receive Fail!");
                try {
                    if (socket != null) {
                        socket.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch ( IOException io_e) {
                    io_e.printStackTrace();
                }
                e.printStackTrace();
            }
        }).start();
    }


    /**
     * 发送网络数据
     *
     * @param userName 发送消息的账户ID
     * @param message 发送的消息内容
     */
    public void Send(String userName, String message) {
        // 实例化 日期时间对象
        date = new Date();
        //定义日期时间格式
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // 获取消息发送的时间
        String userDatetime = formatter.format(date);

        System.out.println("username:  " + userName);
        System.out.println("message:  " + message);
        System.out.println("time:  " + userDatetime);

        try {
            // 将 发送消息的账户名长度，账户名，发送的时间，发送的消息，打包成 utf-8 格式带走
            outputStream.write( ( String.valueOf(userName.length()) +userName + userDatetime + message).getBytes(StandardCharsets.UTF_8) );
            // 刷新缓存流
            outputStream.flush();

        } catch (IOException e) {
            System.out.println("Send Fail!");
            try {
                if (socket != null) {
                    socket.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException io_e) {
                io_e.printStackTrace();
            }
        }
    }


    public void close() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }


}