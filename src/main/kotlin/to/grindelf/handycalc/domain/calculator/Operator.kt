package to.grindelf.handycalc.domain.calculator

import to.grindelf.handycalc.exceptions.UnknownOperatorException

/**
 * Class representing an operator.
 * Each operator has a symbol and a precedence.
 * The precedence is used to determine the order of operations.
 * It is evaluated from 1 to 2, where 1 is the precedence of + and -
 * and 2 is the precedence of * and /.
 */
data class Operator(
    val symbol: String
) {
    val precedence: Int

    /**
     * It is required that the symbol is one character long.
     * If so, the precedence is set according to the symbol.
     */
    init {
        require(symbol.length == 1) {
            "Operator symbol must be one character long"
        }

        precedence = when (symbol) {
            "+", "-" -> 1
            "*", "/" -> 2
            else -> throw UnknownOperatorException()
        }
    }

    fun operate(left: Int, right: Int): Int {
        return when (symbol) {
            "+" -> left + right
            "-" -> left - right
            "*" -> left * right
            "/" -> left / right
            else -> throw UnknownOperatorException()
        }
    }
}
