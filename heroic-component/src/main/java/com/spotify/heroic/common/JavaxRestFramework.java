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

package com.spotify.heroic.common;

import javax.ws.rs.container.AsyncResponse;

import eu.toolchain.async.AsyncFuture;

public interface JavaxRestFramework {
    interface Resume<T, R> {
        R resume(T value) throws Exception;
    }

    <T> void bind(final AsyncResponse response, final AsyncFuture<T> callback);

    /**
     * Helper function to correctly wire up async response management.
     *
     * @param response The async response object.
     * @param callback Callback for the pending request.
     * @param resume The resume implementation.
     */
    <T, R> void bind(final AsyncResponse response, final AsyncFuture<T> callback,
            final Resume<T, R> resume);

    <T> Resume<T, T> passthrough();
}
