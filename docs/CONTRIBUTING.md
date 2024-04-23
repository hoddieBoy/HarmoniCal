# Contributing to HarmoniCal

Welcome to HarmoniCal! We appreciate your interest in contributing and are excited to have you on board. This document
outlines the process for contributing via forks and pull requests, ensuring that all contributions adhere to our project
standards.

## Getting Started

Before contributing, please ensure that you have:
 1. Have a GitHub account.
 2. Have read the [Code of Conduct](CODE_OF_CONDUCT.md).
 3. Read the [README.md](../README.md) file to understand the project's purpose and functionality.
 4. Reviewed the [LICENSE](../LICENSE) file to understand the project's licensing.
 5. Familiarized yourself with the project's structure and codebase.

## Quick Start

1. Fork the repository:
   >Go to the GitHub repository for [HarmoniCal](https://github.com/jarhead-killgrave/HarmoniCal) and click the "Fork"
    button in the upper right corner. This will create a copy of the repository in your GitHub account.

2. Clone the repository:
    >Clone the repository to your local machine using the following command:
    >```bash
    >git clone https://github.com/yourusername/HarmoniCal.git
    >```
    >Replace `yourusername` with your GitHub username.

3. **Set up your local environment** (if necessary) according to the project's setup documentation.

## Making Changes

### Branching Guidelines

When making changes, please adhere to the following guidelines:
 1. Navigate to the repository directory on your local machine.
 2. **Pull the latest changes from the `main` repository branch**(important for keeping your fork up-to-date):
    >```bash
    >git pull https://github.com/jarhead-killgrave/HarmoniCal.git main
    >``` 
 3. **Create a new branch** following the [conventional commit format](https://www.conventionalcommits.org/).Use
    descriptive names, like:
    - Feature: `feat/short-feature-description`
    - Bugfix: `fix/short-bug-description`
    - Documentation: `docs/short-documentation-description`
    - Refactor: `refactor/short-refactor-description`
    - Test: `test/short-test-description`
    - Chore: `chore/short-chore-description`
    - Style: `style/short-style-description`
    - CI/CD: `ci/short-ci-description`
    - Build: `build/short-build-description`
    - Revert: `revert/short-revert-description`
    - Performance: `perf/short-performance-description`
    - Security: `security/short-security-description`
    - Dependency: `deps/short-dependency-description`
    - Release: `release/short-release-description`
    - Configuration: `config/short-config-description`
    - Deployment: `deploy/short-deploy-description`
    
    >```bash
    >git checkout -b feat/short-feature-description
    >```
    >Replace `feat/short-feature-description` with the appropriate branch name.
 4. Make your changes and commit them:
    >```bash
    >git add .
    >git commit -m "feat: Add new feature"
    >```
    Replace `Add new feature` with a brief description of your changes.  
    **Note**: Use the present tense (e.g., "Add feature" not "Added feature").  
    **Note**: Use the imperative mood (e.g., "Add feature" not "Adds feature").  
    **Note**: Use the conventional commit format (e.g., "feat: Add feature", "fix: Correct typo"...).  
    **Note**: Use the appropriate commit type (e.g., `feat`, `fix`, `docs`, `refactor`, `test`, `chore`, `style`, `ci`, 
`build`, `revert`, `perf`, `security`, `deps`, `release`, `config`, `deploy`).  

 5. Push your changes to your fork:
    >```bash
    >git push origin feat/short-feature-description
    >```
    Replace `feat/short-feature-description` with the appropriate branch name.
 6. Create a pull request:
    >Go to the GitHub repository for your fork and click the "New pull request" button. Fill out the pull request form
    with a title and description of your changes. Click "Create pull request" to submit your contribution for review.
    > 
     **Note**: Ensure that your pull request adheres to the project's standards and guidelines.  
     **Note**: Include a brief description of your changes and any relevant information that may help reviewers understand
     your contribution.  
     **Note**: Reference any related issues or pull requests in your description using the appropriate GitHub syntax.  
     **Note**: Assign the pull request to the appropriate reviewer(s) and label it accordingly.  
     **Note**: Be prepared to address any feedback or requests for changes during the review process.  
     **Note**: Once your pull request is approved, it will be merged into the main repository branch.  
     **Note**: After your pull request is merged, you can delete the branch associated with your changes.  
     **Note**: Thank you for your contribution!
