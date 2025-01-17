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

package com.xiaomi.duckling.dimension.quantity

import com.xiaomi.duckling.ranking.Testing.{testContext, testOptions}
import com.xiaomi.duckling.Api.analyze
import com.xiaomi.duckling.dimension.numeral.{Numeral, NumeralValue}
import com.xiaomi.duckling.UnitSpec

class QuantityTest extends UnitSpec {

  val options = testOptions.copy(targets = Set(Quantity))

  describe("QuantityTest") {
    it("hybrid") {
      val answers = analyze(
        "一千米+1000",
        testContext,
        options.copy(targets = Set(Quantity, Numeral), full = false)
      )
      answers(0).token.value match {
        case QuantityValue(1.0, "千米", _) => true shouldBe true
        case _ => true shouldBe (false)
      }
      answers(1).token.value match {
        case NumeralValue(v, _) =>
          v shouldBe 1000.0
      }
    }
  }
}
