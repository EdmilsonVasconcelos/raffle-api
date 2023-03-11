package br.com.vsc.raffle.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfiguration {

    @Bean
    public Cloudinary configure() {
        Map config = new HashMap();
        config.put("cloud_name", "deur7tqz5");
        config.put("api_key", "356474588687782");
        config.put("api_secret", "rElhQHuOTYQr_pq8PwkHn6GGTIM");
        return new Cloudinary(config);
    }

}
