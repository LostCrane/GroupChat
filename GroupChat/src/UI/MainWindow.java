package UI;

import Location.SaveData;
import Network.NetworkMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * 主窗口
 */
public class MainWindow {
    private JFrame mainWindow; //窗口
    JTextArea ta1,ta2; //上下两个文本框
    private JButton SendButton,ExitButton,ClearButton; //确定按钮
    private NetworkMessage Net; //网络
    private SendButtonListener SL; //发送按钮监听器
    private String User;
    private SaveData LocalMessage;


    public MainWindow(String user)//主窗口构造函数
    {
        User=user;
        LocalMessage=new SaveData();
        //初始化窗口
        mainWindow =new JFrame("聊天窗口");
        mainWindow.setSize(800,480);
        //一定要设置界面布局，否则界面会出问题
        mainWindow.setLayout(null);

        //添加关闭窗口的监听器，关闭串口的同时关闭程序。
        //new部分的代码使用匿名类编写
        mainWindow.addWindowListener(new WindowAdapter() {
            @Override//声明本方法重载了父类中的一个方法
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);//启用父类中的一个方法
                System.exit(0);
            }
        });

        //建立监听器类
        execlear ec=new execlear();



        //接受区滚动面板
        JScrollPane ta1_sp=new JScrollPane();

        //初始化接收文本框
        ta1=new JTextArea();

        ta1.setColumns(48);//行数
        ta1.setRows(10);//列数
        ta1.setLineWrap(true);//设置文本区自动换行
        ta1.setEditable(false);//不可编辑
        ta1.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        //将滚动设置为接受文本框
        ta1_sp.setViewportView(ta1);

        //初始化发送文本框
        ta2=new JTextArea();
        ta2.setColumns(48);
        ta2.setRows(5);
        ta2.setLineWrap(true);
        ta2.setFont(new Font(Font.DIALOG,Font.PLAIN,20));

        //获得两个文本区的尺寸
        Dimension dime_ta1=ta1.getPreferredSize(),dime_ta2=ta2.getPreferredSize();
        //根据尺寸设置文本区大小
        ta1_sp.setBounds(10,10,dime_ta1.width,dime_ta1.height);

        ta2.setBounds(10,dime_ta1.height+40,dime_ta2.width,dime_ta2.height);
        mainWindow.add(ta2);
        //发送按钮
        SendButton=new JButton("发送");
        SL=new SendButtonListener();
        SendButton.setBounds(717,dime_ta1.height+13,60,20);
        SendButton.addActionListener(SL);

        ClearButton=new JButton("清空记录");
        ClearButton.setBounds(10,dime_ta1.height+13,90,20);
        ClearButton.addActionListener(ec);
        mainWindow.add(ClearButton);

        //在窗口中添加发送按钮
        mainWindow.add(SendButton);
        //向主窗口添加两个文本框的滚动面板
        mainWindow.add(ta1_sp);
        //mainWindow.add(ta2_sp);
        mainWindow.setLocationRelativeTo(null);
        //设置窗口可见
        mainWindow.setVisible(true);

        Net=new NetworkMessage();
        Net.Receive(ta1);

        ArrayList<String> Message=LocalMessage.read();
        for(int j=0;j<Message.size();j++) {
            ta1.append(Message.get(j));
        }

    }

    class SendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String text;
            text=ta2.getText();
            System.out.println("输出："+text);
            LocalMessage.write(text+"\n");
            ta2.setText("");
            Net.Send(User,text);
        }
    }

    class execlear implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String Actionname = e.getActionCommand();
            switch(Actionname) {
                case "退出":
                    mainWindow.dispose();
                    new LoginWindow();
                    break;

                case "清空记录":
                    ta1.setText("");
                    LocalMessage.clearMessage();
                    break;
            }

        }
    }


}