package org.tj.hedera.samples;

import com.hedera.hashgraph.sdk.*;

import java.util.concurrent.TimeoutException;

import static org.tj.hedera.samples.Utils.log;

public class TransferHBAR {
    public static void main(String[] args) throws PrecheckStatusException, TimeoutException {
        AccountId myAccountId = Constants.ACCOUNT_467739;
        log("AccountId: " + myAccountId);
        PrivateKey myPrivateKey = Constants.PRI_KEY_467739;
        log("Private Key: " + myPrivateKey);
        Client client = Client.forTestnet();
        client.setOperator(myAccountId, myPrivateKey);
        client.setDefaultMaxTransactionFee(new Hbar(100));
        client.setMaxQueryPayment(new Hbar(50));

        final Hbar amount = new Hbar(1000);

        final TransactionResponse transactionResponse = new TransferTransaction()
                .addHbarTransfer(myAccountId, amount.negated())
                .addHbarTransfer(AccountId.fromString("0.0.490575"), amount)
                .setTransactionMemo("Native HBAR Transfer")
                .execute(client);

        log("transaction ID: " + transactionResponse);
    }
}
