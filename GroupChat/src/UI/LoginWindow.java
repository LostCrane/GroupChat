package UI;

import Network.Network;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * 登入窗口
 */
public class LoginWindow {
    // TODO all updatebuttontes
    private final JFrame loginWindow;
    private JButton addbutton,deletebutton,signupbutton, updatebutton;
    private JLabel Label_User, Label_pswd,Label_title;
    private JTextField UserName;
    private JPasswordField PassWord;
    private CommandListener CL;
    private Network NetAccount;
    private JFrame updateFrame;
    private String inputusername;

    private int option;

    //更新窗口
    private JLabel username,password,newpassword,checknewpassword;
    private JTextField UserNameArea;
    private JPasswordField PassWordArea,newpassword1,newpassword2;
    private JButton yes,cancel;
    private update_commandLstener UpdateCL;


    // 设置登入窗口
    public LoginWindow() {
        loginWindow = new JFrame("登录");
        // 设置登入窗口 大小
        loginWindow.setSize(350, 250);
        // 设置登入窗口位置 居中
        loginWindow.setLocationRelativeTo(null);
        // TODO updatebuttontes
        loginWindow.setLayout(null);
        // 设置 关闭窗口后退出程序
        loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Label_title=new JLabel("正在从服务器获取信息...");
        Label_title.setBounds(100,0,150,20);
        loginWindow.add(Label_title);


        // 设置 第一行的标注文字和输入窗口
        Label_User = new JLabel("用户名：");
        Label_User.setBounds(40, 30, 50, 30);
        UserName = new JTextField();
        UserName.setBounds(90, 30, 200, 30);
        UserName.setFont(new Font(Font.DIALOG,Font.PLAIN,20));

        // 将组件添加进登入窗口中
        loginWindow.add(Label_User);
        loginWindow.add(UserName);

        // 设置 第二行的标注文字和密码窗口
        Label_pswd = new JLabel("密码：");
        Label_pswd.setBounds(40, 70, 50, 30);
        PassWord = new JPasswordField();
        PassWord.setEchoChar('*');
        PassWord.setBounds(90, 70, 200, 30);
        PassWord.setFont(new Font(Font.DIALOG,Font.PLAIN,20));

        loginWindow.add(Label_pswd);
        loginWindow.add(PassWord);

        // 设置 第三行的 确认\取消 按钮
        CL = new CommandListener();
        signupbutton = new JButton("登录");
        signupbutton.setBounds(40, 120, 100, 25);
        signupbutton.addActionListener(CL);

        addbutton = new JButton("注册");
        addbutton.setBounds(190, 120, 100, 25);
        addbutton.addActionListener(CL);

        updatebutton = new JButton("修改密码");
        updatebutton.setBounds(40, 160, 100, 25);
        updatebutton.addActionListener(CL);

        deletebutton = new JButton("注销");
        deletebutton.setBounds(190, 160, 100, 25);
        deletebutton.addActionListener(CL);

        loginWindow.add(signupbutton);
        loginWindow.add(updatebutton);
        loginWindow.add(deletebutton);
        loginWindow.add(addbutton);

        // 设置登入窗口 可见
        loginWindow.setVisible(true);

        //new NetworkConnect();
        NetAccount=new Network();
        NetAccount.receive(LoginWindow.this);

        Label_title.setVisible(false);
    }


    //注册窗口
    public void updateWindow() {
        loginWindow.setVisible(false);

        updateFrame=new JFrame("修改密码");

        updateFrame.setVisible(true);
        updateFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        updateFrame.setSize(300,250);
        updateFrame.setLayout(null);
        updateFrame.setResizable(false);
        updateFrame.setLocationRelativeTo(null);

        UpdateCL=new update_commandLstener();

        username=new JLabel("用户名：");
        username.setBounds(30,30,70,20);

        password=new JLabel("密码：");
        password.setBounds(30,60,70,20);

        newpassword=new JLabel("新密码：");
        newpassword.setBounds(30,90,70,20);

        checknewpassword=new JLabel("确认密码：");
        checknewpassword.setBounds(30,120,70,20);

        UserNameArea=new JTextField();
        UserNameArea.setBounds(100,30,150,20);

        PassWordArea=new JPasswordField();
        PassWordArea.setBounds(100,60,150,20);

        newpassword1=new JPasswordField();
        newpassword1.setBounds(100,90,150,20);

        newpassword2=new JPasswordField();
        newpassword2.setBounds(100,120,150,20);

        yes=new JButton("确定");
        yes.setBounds(50,150,70,30);
        yes.addActionListener(UpdateCL);

        cancel=new JButton("取消");
        cancel.setBounds(170,150, 70,30);
        cancel.addActionListener(UpdateCL);


        //updateFrame.add(Label_update);
        updateFrame.add(username);
        updateFrame.add(password);
        updateFrame.add(newpassword);
        updateFrame.add(checknewpassword);
        updateFrame.add(UserNameArea);
        updateFrame.add(PassWordArea);
        updateFrame.add(newpassword1);
        updateFrame.add(newpassword2);
        updateFrame.add(yes);
        updateFrame.add(cancel);


    }

