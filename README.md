# DiceBot
[![Build Status](https://travis-ci.com/tfriedrichs/dicebot.svg?branch=master)](https://travis-ci.com/tfriedrichs/dicebot)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

DiceBot is a java library for parsing and evaluating dice expressions like they occur in Pen&Paper games.

# How to use
```java
DiceExpression expression = DiceExpression.parse("d20 + 4");
System.out.println(expression.roll().getValue());
// prints "11"
```

```java
DiceExpression expression = DiceExpression.parse("4d8 + 2d6");
String pretty = new DiceResultPrettyPrinter().prettyPrint(expression.roll());
// prints 20 = 12[1, 5, 3, 3] + 8[2, 6]
```
# How to get

TODO

# Examples

Roll | Expression
--- | ---
Pathfinder or DnD | ```1d20 + 6```
DnD5e advantage |```2d20kh + 4```
DnD5e disadvantage | ```2d20kl + 4```
Shadowrun | ```10d6e5```
Shadowrun exploding | ```10d6!e5```


# Supported Dice Expressions

TODO

# Used Technologies

- [Antlr4](https://www.antlr.org/) for building the dice expression grammar
- [Junit5](https://junit.org/junit5/) for unit and acceptance testing
- [Mockito](https://site.mockito.org/) for mocking during testing
