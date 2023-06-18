package ist.challenge.agil_syofian_hidayat.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class InitConfig extends AsyncConfigurerSupport implements WebMvcConfigurer {

    private final int TIMEOUT = (int) TimeUnit.SECONDS.toMillis(62);
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        var requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);
        requestFactory.setConnectTimeout(TIMEOUT);
        requestFactory.setReadTimeout(TIMEOUT);
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(requestFactory);
        var restTemplate = restTemplateBuilder.build();
        restTemplate.setRequestFactory(factory);
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

}
