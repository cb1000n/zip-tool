package com.zhangrenjie.component;

import com.zhangrenjie.utils.ZipUtil;

import javax.swing.*;

import static javax.swing.JOptionPane.PLAIN_MESSAGE;

public class DecompressionDialog {
    public DecompressionDialog() {
            String decompressionFilePath = JOptionPane.showInputDialog(null, "请输入压缩文件地址，例：D:\\Test.zip", "解压", PLAIN_MESSAGE);
        if (decompressionFilePath != null && decompressionFilePath.length() > 0){
            if (new ZipUtil().decompress(decompressionFilePath)) {
                JOptionPane.showMessageDialog(null,"解压完成");
            }
        }
    }
}
