/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.gcp.pubsub.support;

import com.google.api.gax.batching.BatchingSettings;
import com.google.api.gax.core.ExecutorProvider;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.pubsub.v1.Publisher;
import io.micronaut.context.annotation.Factory;
import io.micronaut.gcp.GoogleCloudConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The default {@link PublisherFactory} implementation.
 *
 * <p>Creates {@link Publisher}s for topics once, caches and reuses them.
 *
 * Original source at : https://github.com/spring-cloud/spring-cloud-gcp/blob/master/spring-cloud-gcp-pubsub/src/main/java/org/springframework/cloud/gcp/pubsub/support/DefaultPublisherFactory.java
 *
 * @author João André Martins
 * @author Chengyuan Zhao
 * @author Vinicius Carvalho
 */
@Factory
public class DefaultPublisherFactory implements PublisherFactory {

    private final ConcurrentHashMap<String, Publisher> publishers = new ConcurrentHashMap<>();

    private final ExecutorProvider executorProvider;

    private final TransportChannelProvider transportChannelProvider;

    private final RetrySettings retrySettings;

    private final BatchingSettings batchingSettings;

    private final GoogleCloudConfiguration googleCloudConfiguration;

    public DefaultPublisherFactory(ExecutorProvider executorProvider,
                                   TransportChannelProvider transportChannelProvider, @Nullable RetrySettings retrySettings,
                                   @Nullable BatchingSettings batchingSettings,
                                   GoogleCloudConfiguration googleCloudConfiguration) {
        this.executorProvider = executorProvider;
        this.transportChannelProvider = transportChannelProvider;
        this.retrySettings = retrySettings;
        this.batchingSettings = batchingSettings;
        this.googleCloudConfiguration = googleCloudConfiguration;
    }

    /**
     * Creates and caches a publisher for a given topic.
     * @param topic
     * @return
     */
    @Override
    public Publisher createPublisher(@Nonnull String topic) {
        return this.publishers.computeIfAbsent(topic, (key) -> {
            try {
                Publisher.Builder publisherBuilder = Publisher.newBuilder(PubSubTopicUtils.toProjectTopicName(topic, googleCloudConfiguration.getProjectId()));
                if (this.executorProvider != null) {
                    publisherBuilder.setExecutorProvider(this.executorProvider);
                }
                if (this.retrySettings != null) {
                    publisherBuilder.setRetrySettings(this.retrySettings);
                }
                if (this.batchingSettings != null) {
                    publisherBuilder.setBatchingSettings(this.batchingSettings);
                }
                return publisherBuilder.build();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

}