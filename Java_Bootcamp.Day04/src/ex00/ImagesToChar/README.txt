# deleting and creating target directory
rm -rf target && mkdir target

# set the destination directory for class files
javac `find . -name "*.java"` -d ./target

# specify where to find user class files
java -classpath ./target edu.school21.printer.app.Program WHITE_SYMBOL BLACK_SYMBOL PATH_TO_THE_FILE
example: java -classpath ./target edu.school21.printer.app.Program . 0 /home/regulusb/java/it.bmp
