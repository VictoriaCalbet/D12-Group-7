
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ProjectComment;

@Component
@Transactional
public class ProjectCommentToStringConverter implements Converter<ProjectComment, String> {

	@Override
	public String convert(final ProjectComment projectComment) {
		String result;

		if (projectComment == null)
			result = null;
		else
			result = String.valueOf(projectComment.getId());

		return result;
	}
}
