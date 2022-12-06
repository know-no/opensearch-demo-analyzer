package org.opensearch.analysis.demo.charfilters;

import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

import org.opensearch.common.settings.Settings;
import org.opensearch.env.Environment;
import org.opensearch.index.IndexSettings;
import org.opensearch.index.analysis.AbstractCharFilterFactory;
import org.opensearch.index.analysis.Analysis;

public final class DemoRemoveSomeCharsFilterFactory extends AbstractCharFilterFactory {

    private final List<Character> chars;

    public DemoRemoveSomeCharsFilterFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, name);
        var removedChars = Analysis.getWordList(env, settings, "removed_chars");
        removedChars = null == removedChars ? List.of() : removedChars;
        if (removedChars.stream().anyMatch(x -> x.length() > 1)) {
            throw new IllegalArgumentException("暂时只支持单个char");// 可以考虑改为去掉某个unicode, unicode码点在java内可能是多个char
        }
        this.chars = removedChars.stream().filter(x -> x.length() > 0).map(x -> x.charAt(0))
            .collect(Collectors.toList());

    }

    @Override
    public Reader create(Reader input) {
        return new DemoRemoveSomeCharsCharFilter(chars, input);
    }
}
