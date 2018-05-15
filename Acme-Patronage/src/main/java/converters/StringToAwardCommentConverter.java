
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AwardCommentRepository;
import domain.AwardComment;

@Component
@Transactional
public class StringToAwardCommentConverter implements Converter<String, AwardComment> {

	@Autowired
	AwardCommentRepository	awardCommentRepository;


	@Override
	public AwardComment convert(final String text) {
		AwardComment result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.awardCommentRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
