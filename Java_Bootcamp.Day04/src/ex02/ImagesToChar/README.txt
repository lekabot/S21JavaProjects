#1. Recreate directories
    rm -rf target lib && mkdir target lib

#2. Download libraries
    curl -s https://repo1.maven.org/maven2/com/beust/jcommander/1.72/jcommander-1.72.jar -o lib/jcommander-1.72.jar
    curl -s https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.0/JCDP-4.0.0.jar -o lib/JCDP-4.0.0.jar

#3. Extract files and replace to directory 'target'
    cd target && jar xf ../lib/jcommander-1.72.jar && jar xf ../lib/JCDP-4.0.0.jar && cd ..

#4. Set the destination directory for class files
    javac -cp lib/JCDP-4.0.0.jar:lib/jcommander-1.72.jar: `find ./src -name "*.java"` -d ./target

#5. Copy resources
    cp -R src/resources ./target/.

#6. Create jar file
    jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C ./target .
    chmod u+x target/images-to-chars-printer.jar

#7. Run program
    java -jar ./target/images-to-chars-printer.jar --white=RED --black=GREEN
