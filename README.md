# PDF Generator

A Kotlin application that demonstrates PDF generation using different libraries (Flying Saucer and OpenPDF).

## Features

- Generate PDFs using Flying Saucer (HTML to PDF)
- Generate PDFs using OpenPDF (programmatic PDF generation)
- Configurable output paths
- Optional automatic PDF opening

## Building

The project uses Gradle for building. To build the project:

```bash
./gradlew build
```

This will create a JAR file in `build/libs/`.

## Running

### Using Gradle

You can run the application directly using Gradle:

```bash
# Generate PDF using Flying Saucer
./gradlew run --args="flyingsaucer"

# Generate PDF using OpenPDF
./gradlew run --args="openpdf"

# Generate PDFs using both libraries
./gradlew run --args="all"

# Specify output path
./gradlew run --args="flyingsaucer -o /tmp/my-pdf"

# Disable automatic opening
./gradlew run --args="openpdf --no-open"
```

### Using the JAR file

After building, you can run the JAR file directly:

```bash
# Generate PDF using Flying Saucer
java -jar build/libs/pdf-generator.jar flyingsaucer

# Generate PDF using OpenPDF
java -jar build/libs/pdf-generator.jar openpdf

# Generate PDFs using both libraries
java -jar build/libs/pdf-generator.jar all

# Specify output path
java -jar build/libs/pdf-generator.jar flyingsaucer -o /tmp/my-pdf

# Disable automatic opening
java -jar build/libs/pdf-generator.jar openpdf --no-open
```

## Command Line Options

- `flyingsaucer`: Generate PDF using Flying Saucer
- `openpdf`: Generate PDF using OpenPDF
- `all`: Generate PDFs using both libraries
- `-o, --output <path>`: Specify output file path (without extension)
- `--no-open`: Don't automatically open the generated PDF

## Dependencies

- Flying Saucer (for HTML to PDF conversion)
- OpenPDF (for programmatic PDF generation)
- Kotlin standard library 