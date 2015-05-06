find -path "*/*.java" > source.txt
if [ -d bin ];
	then
		rm -rf bin
fi
mkdir bin
javac -encoding UTF-8 -d bin @source.txt
rm source.txt
jar cvfe GameBot.jar command.GameBot -C bin .