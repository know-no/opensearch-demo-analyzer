/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.analysis.demo.charfilters;

import org.apache.lucene.analysis.charfilter.BaseCharFilter;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class DemoRemoveSomeCharsCharFilter extends BaseCharFilter {
    private final List<Character> chars;

    public DemoRemoveSomeCharsCharFilter(List<Character> chars, Reader in) {
        super(in);
        this.chars = chars;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int numRead = 0;
        for (int i = off; i < off + len; i++) {
            var c = input.read();
            if (c == -1) {
                break;
            }
            if (chars.stream().noneMatch(x -> x == c)) {
                cbuf[i] = (char) c;
                ++numRead;
            }
        }
        return numRead == 0 ? -1 : numRead;
    }
}
