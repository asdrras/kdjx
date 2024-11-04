package org.kdjx.utils;

import com.benjaminwan.ocrlibrary.OcrResult;
import com.benjaminwan.ocrlibrary.Point;
import com.benjaminwan.ocrlibrary.TextBlock;
import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;
import io.github.mymonstercat.ocr.config.ParamConfig;
import org.kdjx.common.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * OCR操作类
 */

public class OCRUtil {

    private static final ParamConfig paramConfig = ParamConfig.getDefaultConfig();
    private static final InferenceEngine inferenceEngine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V4);;

    static {
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
    }

    /**
     * 识别
     * @param str 文件名
     */
    public static ArrayList<TextBlock> getTextBlockList(String str){
        OcrResult ocrResult = inferenceEngine.runOcr(Constant.PICTUREPATH + str, paramConfig);
        return ocrResult.getTextBlocks();
    }

    /**
     * 是否存在 字符串
     * @param writing 待查找的字符串
     * @param str 文件名
     */
    public static TextBlock getTextBlock(String str, String writing){
        ArrayList<TextBlock> textBlockList = getTextBlockList(str);

        for(TextBlock textBlock : textBlockList){
            String text = textBlock.getText();
            if(text.contains(writing)){
                return textBlock;
            }
        }
        return null;
    }

    /**
     * 是否存在 字符串数组
     * @param writing 待查找的字符串数组
     * @param str 文件名
     */
    public static TextBlock getTextBlock(String str, String... writing){
        ArrayList<TextBlock> textBlockList = getTextBlockList(str);
        ArrayList<TextBlock> res = new ArrayList<>();

        ArrayList<String> stringArrayList = new ArrayList<>(List.of(writing));

        int count = 0;

        for(TextBlock textBlock : textBlockList){
            String text = textBlock.getText();
            if(stringArrayList.contains(text)){
                count++;
                res.add(textBlock);
            }
        }

        if(count != 0){
            return res.get(res.size() / 2);
        }

        return null;
    }

    /**
     * 获取 识别完的 Point
     * @return
     */

    public static Point getPoint(String name ,String... writing){
        TextBlock textBlock = getTextBlock(name,writing);

        if (textBlock != null) {
            ArrayList<Point> boxPoint = textBlock.getBoxPoint();
            return boxPoint.get(boxPoint.size() / 2);
        }
        return null;
    }
}
