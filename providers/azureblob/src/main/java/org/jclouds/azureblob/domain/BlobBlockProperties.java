package org.jclouds.azureblob.domain;

/**
 * Properties on a specific block within a blob
 */
public interface BlobBlockProperties {
    String getBlockName();
    boolean isCommmitted();
    long getContentLength();
}
