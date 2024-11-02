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
     * 识别
     */
    public ArrayList<TextBlock> getTextBlockList(){
        OcrResult ocrResult = inferenceEngine.runOcr(Constant.PICTUREPATH.getPath(), paramConfig);
        return ocrResult.getTextBlocks();
    }

    /**
     * 是否存在这个字符串
     */
    public TextBlock getTextBlock(String str){
        ArrayList<TextBlock> textBlockList = this.getTextBlockList();

        for(TextBlock textBlock : textBlockList){
            if(str.equals(textBlock.getText())){
                return textBlock;
            }
        }

        return null;
    }
}
