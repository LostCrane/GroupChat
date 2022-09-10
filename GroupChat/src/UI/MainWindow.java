package UI;

import Network.NetworkMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 主窗口
 */
public class MainWindow {
    private JFrame mainWindow; //窗口
    private JMenuBar menubar; //菜单条
    JTextArea ta1,ta2; //上下两个文本框
    private JButton SendButton; //确定按钮
    private NetworkMessage Net; //网络
    private SendButtonListener SL; //发送按钮监听器
    private String User;

    MainWindow(String user)
    {
        init(user);
    }

    private void init(String user)//主窗口委托构造函数
    {
        User=user;
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

        menubar=new JMenuBar();//创建菜单条
        JMenu menu1=new JMenu("系统");//创建“系统”菜单
        //向菜单条中添加系统菜单
        menubar.add(menu1);

        JMenuItem item=new JMenuItem("清空记录");//系统菜单下的清空记录选项
        //添加清空按钮监听器
        item.addActionListener(ec);
        //向“系统”中添加清空记录子菜单
        menu1.add(item);


        //接受区滚动面板
        JScrollPane ta1_sp=new JScrollPane();

        //初始化接收文本框
        ta1=new JTextArea();
        //ta1.setBounds(10,10,200,200);
        ta1.setColumns(70);//行数
        ta1.setRows(18);//列数
        ta1.setLineWrap(true);//设置文本区自动换行
        ta1.setEditable(false);//不可编辑
        //将滚动设置为接受文本框
        ta1_sp.setViewportView(ta1);


        //发送区滚动面板
        //JScrollPane ta2_sp=new JScrollPane();

        //初始化发送文本框
        ta2=new JTextArea();
        ta2.setColumns(70);
        ta2.setRows(5);
        ta2.setLineWrap(true);

        //将滚动设置为发送文本框
        //ta2_sp.setViewportView(ta2);
        //获得两个文本区的尺寸
        Dimension dime_ta1=ta1.getPreferredSize(),dime_ta2=ta2.getPreferredSize();
        //根据尺寸设置文本区大小
        ta1_sp.setBounds(10,0,dime_ta1.width,dime_ta1.height);
        // ta2_sp.setBounds(10,dime_ta1.height+20,dime_ta2.width,dime_ta2.height);

        ta2.setBounds(10,dime_ta1.height+20,dime_ta2.width,dime_ta2.height);
        mainWindow.add(ta2);
        //发送按钮
        SendButton=new JButton("发送");
        SL=new SendButtonListener();
        SendButton.setBounds(720,395,60,20);
        SendButton.addActionListener(SL);

        //在窗口中添加发送按钮
        mainWindow.add(SendButton);
        //向主窗口添加两个文本框的滚动面板
        mainWindow.add(ta1_sp);
        //mainWindow.add(ta2_sp);
        //设置窗口的菜单条
        mainWindow.setJMenuBar(menubar);
        mainWindow.setResizable(false);
        mainWindow.setLocationRelativeTo(null);
        //设置窗口可见
        mainWindow.setVisible(true);

//        DBM.querySQL(ta1);

        Net=new NetworkMessage();
        Net.Receive(ta1);

    }

    class SendButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String text;
            text=ta2.getText();
            System.out.println("输出："+text);
//            ta1.append(sdf.format(date)+" Server:"+text+"\n");
            ta2.setText("");
//            Net.Send(User,ta1.getText());
            Net.Send(User,text);
//            Net.Send(text);
        }
    }

    class execlear implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            ta1.setText("");
        }
    }


}