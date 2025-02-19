package com.example.deepseekapi.platform;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author zhengqi
 * @date 2025/2/18 下午5:29
 */
public class AskQuestion {
    private static final String API_URL = "http://localhost:9012/ollama/chat/v3?msg=%s";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入问题：");
            String question = scanner.nextLine();
            if (question.equals("exit")) {
                break;
            }
            String response = askQuestion(question);
            System.out.println("Response: " + response);
        }
    }

    public static String askQuestion(String question) {
        String encodedMsg = URLEncoder.encode(question, StandardCharsets.UTF_8);
        try {
            // 创建URL对象
            URL url = new URL(String.format(API_URL, encodedMsg));

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 获取响应码
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // 如果响应码是200（OK），则读取响应内容
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } else {
                return "Error: " + responseCode;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "抱歉，没有获取到请求结果~~~~";
    }
}
