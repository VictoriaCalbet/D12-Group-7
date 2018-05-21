
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Award;

@Component
@Transactional
public class AwardToStringConverter implements Converter<Award, String> {

	@Override
	public String convert(final Award award) {
		String result;

		if (award == null)
			result = null;
		else
			result = String.valueOf(award.getId());

		return result;
	}
}
