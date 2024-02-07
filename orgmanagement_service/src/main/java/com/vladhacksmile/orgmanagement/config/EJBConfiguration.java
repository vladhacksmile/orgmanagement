package com.vladhacksmile.orgmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.wildfly.naming.client.WildFlyInitialContextFactory;
import service.EmployeeService;
import service.OrganizationService;
import service.impl.EmployeeServiceImpl;
import service.impl.OrganizationServiceImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@Configuration
public class EJBConfiguration {

    @Bean
    public Context context() throws NamingException {
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        jndiProps.put(Context.PROVIDER_URL, "remote+http://localhost:8081");
        return new InitialContext(jndiProps);
    }

    @Bean
    @DependsOn("context")
    public OrganizationServiceImpl organizationServiceBean(Context context) throws NamingException {
        return (OrganizationServiceImpl) context.lookup(getFullName(OrganizationServiceImpl.class, OrganizationService.class));
    }

    @Bean
    @DependsOn("context")
    public EmployeeServiceImpl employeeServiceBean(Context context) throws NamingException {
        return (EmployeeServiceImpl) context.lookup(getFullName(EmployeeServiceImpl.class, EmployeeService.class));
    }

    private String getFullName(Class<?> classType, Class<?> interfaceType) {
        String moduleName = "ejb:/ejb-service";
        String beanName = classType.getSimpleName();
        String viewClassName = interfaceType.getName();
        return moduleName + "/" + beanName + "!" + viewClassName;
    }
}