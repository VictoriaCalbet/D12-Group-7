
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.AwardComment;

@Component
@Transactional
public class AwardCommentToStringConverter implements Converter<AwardComment, String> {

	@Override
	public String convert(final AwardComment awardComment) {
		String result;

		if (awardComment == null)
			result = null;
		else
			result = String.valueOf(awardComment.getId());

		return result;
	}

}
