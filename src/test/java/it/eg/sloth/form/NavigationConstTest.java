package it.eg.sloth.form;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NavigationConstTest {

    @Test
    public void navStrTest() {
        assertEquals("navigationprefix___aa", NavigationConst.navStr("aa"));
        assertEquals("navigationprefix___aa___bb", NavigationConst.navStr("aa", "bb"));
        assertEquals("navigationprefix___aa___bb___cc", NavigationConst.navStr("aa", "bb", "cc"));
    }

    @Test
    public void hrefTest() {
        assertEquals("page.html?navigationprefix___aa=x", NavigationConst.hrefStr("page.html", "aa"));
        assertEquals("page.html?navigationprefix___aa___bb=x", NavigationConst.hrefStr("page.html", "aa", "bb"));
        assertEquals("page.html?navigationprefix___aa___bb___cc=x", NavigationConst.hrefStr("page.html", "aa", "bb", "cc"));
    }
}
