package io.mjimenez

import org.junit.Before
import org.mockito.MockitoAnnotations
import java.util.*

typealias IntegrationTest = UnitTest

/**
 * Base class which inits mocks and sets a default locale, so tests are consistent regardless of the VM's locale.
 */
abstract class UnitTest {
    @Before
    fun baseSetUp() {
        //Set a standard locale to make Threeten and java number formatting reproducible
        Locale.setDefault(Locale.US)

        MockitoAnnotations.initMocks(this)
    }
}