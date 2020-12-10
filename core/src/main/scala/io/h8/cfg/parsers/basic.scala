package io.h8.cfg.parsers

import java.lang.{Boolean => jBoolean}
import io.h8.cfg._
import io.h8.cfg.{Node, Factory}

import scala.annotation.tailrec
import scala.collection.mutable
import scala.jdk.CollectionConverters._

given NodeParserBoolean as NodeParser[Boolean] = NodeParserScalarAnyRef andThen {
  case v: jBoolean => v.booleanValue
  case v: String => jBoolean.parseBoolean(v)
  case v: Number => v != 0
}

given NodeParserNumber as NodeParser[Number] = NodeParserScalarAnyRef andThen {
  case v: Number => v
  case v: String => BigDecimal(v)
}

given NodeParserList[T](using parser: NodeParser[T]) as NodeParser[List[T]] =
  NodeParserIterableNode andThen { node => (node.iterator map parser).toList }

given NodeParserSet[T] (using parser: NodeParser[T]) as NodeParser[Set[T]] =
  NodeParserIterableNode andThen { node => (node.iterator map parser).toSet }

given NodeParserMap[T] (using parser: NodeParser[T]) as NodeParser[Map[String, T]] =
  NodeParserCfgNode andThen { cfg =>
    (cfg.iterator map { (key: String, value: Node) => key -> parser(value) }).toMap
  }