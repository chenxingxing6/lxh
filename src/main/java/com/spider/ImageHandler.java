package com.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * User: lanxinghua
 * Date: 2019/11/12 15:37
 * Desc:
 */
public class ImageHandler {
    /**
     * 根据网页和编码获取网页内容和源代码
     */
    public static String getHtmlResourceByUrl(String url,String encoding){
        StringBuffer buffer   = new StringBuffer();
        URL urlObj            = null;
        URLConnection uc      = null;
        InputStreamReader in  = null;
        BufferedReader reader = null;
        try {
            // 建立网络连接
            urlObj = new URL(url);
            // 打开网络连接
            uc = urlObj.openConnection();
            // 创建输入流
            in     = new InputStreamReader(uc.getInputStream(),encoding);
            // 创建一个缓冲写入流
            reader = new BufferedReader(in);

            String line = null;
            while ((line = reader.readLine()) != null) {
                // 一行一行追加
                buffer.append(line+"\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer.toString();
    }

    public static int handler(String path, String savePath, JTextArea jta){
        int num = 0;
        String htmlResource = getHtmlResourceByUrl(path,"utf-8");
        Document document = Jsoup.parse(htmlResource);
        Elements elements = document.getElementsByTag("img");
        for (Element element : elements) {
            num ++;
            try {
                String imgSrc = element.attr("src");
                if (!"".equals(imgSrc) && (imgSrc.startsWith("http://") || imgSrc.startsWith("https://"))) {
                    System.out.println("正在下载的图片的地址：" + imgSrc);
                    jta.setText(jta.getText() + "\n" + "正在下载图片：" + imgSrc);
                    String fileName = num + imgSrc.substring(imgSrc.lastIndexOf("/") + 1);
                    download(imgSrc, fileName, savePath);
                    num++;
                }
            }catch (Exception e){
                e.printStackTrace();
                jta.setText(jta.getText() + "\n" + e.getStackTrace().toString());
                continue;
            }
        }
        return num;
    }

    public static void download(String urlString, String filename,String savePath){
        try {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            //设置请求超时为5s
            con.setConnectTimeout(5*1000);
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            File sf=new File(savePath);
            if(!sf.exists()){
                sf.mkdirs();
            }
            OutputStream os = new FileOutputStream(savePath + "/"+filename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
