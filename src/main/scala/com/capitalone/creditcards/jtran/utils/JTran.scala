package com.capitalone.creditcards.jtran.utils

import io.circe.Json
import io.circe.parser.parse

object JTran extends App {


  val template = Json.fromString(args(0))
  val payload = parse(args(1)).getOrElse(Json.Null)

  val newJson = utils.bind(template,payload)

  Json.fromString(args(0))
  println(newJson.spaces2)




}
