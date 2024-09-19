package contracts;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class TokenContract extends Contract {

    public TokenContract(String contractAddress, Web3j web3j, TransactionManager manager, DefaultGasProvider gasProvider) {
        super("", contractAddress, web3j, manager, gasProvider);
    }

    public RemoteCall<TransactionReceipt> approve(String spenderAddress, BigInteger amount) {
        Function function = new Function(
                "approve",
                Arrays.asList(new Address(spenderAddress), new Uint256(amount)),
                Collections.emptyList()
        );

        return executeRemoteCallTransaction(function);
    }

}