    public void NetCallback(int res) {
           switch(option) {
               case 1:
                   if(res!=0) {
                       loginWindow.dispose();
                       NetAccount.close();
                       new MainWindow(inputusername);
                   }
                   else {
                       JOptionPane.showMessageDialog(null,"用户名或密码错误，请重新输入","错误",JOptionPane.WARNING_MESSAGE);
                   }
                   break;

               case 2:
                   if(res!=0) {
                       JOptionPane.showMessageDialog(null,"注册成功！","成功",JOptionPane.INFORMATION_MESSAGE);
                   }
                   else {
                       JOptionPane.showMessageDialog(null,"注册失败","错误",JOptionPane.WARNING_MESSAGE);
                   }
                   break;

               case 3:
                   if(res!=0) {
                       //updateWindow();
                   }
                   else {
                       //JOptionPane.showMessageDialog(null,"没有找到对应的用户，","失败",JOptionPane.ERROR_MESSAGE);
                   }
                   break;

               case 4:
                   if(res!=0) {
                       JOptionPane.showMessageDialog(null,"注销成功","成功",JOptionPane.INFORMATION_MESSAGE);

                   }
                   else {
                       JOptionPane.showMessageDialog(null,"注销失败","失败",JOptionPane.ERROR_MESSAGE);
                   }
                   break;

               case 5:
                   if(res!=0) {
                       NetAccount.sendAccount("3",String.valueOf(UserNameArea.getText().length()),UserNameArea.getText(),new String(newpassword1.getPassword()));
                    option=6;
                   }
                   else {
                       JOptionPane.showMessageDialog(null,"用户名或密码输入错误","错误",JOptionPane.ERROR_MESSAGE);

                   }
                   break;

               case 6:
                   if(res!=0) {
                       JOptionPane.showMessageDialog(null,"修改密码成功","成功",JOptionPane.INFORMATION_MESSAGE);
                   }
           }

    }


    // 设计 监听器
    class CommandListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            // 返回响应的按钮
            String name = e.getActionCommand();

            if(UserName.getText().length()>9) {
                JOptionPane.showMessageDialog(null,"用户名长度不能超过9位，请重新输入","输入错误",JOptionPane.WARNING_MESSAGE);
                return;
            }
            switch (name) {
                case "登录":
                    if(Objects.equals(UserName.getText(), "")||PassWord.getPassword().length==0 ) {
                        JOptionPane.showMessageDialog(null,"用户名或密码不能为空，请重新输入","输入错误",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    option=1;
                    inputusername=UserName.getText();
                    NetAccount.sendAccount("1",new String(String.valueOf(UserName.getText().length())),UserName.getText(),new String(PassWord.getPassword()));
                    break;

                case "注册":
                    if(Objects.equals(UserName.getText(), "")||PassWord.getPassword().length==0 ) {
                        JOptionPane.showMessageDialog(null,"用户名或密码不能为空，请重新输入","输入错误",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    option=2;
                    NetAccount.sendAccount("2",new String(String.valueOf(UserName.getText().length())),UserName.getText(),new String(PassWord.getPassword()));
                    break;

                case "修改密码":
                    option=3;
                    //打开更新窗口
                    updateWindow();
                    break;

                case "注销":
                    if(Objects.equals(UserName.getText(), "")||PassWord.getPassword().length==0 )
                    {
                        JOptionPane.showMessageDialog(null,"用户名或密码不能为空，请重新输入","输入错误",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    option=4;
                    NetAccount.sendAccount("4",new String(String.valueOf(UserName.getText().length())),UserName.getText(),new String(PassWord.getPassword()));
                    break;
            }
        }
    }

    class update_commandLstener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 返回响应的按钮
            String name = e.getActionCommand();
            switch (name) {
                case "确定":
                    if(Objects.equals(UserNameArea.getText(), "")||PassWordArea.getPassword().length==0 ) {
                        JOptionPane.showMessageDialog(null,"用户名或密码不能为空，请重新输入","输入错误",JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if(!new String(newpassword1.getPassword()).equals(new String(newpassword2.getPassword())))
                    {
                        JOptionPane.showMessageDialog(null,"两次输入的新密码不一致","错误",JOptionPane.WARNING_MESSAGE);
                        break;
                    }
                    option=5;
                    NetAccount.sendAccount("3",new String(String.valueOf(UserNameArea.getText().length())),UserNameArea.getText(),new String(newpassword1.getPassword()));
                    break;

                case "取消":
                    updateFrame.dispose();
                    loginWindow.setVisible(true);
                    break;
            }

        }
    }

}
