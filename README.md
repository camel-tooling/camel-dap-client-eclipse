# Camel Debug Adapter client for Eclipse

This repository will provide a client for the Debug Adapter implementation for Apache Camel. As a first step, manual steps are provided to use the Debug Adapter for Apache Camel inside your Eclipse instance.

## How to use the Debug Adapter for Apache Camel

- Build the Camel Debug Adapter Jar from this [repo](https://github.com/camel-tooling/camel-debug-adapter)
- Install [Eclipse LSP4E](https://projects.eclipse.org/projects/technology.lsp4e) in your Eclipse Desktop instance
- Launch the Camel application that you want to debug
- Grab the pid of the Camel Application
- Create a `Debug Adapter Launcher` configuration
  - Select `launch a Debug Server using the following arguments`
  - in `command` field, set `java`
  - in `Arguments` field, set `-jar <pathTo>/camel-dap-server-xxx.jar`
  - in `Launch Parameters (Json)` field, set { "attach_pid": "<thePidOfTheCamelApplication>"}
  - Click `Debug`
- You can now set breakpoints in textual Camel route definition
