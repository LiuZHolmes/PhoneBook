package com.example.retr0.phonebook;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
/**
 * Created by mhy on 2018/6/25.
 */

public class PinYinUtils {
    public static String getPinyin(String hanzi){
        StringBuffer sb=new StringBuffer();
        HanyuPinyinOutputFormat format=new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //由于不能直接对多个汉子转换，只能对单个汉子转换
        char[] arr=hanzi.toCharArray();
        for ( int i = 0; i < arr.length; i++ ) {
            if(Character.isWhitespace(arr[i])){//如果是空格
                continue;
            }
            try {
                String[] pinyinArr= PinyinHelper.toHanyuPinyinStringArray(arr[i],format);
                if(pinyinArr!=null)sb.append(pinyinArr[0]);
                else sb.append(arr[i]);
            } catch ( BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination ) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
                //不是正确的汉字
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }
}
