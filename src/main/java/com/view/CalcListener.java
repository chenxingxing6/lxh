package com.view;

import com.spider.ImageHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: lanxinghua
 * Date: 2019/11/12 14:51
 * Desc:
 */
public class CalcListener implements ActionListener {
    private JTextField jtf;
    private JButton button;
    private JTextArea jta;

    public CalcListener(JTextField jtf, JButton button, JTextArea jta) {
        this.jtf = jtf;
        this.button = button;
        this.jta = jta;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            jta.setText("开始处理.........");
        String command = e.getActionCommand();
        String path = jtf.getText();
            if (path == null || path == ""){
                JOptionPane.showMessageDialog(null, "请输入网页地址", "提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isHttpUrl(path)){
                JOptionPane.showMessageDialog(null, "请输入正确的网页地址", "提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!path.contains("http")){
                JOptionPane.showMessageDialog(null, "请输入正确的网页地址", "提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            String userName = System.getProperties().getProperty("user.name");
            if (userName == null){
                JOptionPane.showMessageDialog(null, "处理失败,用户名为空", "提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            String downPath = "/Users/"+userName+"/Downloads/images";
            File file = new File(downPath);
            if (file.exists()){
                file.delete();
            }
            button.setText("正在处理，请稍后....");
            int num = ImageHandler.handler(path, downPath, jta);
            JOptionPane.showMessageDialog(null, "处理成功,总共"+ num +"张图片");
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, "处理失败" + ex.getMessage());
            return;
        }finally {
            button.setText("开始处理");
        }
    }

    public static boolean isHttpUrl(String urls) {
        boolean isurl = false;
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式
        Pattern pat = Pattern.compile(regex.trim());//对比
        Matcher mat = pat.matcher(urls.trim());
        isurl = mat.matches();//判断是否匹配
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
