package by.bntu.fitr.povt.vasilkou.bntu_shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir:${user.home}}" + "\\Pictures\\Saved Pictures")
    private String UPLOAD_PATH;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:" + UPLOAD_PATH + File.separator);
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
