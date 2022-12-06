/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.analysis.demo;

import java.util.HashMap;
import java.util.Map;

import org.opensearch.analysis.demo.charfilters.DemoRemoveSomeCharsFilterFactory;
import org.opensearch.index.analysis.CharFilterFactory;
import org.opensearch.index.analysis.TokenFilterFactory;
import org.opensearch.index.analysis.TokenizerFactory;
import org.opensearch.indices.analysis.AnalysisModule.AnalysisProvider;
import org.opensearch.plugins.AnalysisPlugin;
import org.opensearch.plugins.Plugin;


public class DemoAnalyzerPlugin extends Plugin implements AnalysisPlugin {

    @Override
    public Map<String, AnalysisProvider<CharFilterFactory>> getCharFilters() {
        var charFilters = new HashMap<String, AnalysisProvider<CharFilterFactory>>();
        charFilters.put("remove_some_chars",
            AnalysisPlugin.requiresAnalysisSettings(DemoRemoveSomeCharsFilterFactory::new));
        return charFilters;
    }

    @Override
    public Map<String, AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
        // TODO Auto-generated method stub
        return AnalysisPlugin.super.getTokenFilters();
    }

    @Override
    public Map<String, AnalysisProvider<TokenizerFactory>> getTokenizers() {
        // TODO Auto-generated method stub
        return AnalysisPlugin.super.getTokenizers();
    }
    // Implement the relevant Plugin Interfaces here


}
