GitHub: https://github.com/Floopert/CW2024.git



# ----------------------------------------------------------------------------------------------------------------
# ----------------------------------------------------------------------------------------------------------------
Compilation Instructions: No special instructions, just run Main.java






# ----------------------------------------------------------------------------------------------------------------
# ----------------------------------------------------------------------------------------------------------------
Implemented and Working Properly: This section only lists additional features/extensions and fine tuning works. Bug fixes or refactoring works are described in 'Modified Java Classes' section below.

### fine tuned so that each bullet can now only register collision with one enemy at a time. In the original code, one bullet can register collision with multiple enemies if enemy hitboxes overlap closely. [Commit: 6258a12]


### fine tuned so that only kills from user projectiles will add to the kill score for progression of next level. Collision between user plane with enemy plane OR enemy plane reaching end of screen now only deducts life, but score does not increase. E.g. the kill score required to progress from level one to two is 10. If user projectile destroyed 6 planes, user plane collided with 2 planes, 2 enemy planes reached the left edge of screen, total kill score is still only 6 and user cannot progress to level two yet. However, 4 lives will already have been deducted. [Commit: 6258a12]


### fine tuned so that hitboxes of all objects are now more closely wrapped around the actual image. The initial hitbox from the original was too huge due to redundant white spaces in png image. Scale and x,y position of all images are also adjusted to match the newly modified images. [Commit: 5653dea]


### user plane can now move left and right as well. [Commit: 3ea6455]


### shield follows the boss' position instead of being static. [Commit: 861f25e]


### added FXML main menu page. The game now will start with a main menu where users can choose to click 'Play' to proceed to Level 1, or click 'Exit' to close the application. [Commit: 0c808d3]
    Important changes implemented: Since loading FXML pages are slightly different from loading a level directly, a separate function was created to handle the creation of FXML pages.

    -Controller.java: added goToFXML() and loadFXML() methods. The loadFXML() method will load the respective FXML file, and then the scene is created and set using the goToFXML() method. The goToFXML() method functions similarly to the goToLevel() method in the class but for FXML files instead. The launchGame() method now loads the main menu FXML first instead of Level 1.


### fine tuned so that the planes (User and Enemy) can never go above the hearts display or any graphcs displayed at the top of the level screen. [Commit: 6f8de6a]


### added FXML game over page.
    Important changes implemented: Keeps track of which level did the user lost. If the user chooses to retry, the user will restart at the level that they died on. E.g. if the user died during Level Two, they can retry and start from Level Two again.
    Alternatively, they can choose to go back to main menu and start from Level One all over again.

    -LevelEventListener.java: Added goToFXML() method as part of the interface method. So that LevelParent class can notify the controller class that the user has died, and should load the gameOver.fxml page.

    -Controller.java: goToFXML() is now an override method from the LevelEventListener.java interface. The loadFXML() method will now also set the level to reload (if any) when loading the FXML page.

    -LevelParent.java: Constructor now initializes the value of CURRENT_LEVEL to be passed to game over FXML page so that the FXML page has a reference of which level to reload. The loseGame() method now loads the game over FXML page instead of just displaying the game over image.

    -LevelOne.java | LevelTwo.java: Added a new constant CURRENT_LEVEL that stores the class' class path and passes it to the parent's constructor.


### added pause function.
    Important changes implemented: The game can now pause by pressing 'Escape'. In the pause menu, users can either choose to resume the game or go to main menu. If the user chooses to go to main menu, they will have to start from Level 1 again.

    -Controller.java: Broke up some lines from the goToLevel() method into a new method called getLevelInstance(). This method will allow the FXML controllers to get the instance of an active level. During a pause, the scene is changed but level instance is not destroyed yet because the user may choose to resume. But if the user chooses to go back to main menu, the instance should be destroyed so that if they click play again it may be reset. So an instance of the level needs to be retrieved when user chooses to go to main menu to destroy the instance.
    A resumeGame() method is also added to just get the instance of the level and show the scene.

    -InputEventListener.java: An additional interface method called pauseGame() is added so that the LevelParent could be informed that 'Escape' has been pressed and a pause screen should be displayed.

    -InputHandler.java: Added additional event listener for 'Escape' button.

    -LevelParent.java: The class now also implements InputEventListener so that it could react to the pause game action when an 'Escape' key is pressed. Added new getScene() method so that a temporarily paused screen could return its former scene and resume from where it stopped. pauseGame() method is added to pause the timeline and switch to the pause FXML page. resumeGame() method is added to resume the timeline if user chooses to resume from a pause.




