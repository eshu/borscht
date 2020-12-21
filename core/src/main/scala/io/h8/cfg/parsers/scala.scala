package io.h8.cfg.parsers

import io.h8.cfg.NodeParser

import scala.concurrent.duration.{Duration, FiniteDuration}

given NodeParserDuration(using parser: NodeParser[String]): NodeParser[Duration] = parser andThen (Duration(_))

given NodeParserFiniteDuration(using parser: NodeParser[Duration]): NodeParser[FiniteDuration] = parser andThen {
  case value: FiniteDuration => value
  case infinite => throw IllegalArgumentException("A finite duration is expected")
}
