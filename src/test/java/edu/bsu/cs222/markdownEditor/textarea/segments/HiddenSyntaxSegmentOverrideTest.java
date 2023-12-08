package edu.bsu.cs222.markdownEditor.textarea.segments;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HiddenSyntaxSegmentOverrideTest {

    @Test
    public void testCreateMethodOverride() throws NoSuchMethodException {
        Assertions.assertEquals(HiddenSyntaxSegment.class,
                HiddenSyntaxSegment.class.getDeclaredMethod("create",
                        String.class).getDeclaringClass());
    }

    @Test
    public void testSubsequenceMethodOverride() throws NoSuchMethodException {
        Assertions.assertEquals(HiddenSyntaxSegment.class,
                HiddenSyntaxSegment.class.getDeclaredMethod("subSequence",
                        int.class, int.class).getDeclaringClass());
    }

}
