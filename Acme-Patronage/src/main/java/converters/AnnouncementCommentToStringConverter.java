
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.AnnouncementComment;

@Component
@Transactional
public class AnnouncementCommentToStringConverter implements Converter<AnnouncementComment, String> {

	@Override
	public String convert(final AnnouncementComment announcementComment) {
		String result;

		if (announcementComment == null)
			result = null;
		else
			result = String.valueOf(announcementComment.getId());

		return result;
	}
}
