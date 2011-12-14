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
package org.jclouds.glesys.domain;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Adam Lowe
 */
public class ServerUptime {
   private final long time;
   private final String timeString;

   public ServerUptime(long time) {
      this.time = time;
      long days = TimeUnit.SECONDS.toDays(time);
      long hours = TimeUnit.SECONDS.toHours(time - TimeUnit.DAYS.toSeconds(days));
      Long[] bits = new Long[]{
            0L,
            (days / 365),
            ((days % 365) / 30),
            ((days % 365) % 30),
            hours,
            TimeUnit.SECONDS.toMinutes(time - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.DAYS.toSeconds(days)),
            time % 60
      };
      this.timeString = Joiner.on(' ').join(bits);
   }

   private ServerUptime(String timeString) {
      Splitter splitter = Splitter.on(' ').omitEmptyStrings().trimResults();
      List<String> data = new ArrayList<String>();
      Iterables.addAll(data, splitter.split(timeString));
      long result = Integer.parseInt(data.get(6));
      result += TimeUnit.SECONDS.convert(Integer.parseInt(data.get(5)), TimeUnit.MINUTES);
      result += TimeUnit.SECONDS.convert(Integer.parseInt(data.get(4)), TimeUnit.HOURS);
      result += TimeUnit.SECONDS.convert(Integer.parseInt(data.get(3)), TimeUnit.DAYS);
      result += TimeUnit.SECONDS.convert(Integer.parseInt(data.get(2)) * 30, TimeUnit.DAYS);
      result += TimeUnit.SECONDS.convert(Integer.parseInt(data.get(1)) * 365, TimeUnit.DAYS);
      this.time = result;
      this.timeString = timeString;
   }

   /**
    * @param uptimeString a Glesys uptime string
    */
   public static ServerUptime fromValue(String uptimeString) {
      return new ServerUptime(uptimeString);
   }

   /**
    * @param time number of seconds
    */
   public static ServerUptime fromValue(long time) {
      return new ServerUptime(time);
   }

   public long getTime() {
      return time;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      return object instanceof ServerUptime
            && time == ((ServerUptime) object).time;
   }

   @Override
   public int hashCode() {
      return 37 * (int) time;
   }

   @Override
   public String toString() {
      return timeString;
   }


}