package org.jclouds.azureblob.domain;

import java.util.List;

/**
 * Typed response from Get Blob Block List
 */
public interface ListBlobBlocksResponse {
    List<BlobBlockProperties> getBlocks();
}
