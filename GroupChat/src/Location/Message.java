package Location;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Message {

    File file;
    FileInputStream fileInputStream;
    FileOutputStream fileOutputStream;

    ArrayList<String> saveMessage;

    public Message() {
        try {
            file = new File("data\\Message.dat");
            fileInputStream = new FileInputStream(file);
            fileOutputStream = new FileOutputStream(file);

        } catch ( IOException e) {
            System.out.println("文件存储路径出错！");
            if (file != null) {

            }
            e.printStackTrace();
        }
    }


    /**
     * 消息结尾 + \n
     * @param message 将要保存的消息
     */
    public void write(String message) {
        try {
            fileOutputStream.write(message.getBytes(StandardCharsets.UTF_8));
        } catch ( Exception e) {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch ( IOException io_e) {
                io_e.printStackTrace();
            }
        }
    }

// TODO
    public ArrayList<String> read() {
        saveMessage = new ArrayList<>();
        String temp;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        try {
            while ((temp = bufferedReader.readLine()) != null) {
                saveMessage.add(temp);
            }
        } catch ( Exception e) {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch ( IOException io_e) {
                io_e.printStackTrace();
            }
        }

        return saveMessage;
    }


}
