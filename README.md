[![Build and Test](https://github.com/camel-tooling/camel-dap-client-eclipse/actions/workflows/main.yml/badge.svg)](https://github.com/camel-tooling/camel-dap-client-eclipse/actions/workflows/main.yml)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=camel-tooling_camel-dap-client-eclipse&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=camel-tooling_camel-dap-client-eclipse)
[![Drag to your running Eclipse* workspace to install. *Requires Eclipse Marketplace Client](https://marketplace.eclipse.org/sites/all/themes/solstice/public/images/marketplace/btn-install.svg)](http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=5498142 "Drag to your running Eclipse* workspace to install. *Requires Eclipse Marketplace Client")

# Camel Debug Adapter client for Eclipse

This repository will provide a client for the Debug Adapter implementation for Apache Camel. As a first step, manual steps are provided to use the Debug Adapter for Apache Camel inside your Eclipse instance.

## How to use the Debug Adapter for Apache Camel

### Camel Specific Launcher

#### Happy path

The advantage is that a minimal set of information is visible.

- Install extension from this repository
- Launch the Camel (3.16+) application that you want to debug with `camel-debug` on the classpath
- Create a `Camel Textual debug` debug configuration
  - Click `Debug`
- You can now set breakpoints in textual Camel route definition

#### Advanced configuration

In the `Camel Textual debug` debug configuration, you can provide additional parameters. For instance when trying to connect to a remote JMX URL:
`{ "request": "attach", "attach_jmx_url":"<aJMXURL>" }`

it is also possible to use the PID of the Camel application:

`{ "request": "attach", "attach_pid":"<pidOfCamelApplication>" }`


### Built-in LSP4E.Debug Launcher

The advantage is that it is giving more power on the actions and the versions to use.

- Build the Camel Debug Adapter Jar from this [repo](https://github.com/camel-tooling/camel-debug-adapter) or retrieve it from 
- Install [Eclipse LSP4E](https://projects.eclipse.org/projects/technology.lsp4e) in your Eclipse Desktop instance
- Launch the Camel application that you want to debug
- Create a `Debug Adapter Launcher` debug configuration
  - Select `Launch a Debug Server using the following arguments`
  - in `command` field, set `java`
  - in `Arguments` field, set `-jar <pathTo>/camel-dap-server-xxx.jar`
  - in `Launch Parameters (Json)` field, set `{ "request": "attach" }`
  - Click `Debug`
- You can now set breakpoints in textual Camel route definition
