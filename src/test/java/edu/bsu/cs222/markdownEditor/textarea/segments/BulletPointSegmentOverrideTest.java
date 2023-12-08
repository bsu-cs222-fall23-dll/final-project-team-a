package edu.bsu.cs222.markdownEditor.textarea.segments;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BulletPointSegmentOverrideTest {
    @Test
    public void testCreateMethodOverride() throws NoSuchMethodException {
        Assertions.assertEquals(BulletPointSegment.class,
                BulletPointSegment.class.getDeclaredMethod("create",
                        String.class).getDeclaringClass());
    }

    @Test
    public void testSubsequenceMethodOverride() throws NoSuchMethodException {
        Assertions.assertEquals(BulletPointSegment.class,
                BulletPointSegment.class.getDeclaredMethod("subSequence",
                        int.class, int.class).getDeclaringClass());
    }

}
