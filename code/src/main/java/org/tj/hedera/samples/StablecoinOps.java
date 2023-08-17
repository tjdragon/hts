package org.tj.hedera.samples;

import com.hedera.hashgraph.sdk.*;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static org.tj.hedera.samples.Utils.log;

public class StablecoinOps {
    public static final ContractId STABLE_COIN_CONTRACT_ID = ContractId.fromString("0.0.490601");
    // https://hashscan.io/testnet/contract/0.0.490600
    public static final ContractId STABLE_COIN_PROXY_ID = ContractId.fromString("0.0.490600");
    public static final long DEFAULT_GAS = 4000000;

    private static Client client;

    private static void createClient() {
        AccountId myAccountId = AccountId.fromString("0.0.490575");
        PrivateKey myPrivateKey = PrivateKey.fromString("302e020100300506032b6570042204209c08a7e451bffcd6a382542264d12cfaf22525cbbff03ad4c34b43d388e79719");

        client = Client.forTestnet();
        client.setOperator(myAccountId, myPrivateKey);
        client.setDefaultMaxTransactionFee(new Hbar(100));
        client.setMaxQueryPayment(new Hbar(50));
    }

    public static void main(String[] args) {
        // https://testnet.dragonglass.me/hedera/accounts/0.0.490575
        log("*** Hedera Stablecoin Ops ***");

        createClient();

        while (true) {
            log("");
            log("Stablecoin Operation Menu:");
            log("   1. Get balance for account");
            log("   2. Rescue Tokens");

            try {
                final Scanner in = new Scanner(System.in);
                final int option = in.nextInt();
                processOption(option);
            } catch (Exception e) {
                log("Issue for chosen option:" + e.getMessage());
            }
        }
    }

    private static void processOption(final int option) throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        switch (option) {
            case 1:
                getBalanceForAccount();
                break;
            case 2:
                rescueTokens();
                break;
            default:
        }
    }

    private static void rescueTokens() throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        log(" ->> RESCUE...");

        final Scanner in = new Scanner(System.in);
        log("Rescue amount:");
        final long amount = in.nextLong();

        final TransactionResponse contractExecResponse = new ContractExecuteTransaction()
                .setContractId(ContractId.fromString("0.0.490600"))
                .setGas(4_000_000)
                .setFunction("rescue", new ContractFunctionParameters()
                        .addInt64(amount))
                .execute(client); // 0.0.490575

        final TransactionReceipt transactionReceipt = contractExecResponse.getReceipt(client);
        log("TransactionReceipt: " + transactionReceipt);
    }

//    private static void mintTo() throws PrecheckStatusException, TimeoutException {
//        log(" ->> MINT TO...");
//
//        final Scanner in = new Scanner(System.in);
//        log("Mint amount?");
//        final long amount = in.nextLong();
//        log("To account? (0.0.xxx)"); // 0.0.490575
//        final String toAddress = AccountId.fromString(in.next().trim()).toSolidityAddress();
//
//        final ContractFunctionResult contractUpdateResult = new ContractCallQuery()
//                .setContractId(STABLE_COIN_PROXY_ID)
//                .setGas(DEFAULT_GAS)
//                .setFunction("mint", new ContractFunctionParameters()
//                        .addAddress(toAddress)
//                        .addInt64(amount))
//                .execute(client);
//
//        final boolean success = contractUpdateResult.getBool(0);
//        log("Transfer Success: " + success);
//    }

//    private static void transferTokens() throws PrecheckStatusException, TimeoutException {
//        log(" ->> TRANSFER TOKENS...");
//
//        final Scanner in = new Scanner(System.in);
//        log("Transfer amount?");
//        final BigInteger amount = BigInteger.valueOf(in.nextLong());
//        log("To account? (0.0.xxx)"); // 0.0.490575
//        final String toAddress = AccountId.fromString(in.next().trim()).toSolidityAddress();
//
//        final ContractFunctionResult contractUpdateResult = new ContractCallQuery()
//                .setContractId(STABLE_COIN_CONTRACT_ID)
//                .setGas(DEFAULT_GAS)
//                .setFunction("transfer", new ContractFunctionParameters()
//                        .addAddress(toAddress)
//                        .addUint256(amount))
//                .execute(client);
//
//        final boolean success = contractUpdateResult.getBool(0);
//        log("Transfer Success: " + success);
//    }

    private static void getBalanceForAccount() throws PrecheckStatusException, TimeoutException {
        log(" ->> GET BALANCE...");
        log("Account (0.0.xxx) ?"); // 0.0.490575
        final Scanner in = new Scanner(System.in);
        final String id = in.next().trim();
        final String address = AccountId.fromString(id).toSolidityAddress();

        final ContractFunctionResult contractUpdateResult = new ContractCallQuery()
                .setContractId(STABLE_COIN_CONTRACT_ID)
                .setGas(DEFAULT_GAS) // gasUsed=2876
                .setFunction("balanceOf", new ContractFunctionParameters()
                        .addAddress(address))
                .execute(client);
        final BigInteger balance = contractUpdateResult.getUint256(0);
        log(id + "'s balance is " + balance);
    }
}
