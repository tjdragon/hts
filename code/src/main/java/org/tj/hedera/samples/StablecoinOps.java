package org.tj.hedera.samples;

import com.hedera.hashgraph.sdk.*;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static org.tj.hedera.samples.Utils.log;

public class StablecoinOps {
    public static final ContractId STABLE_COIN_CONTRACT_ID = ContractId.fromString("0.0.490601");
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
        log("*** Hedera Stablecoin Ops ***");

        createClient();

        while (true) {
            log("");
            log("Stablecoin Operation Menu:");
            log("1. Get balance for account");

            try {
                final Scanner in = new Scanner(System.in);
                final int option = in.nextInt();
                processOption(option);
            } catch (Exception e) {
                log("Issue for chosen option:" + e.getMessage());
            }
        }
    }

    private static void processOption(final int option) throws PrecheckStatusException, TimeoutException {
        switch (option) {
            case 1:
                getBalanceForAccount();
                break;
            default:
        }
    }

    private static void getBalanceForAccount() throws PrecheckStatusException, TimeoutException {
        log("Account (0.0.xxx) ?"); // 0.0.490575
        final Scanner in = new Scanner(System.in);
        final String id = in.next().trim();
        final String address = AccountId.fromString(id).toSolidityAddress();

        final ContractFunctionResult contractUpdateResult = new ContractCallQuery()
                .setContractId(STABLE_COIN_CONTRACT_ID)
                .setGas(DEFAULT_GAS) // gasUsed=2876
//                .setQueryPayment(new Hbar(1))
                .setFunction("balanceOf", new ContractFunctionParameters()
                        .addAddress(address))
                .execute(client);
        final BigInteger balance = contractUpdateResult.getUint256(0);
        log(id + "'s balance is " + balance);
    }
}
