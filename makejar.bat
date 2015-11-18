chcp 65001
dir /S /B *.java > sources.txt
mkdir classes
javac -encoding UTF-8 -d classes @sources.txt
del sources.txt
jar cvfe GameBot.jar command.GameBot -C classes .
rmdir /S /Q classes
