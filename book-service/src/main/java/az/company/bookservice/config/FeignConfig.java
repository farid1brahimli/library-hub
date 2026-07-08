package az.company.bookservice.config;

import az.company.bookservice.client.CustomFeignErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    public ErrorDecoder feignErrorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}
