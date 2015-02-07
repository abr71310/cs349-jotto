# cs349-jotto

The Jotto game I made for CS349 (Fall 2014 iteration). Built in ~9 hours from just the Word and WordList Java starter classes (MVC built from scratch)

## README - Michael Shao - Last updated: November 7, 2014 - V0.9.0.0.3.1.13
==========================================================
### Basic Compilation & Running the Application
`make run` makes the proper files and targets, then runs the main game (found in jotto)

### COMPILATION NOTES
- Make sure you do this in the a3/ folder and not before or after it (it won't work anywhere else).
-- old and other are WIPs that were interfering with the main files, but that I wanted to keep track of just in case.
-- `make clean` cleans up all of the class files, because it pollutes things, and we don't like pollution.

### Bugs and other annoying things
- You have to keep erasing the text in the text field before entering a guess - this is to preserve the length of the box.
-- Sorry about that... there's no simple way to put a placeholder without invoking a crapton of other useless things.

### APPLICATION NOTES
- Enter a number in the dialog box between 0-2 to generate a word based on the difficulty.
- Enter a string (5 characters) to set a word target.
- Left-hand side shows words guessed, partial / exact matches, and lists them as you guess.
- Shows dialog windows for any error during input, or at game end!
- Press the corresponding button to see what letters have been used.
- The counter in the right-middle area is how many guesses remain.
- DON'T PRESS CANCEL ON ANY OF THE DIALOG BOXES (it crashes the program).
- Press the hint buttons if you get stuck.

#### Three views to note:
ResponseView - Past guesses, partial and exact matches.
GuessView - allow user to input guesses, and do stuff regarding that.
JottoViewer - kind of like a hints section; has letters guessed, as well as how many guesses remain

### Random other notes

Layouts were definitely annoying, so they don't align really beautifully or anything, but it's functional!

### Comments / Questions / Feedback

Contact me via this repo (comment in code), or via Twitter (same username). Thanks for stopping by!