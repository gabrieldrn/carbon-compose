# Contributing to Carbon Compose
Thank you for considering contributing to Carbon Compose! Before you start, please take a moment to review these guidelines.

## Code of Conduct
By participating in this project, you agree to abide by the [Code of Conduct](https://github.com/gabrieldrn/carbon-compose/blob/main/CODE_OF_CONDUCT.md). Please be respectful to others and help create a positive environment.

## How to Contribute
### Reporting Bugs
If you find a bug, please report it by opening a new issue. When reporting a bug, please provide as much detail as possible:

- A clear and descriptive title.
- Steps to reproduce the issue.
- Expected and actual behavior.
- Relevant screenshots, if applicable.
- Information about your environment.

### Suggesting Enhancements
New ideas for new features or improvements are welcomed. To suggest an enhancement:

- Check if the feature is already requested or discussed by searching existing issues.
- If not, open a new issue with a detailed description of the enhancement, including:
  - The motivation behind the enhancement.
  - How it benefits the project.
  - Any possible implementation ideas (encouraged).

### Git workflow

This project follows the [Trunk-Based Development model](https://trunkbaseddevelopment.com/). The
`main` branch is the production-ready and PRs should be submitted against the `main` branch.
In its specificities, the project follows the
"[release from trunk](https://trunkbaseddevelopment.com/release-from-trunk/)" strategy.
As feature flags are quite complex to implement in a library because code stays accessible to the
user, wip features are marked with `@Experimental` annotation.

### Submitting Pull Requests
Pull Requests (PRs) are the best way to propose changes to the codebase. Here’s how to get started:

- **Fork the repository** and clone your fork to your local machine.
- **Create a new branch** for your changes. 
  - Please name your branch with a sub-directory corresponding to what you are **mainly** working on: `git checkout -b <scope>/<changes-name>`.
  - `<scope>` can be `component`, `foundation`, `cicd`, `misc`, `documentation`, etc. It can be anything as long as it remains relevant.

    > For example, if you're correcting a bug in the Tag component and you also have to make changes in the catalog app, the main scope remains `component` so your branch could be named `component/fix-tag-bug`.
- **Make your changes** and commit them with clear and descriptive messages (see Git Commit Messages below).
- **Make sure your changes does not contain code smells**. The project uses the  [Detekt](https://detekt.dev/) plugin for static code analysis.
- If you've worked on the library, **ensure you have updated the API signature** by running `gradle apiDump`.
- **Push your changes** to your fork and submit a PR against the `develop` branch.
- Ensure that your PR:
  - Links to any related issues.
  - Passes all tests.
  - Includes relevant documentation updates.

Your PR will be reviewed and provided with feedback. Please be responsive to comments and requests for changes.

## Development setup

To set up the development environment:
- Check your system with [KDoctor](https://github.com/Kotlin/kdoctor).
- Install JDK 17 on your machine.
- Add `local.properties` file to the project root and set a path to Android SDK there.

## Style Guides
### Git Commit Messages
- Use the present tense ("Add feature" not "Added feature").
- Use the imperative mood ("Move button" not "Moves button").
- Reference issues and pull requests when applicable.

### Code Style
Code style and code smells are checked by Detekt. The project also has an included code styles configuration for IDEA-based IDEs, please use the formatting tool to format your code automatically (macOS shortcut: ⌥⌘L)

### License
By contributing to Carbon Compose, you agree that your contributions will be licensed under the [Apache-2.0 license](https://github.com/gabrieldrn/carbon-compose/blob/main/LICENSE).

---

Thank you again for your contribution!
