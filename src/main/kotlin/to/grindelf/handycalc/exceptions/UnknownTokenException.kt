package to.grindelf.handycalc.exceptions

/**
 * Exception thrown when the token is unknown.
 * If it is not an operand or one operator
 * then this Exception is thrown.
 */
class UnknownTokenException(
    stringedToken: String
) : Exception() {

    override val message: String = "Unknown token $stringedToken"

    /**
     * Secondary constructor for the case when the token is unknown
     * but the stringed representation of the token is not specified
     * for the Exception message.
     */
    constructor() : this("")
}
