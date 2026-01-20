package de.htwg.swe.evenup.view.tui

import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.util.Success
import scala.util.Failure

class ParserSpec extends AnyWordSpec with Matchers:

  val parser = new Parser

  "Parser" should {

    "parse help command" in:
      val result = parser.parseInput(":h")
      result shouldBe a[Success[?]]
      result.get shouldBe List(":h")

    "parse quit command" in:
      val result = parser.parseInput(":q")
      result shouldBe a[Success[?]]
      result.get shouldBe List(":q")

    "parse undo command" in:
      val result = parser.parseInput(":undo")
      result shouldBe a[Success[?]]
      result.get shouldBe List(":undo")

    "parse redo command" in:
      val result = parser.parseInput(":redo")
      result shouldBe a[Success[?]]
      result.get shouldBe List(":redo")

    "parse main menu command" in:
      val result = parser.parseInput(":m")
      result shouldBe a[Success[?]]
      result.get shouldBe List(":m")

    "parse calculate debts command" in:
      val result = parser.parseInput(":debts")
      result shouldBe a[Success[?]]
      result.get shouldBe List(":debts")

    "parse load command" in:
      val result = parser.parseInput(":load")
      result shouldBe a[Success[?]]
      result.get shouldBe List(":load")

    "parse newgroup command with name" in:
      val result = parser.parseInput(":newgroup MyGroup")
      result shouldBe a[Success[?]]
      result.get shouldBe List(":newgroup", "MyGroup")

    "fail newgroup command without name" in:
      val result = parser.parseInput(":newgroup")
      result shouldBe a[Failure[?]]

    "parse adduser command with names" in:
      val result = parser.parseInput(":adduser Alice Bob")
      result shouldBe a[Success[?]]
      result.get shouldBe List(":adduser", "Alice", "Bob")

    "fail adduser command without names" in:
      val result = parser.parseInput(":adduser")
      result shouldBe a[Failure[?]]

    "parse gotoGroup command with name" in:
      val result = parser.parseInput(":group MyGroup")
      result shouldBe a[Success[?]]
      result.get shouldBe List(":group", "MyGroup")

    "fail gotoGroup command without name" in:
      val result = parser.parseInput(":group")
      result shouldBe a[Failure[?]]

    "parse setStrategy command with strategy" in:
      val result = parser.parseInput(":strategy normal")
      result shouldBe a[Success[?]]
      result.get shouldBe List(":strategy", "normal")

    "fail setStrategy command without strategy" in:
      val result = parser.parseInput(":strategy")
      result shouldBe a[Failure[?]]

    "fail for unknown command" in:
      val result = parser.parseInput("unknown")
      result shouldBe a[Failure[?]]

    "parse addExpense command with valid shares" in:
      val result = parser.parseInput(":addexp Dinner Alice 30 Alice:15_Bob:15 2025-06-15")
      result shouldBe a[Success[?]]
      result.get.head shouldBe ":addexp"

    "fail addExpense command with invalid shares format" in:
      val result = parser.parseInput(":addexp Dinner Alice 30 invalid_format 2025-06-15")
      result shouldBe a[Failure[?]]
      result.failed.get.getMessage should include("Invalid format")

    "fail addExpense command with empty person in shares" in:
      val result = parser.parseInput(":addexp Dinner Alice 30 :15 2025-06-15")
      result shouldBe a[Failure[?]]
      result.failed.get.getMessage should include("Empty person name")

    "fail addExpense command with invalid amount in shares" in:
      val result = parser.parseInput(":addexp Dinner Alice 30 Alice:abc 2025-06-15")
      result shouldBe a[Failure[?]]
      result.failed.get.getMessage should include("Invalid amount")

    "fail addExpense command with negative amount in shares" in:
      val result = parser.parseInput(":addexp Dinner Alice 30 Alice:-10 2025-06-15")
      result shouldBe a[Failure[?]]
      result.failed.get.getMessage should include("Negative amount")
  }

  "Parser.validSharePattern" should {

    "return true for valid pattern" in:
      parser.validSharePattern("Alice:10") shouldBe true
      parser.validSharePattern("Alice:10_Bob:20") shouldBe true
      parser.validSharePattern("Alice:10.5_Bob:20.75") shouldBe true

    "return false for invalid pattern" in:
      parser.validSharePattern("invalid") shouldBe false
      parser.validSharePattern("Alice:") shouldBe false
      parser.validSharePattern(":10") shouldBe false
  }

  "Parser.decorateErrorMessage" should {

    "create decorated error message" in:
      val message = parser.decorateErrorMessage(TuiKeys.newGroup)
      message should include(TuiKeys.newGroup.key)
      message should include(TuiKeys.newGroup.usage)
  }