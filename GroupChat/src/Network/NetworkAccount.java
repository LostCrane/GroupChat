package Network;

import UI.LoginWindow;
//import sun.security.util.Password;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


/**
 * 关于账户操作的网络连接
 */
public class NetworkAccount {

    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;


    /**
     * 初始化网络配置，同时等待网络的连接
     */
    public NetworkAccount() {
        try {
            // TODO
            socket = new Socket("49.232.218.125"/*"127.0.0.1"*/, 6000);

            System.out.println("Connect Success!");
            // 获取网络连接中的IO流
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

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
            e.printStackTrace();
        }
    }


    /**
     * 接收服务器返回的数据
     */
    public void Receive(LoginWindow LW) {
        new Thread(() -> {
            try {
                // 存放接收到的字节流
                byte[] data = new byte[1024 * 128];
                // 用于分割字节流
                int i;
                // 如果接收到的字节流长度不为 -1（代表这个流有点东西）
                while ((i = inputStream.read(data)) != -1) {

                    // TODO 服务器返回的是 int 类型的 账户ID号
                    int userID = Integer.parseInt(new String(data, 0, i));
                    System.out.println("net: " + userID);
//                    // TODO 现在返回的是你查询和其他操作的结果，就是真假值
//                    ta1.append(" User: " + new String(data, 0, i) + "\n");
                    LW.NetCallback(userID);
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
                } catch (IOException io_e) {
                    io_e.printStackTrace();
                }
                e.printStackTrace();
            }
        }).start();
    }


    /**
     * 给服务器发送数据
     *
     * @param purpose 本次发送的数据意图
     * @param unit  发送的账户名长度
     * @param userName  发送的账户名
     * @param Password  发送的密码
     */
    public void Send(String purpose, String unit, String userName, String Password) {
        try {
            // 将 数据意图、发送的账户名长度、发送的账户名和密码，打包成 utf-8 格式带走
            outputStream.write((purpose + unit + userName + Password).getBytes(StandardCharsets.UTF_8));
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