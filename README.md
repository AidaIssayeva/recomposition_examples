### ⚡ Recomposition in Jetpack Compose
List of practical tips and code snippets to avoid unnecessary recomposition in Jetpack Compose. 

This is an active repo, please [contribute](#-contribution) if you discovered useful tips related to recomposition.

The first 5 tips were part of the talk "What does Recomposition mean to your app" by [me](https://cupsofcode.com/about/):

| Depth Level | Notes | Conference | Slides |
| ----------- | ----------- | ----------- |----------- |
| Light   |  |[Connect Recharge'22](https://hopin.com/events/connect-recharge-2022/registration?code=wwK1JKQKXBSzI6RaQZcehxqtr)| [Link](https://docs.google.com/presentation/d/e/2PACX-1vRfsVk1LQ_2hexLTmGLmlPmdMtcWzjiHtnl41c_4aKQlC5c4BZUUrGMbG8LUWCBmqavuutT_31pHX6i/pub) |
| Deep (v1) |   | [Droidcon SF'22](https://www.sf.droidcon.com/speaker/aida-issayeva/what-does-recomposition-mean-to-your-app%3F)  | [Link](https://docs.google.com/presentation/d/e/2PACX-1vQs4J5e6X-zDfiynVUtVGkPl19NyzPD6qjiPY5xFGWIrWZ_mWI4ebuK6LfF54B7caM11DJ7K7utauTK/pub) |
| Deep (v2) | Includes tips 6 and 7  | [Droidcon NYC'22](https://nyc.droidcon.com/aida-issayeva/)  | [Link](https://speakerdeck.com/aida_isay/what-does-recomposition-mean-to-your-app) |


📣 Don't forget to star ⭐ or watch 👀 the repo to get updates!

### 🔥 List of tips:

1. [Break down](/app/src/main/java/com/cupsofcode/recomposition_examples/Tip1.kt) composable functions as much as possible;
2. Use the [key composable](/app/src/main/java/com/cupsofcode/recomposition_examples/Tip2.kt);
3. [Read the state value](/app/src/main/java/com/cupsofcode/recomposition_examples/Tip3.kt) at the lowest composable function;
4. Use [Modifier lambdas](/app/src/main/java/com/cupsofcode/recomposition_examples/Tip4.kt) for every frequent changing state read in Modifier functions;
5. Use [`derivedStateOf()`](/app/src/main/java/com/cupsofcode/recomposition_examples/Tip5.kt) to buffer the rate of changes;
6. Declare properties as read-only(`val` instead of `var`)
7. Use [KotlinX immutable collections](https://github.com/Kotlin/kotlinx.collections.immutable) instead of regular collection interfaces, like `List`, `Map`, `Set`, etc.

to be continued...

### 📌 Contribution

Contributions are more than welcome! 
The more we learn **together** Jetpack Compose, the faster we'll climb that learning curve 💪.

Here are some contribution **rules** to follow:
1. Give the good and bad examples of the same output (see: each Tip file contains `GoodGreetings()` and `BadGreetings()` composable functions);
2. Add the recompose [highlighter](/app/src/main/java/com/cupsofcode/recomposition_examples/RecomposeHighlighter.kt) & [counter](/app/src/main/java/com/cupsofcode/recomposition_examples/RecompositionCounter.kt) to ***every*** composable function via Modifier;
3. Add comments in the beginning of the file to give the overview of the tip, then in subsequent good and bad sections.

Here are some contribution **steps** to follow:
1. Clone this repository;
2. Branch out via `git branch -b someBranchName`;
3. Open Android Studio (or any IDE you feel comfortable working with);
4. Create a separate .kt file;
5. Create a composable function, that starts with `TipX()`, where `X` = the last available number in the tip list + 1;
6. Write your tip;
7. Modify the README doc and include your tip to the [list](#-list-of-tips);
8. Push your tip and open the PR [here](https://github.com/AidaIssayeva/recomposition_examples/pulls).

Thank you so much for contributing and helping others to learn 💚💚💚
