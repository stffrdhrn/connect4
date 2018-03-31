# Connect 4

[![Build Status](https://travis-ci.org/stffrdhrn/connect4.svg?branch=master)](https://travis-ci.org/stffrdhrn/connect4)

Simple java game challenge.  I have implemented this in a no frills
way.  The requirement is prod-ready which I take as:
 - Proper input validations
 - Good documentation and comments
 - Proper testing suite
 - Continuous Integration

There are some "prod-ready" items that I would usually build into a system but
I am leaving out as I don't think its needed for such a simple game.
 - Logging infrastructure
 - Monitoring

## TODO
 - Support automatic gradle build

## Build with

```
gradle build
```

## Run with

```
java -cp ./build/classes/main org.shorne.connect4.GameMain

# or (doesnt work with current Console implementation)

gradle run
```
