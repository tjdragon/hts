package org.tj.hedera.samples;

import com.hedera.hashgraph.sdk.*;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static org.tj.hedera.samples.Utils.log;

public class StablecoinOps {

    private static Client client;

    private static void createClient() {
        AccountId myAccountId = Constants.ACCOUNT_490575;
        PrivateKey myPrivateKey = Constants.PRI_KEY_490575;

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
                .setContractId(Constants.STABLE_COIN_PROXY_ID)
                .setGas(4_000_000)
                .setFunction("rescue", new ContractFunctionParameters()
                        .addInt64(amount))
                .execute(client); // 0.0.490575

        final TransactionReceipt transactionReceipt = contractExecResponse.getReceipt(client);
        log("TransactionReceipt: " + transactionReceipt);
    }

    private static void getBalanceForAccount() throws PrecheckStatusException, TimeoutException {
        log(" ->> GET BALANCE...");
        log("Account (0.0.xxx) ?"); // 0.0.490575
        final Scanner in = new Scanner(System.in);
        final String id = in.next().trim();
        final String address = AccountId.fromString(id).toSolidityAddress();

        final ContractFunctionResult contractUpdateResult = new ContractCallQuery()
                .setContractId(Constants.STABLE_COIN_CONTRACT_ID)
                .setGas(Constants.DEFAULT_GAS) // gasUsed=2876
                .setFunction("balanceOf", new ContractFunctionParameters()
                        .addAddress(address))
                .execute(client);
        final BigInteger balance = contractUpdateResult.getUint256(0);
        log(id + "'s balance is " + balance);
    }
}
