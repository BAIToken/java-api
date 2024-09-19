package connectors;

import contracts.TaskContract;
import contracts.TokenContract;
import io.github.cdimascio.dotenv.Dotenv;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.Locale;

public class EVMConnection implements BlockChainConnection {

    private Web3j node;
    private TaskContract contractAddress;
    private TokenContract tokenContract;
    private String privateKey;
    private String tokenId;
    private String myAddress;

    public EVMConnection() {
        Dotenv dotenv = Dotenv.load();
        Credentials credentials;
        RawTransactionManager manager;

        this.privateKey = dotenv.get("privateKey");
        this.node = Web3j.build(new HttpService(dotenv.get("endpoint")));
        credentials = Credentials.create(privateKey);
        manager = new RawTransactionManager(this.node, credentials, Long.parseLong(dotenv.get("chainId")));
        this.contractAddress = new TaskContract(dotenv.get("contractAddress"), node, manager, new DefaultGasProvider());
        this.tokenContract = new TokenContract(dotenv.get("tokenId"), node, manager, new DefaultGasProvider());
        this.myAddress = credentials.getAddress();
    }

    public BigInteger getPaymentForTaskType(String tasktype) {
        BigInteger price;

        try {
            price = this.contractAddress.getPriceForType(tasktype).send();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return price;
    }

    public String waitForSolution(String taskId) {
        String data;

        try {
            data = this.contractAddress.getResultForTaskId(taskId).send();

            if (data.length() > 0) {
                return data;
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                return this.waitForSolution(taskId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String solveTask(String task, String taskType) {
        try {
            BigInteger price;
            BigInteger counter;
            String taskId;

            price = getPaymentForTaskType(taskType);

            this.tokenContract.approve(this.contractAddress.getContractAddress(), price).send();
            counter = this.contractAddress.getLastCounter().send();
            this.contractAddress.registerTask(task, taskType).send();
            taskId = myAddress.toLowerCase(Locale.ROOT) + "_" + counter.add(BigInteger.ONE).toString();

            return waitForSolution(taskId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
