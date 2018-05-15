
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CorporationRepository;
import domain.Corporation;

@Component
@Transactional
public class StringToCorporationConverter implements Converter<String, Corporation> {

	@Autowired
	CorporationRepository	corporationRepository;


	@Override
	public Corporation convert(final String text) {
		Corporation result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.corporationRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
