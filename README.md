---

# Slug Smith

A handy utility to generate random word slugs (e.g., `brainy-fancy-country`) for your projects.

---

## Installation

### Maven

Add the following dependency to your `pom.xml` file:

```xml
<dependency>
  <groupId>dev.subrotokumar</groupId>
  <artifactId>slug-smith</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Gradle

Add the following dependency to your `build.gradle` file:

```gradle
dependencies {
    implementation 'dev.subrotokumar:slug-smith:1.0.0' // Replace with the latest version
}
```

---

## Usage

The `slug-smith` package provides a simple API to generate random slugs. Here's how you can use it:

```java
import dev.subrotokumar.slugsmith;

public class Main {
    public static void main(String[] args) {
        String slug = SlugSmith.generateSlug();
        System.out.println(slug);
        // Example output: "deafening-damp-cartoon"
    }
}
```

---

## Contributing

Contributions are welcome! If you have any ideas, suggestions, or bug reports, please open an issue or submit a pull request on [GitHub](https://github.com/subrotokumar/slug-smith).

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---