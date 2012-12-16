/**
 * Provides a wrapper around nio selectable channel drudgery. This package is intended to support only low-level
 * socket IO. It does not assume any on-the wire format, it simply sends and receives bytes.
 * <p>
 * A receiver is notified when data is available. Data may arrive as one or more bytes.
 */
package splumb.net.nio;
