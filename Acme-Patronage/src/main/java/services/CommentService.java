
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Comment;

@Service
@Transactional
public class CommentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CommentRepository	commentRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: Comment - create
	public Comment create() {
		final Comment result = null;

		return result;
	}

	public Collection<Comment> findAll() {
		Collection<Comment> result = null;
		result = this.commentRepository.findAll();
		return result;
	}

	public Comment findOne(final int commentId) {
		Comment result = null;
		result = this.commentRepository.findOne(commentId);
		return result;
	}

	public Comment save(final Comment comment) {
		Assert.notNull(comment);
		Comment result = null;
		result = this.commentRepository.save(comment);
		return result;
	}

	// TODO: Comment - saveFromCreate
	public Comment saveFromCreate() {
		final Comment result = null;

		return result;
	}

	// TODO: Comment - saveFromEdit
	public Comment saveFromEdit() {
		final Comment result = null;

		return result;
	}

	public void flush() {
		this.commentRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
