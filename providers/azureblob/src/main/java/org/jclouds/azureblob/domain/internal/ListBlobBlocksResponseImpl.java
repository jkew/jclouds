package org.jclouds.azureblob.domain.internal;

import org.jclouds.azureblob.domain.BlobBlockProperties;
import org.jclouds.azureblob.domain.ListBlobBlocksResponse;

import java.util.List;

/**
 * Represents the list of blocks which compose a blob
 */
public class ListBlobBlocksResponseImpl implements ListBlobBlocksResponse {
    private final List<BlobBlockProperties> blocks;

    public ListBlobBlocksResponseImpl(List<BlobBlockProperties> blocks) {
        this.blocks = blocks;
    }

    @Override
    public List<BlobBlockProperties> getBlocks() {
        return blocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListBlobBlocksResponseImpl that = (ListBlobBlocksResponseImpl) o;

        if (blocks != null ? !blocks.equals(that.blocks) : that.blocks != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return blocks != null ? blocks.hashCode() : 0;
    }
}
