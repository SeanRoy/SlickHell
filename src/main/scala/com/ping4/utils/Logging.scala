package com.ping4.utils

import org.slf4j.{Logger, LoggerFactory}

trait Logging {
  protected lazy val log = LoggerFactory.getLogger(getClass)
}
