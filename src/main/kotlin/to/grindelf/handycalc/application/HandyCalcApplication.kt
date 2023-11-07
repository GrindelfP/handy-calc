package to.grindelf.handycalc.application

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class HandyCalcApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HandyCalcApplication::class.java.getResource("main-view.fxml"))
        val scene = Scene(fxmlLoader.load(), WIDTH, HEIGHT)
        stage.title = TITLE
        stage.scene = scene
        stage.show()
    }

    companion object {
        const val TITLE = "Handy Calc"
        const val WIDTH = 960.0
        const val HEIGHT = 720.0
    }
}