# ----------------------------------------------------------------------------------------------------------------
# ----------------------------------------------------------------------------------------------------------------
Implemented but Not Working Properly:







# ----------------------------------------------------------------------------------------------------------------
# ----------------------------------------------------------------------------------------------------------------
Features Not Implemented:

### add kill score in LevelOneLevelView.java
### change frame rate of game
### visual improvements (CSS styling) for the menu pages (Main Menu, Game Over, Win Game, Pause Game)






# ----------------------------------------------------------------------------------------------------------------
# ----------------------------------------------------------------------------------------------------------------
New Java Classes:

### LevelViewLevelOne.java (com.example.demo.levelViews)
    -a subclass of LevelView, since LevelView is made into an abstract class, all levels will inherit from it to create a concrete class so that objects could be instantiated
    -LevelView will only handle adding and removing general images from the scene. General images means images that are applicable throughout all levels such as hearts, win image and game over image.
    -subclasses of LevelView such as LevelViewLevelOne will define other images to add to scene that are applicable only to that level.


### Interface: BossEventListener.java (com.example.demo.eventListeners)
    -acts as the event listener interface for any events triggered by the Boss plane.
    -when boss' shield is activated or deactivated, the events are handled by this interface and appropriate actions are taken by its registered listeners


### ActiveActorManager.java (com.example.demo.handlers)
    -stores all instantiated game characters (ActiveActorDestructible objects).
    -manages all game character objects in a level (gets and sets the list of ActiveActorDestructible e.g. friendlyUnits, enemyUnits etc)
    -handles all frame updates of the game characters


### InputHandler.java (com.example.demo.handlers)
    -takes the ImageView object passed into it during instantiation and setting that ImageView as the receiver of user input.
    -this class also handles user input.


### Interface: InputEventListener.java (com.example.demo.eventListeners)
    -acts as the event listener interface for any relevant input received by InputHandler.java class.
    -e.g. user presses 'space bar' will trigger fireProjectile() in ActiveActorManager class.


### CollisionHandler.java (com.example.demo.handlers)
    -this class handles all the logic to check if a collision had occurred, and if yes, which object had collided.
    -this class also checks for collision with the screen edge (to see if object has left the scene).
    -this class has listeners subscribed to it so if any relevant collision event occurs, the relevant classes will be notified to give proper reaction.


### Interface: CollisionEventListener.java (com.example.demo.eventListeners)
    -acts as the event listener interface for any relevant collision events triggered by CollisionHandler.java class.
    -e.g. user projectile collides and destroys enemy plane will trigger updateKillCount().


### Interface: LevelEventListener.java (com.example.demo.eventListeners)
    -acts as the event listener interface for any level changing events triggered by LevelParent.java class.
    -allows LevelParent.java to inform the Controller.java class to switch levels


### FxmlController.java (com.example.demo.controller)
    -the parent class for all FXML pages.
    -the main purpose of this class is to pass the Controller.java object reference to the FXML controllers so that it can change the scene to playable levels.
    -the initial idea was to inherit directly from the Controller.java class, but since that is made a singleton, it is not possible to inherit from it (due to private access modifier of the constructor).


### MenuController.java | GameOverController.java | PauseController.java (com.example.demo.controller.fxmlPageControllers)
    -the controllers for the FXML page
    -all logic for the buttons in the FXML pages are handled in their respective controllers.




