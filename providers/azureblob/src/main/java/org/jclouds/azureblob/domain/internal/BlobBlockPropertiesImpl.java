package org.jclouds.azureblob.domain.internal;

import org.jclouds.azureblob.domain.BlobBlockProperties;

/**
 * Representation of the blocks which compose a Blob
 */
public class BlobBlockPropertiesImpl implements BlobBlockProperties {
    private final String blockName;
    private final long contentLength;
    private final boolean committed;

    public BlobBlockPropertiesImpl(String blockName, long contentLength, boolean committed) {
        this.blockName = blockName;
        this.contentLength = contentLength;
        this.committed = committed;
    }

    @Override
    public String getBlockName() {
        return blockName;
    }

    @Override
    public boolean isCommmitted() {
        return committed;
    }

    @Override
    public long getContentLength() {
        return contentLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlobBlockPropertiesImpl that = (BlobBlockPropertiesImpl) o;

        if (committed != that.committed) return false;
        if (contentLength != that.contentLength) return false;
        if (blockName != null ? !blockName.equals(that.blockName) : that.blockName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = blockName != null ? blockName.hashCode() : 0;
        result = 31 * result + (int) (contentLength ^ (contentLength >>> 32));
        result = 31 * result + (committed ? 1 : 0);
        return result;
    }
}
