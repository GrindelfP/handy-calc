package to.grindelf.handycalc

import javafx.application.Application
import to.grindelf.handycalc.application.HandyCalcApplication

object HandyCalc {

    @JvmStatic
    fun main(args: Array<String>) {
        Application.launch(HandyCalcApplication::class.java)
    }

}
