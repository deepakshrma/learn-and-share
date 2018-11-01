package com.dbs.ui.utils;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DBSAvatarViewHelperTest {
    @Test
    public void testLetter() {
        assertEquals(DBSAvatarViewHelper.get('0'), 0xffFF88BF);
        assertEquals(DBSAvatarViewHelper.get('1'), 0xffAFB1FF);
        assertEquals(DBSAvatarViewHelper.get('2'), 0xff5AC1FF);
        assertEquals(DBSAvatarViewHelper.get('3'), 0xffFF9A61);
        assertEquals(DBSAvatarViewHelper.get('4'), 0xffFF8384);
        assertEquals(DBSAvatarViewHelper.get('5'), 0xffFF7E82);
        assertEquals(DBSAvatarViewHelper.get('6'), 0xffBBAAFF);
        assertEquals(DBSAvatarViewHelper.get('7'), 0xff7DBDFF);
        assertEquals(DBSAvatarViewHelper.get('8'), 0xffFFA964);
        assertEquals(DBSAvatarViewHelper.get('9'), 0xffFF918A);


        assertEquals(DBSAvatarViewHelper.get('A'), 0xffFF9DA2);
        assertEquals(DBSAvatarViewHelper.get('B'), 0xffFFA480);
        assertEquals(DBSAvatarViewHelper.get('C'), 0xffFFC72B);
        assertEquals(DBSAvatarViewHelper.get('D'), 0xffA8B5FF);
        assertEquals(DBSAvatarViewHelper.get('E'), 0xffFF92DD);
        assertEquals(DBSAvatarViewHelper.get('F'), 0xffFF9CA2);
        assertEquals(DBSAvatarViewHelper.get('G'), 0xffFFA289);
        assertEquals(DBSAvatarViewHelper.get('H'), 0xffFFB23D);


        assertEquals(DBSAvatarViewHelper.get('I'), 0xffA3B8FF);
        assertEquals(DBSAvatarViewHelper.get('J'), 0xffF085FF);
        assertEquals(DBSAvatarViewHelper.get('K'), 0xffFF8F9B);
        assertEquals(DBSAvatarViewHelper.get('L'), 0xffFF9482);
        assertEquals(DBSAvatarViewHelper.get('M'), 0xffFFC471);
        assertEquals(DBSAvatarViewHelper.get('N'), 0xff97BEFF);
        assertEquals(DBSAvatarViewHelper.get('O'), 0xffD0A2FF);
        assertEquals(DBSAvatarViewHelper.get('P'), 0xffFF8DA2);
        assertEquals(DBSAvatarViewHelper.get('Q'), 0xffFF918A);
        assertEquals(DBSAvatarViewHelper.get('R'), 0xffFFA964);
        assertEquals(DBSAvatarViewHelper.get('S'), 0xff7DBDFF);
        assertEquals(DBSAvatarViewHelper.get('T'), 0xffBBAAFF);
        assertEquals(DBSAvatarViewHelper.get('U'), 0xffFF7EA2);

        assertEquals(DBSAvatarViewHelper.get('V'), 0xffFF8384);


        assertEquals(DBSAvatarViewHelper.get('W'), 0xffFF9A61);
        assertEquals(DBSAvatarViewHelper.get('X'), 0xff5AC1FF);
        assertEquals(DBSAvatarViewHelper.get('Y'), 0xffAFB1FF);
        assertEquals(DBSAvatarViewHelper.get('Z'), 0xffFF88BF);

        assertEquals(DBSAvatarViewHelper.get('.'), 0xff000000);

    }


    @Test
    public void getCharsTest() {
        assertArrayEquals(DBSAvatarViewHelper.getChars("Tran Vuong Kha"), new char[]{
                'T', 'K'
        });


        char[] test1 = new char[2];
        test1[0] = 'T';
        assertArrayEquals(DBSAvatarViewHelper.getChars("tran "), test1);


        char[] test2 = DBSAvatarViewHelper.getChars("");
        assertEquals(test2.length, 0);


        char[] test3 = DBSAvatarViewHelper.getChars("a");
        assertEquals(test3.length, 2);
        assertEquals(test3[1], 0);


    }
}
