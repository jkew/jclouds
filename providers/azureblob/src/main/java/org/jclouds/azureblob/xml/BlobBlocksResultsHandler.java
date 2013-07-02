package org.jclouds.azureblob.xml;

import org.jclouds.azureblob.domain.BlobBlockProperties;
import org.jclouds.azureblob.domain.ListBlobBlocksResponse;
import org.jclouds.azureblob.domain.internal.BlobBlockPropertiesImpl;
import org.jclouds.azureblob.domain.internal.ListBlobBlocksResponseImpl;
import org.jclouds.http.functions.ParseSax;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.LinkedList;
import java.util.List;

/**
 * Parses the following document:
 * <?xml version="1.0" encoding="utf-8"?>
 * <BlockList>
 * <CommittedBlocks>
 * <Block>
 * <Name>base64-encoded-block-id</Name>
 * <Size>size-in-bytes</Size>
 * </Block>
 * <CommittedBlocks>
 * </BlockList>
 */
public class BlobBlocksResultsHandler extends ParseSax.HandlerWithResult<ListBlobBlocksResponse> {

    private StringBuilder currentText = new StringBuilder();
    private boolean inCommitted = false;
    private boolean inBlock = false;
    private boolean inName = false;
    private boolean inSize = false;
    private String blockName;
    private long size;
    private List<BlobBlockProperties> blocks = new LinkedList<BlobBlockProperties>();

    @Override
    public ListBlobBlocksResponse getResult() {
       return new ListBlobBlocksResponseImpl(blocks);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if ("CommittedBlocks".equals(qName)) {
            inCommitted = true;
        } else if ("UncommittedBlocks".equals(qName)) {
            inCommitted = false;
        } else if ("Block".equals(qName)) {
            inBlock = true;
        } else if ("Name".equals(qName)) {
            inName = true;
        } else if ("Size".equals(qName)) {
            inSize = true;
        }
    }

    public void endElement(String uri, String name, String qName) {
        if ("CommittedBlocks".equals(qName)) {
            inCommitted = false;
        } else if ("UncommittedBlocks".equals(qName)) {
            inCommitted = false;
        } else if ("Block".equals(qName)) {
            BlobBlockProperties block = new BlobBlockPropertiesImpl(blockName, size, inCommitted);
            blocks.add(block);
            inBlock = false;
        } else if ("Name".equals(qName)) {
            blockName = currentText.toString().trim();
            inName = false;
        } else if ("Size".equals(qName)) {
            size = Long.parseLong(currentText.toString().trim());
            inSize = false;
        }
        currentText = new StringBuilder();
    }

    public void characters(char ch[], int start, int length) {
        currentText.append(ch, start, length);
    }

}
