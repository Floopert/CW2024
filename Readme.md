GitHub: https://github.com/Floopert/CW2024.git

----------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------
Compilation Instructions:






----------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------
Implemented and Working Properly: This section only lists additional features/extensions and fine tuning works. Bug fixes or refactoring works are described in 'Modified Java Classes' section below.

- fine tuned so that each bullet can now only register collision with one enemy at a time. In the original code, one bullet can register collision with multiple enemies if enemy hitboxes overlap closely.

- fine tuned so that only kills from user projectiles will add to the kill score for progression of next level. Collision between user plane with enemy plane OR enemy plane reaching end of screen now only deducts life, but score does not increase. E.g. the kill score required to progress from level one to two is 10. If user projectile destroyed 6 planes, user plane collided with 2 planes, 2 enemy planes reached the left edge of screen, total kill score is still only 6 and user cannot progress to level two yet. However, 4 lives will already have been deducted.

- fine tuned so that hitboxes of all objects are now more closely wrapped around the actual image. The initial hitbox from the original was too huge due to redundant white spaces in png image. Scale and x,y position of all images are also adjusted to match the newly modified images.



----------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------
Implemented but Not Working Properly:





----------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------
Features Not Implemented:

- add kill score in LevelOneLevelView.java






----------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------
New Java Classes:

- LevelViewLevelOne.java (TO ADD LOCATION OF FILE)
    -a subclass of LevelView, since LevelView is made into an abstract class, all levels will inherit from it to create a concrete class so that objects could be instantiated
    -LevelView will only handle adding and removing general images from the scene. General images means images that are applicable throughout all levels such as hearts, win image and game over image.
    -subclasses of LevelView such as LevelViewLevelOne will define other images to add to scene that are applicable only to that level.


- Interface: BossEventListener.java (TO ADD LOCATION OF FILE)
    -acts as the event listener interface for any events triggered by the Boss plane.
    -when boss' shield is activated or deactivated, the events are handled by this interface and appropriate actions are taken by its registered listeners








----------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------

Modified Java Classes: This section shall only include modifications to classes due to bug fix or refactoring. Any code modification/addition for feature extension or fine tuning is not mentioned here, the nature of the extended feature or fine tuning is briefly described in the 'Implemented and Working Properly' section above. Each paragraph in this section may lump several classes together, to signify that they were modified together to complete a single bug fix. Paragraphs may mention similar class again, but for different bug fix.



-  ShieldImage.java [BUG FIX]
    Objective: To fix a bug where an alert window errors pops up just after 

    -amended path stored in IMAGE_NAME constant. amended so that the constant stores the correct path to the image and can be used
    -in the setImage() method, removed the String passed in the method and replaced with the IMAGE_NAME constant. the string had to be replaced because it is pointing to a wrong file name, and placing a constant instead of a raw String provides better code readability.


- Controller.java [BUG FIX]
    Objective: To fix a bug that freezes when game changes between levels and ends up taking a lot of RAM

    -when calling goToLevel() method in the launchGame() method, wrapped the goToLevel() method in a try-catch block and added a printStackTrace for any caught errors. This change makes for easier debugging when an error occurs.
    -in the update() method, the old level is removed from the Observer List with the line arg0.deleteObserver(). The controller will not listen to any events happening from the old level anymore when it's switching to a new level.


- Controller.java | LevelOne.java | LevelTwo.java [REFACTOR]
    Objective: To implement Singleton design pattern for LevelOne and LevelTwo class. This prevents excess memory usage when switching between levels. In the original code, when switching between levels, there is a short window where several new instances of LevelTwo objects could be made, resulting in memory wastage. 

    -LevelOne.java & LevelTwo.java: Creates a new private static instance that will store the object of the ONLY copy of the class. Changed the constructor's access modifier to private so that objects cannot be created outside the class. Added getInstance() method that creates the object of the class ONLY if the instance variable is null. The getInstance() method returns the reference to the instance object so that outside classes could access the instance.

    -Controller.java: Removed line that dynamically calls the constructor of a class. Instead, replaced it with a dynamic call of a class' method (the getInstance method). This way, the LevelOne and LevelTwo can never have more than one instance at runtime since no objects could be created outside of the class.


-  LevelViewLevelTwo.java | LevelView.java | LevelOne.java | LevelTwo.java | LevelParent.java | Boss.java [BUG FIX & REFACTOR]
    Objective: To fix bug where the shield image does not appear in level two.

    -LevelViewLevelTwo.java: Removed the call to addImagesToRoot() method in the constructor. This is the main reason the image is not showing on scene, because the shield image is rendered before the background, so the shield image is blocked by the background. Changed access modifier to public for addImagesToRoot() method since this is changed to an abstract method declared in LevelView (parent class).

    -LevelView.java: Changed the class to an abstract class so that it is now a blueprint for all levels and subsequent levels. It is now tasks to only hold and add images to scene that are relevant ONLY to all levels such as heart, win image and game over image. Added addImagesToRoot() method that will be overriden by its child classes to add all images relevant to a specific level to root.

    -LevelOne.java: Changed instantiateLevelView() method to instantiate an instance of LevelViewLevelOne instead of LevelView because LevelView is no longer a concrete class.

    -LevelTwo.java: Subscribed LevelTwo to be a listener for BossEventListener events such as shieldActivated or shieldDeactivated. Created the response method for shieldActivated() and shieldDeactivated() method from implementation of BossEventListener. shieldActivated() will set shield as visible while shieldDeactivated() will do the opposite.

    -LevelParent.java: In the initializeScene() method, called addImagesToRoot() method. This will ensure that all images relevant specific only to any other future levels will be able to be added to scene, without having to add anything new to the code. Since all other levels will inherit the addImagesToRoot() method and override it to include all 'level-specific' images to it.

    -Boss.java: Added BossEventListener array list to store all subscribers. Added methods addEventListener() and removeEventListener() to add or remove subscribers from the array list. In activateShield() and deactivateShield() methods, added the event trigger to notify all subscribers that shield has been activated or deactivated, and will trigger the relevant BossEventListener methods.



----------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------
Unexpected Problems: