package to.grindelf.handycalc.domain.calculator

import to.grindelf.handycalc.exceptions.UnknownTokenException
import java.util.*

/**
 * Object-toolbox for calculator part of the application.
 * It contains the [calculate] function that calculates the result of the equation
 * and multiple supporting and extension functions.
 */
object Calculator {

    /**
     * Function that calculates the result of the equation.
     *
     * @param equation the equation to be calculated, represented
     * as [List] of [String], where each element is either an operator or an operand.
     *
     * @return the result of the equation as [Double].
     */
    fun calculate(equation: List<String>): Double {
        val operandStack = Stack<Operand>()
        val operatorStack = Stack<Operator>()

        equation.forEach { stringedToken ->
            val token = generateValidToken(stringedToken)

            if (token.type == TokenType.OPERAND) processOperand(token, operandStack)
            else processOperator(token, operandStack, operatorStack)
        }

        evaluateRemaining(operandStack, operatorStack)

        return operandStack.pop().value
    }

    /**
     * List of operators that are supported by the calculator.
     */
    private val OPERATORS = listOf("+", "-", "*", "/")

    /**
     * Function that generates a valid token from the stringed representation of the token.
     * It is used to convert string representation of a token into
     * [Token] object and to assert that the token is valid.
     *
     * @param stringedToken the stringed representation of the token.
     *
     * @return the [Token] object from [stringedToken].
     */
    private fun generateValidToken(stringedToken: String): Token {
        val token: Token
        try {
            token = defineToken(stringedToken)
        } catch (exception: UnknownTokenException) {
            throw exception
        }

        return token
    }

    /**
     * Function that evaluates the remaining operators and operands.
     * At the end of the calculation, there can be some operands and operators left
     * and this function evaluates them.
     *
     * Params are passed by reference and that lets the function modify initial stacks:
     *
     * @param operandStack the stack of operands.
     *
     * @param operatorStack the stack of operators.
     */
    private fun evaluateRemaining(operandStack: Stack<Operand>, operatorStack: Stack<Operator>) {
        while (operatorStack.isNotEmpty()) {
            val right = operandStack.pop()
            val left = operandStack.pop()
            val topOperator = operatorStack.pop()
            operandStack.push(topOperator.operate(left, right))
        }
    }

    /**
     * Function that processes the operator.
     *
     * Params are passed by reference and that lets the function modify initial stacks:
     *
     * @param token the operator to be processed.
     *
     * @param operandStack the stack of operands.
     *
     * @param operatorStack the stack of operators.
     */
    private fun processOperator(token: Token, operandStack: Stack<Operand>, operatorStack: Stack<Operator>) {
        val operator = token as Operator
        while (operatorStack.isNotEmpty() && operatorStack.peek().precedence >= operator.precedence) {
            val right = operandStack.pop()
            val left = operandStack.pop()
            val topOperator = operatorStack.pop()
            operandStack.push(topOperator.operate(left, right))
        }
        operatorStack.push(operator)
    }

    /**
     * Function that puts the operand into the stack of operands.
     *
     * Param is passed by reference and that lets the function modify initial stacks:
     *
     * @param token the operand to be processed.
     *
     * @param operandStack the stack of operands.
     */
    private fun processOperand(token: Token, operandStack: Stack<Operand>) {
        operandStack.push(token as Operand)
    }

    /**
     * Function that defines the token.
     * It is used to convert string representation of a token into
     * either [Operator] or [Operand] object. Also, if the function is provided
     * with an unknown token (which does not fit
     * either of two categories), it throws [UnknownTokenException].
     *
     * @param stringedToken the stringed representation of the token.
     *
     * @return the [Token] object from [stringedToken].
     */
    private fun defineToken(stringedToken: String): Token {
        return if (stringedToken.isOperator()) {
            Operator(stringedToken)
        } else if (stringedToken.isOperand()) {
            Operand(stringedToken)
        } else {
            throw UnknownTokenException(stringedToken)
        }
    }

    /**
     * Extension-function of class [String] that checks
     * if the stringed representation of the token is an operator.
     *
     * @return true if the stringed representation of the token is an operator,
     * false otherwise.
     */
    private fun String.isOperator(): Boolean = OPERATORS.contains(this)

    /**
     * Extension-function of class [String] that checks
     * if the stringed representation can be interpreted as an operand (integer number).
     *
     * @return true if the stringed representation of the token is an operand,
     */
    private fun String.isOperand(): Boolean = this.toIntOrNull() != null
}
