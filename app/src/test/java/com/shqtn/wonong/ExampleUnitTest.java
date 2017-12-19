package com.shqtn.wonong;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testStringFormat(){
        long t = 222222;
        String  account = "test";
        long f = t << account.length();
        String format = String.format("%s_%d_%d",account,t,f);
        System.out.print(format);
    }

    @Test
    public void testSplit(){
        String a = "aaaa/ffff/ggg/ggg/dd.jpg";

        String[] split = a.split("/");
        for (String s : split) {
            System.out.println(s);
        }
    }
}