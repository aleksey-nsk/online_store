package ru.geekbrains.spring.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

// Класс для настройки DispatcherServlet
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        // Создание фильтра кодировки, который позволит работать с русскими символами
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        // Создание фильтра, который добавляет поддержку HTTP-методов (например таких как PUT),
        // необходимых для REST API
        HiddenHttpMethodFilter httpMethodFilter = new HiddenHttpMethodFilter();

        return new Filter[]{characterEncodingFilter, httpMethodFilter};
    }
}
