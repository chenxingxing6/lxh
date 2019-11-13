package com.view;

import javax.swing.*;
import java.awt.*;

/**
 * User: lanxinghua
 * Date: 2019/11/12 14:50
 * Desc:
 */

public class Calc extends JFrame {
    private void initFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setSize(500, 450);
        this.setTitle("批量获取图片工具");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        // JTextField调用方法时传递
        JTextField jtf = this.readPath(0, 0);
        this.writePath(0, 30);
        this.bottom(jtf);
        this.setResizable(false);
        this.setVisible(true);
    }


    private JTextField readPath(int x, int y){
        JPanel topPanel = new JPanel();
        topPanel.setBounds(x,y,500,50);
        JTextField jtf = new JTextField(30);
        // 设置文件右边往左边输出
        jtf.setHorizontalAlignment(JTextField.LEFT);
        jtf.setForeground(Color.red);
        jtf.setFont(new Font("SansSerif",Font.PLAIN,16));
        jtf.setEditable(true);
        jtf.setText("请输入网页地址...");
        topPanel.add(jtf);
        this.add(topPanel);
        return jtf;
    }

    private JTextField writePath(int x, int y){
        JPanel topPanel = new JPanel();
        topPanel.setBounds(x,y,500,50);
        JTextField jtf = new JTextField(30);
        // 设置文件右边往左边输出
        jtf.setHorizontalAlignment(JTextField.LEFT);
        jtf.setForeground(Color.red);
        jtf.setFont(new Font("SansSerif",Font.PLAIN,16));
        jtf.setEditable(false);
        jtf.setText("文件下载位置：Downloads/images");
        topPanel.add(jtf);
        this.add(topPanel);
        return jtf;
    }


    private void bottom(JTextField jtf){
        JPanel jPanel = new JPanel();
        jPanel.setBounds(0,120,500,300);
        JTextArea jta = new JTextArea(50, 40);
        jta.setText("运行日志....");
        jta.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(jta);
        jPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(jPanel);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(150,85,200,150);
        bottomPanel.setLayout(new GridLayout(5,7,1,1));
        // 监听器不能重复创建
        JButton button = new JButton("开始处理");
        try{
            CalcListener cl = new CalcListener(jtf, button, jta);
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            button.setHorizontalAlignment(JButton.CENTER);
            button.setFont(new Font("宋体", 0, 20));
            button.setForeground(Color.BLACK);
            bottomPanel.add(button);
            button.addActionListener(cl);
            this.add(bottomPanel);
        }catch (Exception e){
            jta.setText(e.getMessage());
        }

    }


    /**=============================计算器分割线========================================**/
    // 定义自己的画笔，把paint方法的Graphics对象强转为自定义画笔
    private Graphics2D g;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.g = (Graphics2D) g;
        this.g.setStroke(new BasicStroke(3));
        this.g.setColor(new Color(18,18,9));
        this.g.drawLine(0, 95, this.getWidth(), 95);
    }
    public static void main(String[] args) {
        Calc calc = new Calc();
        calc.initFrame();
    }
}
