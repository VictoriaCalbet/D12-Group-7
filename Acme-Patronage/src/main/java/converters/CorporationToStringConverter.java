
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Corporation;

@Component
@Transactional
public class CorporationToStringConverter implements Converter<Corporation, String> {

	@Override
	public String convert(final Corporation corporation) {
		String result;

		if (corporation == null)
			result = null;
		else
			result = String.valueOf(corporation.getId());

		return result;
	}
}
