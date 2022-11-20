# Space Chase 🚀

Welcome to the Space Chase repository! Below is an outline of how to install and run this application, developers can also find documentation on cloning this repository, file structures, pull requests and checkstyle configurations here.


# Build and Run

If you would like to run the most stable build then clone the release branch, cd into the top-level project directory and run the following command:

    mvn clean javafx:run

> **Note:** This Javafx project was built with **Java 17** and **maven version 3.8.6**. If you experience issues running this project you should check your java and maven versions.


# Developer Information

The following sections provide important information that developers may want to familiarise themselves with.

## Cloning this repository
You can clone this repository by pressing the clone button in the repository. Copy everything from and including https://. **Do not include git clone unless you are using git from the command line**.

In Intelij you can create a new project by **selecting "Get from VCS"** in the welcome window, you will then be prompted to enter your bitbucket login details.

## Getting Bitbucket login details
Go to: https://bitbucket.org/account/settings/

your username is under "Bitbucket profile settings" or you can use your email.

You may need to generate an app password.

Go to: https://bitbucket.org/account/settings/app-passwords/

Select "Create App Password" and follow the prompts on the screen. Save this password somewhere safe in case you need to use it again.

## Folder structure

The project folders have been divided into the following project tree:
```
📦 src
└─ main
   ├─ java
   │  └─ com.example.spacechase
   └─ resources
      └─ com.example.spacechase
         ├─ data
         │  ├─ levels
         │  └─ profiles
         ├─ fonts
         ├─ fxml
         ├─ html
         ├─ images
         └─ licenses
```

> **note**: You can create new folders in the resources.com.example.spacechase directory. **Do not create new folders in java.com.example.spacechase as this is prohibited in maven.**

## Branch naming conventions

Sub-branches should be named appropriately. The name should be like this: **feature**_name_of_jira_story, if it is a **new** feature (aka a Story) or **improvement**_name_of_jira_story **if you are making changes to existing code**.

## Making a pull request

You should make a pull request if you have finished a feature or need help.

1. Find this project in the bitbucket repository.
2. Select Pull request (menu to the left of the screen).
3. Select Create pull request.
4. Add an appropriate title and description of what you have done.

If you are making a pull request because you need help, please put HELP in the Title.

> **Note:** The Source Branch is **your feature branch**, and the Destination Branch is The destination of where you would like to add your code to **typically this is the release branch** but it can be whichever branch is most suitable at the time.

## Using Checkstyle

The Group 40 checkstyle file has been provided for you in the top level project directory. You can download the checkstyle plugin to gather real time feedback while you code.

> **Note:** You may only need to do step 3, so **check step 3 first** before attempting steps 1 and 2

1. Download the checkstyle plugin:
   > File -> Settings -> Plugins -> Marketplace -> CheckStyle-IDEA
2. Add the checkstyle file to the plugin:
   > File -> Settings -> Tools -> Checkstyle -> Add Configuration File (Plus icon) -> Description = Group 40 Checkstyle -> Select "Use a local Checkstyle file" and paste the file path to the checkstyle -> Select Next -> Select Next again (You don't need to input anything into the fields. -> Select Finish -> Select Apply -> Select OK
3. Activate the Checkstyle:
   > Select Checkstyle (Button near bottom of the screen) -> Set active configuration to Group 40 Checkstyle