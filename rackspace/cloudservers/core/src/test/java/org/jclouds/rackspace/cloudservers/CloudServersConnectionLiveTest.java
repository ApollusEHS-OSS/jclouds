/**
 *
 * Copyright (C) 2009 Global Cloud Specialists, Inc. <info@globalcloudspecialists.com>
 *
 * ====================================================================
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
 * ====================================================================
 */
package org.jclouds.rackspace.cloudservers;

import static org.jclouds.rackspace.reference.RackspaceConstants.PROPERTY_RACKSPACE_KEY;
import static org.jclouds.rackspace.reference.RackspaceConstants.PROPERTY_RACKSPACE_USER;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.jclouds.rackspace.cloudservers.domain.Flavor;
import org.jclouds.rackspace.cloudservers.domain.Image;
import org.jclouds.rackspace.cloudservers.domain.Server;
import org.testng.annotations.Test;

/**
 * Tests behavior of {@code CloudServersConnection}
 * 
 * @author Adrian Cole
 */
@Test(groups = "live", testName = "cloudservers.CloudServersConnectionLiveTest")
public class CloudServersConnectionLiveTest {

   protected static final String sysRackspaceUser = System.getProperty(PROPERTY_RACKSPACE_USER);
   protected static final String sysRackspaceKey = System.getProperty(PROPERTY_RACKSPACE_KEY);

   @Test
   public void testListServers() throws Exception {
      CloudServersConnection connection = CloudServersContextBuilder.newBuilder(sysRackspaceUser,
               sysRackspaceKey).withJsonDebug().buildContext().getConnection();
      List<Server> response = connection.listServers();
      assert null != response;
      long initialContainerCount = response.size();
      assertTrue(initialContainerCount >= 0);

   }

   @Test
   public void testListServersDetail() throws Exception {
      CloudServersConnection connection = CloudServersContextBuilder.newBuilder(sysRackspaceUser,
               sysRackspaceKey).withJsonDebug().buildContext().getConnection();
      List<Server> response = connection.listServerDetails();
      assert null != response;
      long initialContainerCount = response.size();
      assertTrue(initialContainerCount >= 0);
   }

   @Test
   public void testListFlavors() throws Exception {
      CloudServersConnection connection = CloudServersContextBuilder.newBuilder(sysRackspaceUser,
               sysRackspaceKey).withJsonDebug().buildContext().getConnection();
      List<Flavor> response = connection.listFlavors();
      assert null != response;
      long flavorCount = response.size();
      assertTrue(flavorCount >= 1);
      for (Flavor flavor : response) {
         assertTrue(flavor.getId() >= 0);
         assert null != flavor.getName() : flavor;
      }

   }

   @Test
   public void testListFlavorsDetail() throws Exception {
      CloudServersConnection connection = CloudServersContextBuilder.newBuilder(sysRackspaceUser,
               sysRackspaceKey).withJsonDebug().buildContext().getConnection();
      List<Flavor> response = connection.listFlavorDetails();
      assert null != response;
      long flavorCount = response.size();
      assertTrue(flavorCount >= 0);
      for (Flavor flavor : response) {
         assertTrue(flavor.getId() >= 1);
         assert null != flavor.getName() : flavor;
         assert null != flavor.getDisk() : flavor;
         assert null != flavor.getRam() : flavor;
      }
   }

   @Test
   public void testListImages() throws Exception {
      CloudServersConnection connection = CloudServersContextBuilder.newBuilder(sysRackspaceUser,
               sysRackspaceKey).withJsonDebug().buildContext().getConnection();
      List<Image> response = connection.listImages();
      assert null != response;
      long imageCount = response.size();
      assertTrue(imageCount >= 1);
      for (Image image : response) {
         assertTrue(image.getId() >= 0);
         assert null != image.getName() : image;
      }

   }

   @Test
   public void testListImagesDetail() throws Exception {
      CloudServersConnection connection = CloudServersContextBuilder.newBuilder(sysRackspaceUser,
               sysRackspaceKey).withJsonDebug().buildContext().getConnection();
      List<Image> response = connection.listImageDetails();
      assert null != response;
      long imageCount = response.size();
      assertTrue(imageCount >= 0);
      for (Image image : response) {
         assertTrue(image.getId() >= 1);
         assert null != image.getName() : image;
         // TODO: Web Hosting #118779
         // assert null != image.getCreated() : image;
         // assert null != image.getUpdated() : image;
         assert null != image.getStatus() : image;
      }
   }
}
