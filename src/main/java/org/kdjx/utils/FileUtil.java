package org.kdjx.utils;

import org.kdjx.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;

public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 删除所有 图片
     */
    public static void closeAllFile() {

        File file = new File(Constant.PICTUREPATH);

        // 检查目录是否存在
        if (!file.exists() || !file.isDirectory()) {
            log.error("目录不存在或不是一个有效的目录: {}", file);
            return;
        }

        File[] files = file.listFiles();

        if (files != null) {

            // 当 截图 的长度大于 8 删除所有照片
            if (files.length >= 8) {
                for (File f : files) {

                    String[] name = f.getName().split("\\.");

                    if ("png".equals(name[1])) {
                        break;
                    }

                    boolean delete = f.delete();

                    if (delete) {
                        log.info("删除{}文件成功", f.getName());
                    } else {
                        log.error("删除{}文件失败", f.getName());
                    }
                }
            }

        }

    }

    /**
     * 图片裁剪
     * @param x 起始点的X坐标
     * @param y 起始点的Y坐标
     * @param width 裁剪区域的宽度
     * @param height 裁剪区域的高度
     */
    public static void CropImage(BufferedImage image, int x, int y, int width, int height) {
        BufferedImage croppedImage = image.getSubimage(x, y, width, height);
    }
}
