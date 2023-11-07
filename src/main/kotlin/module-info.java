module to.grindelf.handycalc {

    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens to.grindelf.handycalc to javafx.fxml;
    exports to.grindelf.handycalc;

    opens to.grindelf.handycalc.application to javafx.fxml;
    exports to.grindelf.handycalc.application;

    opens to.grindelf.handycalc.controllers to javafx.fxml;
    exports to.grindelf.handycalc.controllers;

}