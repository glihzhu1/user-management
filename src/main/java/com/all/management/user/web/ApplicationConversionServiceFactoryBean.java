package com.all.management.user.web;

import com.all.management.user.model.Alluser;
import com.all.management.user.model.AlluserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	@Autowired
    AlluserRepository alluserRepository;

	public Converter<Alluser, String> getAlluserToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.all.management.user.model.Alluser, java.lang.String>() {
            public String convert(Alluser alluser) {
                return new StringBuilder().append(alluser.getLoginId()).append(' ').append(alluser.getPasswordHash()).append(' ').append(alluser.getEmail()).append(' ').append(alluser.getLastUpdateDate()).toString();
            }
        };
    }

	public Converter<Integer, Alluser> getIdToAlluserConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, com.all.management.user.model.Alluser>() {
            public com.all.management.user.model.Alluser convert(java.lang.Integer id) {
                return alluserRepository.findOne(id);
            }
        };
    }

	public Converter<String, Alluser> getStringToAlluserConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.all.management.user.model.Alluser>() {
            public com.all.management.user.model.Alluser convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), Alluser.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getAlluserToStringConverter());
        registry.addConverter(getIdToAlluserConverter());
        registry.addConverter(getStringToAlluserConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
