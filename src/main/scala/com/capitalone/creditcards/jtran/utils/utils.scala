package com.capitalone.creditcards.jtran.utils

import io.circe.Json
import io.circe.optics.JsonPath.root

package object utils {

  def walk(path: String)(json: Json) = path.split("\\.").foldLeft(root)((acc, p) => acc.selectDynamic(p)).json.getOption(json).head

  def flatten(j: Json, key: String = "key", value: String = "value"): Json =
    j.fold[Json](
      j,
      _ => j,
      _ => j,
      _ => j,
      jArray => Json.fromValues(jArray.map(flatten(_, key, value))),
      jObject => Json.obj((walk(key)(j).toString, walk(value)(j))))


  def bind(template: Json, payLoad: Json): Json =
    template.fold[Json](
      template,
      jBoolean => template,
      jNumber => template,
      jString => walk(jString)(payLoad),
      jArray => Json.fromValues(jArray.map(bind(_, payLoad))),
      jObject => Json.fromJsonObject(jObject.mapValues(bind(_, payLoad)))
    )

}
