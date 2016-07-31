package ru.arturvasilov.stackexchangeclient.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class TextUtilsTest {

    @Test
    public void testNullStringIsEmpty() throws Exception {
        boolean empty = TextUtils.isEmpty(null);
        //noinspection ConstantConditions
        assertTrue(empty);
    }

    @Test
    public void testEmptyString() throws Exception {
        boolean empty = TextUtils.isEmpty("");
        assertTrue(empty);
    }

    @Test
    public void testNotEmptyString1() throws Exception {
        boolean empty = TextUtils.isEmpty("s");
        assertFalse(empty);
    }

    @Test
    public void testNotEmptyString2() throws Exception {
        boolean empty = TextUtils.isEmpty("safgbgnsvskvbs;o;wl dcol ibsvos;cj,sp;csvbwfsl");
        assertFalse(empty);
    }

    @Test
    public void testNotEmptyString3() throws Exception {
        boolean empty = TextUtils.isEmpty(";hvsj'c,;hmvsj,k..ca'j;dglvsbnqufrypnny79o" +
                "'pacha,a,ck'.vfpvvs'fvj,fwgw,evs'vsv.s'[.s.s]AOZ*^906jakz,-[Kk00-K-9-00" +
                "zax.axa/[xxa]");
        assertFalse(empty);
    }

    @Test
    public void testNullStringsEquals() throws Exception {
        boolean equals = TextUtils.equals(null, null);
        assertTrue(equals);
    }

    @Test
    public void testSameStringEquals() throws Exception {
        String s = "aaa";
        boolean equals = TextUtils.equals(s, s);
        assertTrue(equals);
    }

    @Test
    public void testEmptyStringsEquals() throws Exception {
        boolean equals = TextUtils.equals("", "");
        assertTrue(equals);
    }

    @Test
    public void testFirstStringNull() throws Exception {
        String s = "xyz";
        boolean equals = TextUtils.equals(null, s);
        assertFalse(equals);
    }

    @Test
    public void testSecondStringNull() throws Exception {
        String s = "zyx";
        boolean equals = TextUtils.equals(s, null);
        assertFalse(equals);
    }

    @Test
    public void testDifferentLengths() throws Exception {
        String first = "9pda";
        String second = "0ca";
        boolean equals = TextUtils.equals(first, second);
        assertFalse(equals);
    }

    @Test
    public void testDifferentSymbols() throws Exception {
        String first = "arozaupalanalapuazora";
        String second = "urozaupalanalapuazoru";
        boolean equals = TextUtils.equals(first, second);
        assertFalse(equals);
    }

    @Test
    public void testEqualsStrings() throws Exception {
        String first = "Ab0ocam";
        String second = "Ab0ocam";
        boolean equals = TextUtils.equals(first, second);
        assertTrue(equals);
    }

    @Test
    public void testStringBuilderDifferentLengths() throws Exception {
        StringBuilder first = new StringBuilder("9pda");
        StringBuilder second = new StringBuilder("0ca");
        boolean equals = TextUtils.equals(first, second);
        assertFalse(equals);
    }

    @Test
    public void testStringBuilderDifferentSymbols() throws Exception {
        StringBuilder first = new StringBuilder("arozaupalanalapuazora");
        StringBuilder second = new StringBuilder("urozaupalanalapuazoru");
        boolean equals = TextUtils.equals(first, second);
        assertFalse(equals);
    }

    @Test
    public void testStringBuilderEqualsStrings() throws Exception {
        StringBuilder first = new StringBuilder("Ab0ocam");
        StringBuilder second = new StringBuilder("Ab0ocam");
        boolean equals = TextUtils.equals(first, second);
        assertTrue(equals);
    }
}
