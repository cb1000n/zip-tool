package com.zhangrenjie.component;

import com.zhangrenjie.utils.ZipUtil;

import javax.swing.*;
import java.awt.*;

public class CompressDialog {
    public CompressDialog() {
        initDialog();
    }
    private void initDialog() {
        JPanel panel = new JPanel();

        JTextField compressFilePathJTF = new JTextField(20);
        JTextField excludeFolderNameJTF = new JTextField(20);
        excludeFolderNameJTF.setText("node_modules");

        panel.setLayout(new GridLayout(0, 1));
        panel.add(new JLabel("压缩文件目录："));
        panel.add(compressFilePathJTF);
        panel.add(new JLabel("排除文件名："));
        panel.add(excludeFolderNameJTF);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "请输入压缩文件目录", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                if (new ZipUtil().toZip(compressFilePathJTF.getText(), excludeFolderNameJTF.getText())) {
                    JOptionPane.showMessageDialog(null,"压缩完成");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
