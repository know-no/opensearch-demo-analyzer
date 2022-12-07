/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.analysis.demo.tokenizers;

import org.apache.lucene.analysis.Tokenizer;
import org.opensearch.common.settings.Settings;
import org.opensearch.env.Environment;
import org.opensearch.index.IndexSettings;
import org.opensearch.index.analysis.*;


public class DemoCustomWordTokenizerFactory extends AbstractTokenizerFactory {

    public static final int MAX_IGNORED = 255;
    public static final String STOPWORD = "custom_stopword";
    private final int codePoint;

    public DemoCustomWordTokenizerFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, settings, name);
        var stopWord = settings.get(STOPWORD, " ");
        if (stopWord.codePointCount(0, stopWord.length()) != 1) {
            throw new IllegalArgumentException("暂只支持一个字符的分割词 ");
        }
        this.codePoint = stopWord.codePointAt(0);
    }

    @Override
    public Tokenizer create() {
        return new DemoCustomWordTokenizer(this.codePoint);
    }
}
