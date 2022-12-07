/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.analysis.demo.tokenizers;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DemoCustomWordTokenizer extends Tokenizer {
    private final int codePoint;
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final PositionIncrementAttribute positionIncrementAtt = addAttribute(PositionIncrementAttribute.class);
    private boolean exhausted;

    public DemoCustomWordTokenizer(int codePoint) {
        this.codePoint = codePoint;
    }

    @Override
    public boolean incrementToken() throws IOException {
        clearAttributes();
        List<Integer> ans = new LinkedList<>();
        while (false == exhausted) {
            var read = input.read();
            if (read == -1) {
                exhausted = true;
                break;
            }
            if (read != codePoint) {
                ans.add(read);
            } else {
                break;
            }
        }
        if (ans.size() != 0) {
            var collect = ans.stream().map(Character::toChars).collect(Collectors.toList());
            var reduce = collect.stream().map(x -> x.length).reduce(0, Integer::sum);
            termAtt.setLength(reduce);
            for (char[] chars : collect) {
                var as = new String(chars);
                termAtt.append(as);
            }
            positionIncrementAtt.setPositionIncrement(1);
            return true;
        }
        return false;

    }

    @Override
    public void reset() throws IOException {
        super.reset();
        this.exhausted = false;
    }
}
