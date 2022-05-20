module org.openjfx.javaproject {
    requires javafx.controls;
    exports org.openjfx.javaproject;
    requires transitive javafx.graphics;
	requires java.sql;
}