# ----------------------------------------------------------------------------------------------------------------
# ----------------------------------------------------------------------------------------------------------------

Modified Java Classes: This section shall only include modifications to classes due to bug fix or refactoring. Any code modification/addition for feature extension or fine tuning is not mentioned here, the nature of the extended feature or fine tuning is briefly described in the 'Implemented and Working Properly' section above.
Each paragraph in this section may lump several classes together, to signify that they were modified together in one commit, to complete a single bug fix or as one refactoring job. Paragraphs may mention similar class again, but for different bug fix or refactoring job.
The order of the list is in ascending order of commits, with the top being the earliest commit.


### ShieldImage.java [BUG FIX]
    Objective: To fix a bug where an alert window errors pops up just after 

    -amended path stored in IMAGE_NAME constant. amended so that the constant stores the correct path to the image and can be used
    -in the setImage() method, removed the String passed in the method and replaced with the IMAGE_NAME constant. the string had to be replaced because it is pointing to a wrong file name, and placing a constant instead of a raw String provides better code readability.


### Controller.java [BUG FIX]
    Objective: To fix a bug that freezes when game changes between levels and ends up taking a lot of RAM

    -when calling goToLevel() method in the launchGame() method, wrapped the goToLevel() method in a try-catch block and added a printStackTrace for any caught errors. This change makes for easier debugging when an error occurs.
    -in the update() method, the old level is removed from the Observer List with the line arg0.deleteObserver(). The controller will not listen to any events happening from the old level anymore when it's switching to a new level.


### Controller.java | LevelOne.java | LevelTwo.java [REFACTOR]
    Objective: To implement Singleton design pattern for LevelOne and LevelTwo class. This prevents excess memory usage when switching between levels. In the original code, when switching between levels, there is a short window where several new instances of LevelTwo objects could be made, resulting in memory wastage. 

    -LevelOne.java & LevelTwo.java: Creates a new private static instance that will store the object of the ONLY copy of the class. Changed the constructor's access modifier to private so that objects cannot be created outside the class. Added getInstance() method that creates the object of the class ONLY if the instance variable is null. The getInstance() method returns the reference to the instance object so that outside classes could access the instance.

    -Controller.java: Removed line that dynamically calls the constructor of a class. Instead, replaced it with a dynamic call of a class' method (the getInstance method). This way, the LevelOne and LevelTwo can never have more than one instance at runtime since no objects could be created outside of the class.


### LevelViewLevelTwo.java | LevelView.java | LevelOne.java | LevelTwo.java | LevelParent.java | Boss.java [BUG FIX & REFACTOR]
    Objective: To fix bug where the shield image does not appear in level two.

    -LevelViewLevelTwo.java: Removed the call to addImagesToRoot() method in the constructor. This is the main reason the image is not showing on scene, because the shield image is rendered before the background, so the shield image is blocked by the background. Changed access modifier to public for addImagesToRoot() method since this is changed to an abstract method declared in LevelView (parent class).

    -LevelView.java: Changed the class to an abstract class so that it is now a blueprint for all levels and subsequent levels. It is now tasks to only hold and add images to scene that are relevant ONLY to all levels such as heart, win image and game over image. Added addImagesToRoot() method that will be overriden by its child classes to add all images relevant to a specific level to root.

    -LevelOne.java: Changed instantiateLevelView() method to instantiate an instance of LevelViewLevelOne instead of LevelView because LevelView is no longer a concrete class.

    -LevelTwo.java: Subscribed LevelTwo to be a listener for BossEventListener events such as shieldActivated or shieldDeactivated. Created the response method for shieldActivated() and shieldDeactivated() method from implementation of BossEventListener. shieldActivated() will set shield as visible while shieldDeactivated() will do the opposite.

    -LevelParent.java: In the initializeScene() method, called addImagesToRoot() method. This will ensure that all images relevant specific only to any other future levels will be able to be added to scene, without having to add anything new to the code. Since all other levels will inherit the addImagesToRoot() method and override it to include all 'level-specific' images to it.

    -Boss.java: Added BossEventListener array list to store all subscribers. Added methods addEventListener() and removeEventListener() to add or remove subscribers from the array list. In activateShield() and deactivateShield() methods, added the event trigger to notify all subscribers that shield has been activated or deactivated, and will trigger the relevant BossEventListener methods.


