League of Warriors – part II
I have implemented the patterns the homework required. I started with Singleton,
I made the constructor in Game private and then implemented a static method that 
returns the instance for game and used this for the game instance.

The next pattern was Builder Pattern. I implemented this in Information, there are 
methods like setName, setCountry and also the build one. All of them use Builder. 
The Factory Pattern helps me create the characters without specifying the exact 
class of the characters (Warrior, Mage, Rogue). The CharacterFactory class creates 
a new character based on a given type of character. When the player selects the 
character, I use this pattern and this ensures that the correct type is 
instantiated without needing to specify it.

The last one, Visitor Pattern, implements the interfaces. I have implemented the 
accept method in the type of character classes (Warrior, Mage, Rogue) and it calls 
the visit method that I have implemented in the type of spell classes. Based on the 
type of the character it sets the health after damage. Also, this methods apply for 
the Enemy class too. This helped me perform specific actions without changing their 
classes.

For the interface, I started by creating the main menu. I set an image as the 
background and add buttons and text on top of it. I did that by overwriting the 
method paintComponent which facilitates the use of images. I generated two buttons: 
New Game and Exit. I used ActionListener to implement the action behind the button. 
As for the New Game button, it will display a new window and as for the Exit one, 
it will close the program. I used different methods to color the text, add font, 
add bounds to place it exactly where I wanted.

The New Game button generates the window where the user has to authenticate. Again 
I set another picture as background and add boxes where the user will write its 
email and password. For the password I used JPasswordField to keep the password 
secret. Lastly, there is a Login button, which checks if the account exists, if 
not, the user can rewrite a new email and a new password. JOptionPane will show 
a small window signaling the user that the credentials are not correct.

If the account exists, the user can choose the character from the new window. As 
previously, I set the background image and write the available character for the 
selected account. In the generated box, the user will enter the index of the chosen 
character and using that index, I will later display info about the character.

After choosing the character, a new window will be displayed and the grid will 
be shown. In the left corner are 5 buttons, for moving north, south, east, west 
and an exit one. By clicking one of them, the player cell will either move or 
the user will return to the main menu. Below the buttons, I display the info 
about the character, like experience, level, health and mana. Along the game, 
I keep those updated. And in the center and right part, the grid is displayed, 
the player will be blue as the picture in the homework shows. While moving, 
I handle each type of Cell. I used SwingUtilities.invokeLater to synchronize 
threads. I changed the moving methods a little bit to ensure the grid is 
updated correctly. To handle the cells, I have implemented a method that
based on the type of the cell it does specific actions. For the SANCTUARY 
type, it regenerates health and mana and updates the info panel. For the 
PORTAL type, it advances to the next level by displaying a new grid. I do 
that by deleting all previous panels and generate new ones to overwrite 
the old grid. I refresh the window and the game goes on as usual. For 
the ENEMY type, I implemented a separate class to handle the attack. 
The exit button display the final page, where info will be
displayed.

To handle the enemy, I generate a new window, with two pictures that represent 
the enemy and the character. Based on the type of the character (Warrior, 
Mage, Rogue) a different image will be displayed. Below the images, based 
on the type of the entity, the current health and mana are displayed. This 
are updated along the fight. I also generated two buttons. One for regular 
attack. It uses the methods already implemented with few changes in displaying 
different messages, it shows the grid again if the enemy was defeated and the 
final page if the player loses. One for abilities. I call the methods from 
another class. This displays three pictures representing the abilities and 
below it prints the abilities of the character and a box where the user will 
choose which ability to use by entering the index of the ability and then 
press enter. Based on the index the character will use the ability or the 
regular attack keeping the known rules. Like in the regular attack, the 
grid will be shown again or the final page.

The final page shows an image for ending, displays the info of the character, 
current level, experience, maps completed and name. The user can return to 
the menu where it can choose rather to start a new game or exit the game.
The implementation of this homework took me a couple of days. The hardships 
I faced were related to the interface. It was quite difficult to synchronize 
the classes, jump from one window to another and then going back to them and 
to keep the initial implementation. In terms of the degree of difficulty, 
it was average.
