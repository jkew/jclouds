package org.jclouds.azureblob.predicates.validators;

import com.google.inject.Singleton;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.predicates.Validator;

import java.net.URLDecoder;

/**
 * Validates Block IDs used in Put Block:
 *
 * A valid Base64 string value that identifies the block. Prior to encoding, the string must
 * be less than or equal to 64 bytes in size. For a given blob, the length of the value
 * specified for the blockid parameter must be the same size for each block. Note that the
 * Base64 string must be URL-encoded.
 */
@Singleton
public class BlockIdValidator extends Validator<String> {
    @Override
    public void validate(@Nullable String s) throws IllegalArgumentException {
        if (s.length() > 64)
            throw new IllegalArgumentException("block id:" + s + "; Block Ids must be less than or equal to 64 bytes in size");

    }
}
