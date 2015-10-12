package com.qinyuan15.lottery.mvc.mail;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SerialKeyUrlAdapterTest {
    private SerialKeyUrlAdapter adapter = new SerialKeyUrlAdapter();

    @Test
    public void testAdapt() throws Exception {
        String test = "test.html";
        assertThat(adapter.adapt(test)).isEqualTo("test.html?");

        test = "test.html?";
        assertThat(adapter.adapt(test)).isEqualTo("test.html?");

        test = "test.html?hello=world";
        assertThat(adapter.adapt(test)).isEqualTo("test.html?hello=world&");

        test = "test.html?hello=world&";
        assertThat(adapter.adapt(test)).isEqualTo("test.html?hello=world&");
    }
}
