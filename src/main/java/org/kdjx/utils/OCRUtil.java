package org.kdjx.utils;

import com.benjaminwan.ocrlibrary.OcrResult;
import com.benjaminwan.ocrlibrary.TextBlock;
import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;
import io.github.mymonstercat.ocr.config.ParamConfig;
import org.kdjx.common.Constant;

import java.util.ArrayList;

/**
 * OCR操作类
 */

public class OCRUtil {

    private ParamConfig paramConfig;
    private InferenceEngine inferenceEngine;

    public OCRUtil() {
        this.paramConfig = ParamConfig.getDefaultConfig();

        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);

        this.inferenceEngine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V4);
    }

    /**
     * 识别 传文件名
     */
    public ArrayList<TextBlock> getTextBlockList(String str){
        OcrResult ocrResult = inferenceEngine.runOcr(Constant.PICTUREPATH + str, paramConfig);
        return ocrResult.getTextBlocks();
    }

    /**
     * 是否存在这个字符串
     */
    public TextBlock getTextBlock(String writing,String str){
        ArrayList<TextBlock> textBlockList = this.getTextBlockList(str);

        for(TextBlock textBlock : textBlockList){
            if(writing.equals(textBlock.getText())){
                return textBlock;
            }
        }

        return null;
    }
}
