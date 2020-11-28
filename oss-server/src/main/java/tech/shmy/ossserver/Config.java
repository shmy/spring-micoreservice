package tech.shmy.ossserver;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.shmy.ossserver.service.FileService;

import java.util.concurrent.TimeUnit;

@Configuration
public class Config implements WebMvcConfigurer {
    @Autowired
    private FileService fileService;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        val rootPath = fileService.getRootDir();
        registry.addResourceHandler("/f/**")
                .addResourceLocations("file://" + rootPath )
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
    }
}
