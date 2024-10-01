# BAI Project Blockchain API

This repository references the WavesJ and Web3J API for interacting with the BAI project's contracts on the supported blockchain. It provides a convenient way to solve tasks using the blockchain and waits for the task's solutions.

## Installation

Before using this API, ensure you have Java installed on your system. 

## Configuration

To configure the API, create a `.env` file in the root of your project directory with the following contents:

```env
type = "Waves"
seed = "<Your_Seed>"
contractAddress = "<Contradt Address>"
nodeURL = "<main or testnet URL>"
network = "<main or testnet>"
```

for using the Waves network. In case of an EVM based network, the corresponding .env file should look like the following example:

```env
type = "evm"
contractAddress = "<the address of the main BAI contract>"
privateKey = "<your private key>"
endpoint = "<your endpoint, e.g., from alchemy, ...>"
tokenAddress = "<the contract address of the ERC20 BAI token on the network>"
```

Replace the corresponding parameters with your actual settings to interact with the blockchain.

## Usage
Here's a quick example to use the API to solve a task:

```Java
public class Main {

    public static void main(String[] args) {
        BAIAPI baiapi = new BAIAPI();
        String solution = baiapi.solveTask("Who was Albert Einstein?", "chatgpt");
    }

}
```

This script initializes the API, sends a task to the blockchain, and logs the solution once it's ready.

## API Reference

### `solveTask(task, taskType)`

Sends a task to the blockchain for solving and returns the solution.

- `task`: The task description or objective.
- `taskType`: The type of task to register on the blockchain.

## Modules

### `BAIAPI.java`

This is the main API module that abstracts the `WavesConnection` to interact with the Waves blockchain.

### `wavesConnection.java`

Handles direct interactions with the Waves blockchain, such as sending tasks, retrieving task prices, and waiting for task solutions.

## `Contract Addresses`

### Waves mainnet: 
- BAI Token: [2fdzyHvXGCqaz1XA8m9fodemmP9giVBcpe4Jq9F63oFL](https://wavesexplorer.com/assets/2fdzyHvXGCqaz1XA8m9fodemmP9giVBcpe4Jq9F63oFL)
- BAI Tasks: [3PMu8gHthb5uQgpqDvUQ3GZGdovPvypoMQ5](https://wavesexplorer.com/addresses/3PMu8gHthb5uQgpqDvUQ3GZGdovPvypoMQ5)

### BNB mainnet: 
- BAI Token: [0x10Da043D0B46e43B53B74a88AC60CCC28e2AFDf8](https://bscscan.com/token/0x10Da043D0B46e43B53B74a88AC60CCC28e2AFDf8)
- BAI Tasks: [0xbEfd1Ac0eF34136f3F4E2baCc878bea99b45951F](https://bscscan.com/address/0xbEfd1Ac0eF34136f3F4E2baCc878bea99b45951F)

### Base Mainnet: 
- BAI Token: [0x6a27CD26a373530835B9fE7aC472B3e080070F64](https://basescan.org/token/0x6a27CD26a373530835B9fE7aC472B3e080070F64)
- BAI Tasks: [0xC9C19f5ac2433b4B96d8AaAca6890598801f626F](https://basescan.org/address/0xC9C19f5ac2433b4B96d8AaAca6890598801f626F)

*[Documentation](https://docs.blockai.dev/bai/contracts)*

## Contributing
If you'd like to contribute to the project, please fork the repository and use a feature branch. Pull requests are warmly welcome.

## Licensing
The code in this project is licensed under MIT license.
