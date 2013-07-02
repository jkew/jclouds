package org.jclouds.azureblob.blobstore.strategy;

import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import com.google.inject.Inject;
import org.jclouds.azureblob.AzureBlobClient;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.reference.BlobStoreConstants;
import org.jclouds.io.Payload;
import org.jclouds.io.PayloadSlicer;
import org.jclouds.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Named;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Decomposes a blob into blocks for upload and assembly through PutBlock and PutBlockList
 */
public class AzureBlobBlockUploadStrategy implements MultipartUploadStrategy {
    @Resource
    @Named(BlobStoreConstants.BLOBSTORE_LOGGER)
    private Logger logger = Logger.NULL;

    private final AzureBlobClient client;
    private final PayloadSlicer slicer;

    @Inject
    public AzureBlobBlockUploadStrategy(AzureBlobClient client, PayloadSlicer slicer) {
        this.client = checkNotNull(client, "client");
        this.slicer = checkNotNull(slicer, "slicer");
    }

    @Override
    public void execute(String container, Blob blob) {
        String blobName = blob.getMetadata().getName();
        Payload payload = blob.getPayload();
        Long length = payload.getContentMetadata().getContentLength();
        checkNotNull(length,
                "please invoke payload.getContentMetadata().setContentLength(length) prior to azure block upload");
        Long offset = 0L;
        List<String> blockIds = new LinkedList<String>();
        while (offset < length) {
            offset += MultipartUploadStrategy.MAX_BLOCK_SIZE;
            Payload block = slicer.slice(payload, offset, MultipartUploadStrategy.MAX_BLOCK_SIZE);
            String blockName = blobName + "-" + offset + "-" + new SecureRandom().nextInt();
            byte blockIdBytes[] = Hashing.md5().hashBytes(blockName.getBytes()).asBytes();
            String blockId = BaseEncoding.base64().encode(blockIdBytes);
            blockIds.add(blockId);
            client.putBlock(container, blobName, blockId, block);
        }
        client.putBlockList(container, blobName, blockIds);
    }
}
