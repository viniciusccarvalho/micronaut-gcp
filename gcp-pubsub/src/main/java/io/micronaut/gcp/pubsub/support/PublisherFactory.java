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

import com.google.cloud.pubsub.v1.Publisher;

/**
 * The publisher factory interface that can create publishers.
 *
 * https://github.com/spring-cloud/spring-cloud-gcp/blob/master/spring-cloud-gcp-pubsub/src/main/java/org/springframework/cloud/gcp/pubsub/support/PublisherFactory.java
 *
 * @author João André Martins
 * @author Chengyuan Zhao
 * @author Vinicius Carvalho
 */
public interface PublisherFactory {

    Publisher createPublisher(String topic);

}
