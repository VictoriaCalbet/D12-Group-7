
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PatronageRepository;
import domain.Patronage;

@Component
@Transactional
public class StringToPatronageConverter implements Converter<String, Patronage> {

	@Autowired
	PatronageRepository	patronageRepository;


	@Override
	public Patronage convert(final String text) {
		Patronage result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.patronageRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
