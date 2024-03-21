package com.baeldung.jwt.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SimplePlatformProviderOnShutdownTest {
    private SimplePlatformProvider simplePlatformProvider;
    private Runnable shutdownHook1;
    private Runnable shutdownHook2;
    private Runnable shutdownHook3;

    @Before
    public void setUp() {
        simplePlatformProvider = new SimplePlatformProvider();
        shutdownHook1 = Mockito.mock(Runnable.class);
        shutdownHook2 = Mockito.mock(Runnable.class);
        shutdownHook3 = Mockito.mock(Runnable.class);
    }

    @Test
    public void testProvidedShutdownHook() {
        simplePlatformProvider.onShutdown(shutdownHook1);
        assertEquals(shutdownHook1, simplePlatformProvider.shutdownHook);
    }

    @Test(expected = NullPointerException.class)
    public void testNullShutdownHook() {
        simplePlatformProvider.onShutdown(null);
    }

    @Test
    public void testMutabilityOfShutdownHook() {
        simplePlatformProvider.onShutdown(shutdownHook1);
        Runnable originalShutdownHook = shutdownHook1;
        shutdownHook1 = Mockito.mock(Runnable.class); // simulate mutation of original shutdownHook
        assertEquals(originalShutdownHook, simplePlatformProvider.shutdownHook); // ensure shutdownHook inside the object remains unchanged
    }

    @Test
    public void testMultipleInvocations() {
        simplePlatformProvider.onShutdown(shutdownHook1);
        simplePlatformProvider.onShutdown(shutdownHook2);
        simplePlatformProvider.onShutdown(shutdownHook3);
        assertEquals(shutdownHook3, simplePlatformProvider.shutdownHook);
    }
}
