
package com.cchcz.blog.exception;

/**
 * @author cchcz
 * @version 1.0

 * @date 2018/4/16 16:26
 * @since 1.0
 */
public class BlogCommentException extends BlogException {
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BlogCommentException() {
        super();
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message
     *         the detail message. The detail message is saved for
     *         later retrieval by the {@link #getMessage()} method.
     */
    public BlogCommentException(String message) {
        super(message);
    }
}
