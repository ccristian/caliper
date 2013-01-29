/*
 * Copyright (C) 2012 Google Inc.
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

package com.google.caliper.bridge;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A message representing output produced by the JVM when {@code -XX:+PrintCompliation} is enabled.
 */
public final class HotspotLogMessage extends LogMessage {
  @VisibleForTesting static final Pattern PATTERN =
      Pattern.compile(".*::.*( \\(((\\d+ bytes)|(static))\\))?");

  public static final class Parser
      implements TryParser<HotspotLogMessage> {
    @Override
    public Optional<HotspotLogMessage> tryParse(String text) {
      Matcher matcher = PATTERN.matcher(text);
      return matcher.matches() ? Optional.of(new HotspotLogMessage())
          : Optional.<HotspotLogMessage>absent();
    }
  }

  private HotspotLogMessage() {}

  @Override
  public void accept(LogMessageVisitor visitor) {
    visitor.visit(this);
  }
}