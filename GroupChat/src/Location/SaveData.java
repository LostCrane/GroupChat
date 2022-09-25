package Location;

import java.io.*;
import java.util.ArrayList;

public class SaveData {

    File file;
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;


    public SaveData() {
        try {
            file = new File("data\\SaveData.dat");
            bufferedReader = new BufferedReader(new FileReader(file));
            // 参数 true 标识从文件末尾开始写入
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));

        } catch ( IOException e) {
            System.out.println("SaveData Error!");
            e.printStackTrace();
        }
    }


    /**
     * 将发送的消息存入本地文件
     *
     * @param message 将要保存的消息
     */
    public void write(String message) {
        try {
            bufferedWriter.write(message + '\n');
            bufferedWriter.flush();

        } catch ( Exception e) {
            System.out.println("SaveData.write() Error!");
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch ( IOException ce) {
                ce.printStackTrace();
            }
        }
    }


    /**
     * 读取存储在本地文件中的聊天消息
     *
     * @return 本地存储的聊天消息集合
     */
    public ArrayList<String> read() {
        // 本地存储的聊天消息集合
        ArrayList<String> saveMessage = new ArrayList<>();

        String temp;
        try {
            while ((temp = bufferedReader.readLine()) != null) {
                saveMessage.add(temp);
            }

        } catch ( Exception e) {
            System.out.println("SaveData.read() Error!");
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch ( IOException ce) {
                ce.printStackTrace();
            }
        }

        return saveMessage;
    }


    /**
     * 清空本地聊天消息的记录
     */
    public void clearMessage() {
        BufferedWriter bufferedWriterNull = null;
        try {
            bufferedWriterNull = new BufferedWriter(new FileWriter(file));
            // 写入 Null
            bufferedWriterNull.write("");
            bufferedWriterNull.flush();
        } catch (IOException e) {
            System.out.println("SaveData.clearMessage() Error!");
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriterNull != null) {
                    bufferedWriterNull.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
