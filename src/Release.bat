@set /p name="Name your .jar file: "

@if exist build rd /s /q build
mkdir build
javac -d ./build View/Main.java

cd build
jar -cfe %name%.jar View/Main *
@echo:
@echo %name%.jar successfully created.

echo @java -jar %name%.jar > Run.bat
@echo:
@echo Run.bat successfully created.
@echo To run %name%.jar, execute Run.bat

@echo:
@echo To complete the project, move %name%.jar and Run.bat from 'build' folder to 'Release' folder.
@echo After everything is done, delete 'build' folder and this .bat file.

@pause