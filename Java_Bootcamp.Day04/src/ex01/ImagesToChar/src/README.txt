# deleting and creating the target directory
rm -rf target && mkdir target

# set the destination directory for class files
javac `find . -name "*.java"` -d ./target

# copying resources to the target directory
cp -R src/resources ./target/.

# creating new jar archive (с) with file name (f) and manifest information (m)
jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C ./target .
chmod u+x target/images-to-chars-printer.jar

java -jar ./target/images-to-chars-printer.jar . 0