package Main;

import UI.LoginWindow;

public class Main {

//    public static void main(String[] args) {
//        // TODO 这都是测试用的，没啥用
////        DatabaseAccount database = new DatabaseAccount();
//        DatabaseMassage databaseMassage = new DatabaseMassage();
//
//        databaseMassage.insertSQL("sfsafa", "sagga");
//
//        ArrayList<String> arrayMassage = new ArrayList<>();
//        arrayMassage = databaseMassage.querySQL();
//        for (int i = 0; i < arrayMassage.size(); i++) {
//            System.out.println(arrayMassage.get(i++) + ": " + arrayMassage.get(i++) + "  " + arrayMassage.get(i));
//        }
////        LoginWindow Login = new LoginWindow();
//        // TODO Over
//    }
    public static void main(String[] args)
    {
//        try {
//            Thread.currentThread().sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        new LoginWindow();
        //new NetworkMessage();
    }

}
