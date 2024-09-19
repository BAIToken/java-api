package contracts;

import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
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

public class TaskContract extends Contract {

    public TaskContract(String contractAddress, Web3j web3j, TransactionManager manager, DefaultGasProvider gasProvider) {
        super(contractAddress, contractAddress, web3j, manager, gasProvider);
    }

    public RemoteCall<BigInteger> getPriceForType(String taskType) {
        Function function = new Function(
                "getPriceForType",
                Collections.singletonList(new Utf8String(taskType)),
                Collections.singletonList(new org.web3j.abi.TypeReference<Uint256>() {
                })
        );

        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> getResultForTaskId(String taskId) {
        Function function = new Function(
                "getResultForTaskId",
                Collections.singletonList(new Utf8String(taskId)),
                Collections.singletonList(new org.web3j.abi.TypeReference<Utf8String>() {
                })
        );

        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> getLastCounter() {
        Function function = new Function(
                "getLastCounter",
                java.util.Collections.emptyList(),
                Collections.singletonList(new org.web3j.abi.TypeReference<Uint>() {
                })
        );

        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> registerTask(String task, String taskType) {
        Function function = new Function(
                "registerTask",
                Arrays.asList(new Utf8String(task), new Utf8String(taskType)),
                java.util.Collections.emptyList()
        );

        return executeRemoteCallTransaction(function);
    }

}
