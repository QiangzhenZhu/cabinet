package com.hzdongcheng.device;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;
import java.nio.channels.ClosedSelectorException;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        baseA b = new B();
        Method[] declaredMethods = b.getClass().getDeclaredMethods();

        assertEquals("com.dcdz.device.test", appContext.getPackageName());
    }

    static class baseA {
        public void getName() {

        }
    }

    static class B extends baseA {
        public void getAge(String name) {

        }
    }

}
