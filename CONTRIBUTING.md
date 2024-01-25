# Contributing

## Making a PR

To work on a new feature or bug fix, first create a new branch named after the change you intend to make. You can then make changes to the code and commit them to that branch without worrying about messing up anyone else. Almost everything lives within the [`TeamCode/`](https://github.com/BotsBurgh/BOTSBURGH-FTC-2023-24/tree/main/TeamCode) folder, but [`FtcRobotController`](https://github.com/BotsBurgh/BOTSBURGH-FTC-2023-24/tree/main/FtcRobotController) contains useful samples and concepts.

Once you've made some commits to your branch, you can create a [pull request](https://github.com/BotsBurgh/BOTSBURGH-FTC-2023-24/pulls) merging back into the main branch. If you're not done with your changes but still want to create a PR, make sure to mark it as a draft. Additionally please write a description of what you did, to help reviewers understand your changes. Once you've created your PR, you can request a review from [BD103](https://github.com/BD103), [cadenceforney](https://github.com/cadenceforney), or [Vifi5](https://github.com/Vif15).

In order for a PR to be merged, it needs to be reviewed and approved by at least one reviewer. Additionally, Github Actions (CI) needs to pass. If your changes do not pass the build check, please try locally building it using Android Studio and fixing the issues. If your changes do not pass the lint check, please manually run KTLint using Gradle to format your code:

```shell
$ ./gradlew formatKotlin
```

Once your changes get approved, one of the contributors will merge it. Thanks for your help!

## Resources

- [FTC Docs](https://ftc-docs.firstinspires.org/)
- [Game and Season Materials](https://www.firstinspires.org/resource-library/ftc/game-and-season-info)
- [FtcRobotController Javadoc](https://javadoc.io/doc/org.firstinspires.ftc)
- [Game Manual 0](https://gm0.org/)
- [BotsBurgh 2023-24 Javadoc](https://botsburgh.github.io/BOTSBURGH-FTC-2023-24/)
