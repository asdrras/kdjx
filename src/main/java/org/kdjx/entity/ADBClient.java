package org.kdjx.entity;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ADBClient {

    private String ip;
    private Integer port;

    public ADBClient() {

    }

    public ADBClient(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * 写入 adb 指令 并读取 响应
     * @param str
     */
    public String write(String str){
        //创建发送端socket对象
        try (Socket socket = new Socket(this.ip, this.port)){
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)),true);
            pw.println(str);

            StringBuilder sb = new StringBuilder();
            String s;
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            while((s = br.readLine()) != null){
                sb.append(s);
            }

            return sb.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭连接
     * @return
     */
    public void close(){

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
