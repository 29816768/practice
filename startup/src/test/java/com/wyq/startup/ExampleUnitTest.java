package com.wyq.startup;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        System.out.print("value=== \n"+getvalue(2) );
    }

    private int getvalue(int i) {
        int resule = 0;
        switch (i){
            case 1:
                resule = resule+ i ;
            case 2:
                resule = resule+ i*2 ;
            case 3:
                resule = resule+ i*3 ;
        }
        return resule;
    }
}