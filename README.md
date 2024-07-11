# PlayerActionsAPI

Player Actions API allows to transform a string list into a list of actions, send a message to the player, execute a
command in the console etc.
This API uses the same syntax as DeluxeMenu.

# Actions

| Action      | Description                                                     |
|-------------|-----------------------------------------------------------------|
| [broadcast] | Broadcast a message to the server.                              |
| [chat]      | Send a chat message as the player performing the action.        |
| [close]     | Close the viewers open menu.                                    |
| [console]   | Execute a command from the console.                             |
| [message]   | Send a message to the menu viewer.                              |
| [player]    | Execute a command from the player.                              |

You can add a tick delay to your action by using this: ``<delay:{ticks}>``
You must set the delay after defining the action to perform.

**Example**
````yml
commands:
  - "[console] give %player% diamond"
  - "[message] <delay:10> &bAction &f&l» &fExample message"
````

# Developer

JitPack: https://jitpack.io/#Maxlego08/PlayerActionsAPI

## Maven
````xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependency>
    <groupId>com.github.Maxlego08</groupId>
    <artifactId>PlayerActionsAPI</artifactId>
    <version>{version}</version>
</dependency>
````
# Gradle
````kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
    implementation 'com.github.Maxlego08:PlayerActionsAPI:{version}'
}
````


Do not forget to relocate the sources so as not to complicate with other plugin.

## How to use

This configuration allows to execute a command and after a delay of 10 tickets to send a message
````yml
commands:
  - "[console] give %player% diamond"
  - "[message] <delay:10> &bAction &f&l» &fExample message"
````

### Load Actions
The first step and turn your string list into an action list.
````java
public class Example extends JavaPlugin {

    @Override
    public void onEnable() {
        List<String> commands = getConfig().getStringList("commands");
        List<Action> actions = ActionsAPI.loadActions(commands);
    }
}
````

### Use actions
To use the actions just run the `execute` method. The method requires a plugin and a player.
````java
public class Example extends JavaPlugin {
    
    public void execute(List<Action> actions, Player player) {
        actions.forEach(command -> command.execute(this, player));
    }
}
````

### Register actions
To use the actions just run the `registerAction` method. The method requires a plugin and an action type.

````java
public class CustomAction extends Action {

    public CustomAction(int delay) {
        super(delay);
    }

    @Override
    protected Runnable getRunnableAction(Plugin plugin, Player player) {
        return () -> player.sendMessage("Hello, " + player.getName() + "!");
    }
}
````
````java
public class CustomActionType implements ActionType {

    @Override
    public String getIdentifier() {
        return "[custom]";
    }

    @Override
    public Class<? extends Action> getAction() {
        return CustomAction.class;
    }
}
````
````java
public class Example extends JavaPlugin {

    public void onEnable() {
        ActionsAPI.registerAction(this, new CustomActionType());
        ActionsAPI.loadActions("[custom] <delay:20>");
    }
}
````

With this method you can override any action type already registered.