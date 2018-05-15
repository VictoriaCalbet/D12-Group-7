
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Patronage;

@Component
@Transactional
public class PatronageToStringConverter implements Converter<Patronage, String> {

	@Override
	public String convert(final Patronage patronage) {
		String result;

		if (patronage == null)
			result = null;
		else
			result = String.valueOf(patronage.getId());

		return result;
	}
}
