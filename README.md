# Siden
tiny web application framework for Java SE 8.

Siden focus on writing your application quickly and running server more faster.

## Getting Started

### Write simple java application

```java
import ninja.siden.App;

public class Main {
	public static void main(String[] args) {
		App app = new App();
		app.get("/hello", (req, res) -> "Hello world");
		app.listen();
	}
}
```

if you want to more examples, see [example/Main.java](https://github.com/taichi/siden/blob/master/siden-example/src/main/java/example/Main.java).

### Add dependency to your build

```groovy
apply plugin: 'java'

repositories.jcenter()

dependencies {
	compile 'ninja.siden:siden-core:0.0.1'
}

sourceCompatibility = targetCompatibility = 1.8
```

### Run and View

    http://localhost:8080/hello


# License

Apache License, Version 2.0

# Inspired projects

* http://expressjs.com/
* http://www.sinatrarb.com/
* http://www.sparkjava.com/
* http://flask.pocoo.org/

# CI status

[![wercker status](https://app.wercker.com/status/de09957e13da7a18ae6cf3fbd67afc68/m "wercker status")](https://app.wercker.com/project/bykey/de09957e13da7a18ae6cf3fbd67afc68)

