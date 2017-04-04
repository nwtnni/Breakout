# Breakout
Simple implementation with the javafx library.

Usage: java -jar Breakout.jar \<FLAG\> \<STAGE\>

\<FLAG\> is exactly one of:
- "-a" for autoplay (because who actually wants to play breakout) 
- "-k" for keyboard control (A for left, D for right) 
- "-m" for mouse control 

\<STAGE\> is exactly one of
- "1" for up to 4 layer bricks, 5 rows, same layer count per row
- "2" for up to 8 layer bricks, 5 rows, same layer count per row
- "3" for up to 12 layer bricks, 5 rows, same layer count per row
- "4" for up to 12 layer bricks, 10 rows, random layer count per brick
