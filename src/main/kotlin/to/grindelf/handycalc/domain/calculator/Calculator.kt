package to.grindelf.handycalc.domain.calculator


object Calculator {

    private val OPERATORS = listOf("+", "-", "*", "/")
    private val PRECEDENCE = mapOf("+" to 1, "-" to 1, "*" to 2, "/" to 2)

    fun calculate(expressionAsList: List<String>): Int {
        TODO()
    }
}

