package com.zhangrenjie.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutDialog {
    public AboutDialog() {
        JPanel panel = new JPanel();

        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                URI uri = null;
                try {
                    uri = new URI("https://github.com/cb1000n/zip-tool");
                    Desktop.getDesktop().browse(uri);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "打开浏览器失败，请手动打开访问");
                }
            }
        });

        panel.add(new JLabel("" +
                "<html>" +
                    "使用教程：<a href=\"https://github.com/cb1000n/zip-tool\">https://github.com/cb1000n/zip-tool</a>" +
                    "<br>" +
                    "<br>" +
                    "ps：如果觉得有用的话，希望能点个 Star" +
                "</html>"));
        JOptionPane.showMessageDialog(null, panel,
                "请输入压缩文件目录", JOptionPane.PLAIN_MESSAGE);

//        JPanel jPanel = new JPanel();
//        jPanel.setLayout(null);
//        JLabel jLabel = new JLabel("使用教程：https://github.com/cb1000n/zip-tool\\n\" +\n" +
//                "                \"ps：如果觉得有用的话，希望能点个 Star");
//        jLabel.add(jLabel);
//
//        JOptionPane.showMessageDialog(null,jPanel,"关于",JOptionPane. PLAIN_MESSAGE);
    }
}
