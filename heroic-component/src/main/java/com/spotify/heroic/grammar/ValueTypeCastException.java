/*
 * Copyright (c) 2015 Spotify AB.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.spotify.heroic.grammar;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ValueTypeCastException extends RuntimeException {
    private static final long serialVersionUID = 3125985026131597565L;

    private final Object from;
    private final Class<?> to;

    public ValueTypeCastException(Object from, Class<?> to) {
        super(String.format("%s cannot be cast to %s", from, name(to)));

        this.from = from;
        this.to = to;
    }

    private static String name(Class<?> type) {
        final ValueName name = type.getAnnotation(ValueName.class);

        if (name != null) {
            return name.value();
        }

        return type.getSimpleName();
    }
}
