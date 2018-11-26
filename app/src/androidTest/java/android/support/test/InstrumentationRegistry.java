package android.support.test;

import android.content.Context;

/**
 * Workaround to use mockito with androidx test libs
 *
 * @see <a href="https://github.com/mockito/mockito/issues/1472">Github issue</a>
 */
@SuppressWarnings("unused")
public class InstrumentationRegistry {
    public static Context getTargetContext() {
        return androidx.test.InstrumentationRegistry.getTargetContext();
    }
}