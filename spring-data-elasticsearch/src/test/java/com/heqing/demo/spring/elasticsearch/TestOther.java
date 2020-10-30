package com.heqing.demo.spring.elasticsearch;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.junit.Test;

public class TestOther {

    @Test
    public void testPinyin() {
        String str = "贺小白";
        try {
            String mark = PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_MARK);
            System.out.println("mark --> " + mark);

            String number = PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_NUMBER);
            System.out.println("number --> " + number);

            String tone = PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITHOUT_TONE);
            System.out.println("tone --> " + tone);

            String shortPy = PinyinHelper.getShortPinyin(str);
            System.out.println("shortPy --> " + shortPy);
        } catch (PinyinException e) {
           e.printStackTrace();
        }
    }

}
