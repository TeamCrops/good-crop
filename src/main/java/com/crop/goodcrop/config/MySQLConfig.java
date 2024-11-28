//package com.crop.goodcrop.config;
//
//import jakarta.persistence.EntityManagerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
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
//        basePackages = {
//                "com.crop.goodcrop.domain.like.repository",
//                "com.crop.goodcrop.domain.member.repository",
//                "com.crop.goodcrop.domain.product.repository",
//                "com.crop.goodcrop.domain.review.repository",
//                "com.crop.goodcrop.domain.trend.repository.mysql"
//        }, // MySQL 전용 Repository 패키지
//        entityManagerFactoryRef = "mysqlEntityManagerFactory",
//        transactionManagerRef = "mysqlTransactionManager"
//)
//public class MySQLConfig {
//    @Bean(name = "mysqlDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.mysql")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "mysqlEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("mysqlDataSource") DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages(
//                        "com.crop.goodcrop.domain.common.entity",
//                        "com.crop.goodcrop.domain.like.entity",
//                        "com.crop.goodcrop.domain.member.entity",
//                        "com.crop.goodcrop.domain.product.entity",
//                        "com.crop.goodcrop.domain.review.entity",
//                        "com.crop.goodcrop.domain.trend.entity.mysql"
//                ) // MySQL 전용 Entity 패키지
//                .persistenceUnit("mysql")
//                .build();
//    }
//
//    @Bean(name = "mysqlTransactionManager")
//    public PlatformTransactionManager transactionManager(
//            @Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//}
