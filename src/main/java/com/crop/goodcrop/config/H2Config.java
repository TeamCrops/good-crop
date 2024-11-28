//package com.crop.goodcrop.config;
//
//import jakarta.persistence.EntityManagerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackages = { // H2 전용 Repository 패키지
//                "com.crop.goodcrop.domain.trend.repository.h2"
//        },
//        entityManagerFactoryRef = "h2EntityManagerFactory",
//        transactionManagerRef = "h2TransactionManager"
//)
//public class H2Config {
//    public static final long MIGRATION_DURATION = 7200000L; // 2시간
//    public static final long MODIFY_DURATION = 30000L; // 5분
//
//    @Primary
//    @Bean(name = "h2DataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.h2")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Primary
//    @Bean(name = "h2EntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("h2DataSource") DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages("com.crop.goodcrop.domain.trend.entity.h2") // H2 전용 Entity 패키지
//                .persistenceUnit("h2")
//                .build();
//    }
//
//    @Primary
//    @Bean(name = "h2TransactionManager")
//    public PlatformTransactionManager transactionManager(
//            @Qualifier("h2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//}
