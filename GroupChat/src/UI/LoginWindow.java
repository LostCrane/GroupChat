package UI;


//import Database.DatabaseAccount;
import Network.NetworkMessage;
import Network.NetworkAccount;

import javax.swing.*;
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
    //private String username = "root", password = "password";
    //private DatabaseAccount database;
    private NetworkAccount NetAccount;
    private JFrame updateFrame;
    private String inputusername;

    private int UserID;
    private int option;

    //更新窗口
    private JLabel Label_update,username,password,newpassword,checknewpassword;
    private JTextField UserNameArea;
    private JPasswordField PassWordArea,newpassword1,newpassword2;
    private JButton yes,cancel;
    private update_commandLstener UpdateCL;

    public static void main(String[] args) {
        new LoginWindow();
    }

    // 设置登入窗口
    public LoginWindow() {

        //database=new DatabaseAccount();
        NetAccount=new NetworkAccount();


        loginWindow = new JFrame("登录");
        // 设置登入窗口 大小
        loginWindow.setSize(300, 200);
        // 设置登入窗口位置 居中
        loginWindow.setLocationRelativeTo(null);
        // TODO updatebuttontes
        loginWindow.setLayout(null);
        // 设置 关闭窗口后退出程序
        loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Label_title=new JLabel("正在从服务器获取信息...");
        Label_title.setBounds(80,0,150,20);
        loginWindow.add(Label_title);


        // 设置 第一行的标注文字和输入窗口
        Label_User = new JLabel("用户名：");
        Label_User.setBounds(40, 30, 50, 20);
        UserName = new JTextField();
        UserName.setBounds(90, 30, 150, 20);
        // 将组件添加进登入窗口中
        loginWindow.add(Label_User);
        loginWindow.add(UserName);

        // 设置 第二行的标注文字和密码窗口
        Label_pswd = new JLabel("密码：");
        Label_pswd.setBounds(40, 60, 50, 20);
        PassWord = new JPasswordField();
        PassWord.setEchoChar('*');
        PassWord.setBounds(90, 60, 150, 20);

        loginWindow.add(Label_pswd);
        loginWindow.add(PassWord);

        // 设置 第三行的 确认\取消 按钮
        CL = new CommandListener();
        signupbutton = new JButton("登录");
        signupbutton.setBounds(40, 85, 90, 25);
        signupbutton.addActionListener(CL);

        addbutton = new JButton("注册");
        addbutton.setBounds(150, 85, 90, 25);
        addbutton.addActionListener(CL);

        updatebutton = new JButton("修改密码");
        updatebutton.setBounds(40, 120, 90, 25);
        updatebutton.addActionListener(CL);

        deletebutton = new JButton("注销");
        deletebutton.setBounds(150, 120, 90, 25);
        deletebutton.addActionListener(CL);

        loginWindow.add(signupbutton);
        loginWindow.add(updatebutton);
        loginWindow.add(deletebutton);
        loginWindow.add(addbutton);

        // 设置登入窗口 可见
        loginWindow.setVisible(true);

        //new NetworkConnect();

        NetAccount.Receive(LoginWindow.this);
    }

    //注册窗口
    public void updateWindow()
    {
        loginWindow.setVisible(false);

        updateFrame=new JFrame("修改密码");


        updateFrame.setVisible(true);
        updateFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        updateFrame.setSize(300,250);
        updateFrame.setLayout(null);
        updateFrame.setResizable(false);
        updateFrame.setLocationRelativeTo(null);

        UpdateCL=new update_commandLstener();

//        Label_update=new JLabel("修改密码");
//        Label_update.setBounds(100,10,100,20);

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

    public void NetCallback(int res)
    {

           switch(option)
           {
               case 1:
                   if(res!=0)
                   {
                       //UserID=res;
                       loginWindow.dispose();
                       NetAccount.close();
                       new MainWindow(inputusername);
//                       new MainWindow(String.valueOf(res));
                   }
                   else
                   {
                       JOptionPane.showMessageDialog(null,"用户名或密码错误，请重新输入","错误",JOptionPane.WARNING_MESSAGE);
                   }

                   break;
               case 2:
                   if(res!=0)
                   {
                       JOptionPane.showMessageDialog(null,"注册成功！","成功",JOptionPane.INFORMATION_MESSAGE);
                   }
                   else
                   {
                       JOptionPane.showMessageDialog(null,"注册失败","错误",JOptionPane.WARNING_MESSAGE);
                   }
                   break;
               case 3:
                   if(res!=0)
                   {
                       //updateWindow();
                   }
                   else
                   {
                       //JOptionPane.showMessageDialog(null,"没有找到对应的用户，","失败",JOptionPane.ERROR_MESSAGE);
                   }
                   break;
               case 4:
                   if(res!=0)
                   {
                       JOptionPane.showMessageDialog(null,"注销成功","成功",JOptionPane.INFORMATION_MESSAGE);

                   }
                   else
                   {
                       JOptionPane.showMessageDialog(null,"注销失败","失败",JOptionPane.ERROR_MESSAGE);
                   }
                   break;
               case 5:
                   if(res!=0)
                   {
                       NetAccount.Send("3",new String(String.valueOf(UserNameArea.getText().length())),UserNameArea.getText(),new String(newpassword1.getPassword()));
                    option=6;
                   }
                   else
                   {
                       JOptionPane.showMessageDialog(null,"用户名或密码输入错误","错误",JOptionPane.ERROR_MESSAGE);

                   }
                   break;
               case 6:
                   if(res!=0)
                   {
                       JOptionPane.showMessageDialog(null,"修改密码成功","成功",JOptionPane.INFORMATION_MESSAGE);
                   }
           }

    }


    // 设计 监听器
    class CommandListener implements ActionListener {
        // TODO notes
        public void actionPerformed(ActionEvent e) {

            // 返回响应的按钮
            String name = e.getActionCommand();

            switch (name) {
                case "登录":
                    // TODO codes
                    if(Objects.equals(UserName.getText(), "")||PassWord.getPassword().length==0 )
                    {
                        JOptionPane.showMessageDialog(null,"用户名或密码不能为空，请重新输入","输入错误",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    option=1;
                    inputusername=UserName.getText();
                    System.out.println("AAAAAAAA"+inputusername);
                    NetAccount.Send("1",new String(String.valueOf(UserName.getText().length())),UserName.getText(),new String(PassWord.getPassword()));


//                    if (NetAccount.Send(1,UserName.getText().length(),UserName.getText(),new String(PassWord.getPassword())))
//                    {
//
//                        //如果用户名和密码都正确，则继续
//                        // 在这里添加另一个窗口的展开代码，和数据库调用代码
//                        loginWindow.dispose();
//                        new MainWindow(UserName.getText());
//                    }
//                    else {
//                        // TODO codes
//
//                        System.out.println("用户名和密码错误，请重新输入！");
//                        JOptionPane.showMessageDialog(null,"用户名或密码错误，请重新输入","密码错误",JOptionPane.WARNING_MESSAGE);
//                    }
                    break;
                // TODO 可以不要取消，改成注册，存到数据库里
                case "注册":
                    if(Objects.equals(UserName.getText(), "")||PassWord.getPassword().length==0 )
                    {
                        JOptionPane.showMessageDialog(null,"用户名或密码不能为空，请重新输入","输入错误",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    option=2;

                    //System.exit(0);
//                    if(database.querySQL(UserName.getText(),new String(PassWord.getPassword())))
//                    {
//                        JOptionPane.showMessageDialog(null,"用户名已经存在，请重新输入","错误",JOptionPane.WARNING_MESSAGE);
//                        break;
//                    }
                    NetAccount.Send("2",new String(String.valueOf(UserName.getText().length())),UserName.getText(),new String(PassWord.getPassword()));

//                    if()
//                    {
//                        JOptionPane.showConfirmDialog(null,"注册成功！","成功",JOptionPane.INFORMATION_MESSAGE);
//                    }
//                    else
//                    {
//                        JOptionPane.showMessageDialog(null,"加入数据库失败","失败",JOptionPane.ERROR_MESSAGE);
//                    }
                    break;

                case "修改密码":
                    option=3;
                    //打开更新窗口
                    updateWindow();
                    //NetAccount.Send("1",new String(String.valueOf(UserName.getText().length())),UserName.getText(),new String(PassWord.getPassword()));



                    break;
                case "注销":
                    if(Objects.equals(UserName.getText(), "")||PassWord.getPassword().length==0 )
                    {
                        JOptionPane.showMessageDialog(null,"用户名或密码不能为空，请重新输入","输入错误",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    option=4;
                    NetAccount.Send("4",new String(String.valueOf(UserName.getText().length())),UserName.getText(),new String(PassWord.getPassword()));

//                    if(!database.querySQL(UserName.getText(),new String(PassWord.getPassword())))
//                    {
//                        JOptionPane.showMessageDialog(null,"要删除的用户不存在","错误",JOptionPane.WARNING_MESSAGE);
//                        break;
//                    }
//                    if(database.deleteSQL(UserName.getText(),new String(PassWord.getPassword())))
//                    {
//                        JOptionPane.showMessageDialog(null,"注销成功","成功",JOptionPane.INFORMATION_MESSAGE);
//                    }
//                    else
//                    {
//                        JOptionPane.showMessageDialog(null,"注销失败","失败",JOptionPane.ERROR_MESSAGE);
//                    }



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
            switch (name)
            {
                case "确定":
                    //if(database.insertSQL())
                    if(Objects.equals(UserNameArea.getText(), "")||PassWordArea.getPassword().length==0 )
                    {
                        JOptionPane.showMessageDialog(null,"用户名或密码不能为空，请重新输入","输入错误",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
//                    if(!database.querySQL(UserNameArea.getText(),new String(PassWordArea.getPassword())))
//                    {
//                        JOptionPane.showMessageDialog(null,"用户名或密码错误","错误",JOptionPane.WARNING_MESSAGE);
//                        break;
//                    }
                    if(!new String(newpassword1.getPassword()).equals(new String(newpassword2.getPassword())))
                    {
                        JOptionPane.showMessageDialog(null,"两次输入的新密码不一致","错误",JOptionPane.WARNING_MESSAGE);
                        break;
                    }
                    option=5;
                    NetAccount.Send("3",new String(String.valueOf(UserNameArea.getText().length())),UserNameArea.getText(),new String(newpassword1.getPassword()));

//                    if(database.updateSQL(UserNameArea.getText(),new String(PassWordArea.getPassword()),new String(newpassword1.getPassword())))
//                    {
//                        JOptionPane.showMessageDialog(null,"密码修改成功","成功",JOptionPane.INFORMATION_MESSAGE);
//                        updateFrame.dispose();
//                        loginWindow.setVisible(true);
//                    }
//                    else
//                    {
//                        JOptionPane.showMessageDialog(null,"修改数据失败","错误",JOptionPane.WARNING_MESSAGE);
//                    }


                    break;

                case "取消":
                    updateFrame.dispose();
                    loginWindow.setVisible(true);
                    break;

            }

        }
    }

}
