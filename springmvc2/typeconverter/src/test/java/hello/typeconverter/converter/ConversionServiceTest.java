package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

public class ConversionServiceTest {
    @Test
    void conversionService() {
        // 먼저 등록해야함.
        DefaultConversionService defaultConversionService = new DefaultConversionService();
        defaultConversionService.addConverter(new StringToIntegetConverter());
        defaultConversionService.addConverter(new StringToIntegetConverter());
        defaultConversionService.addConverter(new IpPortToStringConverter());
        defaultConversionService.addConverter(new StringToIpPortConverter());

        // 사용
        Assertions.assertThat(defaultConversionService.convert("10", Integer.class)).isEqualTo(10);
        Assertions.assertThat(defaultConversionService.convert(10, String.class)).isEqualTo("10");
        Assertions.assertThat(defaultConversionService.convert("127.0.0.1:8080", IpPort.class)).isEqualTo(new IpPort("127.0.0.1", 8080));
        Assertions.assertThat(defaultConversionService.convert(new IpPort("127.0.0.1", 8080), String.class)).isEqualTo("127.0.0.1:8000");

    }
}
