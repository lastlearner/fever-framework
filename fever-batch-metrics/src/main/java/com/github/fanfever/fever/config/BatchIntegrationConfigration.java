package com.github.fanfever.fever.config;

//@Configuration
public class BatchIntegrationConfigration {

//	@Bean
//	public IntegrationMBeanExporter integrationMbeanExporter(@Value("spring.jmx.default-domain") String domain, MBeanServer mBeanServer) {
//		IntegrationMBeanExporter exporter = new IntegrationMBeanExporter();
//		exporter.setDefaultDomain(domain);
//		exporter.setServer(mBeanServer);
//		return exporter;
//	}

//	@Bean
//	@Primary
//	public BatchMBeanExporter batchMBeanExporter(ObjectNamingStrategy namingStrategy, MBeanServer mBeanServer) {
//		BatchMBeanExporter exporter = new BatchMBeanExporter();
//		// exporter.setRegistrationPolicy(RegistrationPolicy.FAIL_ON_EXISTING);
//		// exporter.setNamingStrategy(namingStrategy);
//		ProxyFactoryBean jobService = new ProxyFactoryBean();
//		jobService.setTargetClass(JobService.class);
//		// jobService.setTargetName("jobService");
//		jobService.setProxyTargetClass(true);
//		exporter.setServer(mBeanServer);
//		exporter.setJobService((JobService) jobService.getObject());
//		exporter.setDefaultDomain("spring.application");
//		return exporter;
//	}
}
