package ru.arturvasilov.stackexchangeclient.model.response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.Tag;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class TagResponseTest {

    @Test
    public void testCreated() throws Exception {
        TagResponse tagResponse = new TagResponse();
        assertNotNull(tagResponse);
    }

    @Test
    public void testTagsNotNull() throws Exception {
        TagResponse tagResponse = new TagResponse();
        assertNotNull(tagResponse.getTags());
    }

    @Test
    public void testSetTags() throws Exception {
        TagResponse tagResponse = new TagResponse();
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("c#", false));
        tagResponse.setTags(tags);
        assertTrue(tags == tagResponse.getTags());
    }

}
