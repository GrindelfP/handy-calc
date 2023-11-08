package to.grindelf.handycalc.domain.calculator

/**
 * Abstract class which represents a token.
 * The token is a part of equation that is being calculated.
 * It can be either an operator or an operand.
 */
abstract class Token {
    abstract val text: String
    abstract val type: TokenType
}

/**
 * Enum class representing the type of the token: operator or operand.
 */
enum class TokenType {
    OPERATOR, OPERAND
}

/**
 * Class representing an operand.
 * Each operand has a value which is stored as double.
 *
 * By the time the token is created, it is already asserted that it is a valid operand.
 *
 * The value is used to perform operations.
 *
 * The class also stores the stringed representation of the operand and its type, which is OPERAND.
 *
 * The class inherits from [Token] class.
 */
data class Operand(
    override val text: String,
    override val type: TokenType = TokenType.OPERAND
) : Token() {

    val value: Double = text.toDouble()

    operator fun plus(right: Operand): Double = value + right.value

    operator fun minus(right: Operand): Double = value - right.value

    operator fun times(right: Operand): Double = value * right.value

    operator fun div(right: Operand): Double = value / right.value
}


/**
 * Class representing an operator.
 *
 *  * By the time the token is created, it is already asserted that it is a valid operator.
 *
 * Each operator has a symbol and a precedence.
 * The precedence is used to determine the order of operations.
 * It is evaluated from 1 to 2, where 1 is the precedence of + and -
 * and 2 is the precedence of * and /.
 *
 * The class also stores the stringed representation of the operator and its type, which is OPERATOR.
 *
 * The class inherits from [Token] class.
 */
data class Operator(
    override val text: String,
    override val type: TokenType = TokenType.OPERATOR
): Token() {
    val precedence: Int

    /**
     * It is required that the symbol is one character long.
     * If so, the precedence is set according to the symbol.
     */
    init {
        require(text.length == 1) { "Operator symbol must be one character long" }

        precedence = when (text) {
            "+", "-" -> 1
            else -> 2
        }
    }

    /**
     * Returns the result of the operation between the two given integers.
     */
    fun operate(left: Operand, right: Operand): Operand {
        val operationResult = when (text) {
            "+" -> left + right
            "-" -> left - right
            "*" -> left * right
            else -> left / right
        } .toString()

        return Operand(operationResult)
    }
}
