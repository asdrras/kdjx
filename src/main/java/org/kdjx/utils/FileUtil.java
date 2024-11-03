package org.kdjx.utils;

import org.kdjx.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 删除所有 图片
     */
    public static void closeAllFile(){

        File file = new File(Constant.PICTUREPATH);

        // 检查目录是否存在
        if (!file.exists() || !file.isDirectory()) {
            log.error("目录不存在或不是一个有效的目录: {}", file);
            return;
        }

        File[] files = file.listFiles();

        if (files != null) {
            for(File f : files){
                boolean delete = f.delete();

                if(delete){
                    log.info("删除{}文件成功",f.getName());
                }else {
                    log.error("删除{}文件失败",f.getName());
                }
            }
        }

    }
}