### LevelParent.java [BUG FIX]
    Objective: To ensure that all projectiles (user & enemy) will be destroyed when it is out of screen. All out of bounds projectiles will be flagged as isDestroyed so that the removeDestroyedActors() method will remove them from the array list.

    -added projectileIsOutOfScreen() method to check if projectile is out of bounds. Added destroyOutofBoundsProjectile() method to flag the projectile isDestroyed=True if projectileIsOutOfScreen() returns True. Added handleProjectileOutOfBounds() method to loop through each projectile and call destroyOutofBoundsProjectile() to flag all projectile isDestroyed=True if they are out of bounds.


### LevelParent.java [BUG FIX]
    Objective: Stop the old level's timeline before going to next level. Not doing this will result in incremental RAM usage as game goes longer.

    -added timeline.stop() in goToNextLevel() method.


### ActiveActorDestructible.java | BossProjectile.java | UserProjectile.java | EnemyProjectile.java | Projectile.java | Boss.java | UserPlane.java | EnemyPlane.java [REFACTOR]
    Objective: Remove all duplicate code in these files. Also removed all unused code in these files.

    -ActiveActorDestructible.java: Added implementation for updateActor() method instead of leaving it as abstract. Because most of the classes inheriting from it uses the same implementation. If any class will have special implementation, can just override it to add additional logic on top of the parent's logic.

    -BossProjectile.java | UserProjectile.java | EnemyProjectile.java | UserPlane.java | EnemyPlane.java: Removed the updateActor() method since it is similar to parent class implementation.

    -Projectile.java: Removed abstract method updatePosition() since it serves no purpose. The first declaration is already in the parent class, and the override in this class is just to declare it as abstract again.

    -Boss.java: In the overriden updateActor() method, instead of redefining the implementation, called super.updateActor() since that is always the same for all classes. Then only added whatever is specially required in this class for this method, in this case, the updateShield() method.


