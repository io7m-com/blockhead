/*
 * Copyright © 2023 Mark Raynsford <code@io7m.com> https://www.io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.blockhead.internal;

import com.io7m.repetoir.core.RPServiceType;
import io.opentelemetry.api.logs.Logger;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.propagation.TextMapPropagator;

/**
 * The type of server telemetry services.
 */

public interface BHTelemetryServiceType extends RPServiceType
{
  /**
   * Set the error code for the current span.
   *
   * @param errorCode The error code
   */

  static void setSpanErrorCode(
    final String errorCode)
  {
    final var span = Span.current();
    span.setAttribute("blockhead.errorCode", errorCode);
    span.setStatus(StatusCode.ERROR);
  }

  /**
   * Set the error code for the current span.
   *
   * @param e The error code
   */

  static void recordSpanException(
    final Throwable e)
  {
    final var span = Span.current();
    span.recordException(e);
    span.setStatus(StatusCode.ERROR);
  }

  /**
   * @return The text map propagator for trace contexts
   */

  TextMapPropagator textMapPropagator();

  /**
   * @return The main tracer
   */

  Tracer tracer();

  /**
   * @return The main meter
   */

  Meter meter();

  /**
   * @return The main logger
   */

  Logger logger();

  /**
   * @return {@code true} if this telemetry service is a no-op service
   */

  boolean isNoOp();
}
