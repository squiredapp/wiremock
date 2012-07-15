/*
 * Copyright (C) 2011 Thomas Akehurst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.tomakehurst.wiremock.http;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import java.util.*;

import static com.google.common.collect.Lists.newArrayList;

public class HttpHeaders extends HashMap<String, String> {

    private final Multimap<String, String> headers;

    public HttpHeaders() {
        headers = LinkedHashMultimap.create();
    }

    public HttpHeaders(HttpHeader... headers) {
        this();
        for (HttpHeader header: headers) {
            put(header.key(), header.firstValue());
            this.headers.putAll(header.key(), header.values());
        }
    }

    public HttpHeaders(Iterable<HttpHeader> headers) {
        this();
        for (HttpHeader header: headers) {
            put(header.key(), header.firstValue());
            this.headers.putAll(header.key(), header.values());
        }
    }

    public HttpHeaders(HttpHeaders headers) {
        this(headers.all());
    }

    public HttpHeader getHeader(String key) {
        if (!headers.containsKey(key)) {
            return HttpHeader.absent(key);
        }

        Collection<String> values = headers.get(key);
        return new HttpHeader(key, values);
    }

    public boolean hasContentTypeHeader() {
        return headers.containsKey(ContentTypeHeader.KEY);
    }

    public Collection<HttpHeader> all() {
        List<HttpHeader> httpHeaderList = newArrayList();
        for (String key: headers.keySet()) {
            httpHeaderList.add(new HttpHeader(key, headers.get(key)));
        }

        return httpHeaderList;
    }

    @Override
    public String get(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String put(String key, String value) {
        headers.put(key, value);
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Map.Entry<String, String>> entrySet() {
        return super.entrySet();
    }

    @Override
    public Set<String> keySet() {
        return super.keySet();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public static HttpHeaders copyOf(HttpHeaders source) {
        return new HttpHeaders(source);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpHeaders that = (HttpHeaders) o;

        if (headers != null ? !headers.equals(that.headers) : that.headers != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (headers != null ? headers.hashCode() : 0);
        return result;
    }
}
