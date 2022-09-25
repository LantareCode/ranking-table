@echo off
echo When prompted for file path enter any of the following to test:
echo Valid sample input: ./test/valid-input.txt
echo Large input: ./test/large-input.txt
echo Invalid/None existing file: ./test/not-here.txt
echo File without extention: ./test/no-extention
echo File with error: ./test/error-input.txt
echo -----
cd ..
javac RankingTable.java
java RankingTable
pause