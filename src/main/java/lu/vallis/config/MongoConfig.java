package lu.vallis.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfig {

	@Bean
	MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {

		return new MongoTransactionManager(dbFactory);
	}

	@Bean
	public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory databaseFactory, MongoMappingContext context, BeanFactory beanFactory) {
    // This sentence solves the problem
			DbRefResolver dbRefResolver = new DefaultDbRefResolver(databaseFactory);
		MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, context);
		mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
		mongoConverter.setMapKeyDotReplacement("_");
		mongoConverter.afterPropertiesSet();

		//		System.out.println("tutm test");
//		DbRefResolver dbRefResolver = new DefaultDbRefResolver(databaseFactory);
//		MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mappingContext);
//		converter.setCustomConversions(customConversions);
//		converter.setCodecRegistryProvider(databaseFactory);
//
		System.out.println("TEST TAMINHTU");
		context.setFieldNamingStrategy(new UpperCaseWithUnderscoreFieldNamingStrategy());
		return mongoConverter;
	}

//	@Bean
//	public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory databaseFactory, MongoCustomConversions customConversions, MongoMappingContext mappingContext) {
//		System.out.println("tutm test");
//		DbRefResolver dbRefResolver = new DefaultDbRefResolver(databaseFactory);
//		MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mappingContext);
//		converter.setCustomConversions(customConversions);
//		converter.setCodecRegistryProvider(databaseFactory);
//
//		mappingContext.setFieldNamingStrategy(new UpperCaseWithUnderscoreFieldNamingStrategy());
//		return converter;
//	}

//	@Bean
//	public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory factory,
//													   MongoMappingContext context, BeanFactory beanFactory) {
//		DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
//		MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver,
//				context);
//
//		// remove _class field
//		mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
//
//		try {
//			mappingConverter
//					.setCustomConversions(beanFactory.getBean(CustomConversions.class));
//		}
//		catch (NoSuchBeanDefinitionException ex) {
//			// Ignore
//		}
//		return mappingConverter;
//	}


//	@Bean
//	public MappingMongoConverter mongoConverter(MongoDatabaseFactory dbFactory, MongoMappingContext mappingContext) throws Exception {
//		System.out.println("taminhtu");
//		DbRefResolver dbRefResolver = new DefaultDbRefResolver(dbFactory);
//		MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
//		//this is my customization
////		mongoConverter.setMapKeyDotReplacement("_");
////		mongoConverter.afterPropertiesSet();
//
//		//		System.out.println("tutm test");
////		DbRefResolver dbRefResolver = new DefaultDbRefResolver(databaseFactory);
////		MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mappingContext);
////		converter.setCustomConversions(customConversions);
////		converter.setCodecRegistryProvider(databaseFactory);
////
//		mappingContext.setFieldNamingStrategy(new UpperCaseWithUnderscoreFieldNamingStrategy());
//		System.out.println("End taminhtu");
////		return converter;
//
//		return mongoConverter;
//	}


}
