package com.crmp.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Spring MVC + Hibernate Java-based configuration.
 * Replaces applicationContext.xml and servlet-context.xml.
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.crmp")
public class AppConfig implements WebMvcConfigurer {

    // ──────────────────────────────────────────────
    // View Resolver — maps logical names to JSPs
    // ──────────────────────────────────────────────
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(1);
        return resolver;
    }

    // ──────────────────────────────────────────────
    // Static Resources
    // ──────────────────────────────────────────────
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static/");
    }

    // ──────────────────────────────────────────────
    // DataSource — H2 in-memory (dev profile)
    // ──────────────────────────────────────────────
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:crmpdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        ds.setUsername("sa");
        ds.setPassword("");
        return ds;
    }

    // ──────────────────────────────────────────────
    // Hibernate SessionFactory
    // ──────────────────────────────────────────────
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(dataSource());
        sf.setPackagesToScan("com.crmp.model");
        sf.setHibernateProperties(hibernateProperties());
        return sf;
    }

    private Properties hibernateProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        props.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
        props.setProperty("hibernate.connection.pool_size", "5");
        return props;
    }

    // ──────────────────────────────────────────────
    // Transaction Manager
    // ──────────────────────────────────────────────
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}
