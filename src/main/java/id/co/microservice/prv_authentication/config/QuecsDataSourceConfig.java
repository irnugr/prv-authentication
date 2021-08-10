package id.co.microservice.prv_authentication.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.slf4j.Logger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ConfigurationProperties(prefix="spring.datasource")
@EnableJpaRepositories(
		entityManagerFactoryRef = "quecsEntityManager",
		basePackages = "id.co.microservice.prv_authentication.repository"
		)
public class QuecsDataSourceConfig extends HikariConfig {
	
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(QuecsDataSourceConfig.class);
	
	@Bean
	public HikariDataSource dataSourceQuecs() {
		try {
			return new HikariDataSource(this);
		} catch (Exception e) {
			LOG.error("Quecs db connection error:", e.getMessage());
			return null;
		}
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean quecsEntityManager(
			@Qualifier("dataSourceQuecs") HikariDataSource dataSourceQuecs) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSourceQuecs);
		em.setPackagesToScan("id.co.microservice.prv_authentication.entity");
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		return em;
	}
	
}
