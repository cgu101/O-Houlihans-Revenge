# game
First project for CompSci 308 Fall 2015

Name: Connor Usry

Date started: Thursday August 27, 2015

Date finished:Friday September 4, 2015

Hours worked: ~30

Resources used: 
    1) Oracle Javafx Docs
    2) Stack Overflow
    3) ASG Teach
    4) Youtube Tutorials 
    5) Sprite Resources:
        - http://www.deviantart.com/morelikethis/collections/158015311?view_mode=2
        -http://nyangelinajoliemariekneeney.tumblr.com/post/43117337827/thatsmoderatelyraven-i-identify-myself-as
        

Main class file:
    -Main.java

Data (non Java) files needed:
    - duck.png
    - patchesMenu.png
    - patchesStand.png
    - stand.png
    - villainStand.png

How to play the game:
    - Training Level:
        Your player can move up, down, left, right in the 2D space confined by your side of the court. The 
        point of this level is to avoid the dodgeballs being thrown at you by Patches O'Houlihan on the other
        side. You win this level by managing to avoid the dodgeballs for the allocated time
    - Battle Level:
        Your player can move left/right or jump/duck in the 2D space confined by your side of the court. The
        point of this level is to both avoid the dodgeballs being thrown at you by White Goodman on the other
        side of the court, while trying to gather dodgeballs and throw them at White to reduce his lives to zero.

Keys/Mouse input:
    -Training Level:
        Up Arrow -> move up
        Down Arrow -> move down
        Left Arrow -> move left
        Right Arrow -> move right
        Mouse Click -> Hit start to start game
    
    -Battle Level:
        Up Arrow -> jump
        Down Arrow -> duck
        Left Arrow -> move left
        Right Arrow-> move right
        Spacebar -> throw ball (if already picked one up)
        Mouse Click -> Hit start to start game
            
Cheat Keys:
    Once inside a training or battle level. Before you hit the 'start game' button.
    
    -Increase/Decrease Your Lives:
        L -> increase your lives.
        D -> decrease your lives.
    -Increase/Decrease Time
        T -> increase time.
        Y -> decrease time.
        
        
Known bugs:
    1) The transition between levels with the popup would unpredictably create a popup (I believe it's due to my 
    implementation of the getGameStarted() conflicting with the gameTimeRemaining).
        edit: I believe I resolved this, however still makes mac "dink" noise like it is trying to do a popup.
    
    2) Issues with the resource build path for images. I was able to get it to work temporarily by directly configuring
    it from Terminal but despite rerouting the path through IntelliJ to include the entire 'main' folder it wasn't
    able to fill the resources stream. However, the static images seem to be stable to access using the route 
    once 'main' was set inside the build route and the inputSteam for example.png was set to 
    "main/resources/images/example.png"... which is where all my static images are stored. 
    
Impressions/Suggestions:
   Impressions: 
    I honestly had a blast creating this game. I have never built a fully scaled up game before so it was awesome
    being able to completely think of/design/implement the entire game.  That being said it took significantly longer
    than I had originally imagined.  I think a lot of my difficulty stems from the fact that I know I am not 
    correctly using my abstract classes correctly. I often found myself confused by when/if I should make variables or
    methods public/private within the subclasses or abstract classes.  After becoming frustrated I unfortunately
    found myself using "protected" which was suggested by intelliJ's autoFill.  While it works correctly I know
    that it was not the correct implementation.  I think for my next assignment I need to spend more time beforehand
    actually mapping out how I will end up implementing the game by setting up some sort of MVC diagram as well
    as better defining the individual models/abstract classes.
    
   Suggestions:
    I think that the class and recitation time within the first 2 weeks would be better spent teaching us more
    about the overall architecture of how to set up a javaFX game.  While we talked and read about them somewhat,
    I still found myself quite confused at points as to stuff like
        -where we should be able to access the main stage
        -which sort of stuff should be controlled by the step methods vs. keyInputs/mouseInputs
        -MVC architecture.
    While this project gave me great exposure to the innerworkings of JavaFX, I still feel like I don't have a
    great understanding of the entire design architecture.
    
   
