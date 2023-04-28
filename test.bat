@ECHO OFF
javac Main.java
ECHO ------------------------------------------------------
ECHO Normal case test:.......
java Main.java normal.arxml
ECHO normal_mod.arxml generated successfully
ECHO ------------------------------------------------------
ECHO Not valid file extension case test:.......
java Main.java normal.txt
ECHO ------------------------------------------------------
ECHO Empty file case test:.......
java Main.java Empty.arxml
ECHO ------------------------------------------------------
pause
