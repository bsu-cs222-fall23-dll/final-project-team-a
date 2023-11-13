package edu.bsu.cs222.markdownEditor.textarea.segments;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class SegmentList {
    private final Map<Integer, Segment> map = new HashMap<>();
    private int index;

    public SegmentList(int start) {
        index = start;
    }

    public SegmentList(Segment segment) {
        index = 0;
        add(segment);
    }

    public void add(Segment segment) {
        map.put(index, segment);
        skip(segment.length());
    }

    public void skip(int length) {
        index += length;
    }

    public Segment get(int index) {
        return map.get(index);
    }

    public void forEach(BiConsumer<Integer, Segment> action) {
        map.forEach(action);
    }
}
