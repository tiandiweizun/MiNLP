/*
 * Copyright (c) 2020, Xiaomi and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiaomi.duckling.dimension.time.repeat

import java.time.LocalDateTime

import com.xiaomi.duckling.Types.ResolvedValue
import com.xiaomi.duckling.dimension.{Dimension, DimExamples}
import com.xiaomi.duckling.dimension.implicits._
import com.xiaomi.duckling.dimension.time.{form, TimeValue}
import com.xiaomi.duckling.dimension.time.duration.DurationData
import com.xiaomi.duckling.dimension.time.enums.Grain._
import com.xiaomi.duckling.dimension.time.Types.DuckDateTime
import com.xiaomi.duckling.dimension.time.form.Form
import com.xiaomi.duckling.dimension.time.helper.TimeValueHelpers._
import com.xiaomi.duckling.dimension.time.repeat.WorkdayType.{NonWorkday, Workday}

object Examples extends DimExamples {

  implicit def _toTuple(tv: TimeValue) = Option(tv, None: Option[Form])

  override def pairs: List[(ResolvedValue, List[String])] = List(
    (RepeatValue(DurationData(15, Minute, schema = "PT15M")), List("每隔15分钟", "隔15分钟")),
    (RepeatValue(
      DurationData(1, Month),
      start = datetimeInterval(
        new DuckDateTime(LocalDateTime.of(2013, 3, 5, 4, 0, 0)),
        new DuckDateTime(LocalDateTime.of(2013, 3, 5, 12, 0, 0)),
        Hour)
    ), List("每个月五号的早上")),
    (RepeatValue(DurationData(1, Month), start = ymd(m = 3, d = 5)), List("每个月的五号")),
    (RepeatValue(DurationData(1, Week), start = (ymd(d = 13), Some(form.DayOfWeek))), List("每周三", "每个星期三")),
    (RepeatValue(DurationData(1, Day), start = (h(8), Some(form.TimeOfDay(Some(8), false)))), List("每天上午八点", "每个上午八点")),
    (RepeatValue(workdayType = NonWorkday), List("非工作日", "节假日")),
    (RepeatValue(workdayType = Workday, start = (yMdHms(d = 13, H = 3, grain = Hour), Some(form.TimeOfDay(Some(3), false)))), List("工作日三点", "每个工作日三点")),
    (RepeatValue(workdayType = Workday, start = (datetimeInterval(
      new DuckDateTime(LocalDateTime.of(2013, 2, 12, 8, 0, 0)),
      new DuckDateTime(LocalDateTime.of(2013, 2, 12, 12, 0, 0)),
      Hour,
      partOfDay = "上午"), Some(form.PartOfDay("上午")))), List("每个工作日上午"))
  )

  override val dimension: Dimension = Repeat
}
