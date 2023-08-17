# HTS
August 2023

A step-by-step guide to use [Hedera Token Service](https://hedera.com/token-service) in [java](https://dev.java/).

## Links & Resources
- [Getting Started](https://docs.hedera.com/hedera/getting-started/introduction)
- [HTS Token Created: 0.0.477461](https://testnet.dragonglass.me/hedera/search?q=0.0.477461)
- [UI](https://34.133.58.213:8081/)

## Stablecoin framework
- Created the stable coin via UI
- Interaction to the smart contract via the UI or code

## Test links:

- The token linked to the Stable Coin is this one : [0.0.490600](https://hashscan.io/testnet/token/0.0.490601?p=1&k=0.0.490600)
- The SC proxy contract : [0.0.490600](https://hashscan.io/testnet/contract/0.0.490600)
- The SC proxy contract is the Token's treasury account, which is the default behaviour when the web app to deploy new SCs
- The initial supply was 100 millions TEURZ
- The max supply was infinite
- After deploying the SC I minted 50 TEURZ to account 0.0.490575, which is the one you used to deploy the SCs

### Transfer
In order for accounts to transfer tokens from account A to account B one cannot use the SCs, 
one has to use HTS and transfer token 0.0.490601, like any other regular hedera token.

However it is very important to note that in order for account A to hold token T, it must first associate to the token, 
that is an operation that cannot be carried out using our web app / SCs, you have to do it using HTS. 
Account 0.0.490575 was automatically associated to toke 0.0.490601 during the token creation.

### Minting
In order to mint new tokens and directly assign them to accounts I must use the SCs via our web app, 
just like I minted 50 tokens to account 0.0.490575.