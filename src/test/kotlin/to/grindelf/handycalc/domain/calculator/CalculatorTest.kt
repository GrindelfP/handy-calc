package to.grindelf.handycalc.domain.calculator

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import to.grindelf.handycalc.exceptions.UnknownTokenException

class CalculatorTest {

    @Test
    fun `GIVEN equation as List(String) AND correct result WHEN calculate THEN return correct result`() {
        // GIVEN
        val equation = listOf("1", "+", "2", "*", "3", "-", "4", "/", "2")
        val resultExpected = 5.0

        // WHEN
        val resultActual = Calculator.calculate(equation)

        // THEN
        assertThat(resultActual).isEqualTo(resultExpected)
    }

    @Test
    fun `GIVEN equation as List(String) requiring non-integer calculation AND correct result WHEN calculate THEN return correct result`() {
        // GIVEN
        val equation = listOf("10", "/", "4", "+", "15")
        val resultExpected = 17.5

        // WHEN
        val resultActual = Calculator.calculate(equation)

        // THEN
        assertThat(resultActual).isEqualTo(resultExpected)
    }

    @Test
    fun `GIVEN equation with multiple divisions AND correct result WHEN calculate THEN return correct result`() {
        // GIVEN
        val equation = listOf("15", "+", "16", "-", "10", "/", "4", "/", "2")
        val resultExpected = 29.75

        // WHEN
        val resultActual = Calculator.calculate(equation)

        // THEN
        assertThat(resultActual).isEqualTo(resultExpected)
    }

    @Test
    fun `GIVEN equation with unknown operators THEN throw UnknownTokenException`() {
        // GIVEN
        val equation = listOf("1", "^", "2")

        // THEN
        assertThatThrownBy { Calculator.calculate(equation) }.isInstanceOf(UnknownTokenException::class.java)

    }

    @Test
    fun `GIVEN equation with unknown operands THEN throw UnknownTokenException`() {
        // GIVEN
        val equation = listOf("1a", "+", "2")

        // THEN
        assertThatThrownBy { Calculator.calculate(equation) }.isInstanceOf(UnknownTokenException::class.java)

    }
}