module rentalApp {
    requires javafx.fxml;
    requires javafx.controls;
    exports pl.rentalApp;
    exports pl.rentalApp.controllers;
    opens pl.rentalApp.controllers to javafx.fxml;
    opens pl.rentalApp to javafx.fxml;
    opens  pl.rentalApp.models to javafx.base;

}