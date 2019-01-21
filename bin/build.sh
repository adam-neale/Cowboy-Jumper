# This script builds and preverifies the code 
# for the tumbleweed game.

# reset this variable to the path to the correct javac
# command on your system:
JAVA4_HOME=/usr/java/j2sdk1.4.0_01/bin
# reset this variable to the corresct path to the WTK2.0
# directory of the WTK2.0 toolkit that you downloaded:
WTK2_HOME=../../../../WTK2.0

echo "clear directories"
# it's better not to leave old class files lying 
# around where they might accidentally get picked up 
# and create errors...
rm ../tmpclasses/net/test/hello/*.class
rm ../classes/net/test/hello/*.class
rm ../tmpclasses/net/test/jump/*.class
rm ../classes/net/test/jump/*.class

echo "Compiling source files"

$JAVA4_HOME/javac -bootclasspath $WTK2_HOME/lib/midpapi.zip -d ../tmpclasses -classpath ../tmpclasses ../src/net/test/hello/*.java ../src/net/test/jump/*.java

echo "Preverifying class files"

$WTK2_HOME/bin/preverify -classpath $WTK2_HOME/lib/midpapi.zip:../tmpclasses -d ../classes ../tmpclasses

echo "Jarring preverified class files"
$JAVA4_HOME/jar cmf MANIFEST.MF jump.jar -C ../classes .

echo "Updating JAR size info in JAD file..."

NB=`wc -l jump.jad | awk '{print $1}'`
head --lines=$(($NB-1)) jump.jad > jump.jad1
echo "MIDlet-Jar-Size:" `stat -c '%s' jump.jar`>> jump.jad1
cp jump.jad1 jump.jad
