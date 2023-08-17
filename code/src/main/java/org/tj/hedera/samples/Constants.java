package org.tj.hedera.samples;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.ContractId;
import com.hedera.hashgraph.sdk.PrivateKey;

public interface Constants {
    public static final ContractId STABLE_COIN_CONTRACT_ID = ContractId.fromString("0.0.490601");

    public static final ContractId STABLE_COIN_PROXY_ID = ContractId.fromString("0.0.490600");
    public static final long DEFAULT_GAS = 4000000;

    public static final AccountId ACCOUNT_467739 = AccountId.fromString("0.0.467739");
    public static final PrivateKey PRI_KEY_467739 = PrivateKey.fromString("...");

    public static final AccountId ACCOUNT_490575 = AccountId.fromString("0.0.490575");
    public static final PrivateKey PRI_KEY_490575 = PrivateKey.fromString("...");
}
