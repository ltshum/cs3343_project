#!/bin/bash

read -p "Name your .jar file: " name

if [ -d build ]; then
    rm -rf build
fi
mkdir build
javac -verbose -d ./build View/Main.java

cd build
jar -cvfe $name.jar View/Main *
echo ""
echo "$name.jar successfully created."

echo java -jar $name.jar > Run.bat
echo "Run.bat successfully created."
echo java -jar $name.jar > Run.sh
echo "Run.sh successfully created."
echo "To run $name.jar, execute Run.bat or Run.sh"

echo ""
echo "To complete the project, move $name.jar, Run.bat and Run.sh from 'build' folder to 'Release' folder."
echo "After everything is done, delete 'build' folder and both Release executables."