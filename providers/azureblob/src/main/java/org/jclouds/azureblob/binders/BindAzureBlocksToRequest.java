package org.jclouds.azureblob.binders;

import org.jclouds.http.HttpRequest;
import org.jclouds.rest.Binder;

import java.util.List;

/**
 * Binds a list of blocks to a putBlockList request
 *
 * <?xml version="1.0" encoding="utf-8"?>
 * <BlockList>
 *   <Committed>first-base64-encoded-block-id</Committed>
 *   <Uncommitted>second-base64-encoded-block-id</Uncommitted>
 *   <Latest>third-base64-encoded-block-id</Latest>
 *   ...
 * </BlockList>
 */
public class BindAzureBlocksToRequest implements Binder {
    @Override
    public <R extends HttpRequest> R bindToRequest(R request, Object input) {
        List<String> blockIds = (List<String>)input;
        StringBuilder latestIds = new StringBuilder();
        for (String id : blockIds) {
            latestIds.append("<Latest>").append(id).append("</Latest>");
        }
        final String content = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<BlockList>%s</BlockList>", latestIds);
        request.setPayload(content);
        return request;
    }
}
