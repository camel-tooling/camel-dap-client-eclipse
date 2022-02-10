[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=camel-tooling_camel-dap-client-eclipse&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=camel-tooling_camel-dap-client-eclipse)

# Camel Debug Adapter client for Eclipse

This repository will provide a client for the Debug Adapter implementation for Apache Camel. As a first step, manual steps are provided to use the Debug Adapter for Apache Camel inside your Eclipse instance.

## How to use the Debug Adapter for Apache Camel

### Camel Specific Launcher

The advantage is that a minimal set of information is visible.

- Install extension from this repository
- Launch the Camel application that you want to debug
- Grab the pid of the Camel Application
- Create a `Camel Textual debug` debug configuration
  - in the text field, add the pid to have something like { "request": "attach", "attach_pid": "1234"}
  - Click `Debug`
- You can now set breakpoints in textual Camel route definition

### Built-in LSP4E.Debug Launcher

The advantage is that it is giving more power on the actions and the versions to use.

- Build the Camel Debug Adapter Jar from this [repo](https://github.com/camel-tooling/camel-debug-adapter) or retrieve it from 
- Install [Eclipse LSP4E](https://projects.eclipse.org/projects/technology.lsp4e) in your Eclipse Desktop instance
- Launch the Camel application that you want to debug
- Grab the pid of the Camel Application
- Create a `Debug Adapter Launcher` debug configuration
  - Select `launch a Debug Server using the following arguments`
  - in `command` field, set `java`
  - in `Arguments` field, set `-jar <pathTo>/camel-dap-server-xxx.jar`
  - in `Launch Parameters (Json)` field, set { "request": "attach", "attach_pid": "<thePidOfTheCamelApplication>"}
  - Click `Debug`
- You can now set breakpoints in textual Camel route definition
