package to.grindelf.handycalc.domain.calculator

import to.grindelf.handycalc.exceptions.UnknownTokenException
import java.util.*


object Calculator {

    fun calculate(equation: List<String>): Double {
        val operandStack = Stack<Operand>()
        val operatorStack = Stack<Operator>()

        equation.forEach { stringedToken ->

            val token: Token // generate token from stringed representation of operator or operand
            try {
                token = generateToken(stringedToken)
            } catch (exception: UnknownTokenException) {
                throw exception
            }

            // at this point unknown tokens should be filtered out

            // If token is an operand
            if (token.type == TokenType.OPERAND) {
                operandStack.push(token as Operand)
            } else {
                // If the token is an operator
                val operator = token as Operator
                // While the operator stack is not empty and the top operator
                // has the highest precedence within the stack's operators,
                // pop the top two operands and the top operator and perform operation
                while (operatorStack.isNotEmpty() && operatorStack.peek().precedence >= operator.precedence) {
                    val right = operandStack.pop()
                    val left = operandStack.pop()
                    val topOperator = operatorStack.pop()
                    operandStack.push(topOperator.operate(left, right))
                }
                // Push the operator to the operator stack
                operatorStack.push(operator)
            }
        }
        // Evaluate the remaining operands and operators
        while (operatorStack.isNotEmpty()) {
            // Pop the top two operands and the top operator
            val right = operandStack.pop()
            val left = operandStack.pop()
            val topOp = operatorStack.pop()
            // Perform the operation using the operate() function and push the result
            operandStack.push(topOp.operate(left, right))
        }

        return operandStack.pop().value
    }

    private val OPERATORS = listOf("+", "-", "*", "/")
    private val OPERANDS = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")

    private fun generateToken(stringedToken: String): Token {
        return if (stringedToken.isOperator()) {
            Operator(stringedToken)
        } else if (stringedToken.isOperand()) {
            Operand(stringedToken)
        } else {
            throw UnknownTokenException(stringedToken)
        }
    }

    private fun String.isOperator(): Boolean {
        return OPERATORS.contains(this)
    }

    private fun String.isOperand(): Boolean {
        var isOperand = true
        for (i in this.indices) {
            if (!OPERANDS.contains(this[i].toString())) {
                isOperand = false
                break
            }
        }

        return isOperand
    }
}
