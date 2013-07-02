package org.jclouds.azureblob.blobstore.strategy;

import org.jclouds.blobstore.domain.Blob;

/**
 * @see <a href="http://msdn.microsoft.com/en-us/library/windowsazure/dd135726.aspx">Azure Put Block Documentation</a>
 *
 * @author John Victor Kew
 */
public interface MultipartUploadStrategy {
    /* Maximum number of blocks per upload */
    public static final int MAX_NUMBER_OF_BLOCKS = 50000;

    /* Maximum block size */
    public static final long MAX_BLOCK_SIZE = 4194304L;

    void execute(String container, Blob blob);
}
