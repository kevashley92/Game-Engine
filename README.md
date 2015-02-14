# Game-Engine
This is a game engine I created for a game engine course.  It is a simple multiplayer 2D platformer that only runs locally in two different windows.

To run any of the programs for this assignment, simply open up Eclipse IDE for Java Developers, and click File>Import

In the "Import" window, click on General>Existing Projects into Workspace.  Then click next.

Click on "Select archive file" then click "Browse" and navigate to where you saved the "Homework3.zip" file.  Check the box next to the project (should say "Game Engine") and then click Finish.

In addition, in Eclipse's Package Explorer, if core.jar, gluegen-rt.jar, and jogl-all.jar are not listed under Referenced Libraries, then they need to be added to the build path (these files can be found in your Processing directory on your machine).

In order to actually run the game, first run the MainProgram as a Java Application.  Once it starts and shows the game world, run a client as a java application.  Any number of clients can be run but only one MainProgram can run.  In order to control a player character, one must have a client window selected.

Controls:

Left - Left Arrow
Right - Right Arrow
Jump - Spacebar

The .java files that are included are as follows:

Client.java - This is part of both the Networking Basics and Putting it all Together sections of the homework.  In order to run, you have to be running either the SimpleServer or Server programs first.  Once you have one of those running, you can run the Client and type whatever you want, hitting enter to send a message to the server.  To run it, simply run it as a java application.

Server.java - This is the server corresponding to the Putting it all Together section of the homework.  It handles clients asynchronously.  You cannot run this program, as it is controlled by the MainProgram.

MainProgram.java - This is the program that handles all of the actions of the server in the Putting it all Together section of the homework.  Simply run the MainProgram as a java application to start the server.

And these are the files that make up the Game Object Model:

Character.java - represents a character in the game

Collision.java - the component for a game object that, when added to it, provides collision functionality.

DeathZone.java - represents a rectangular death zone that, when a character collides with it, spawns them back at a spawn point.

DynamicPlatform.java - represents a platform that moves with the passing of time.

GameObject.java - the main class that all objects int the game are derived from.

HiddenObject.java - a superclass for an object that has no rendering component.

Movement.java - the component for a game object that, when added to it, provides movement functionality.

PhysicalObject.java - a superclass for an object that has a rendering component.

Platform.java - a superclass for a platform that a character can collide with.

Player.java - represents the player in the environment.

Rendering.java - the component for a game object that, when added to it, provides rendering functionality.

SpawnPoint.java - represents a point at which characters can spawn at.

StaticPlatform.java - represents a platform that stays in place.

Event.java - represents an event in the game.

EventHandler.java - responsible for acting upon the events it is given.

EventManager.java - handles registering handlers for events as well as calling their onEvent() method when an event is raised.

EventQueue.java - represents the queue of events that are to be handled in order of delivery time.

ObjectSpawnEvent.java - represents the event in which an object is created.

PlayerCollisionEvent.java - represents the event in which a player collides with another object.

PlayerDeathEvent.java - represents the event in which a player collides with a death zone and is despawned.

ReplaySystem.java - handles the gather of events to be replayed as well as going through those events and playing them at specific speeds.

StartRecordingEvent.java - represents the event in which a player has pressed the button to record events to be replayed.

StopRecordingEvent.java - represents the event in which a player has pressed thed button to stop recording events to be replayed.

