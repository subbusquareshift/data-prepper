/*
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  The OpenSearch Contributors require contributions made to
 *  this file be licensed under the Apache-2.0 license or a
 *  compatible open source license.
 *
 *  Modifications Copyright OpenSearch Contributors. See
 *  GitHub history for details.
 */

package com.amazon.dataprepper.plugins.sink;

import com.amazon.dataprepper.model.annotations.DataPrepperPlugin;
import com.amazon.dataprepper.model.configuration.PluginSetting;
import com.amazon.dataprepper.model.event.Event;
import com.amazon.dataprepper.model.record.Record;
import com.amazon.dataprepper.model.sink.Sink;

import java.util.Collection;

@DataPrepperPlugin(name = "stdout", pluginType = Sink.class)
public class StdOutSink implements Sink<Record<Object>> {

    /**
     * Mandatory constructor for Data Prepper Component - This constructor is used by Data Prepper
     * runtime engine to construct an instance of {@link StdOutSink} using an instance of {@link PluginSetting} which
     * has access to pluginSetting metadata from pipeline
     * pluginSetting file.
     *
     * @param pluginSetting instance with metadata information from pipeline pluginSetting file.
     */
    public StdOutSink(final PluginSetting pluginSetting) {
        this();
    }

    public StdOutSink() {
    }

    @Override
    public void output(final Collection<Record<Object>> records) {
        for (final Record<Object> record : records) {
            checkTypeAndPrintObject(record.getData());
        }
    }

    // Temporary function to support both trace and log ingestion pipelines.
    // TODO: This function should be removed with the completion of: https://github.com/opensearch-project/data-prepper/issues/546
    private void checkTypeAndPrintObject(final Object object) {
        if (object instanceof String) {
            System.out.println(object);
        } else if (object instanceof Event) {
            System.out.println(((Event) object).toJsonString());
        } else {
            throw new RuntimeException("Invalid record type. StdOutSink only supports String and Events");
        }
    }

    @Override
    public void shutdown() {

    }
}
