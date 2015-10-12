package com.qinyuan15.lottery.mvc.mail;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SerialKeyMailPlaceholderConverterTest {
    @Test
    public void testConvert() throws Exception {
        String username = "testUsername";
        String serialKeyUrl = "http://localhost:8080/test.php?";
        String serialKey = "helloworld_12345_helloworld";
        SerialKeyMailPlaceholderConverter converter = new SerialKeyMailPlaceholderConverter(
                username, serialKeyUrl, serialKey);

        String content = "Hello, {{user}}, please visit {{url}}";
        String replacedContent = converter.convert(content);
        String expectedContent = "Hello, testUsername, please visit " +
                "<a href='http://localhost:8080/test.php?serial=helloworld_12345_helloworld' " +
                "target='_blank'>http://localhost:8080/test.php?serial=helloworld_12345_helloworld</a>";
        assertThat(replacedContent).isEqualTo(expectedContent);

        content = "Hello, {{user}}, {{user}}";
        replacedContent = converter.convert(content);
        expectedContent = "Hello, testUsername, testUsername";
        assertThat(replacedContent).isEqualTo(expectedContent);

        converter = new SerialKeyMailPlaceholderConverter(username, "test.php", serialKey);
        content = "Hello, {{url}}";
        replacedContent = converter.convert(content);
        expectedContent = "Hello, <a href='test.php?serial=helloworld_12345_helloworld' " +
                "target='_blank'>test.php?serial=helloworld_12345_helloworld</a>";
        assertThat(replacedContent).isEqualTo(expectedContent);
    }
}
