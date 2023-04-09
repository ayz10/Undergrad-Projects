module bankgui.gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens bankgui.gui to javafx.fxml;
    exports bankgui.gui;
}