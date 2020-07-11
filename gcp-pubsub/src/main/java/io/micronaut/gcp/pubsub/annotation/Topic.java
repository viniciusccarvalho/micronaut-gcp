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
package io.micronaut.gcp.pubsub.annotation;

import io.micronaut.context.annotation.AliasFor;
import io.micronaut.http.MediaType;

import java.lang.annotation.*;

/**
 * Represents a pubsub topic to be used by classes annotated with {@link PubSubClient}.
 *
 * @author Vinicius Carvalho
 * @since 2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Topic {

    /**
     *
     * @return The name of the topic to publish messages to
     */
    @AliasFor(member = "name")
    String value();

    String name();

    String contentType() default MediaType.APPLICATION_JSON;
}
