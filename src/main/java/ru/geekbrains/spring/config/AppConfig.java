package ru.geekbrains.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@ComponentScan("ru.geekbrains.spring")
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

    // Данный метод добавляет обработчики ресурсов
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**", "/webjars/**") // в webjars статика (стили и т.п.)
                .addResourceLocations("/resources/", "/webjars/");

        // В итоге на все запросы /resources/** будет вызываться не контроллер, созданный разработчиком,
        // а возвращаться указанный в запросе файл (например .css)
    }

    // *************************************************************************************************
    // ОБЪЯВЛЯЕМ БИНЫ SPRING. ОНИ ОТНОСЯТСЯ К ВЕБ-УРОВНЮ ПРИЛОЖЕНИЯ:
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine());
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        return thymeleafViewResolver;
    }
    // *************************************************************************************************

}
