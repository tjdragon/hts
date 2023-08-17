package org.tj.hedera.samples;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.ContractId;
import com.hedera.hashgraph.sdk.PrivateKey;

public interface Constants {
    public static final ContractId STABLE_COIN_CONTRACT_ID = ContractId.fromString("0.0.490601");

    public static final ContractId STABLE_COIN_PROXY_ID = ContractId.fromString("0.0.490600");
    public static final long DEFAULT_GAS = 4000000;

    public static final AccountId ACCOUNT_467739 = AccountId.fromString("0.0.467739");
    public static final PrivateKey PRI_KEY_467739 = PrivateKey.fromString("3030020100300706052b8104000a04220420daf20fec209b4da654c4dc3de5c0df2951fcc726a8d90f0cc127be278ba9a650");

    public static final AccountId ACCOUNT_490575 = AccountId.fromString("0.0.490575");
    public static final PrivateKey PRI_KEY_490575 = PrivateKey.fromString("302e020100300506032b6570042204209c08a7e451bffcd6a382542264d12cfaf22525cbbff03ad4c34b43d388e79719");
}
