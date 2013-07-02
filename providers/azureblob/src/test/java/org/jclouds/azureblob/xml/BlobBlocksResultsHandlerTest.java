package org.jclouds.azureblob.xml;

import org.jclouds.azureblob.domain.BlobBlockProperties;
import org.jclouds.azureblob.domain.ListBlobBlocksResponse;
import org.jclouds.azureblob.domain.internal.BlobBlockPropertiesImpl;
import org.jclouds.azureblob.domain.internal.ListBlobBlocksResponseImpl;
import org.jclouds.http.functions.BaseHandlerTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Test XML Parsing of the Blob Block List
 */
@Test(groups = "unit", testName = "BlobBlocksResultsHandlerTest")
public class BlobBlocksResultsHandlerTest extends BaseHandlerTest {

    @BeforeTest
    @Override
    protected void setUpInjector() {
        super.setUpInjector();
    }

    public void testGetResult() throws Exception {
        InputStream is = getClass().getResourceAsStream("/test_list_blob_blocks.xml");

        List<BlobBlockProperties> blocks = new LinkedList<BlobBlockProperties>();
        blocks.add(new BlobBlockPropertiesImpl("blockIdA", 1234, true));
        blocks.add(new BlobBlockPropertiesImpl("blockIdB", 4321, true));
        blocks.add(new BlobBlockPropertiesImpl("blockIdC", 5678, false));
        blocks.add(new BlobBlockPropertiesImpl("blockIdD", 8765, false));
        ListBlobBlocksResponse expected = new ListBlobBlocksResponseImpl(blocks);

        ListBlobBlocksResponse result = factory.create(
                injector.getInstance(BlobBlocksResultsHandler.class)).parse(is);

        assertEquals(expected, result);
    }
}
