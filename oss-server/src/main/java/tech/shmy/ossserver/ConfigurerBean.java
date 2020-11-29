package tech.shmy.ossserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.shmy.ossserver.service.impl.PersistentFileService;
import tech.shmy.ossserver.service.impl.TemporaryFileService;

import java.util.concurrent.TimeUnit;

@Configuration
public class ConfigurerBean implements WebMvcConfigurer {
    @Autowired
    private TemporaryFileService temporaryFileService;
    @Autowired
    private PersistentFileService persistentFileService;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/f/**")
                .addResourceLocations("file://" + temporaryFileService.getDir())
                .addResourceLocations("file://" + persistentFileService.getDir())
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
    }
}
