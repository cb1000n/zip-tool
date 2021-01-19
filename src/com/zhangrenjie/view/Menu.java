package com.zhangrenjie.view;

import com.zhangrenjie.component.AboutDialog;
import com.zhangrenjie.component.CompressDialog;
import com.zhangrenjie.component.DecompressionDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JButton compress;
    private JButton decompression;
    private JButton about;
    private JButton exit;


    private final int FRAME_WIDTH = 230;
    private final int FRAME_HEIGHT = 333;
    private final int BUTTON_START_X = 40;
    private final int BUTTON_START_Y = 35;
    // JPanel空间比设置少了 16
    private final int BUTTON_WIDTH = 134;
    private final int BUTTON_HEIGHT = 40;
    private final int BUTTON_MARGIN_TOP = 25;

    public Menu() {

        initFrame();
        // 添加面板
        initPanel();
        initButton();


        // 设置界面可见
        frame.setVisible(true);
    }

    private void initFrame(){
        frame = new JFrame("ZipTool");
        frame.setResizable(false);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
    }

    private void initPanel() {
        panel = new JPanel();
        panel.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        panel.setLayout(null);
        frame.add(panel);
    }

    private void initButton() {

        // 创建登录按钮
        compress = new JButton("压缩");
        decompression = new JButton("解压");
        about = new JButton("关于");
        exit = new JButton("退出");

        compress.setBounds(BUTTON_START_X, BUTTON_START_Y , BUTTON_WIDTH, BUTTON_HEIGHT);
        decompression.setBounds(BUTTON_START_X, BUTTON_START_Y + BUTTON_MARGIN_TOP + BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
        about.setBounds(BUTTON_START_X, BUTTON_START_Y + BUTTON_MARGIN_TOP*2 + BUTTON_HEIGHT*2, BUTTON_WIDTH, BUTTON_HEIGHT);
        exit.setBounds(BUTTON_START_X, BUTTON_START_Y + BUTTON_MARGIN_TOP*3 + BUTTON_HEIGHT*3, BUTTON_WIDTH, BUTTON_HEIGHT);

        compress.addActionListener(this);
        decompression.addActionListener(this);
        about.addActionListener(this);
        exit.addActionListener(this);

        panel.add(compress);
        panel.add(decompression);
        panel.add(about);
        panel.add(exit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button == compress) {
            new CompressDialog();
        } else if (button == decompression) {
            new DecompressionDialog();
        } else if (button == about) {
            new AboutDialog();
        } else if (button == exit) {
            System.exit(0);
        }
    }
}
