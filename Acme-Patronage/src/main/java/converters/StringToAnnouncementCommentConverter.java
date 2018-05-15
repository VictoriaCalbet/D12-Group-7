
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AnnouncementCommentRepository;

import domain.AnnouncementComment;

@Component
@Transactional
public class StringToAnnouncementCommentConverter implements Converter<String, AnnouncementComment> {

	@Autowired
	AnnouncementCommentRepository	announcementCommentRepository;


	@Override
	public AnnouncementComment convert(final String text) {
		AnnouncementComment result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.announcementCommentRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
