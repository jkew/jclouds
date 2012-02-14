/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
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
package org.jclouds.vcloud.director.v1_5.internal;

import static org.testng.Assert.assertNotNull;

import java.net.URI;

import org.jclouds.date.DateService;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.jclouds.rest.BaseRestClientExpectTest;
import org.jclouds.vcloud.director.v1_5.VCloudDirectorClient;
import org.jclouds.vcloud.director.v1_5.VCloudDirectorMediaType;
import org.testng.annotations.BeforeGroups;

import com.google.common.collect.ImmutableMultimap;
import com.google.inject.Guice;

/**
 * Base class for writing KeyStone Rest Client Expect tests
 * 
 * @author Adrian Cole
 */
public class BaseVCloudDirectorRestClientExpectTest extends BaseRestClientExpectTest<VCloudDirectorClient> {

   public static final String user = "adrian@jclouds.org";
   public static final String org = "JClouds";
   public static final String password = "password";
   public static final String token = "mIaR3/6Lna8DWImd7/JPR5rK8FcUHabt+G/UCJV5pJQ=";
   public static final String endpoint = "https://vcloudbeta.bluelock.com/api";

   protected static DateService dateService;

   @BeforeGroups("unit")
   protected static void setUpInjector() {
      dateService = Guice.createInjector().getInstance(DateService.class);
      assertNotNull(dateService);
   }

   protected HttpRequest loginRequest = HttpRequest.builder()
         .method("POST")
         .endpoint(URI.create(endpoint + "/sessions"))
         .headers(ImmutableMultimap.<String, String>builder()
               .put("Accept", "*/*")
               .put("Authorization", "Basic YWRyaWFuQGpjbG91ZHMub3JnQEpDbG91ZHM6cGFzc3dvcmQ=")
               .build())
         .build();

   protected HttpResponse sessionResponse = HttpResponse.builder()
         .statusCode(200)
         .headers(ImmutableMultimap.<String, String> builder()
               .put("x-vcloud-authorization", token)
               .put("Set-Cookie", String.format("vcloud-token=%s; Secure; Path=/", token))
               .build())
         .payload(payloadFromResourceWithContentType("/session.xml", VCloudDirectorMediaType.SESSION + ";version=1.5"))
         .build();

   public BaseVCloudDirectorRestClientExpectTest() {
      provider = "vcloud-director";
      identity = String.format("%s@%s", user, org);
      credential = password;
   }
   
   protected HttpRequest getStandardRequest(String method, String path) {
      return getStandardRequest(method, path, VCloudDirectorMediaType.ANY);
   }

   protected HttpRequest getStandardRequest(String method, URI uri) {
      return getStandardRequest(method, uri, VCloudDirectorMediaType.ANY);
   }

   protected HttpRequest getStandardRequest(String method, String path, String mediaType) {
      return getStandardRequest(method, URI.create(endpoint + path), VCloudDirectorMediaType.ANY);
   }

   protected HttpRequest getStandardPayloadRequest(String method, String command, String relativeFilePath, 
         String postMediaType) {
      return getStandardPayloadRequest(method, URI.create(endpoint + command), relativeFilePath, postMediaType);
   }
   
   protected HttpRequest getStandardPayloadRequest(String method, URI uri, String relativeFilePath, 
         String postMediaType) {
      return getStandardPayloadRequest(method, uri, VCloudDirectorMediaType.ANY, relativeFilePath, postMediaType);
   }
   
   protected HttpRequest getStandardRequest(String method, URI uri, String mediaType) {
      return HttpRequest.builder()
            .method(method)
            .endpoint(uri)
            .headers(ImmutableMultimap.<String, String> builder()
                  .put("Accept", mediaType)
                  .put("x-vcloud-authorization", token)
                  .build())
            .build();
   }
   
   protected HttpRequest getStandardPayloadRequest(String method, URI uri, String mediaType, 
         String relativeFilePath, String postMediaType) {
      return HttpRequest.builder()
            .method(method)
            .endpoint(uri)
            .headers(ImmutableMultimap.<String, String> builder()
                  .put("Accept", mediaType)
                  .put("x-vcloud-authorization", token)
                  .build())
            .payload(payloadFromResourceWithContentType(relativeFilePath, postMediaType + ";version=1.5"))
            .build();
   }

   protected HttpRequest getStandardRequestWithPayload(String method, String path, String relativeFilePath, String mediaType) {
      return getStandardRequestWithPayload(method, path, VCloudDirectorMediaType.ANY, relativeFilePath, mediaType);
   }
   
   protected HttpRequest getStandardRequestWithPayload(String method, URI uri, String relativeFilePath, String mediaType) {
      return getStandardRequestWithPayload(method, uri, VCloudDirectorMediaType.ANY, relativeFilePath, mediaType);
   }

   protected HttpRequest getStandardRequestWithPayload(String method, String path, String acceptType, String relativeFilePath, String mediaType) {
      URI uri = URI.create(endpoint + path);
      return getStandardRequestWithPayload(method, uri, acceptType, relativeFilePath, mediaType);
   }

   protected HttpRequest getStandardRequestWithPayload(String method, URI uri, String acceptType, String relativeFilePath, String mediaType) {
      return HttpRequest.builder()
            .method(method)
            .endpoint(uri)
            .headers(ImmutableMultimap.<String, String> builder()
                  .put("Accept", acceptType)
                  .put("x-vcloud-authorization", token)
                  .build())
            .payload(payloadFromResourceWithContentType(relativeFilePath, mediaType))
            .build();
   }

   protected HttpResponse getStandardPayloadResponse(String relativeFilePath, String mediaType) {
      return getStandardPayloadResponse(200, relativeFilePath, mediaType);
   }

   protected HttpResponse getStandardPayloadResponse(int statusCode, String relativeFilePath, String mediaType) {
      return HttpResponse.builder()
            .statusCode(statusCode)
            .payload(payloadFromResourceWithContentType(relativeFilePath, mediaType + ";version=1.5"))
            .build();
   }
}