### LevelParent.java | LevelOne.java | LevelTwo.java | UserPlane.java [REFACTOR]
    Objective: To reduce the responsibility of LevelParent class by breaking up unrelated functions that are supposed to be in a separate class. The main bulk of this refactoring is the break up the responsibility of managing the ActiveActorDestructible objects (with the new ActiveActorManager class) and the handling of user input (with the new InputHandler class).

    -LevelParent.java: 
        -Moved the Lists of <ActiveActorDestructibles> (friendlyUnits, enemyUnits etc) to new class ActiveActorManager.java.
        -Added killCount integer variable to keep count of kill score instead of putting the function in the UserPlane.java. A getKillCount() method is added so that killCount data could be fetched by any child class (such as LevelOne).
        -The background ImageView variable is now set to protected, and the background object is instantiated in each specific level's class (LevelOne, LevelTwo). This is to reduce the redundancy to have to pass the background image name through the super's constructor (makes a cleaner constructor). But manipulation of the background ImageView is still done in LevelParent since the steps are going to be similar for all levels (don't need redundant repeating).
        -the initializeScene() method now creates the activeActorManager object and the inputHandler object, adds the background, adds user plane and display other general and level specific images (e.g. hearts or shield). showBackground() and showForegroundImages() methods are newly created and is called inside initializeScene() to do the above jobs. Purpose is just to break down into smaller responsibilities for each method.
        -initializeBackground() method is moved to InputHandler.java and is renamed to initializeUserControls(). Setting the image width, height, and adding to root is removed from this method (due to no relevance, but is moved to the initializeScene as mentioned above).
        -fireProjectile() is moved to InputHandler since it is related to user input action.
        -generateEnemyFire(), spawnEnemyProjectile(), addEnemyUnit(), updateActors(), removeAllDestroyedActors() and removeDestroyedActors() methods are all moved to ActiveActorManager since they are all related to handling activeActorDestructible behaviors and managing the objects' existence in the scene.

    -LevelOne.java | LevelTwo.java:
        -background ImageView object is now created in these classes. Since background has protected access modifier, it will then be accessed in its super class for the full scene construction (adding background, then add activeActorManager, creating input controller, spawn user, display hearts and other level specific images)
        -removed initializeFriendlyUnits() method. Since UserPlane object is already created in the super class, redundant to add the user plane to scene in this class. This is done in the super class, in the createLevel() method where all elements of the scene are added in methodical order (from background all the way to foreground).
        -LevelTwo.java now keeps a reference of LevelViewLevelTwo in levelview variable to carry out any level specific graphic manipulation (toggle shield visibility). Since LevelOne.java does not have such elements yet, not necessary to do the same, so no levelview variable in LevelOne.java class (but is designed to be able to do the same if future extensions require it).

    -UserPlane.java: Removed getNumberOfKills() and incrementKillCount() methods since these is considered as a 'game-wide' tracked data and not supposed to be tracked by the UserPlane object. The kill count is tracked in LevelParent.java now.



### LevelParent.java [REFACTOR]
    Objective: To achieve a more direct way in updating the kill count.

    -removed currentNumberOfEnemies integer variable.
    -removed updateNumberOfEnemies() method
    -modified the updateKillCount() method to directly increment the killCount variable as a way to keep track of kill count.
    -removed updateKillCount() from the updateScene() method, so that it now doesn't get called every frame. Instead, the updateKillCount() is called whenever a collision between a user bullet and an enemy plane occurs (provided the enemy gets destroyed)


### LevelParent.java [BUG FIX]
    Objective: Fix a bug where projectiles are not destroyed immediately even after visually out of screen. This is because 'out of bounds' was defined loosely previously. Now defined the 'out of bounds' more strictly.

    -added new method isOutOfScreen() to check if object has fully gone out of screen. The definition of 'out of bounds' is defined in this method. Previously the moment the plane's head hits the edge, it is considered as destroyed. Now, only when plane has fully passed the edge, only then it will be destroyed and deduct a life.
    -the projectileIsOutOfScreen() and enemyHasPenetratedDefenses() method uses the isOutOfScreen() method, so that the details of the 'out of bounds' only needs to be defined once in isOutOfScreen(), and makes the other methods that implement the 'out of bounds' check much more human readable.


### LevelParent.java | ActiveActorManager.java | InputHandler.java [REFACTOR]
    Objective: To break up the collision handling functionality from the LevelParent into a separate class (CollisionHandler.java). And to refactor the new code structure so that ActiveActorManager and CollisionHandler could interact with each other.

    -LevelParent.java:
        -the class now creates an object of CollisionHandler class so that all collision handling logic is carried out by CollisionHandler class object (reduces responsibility of LevelParent).
        -class implements CollisionEventListener interface to listen to collision events that damages user or updates kill score
        -handlePlaneCollisions(), handleUserProjectileCollisions(), handleEnemyProjectileCollisions(), handleCollisions(), handleEnemyPenetration(), handleProjectileOutOfBounds(), destroyOutofBoundsProjectile(), enemyHasPenetratedDefenses() and isOutOfScreen() methods have been moved to CollisionHandler.java class.
        -projectileIsOutOfScreen() method is deleted (redundant).

    -ActiveActorManager.java:
        -the class now implements InputEventListener so that the class can react to any user input relevant to is (e.g. call fireProjectile method to add projectiles to the List and root when user presses 'space bar').
    
    -InputHandler.java:
        -the class no longer requires a reference to the ActiveActorManager object. Since previously it only requires the object to add the projectile to scene, but now an event listener is implemented instead to inform ActiveActorManager to create a projectile.
        -fireProjectile() method is moved to ActiveActorManager class.


### ActiveActor.java | ActiveActorDestructible.java [REFACTOR]
    Objective: To remove the redundant ActiveActor.java class.

    -ActiveActor.java: This class is removed. All its relevant methods are moved to the ActiveActorDestructible class.

    -ActiveActorDestructible.java: Added the constructor initializations from the previous ActiveActor class. The moveHorizontally() and moveVertically() methods from the ActiveActor class is moved here.


### Controller.java | LevelParent.java [REFACTOR]
    Objective: To replace the deprecated Observer methods with event listener pattern (replaced with self-defined LevelEventListener.java class)

    -Controller.java: Controller.java class now implements LevelEventListener instead of Observer. The update() method from the Observer interface is now replaced with changeLevel() method from the LevelEventListener interface.

    -LevelParent.java: The class no longer extends Observer class since it is no longer used. Added addEventListener() and removeEventListener() to add listeners to any change level events triggered by LevelParent. In the goToNextLevel() method, instead of the Observer methods, it is now replaced with the LevelEventListener's changeLevel() method.


### Main.java | Controller.java [REFACTOR]
    Objective: Implementing Singleton design pattern for Controller.java class

    -Controller.java: access modifier for the constructor method is changed to private. A public method getInstance() is added for other classes to get the reference of the Controller class' object.

    -Main.java: Instead of creating a new instance of Controller, the class now only calls getInstance() method to get a reference of the single instance from Controller.


### FighterPlane.java | Boss.java | UserPlane.java | LevelOne.java [REFACTOR]
    Objective: To remove repeated Y upper and lower bound constants for the planes and to make the upper and lower bound values only defined at one source for all planes to reference.

    -FighterPlane.java: Added public static constants Y_UPPER_BOUND & Y_LOWER_BOUND for all planes to reference. It is made static so that no object is needed to be instantiated to use the value.

    -Boss.java | UserPlane.java | LevelOne.java: Replaced all local constant declarations of the Y upper and lower bound with the newly created static constant in FighterPlane.java. The local constant declarations are also deleted.


### ActiveActorDestructible.java | Destructible.java [REFACTOR]
    Objective: To remove the redundant Destructible interface

    -ActiveActorDestructible.java: The class no longer implements Destructible.java interface. The takeDamage() and destroy() methods no longer has @Override.

    -Destrutible.java: This class is deleted.


### GameOverImage.java | LevelView.java [REFACTOR]
    Objective: Remove the redundant class and fields after adding the game over FXML page feature. The game over image is no longer necessary since a separate FXML page will pop up upon losing the level. The game over image is shown in the FXML page instead.

    -GameOverImage.java: The class is deleted.

    -LevelView.java: The class no longer instantiates an object of GameOverImage.java, and the method showGameOverImage() is also removed. Other related fields tied to GameOverImage is also removed such as LOSS_SCREEN_X_POSITION & LOSS_SCREEN_Y_POSITION.




# ----------------------------------------------------------------------------------------------------------------
# ----------------------------------------------------------------------------------------------------------------
Unexpected Problems:

### At boss level, the longer the game runs, the more RAM it consumes.
    -Started by checking all the generated resources such as projectiles and plane objects. See if they are constantly being generated implicitly. Found out that the projectiles were not being destroyed when out of screen. Fixed that logic but problem still persisted.
    -Then disabled the function to generate projectiles, to see if RAM still increasing. Found out that it did, so most likely was not due to generation of objects.
    -Then found out that the RAM was still increasing at boss level even after death (Game Over). Which was not possible since there should be a timeline.stop() after death. Which then led to a realization that the first level timeline was never stopped because timeline.stop() was never called when switching level. Resources was continouously being taken due to a redundant timeline being run on the background. Added timeline.stop() before going to next level, problem solved.