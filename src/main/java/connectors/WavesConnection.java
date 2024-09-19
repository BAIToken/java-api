package connectors;
import com.wavesplatform.transactions.InvokeScriptTransaction;
import com.wavesplatform.transactions.account.Address;
import com.wavesplatform.transactions.account.PrivateKey;
import com.wavesplatform.transactions.common.Amount;
import com.wavesplatform.transactions.common.AssetId;
import com.wavesplatform.transactions.common.Id;
import com.wavesplatform.transactions.data.DataEntry;
import com.wavesplatform.transactions.invocation.Function;
import com.wavesplatform.transactions.invocation.StringArg;
import io.github.cdimascio.dotenv.Dotenv;
import com.wavesplatform.wavesj.Node;

public class WavesConnection implements BlockChainConnection{

    private int chainId;
    private String seed;
    private Address contractAddress;
    private Node node;
    private String network;
    private AssetId assetId;
    private Address address;

    public WavesConnection() {
        Dotenv dotenv = Dotenv.load();

        this.seed = dotenv.get("seed");
        this.contractAddress = new Address(dotenv.get("contractAddress"));
        this.chainId = 87;
        this.network = dotenv.get("network");
        this.assetId = new AssetId(dotenv.get("assetId"));

        try{
            this.node = new Node(dotenv.get("node"));
        } catch (Exception e){
            System.out.println(e);
        }

        if (this.network.equals("testnet")){
            this.chainId = 84;
        }
        this.address = PrivateKey.fromSeed(this.seed).address();
    }

    public long getPaymentForTaskType(String tasktype){
        DataEntry data;

        try{
            data = node.getData(contractAddress, "price_" + tasktype);

            return (long)data.valueAsObject();
        } catch(Exception e){
            e.printStackTrace();

            return -1;
        }
    }

    public String waitForSolution(String taskId){
        DataEntry data;

        try{
            data = node.getData(contractAddress, "/" + taskId);

            return (String)data.valueAsObject();
        } catch (Exception e){
            try {
                Thread.sleep(3000);

                return waitForSolution(taskId);
            } catch (InterruptedException iEx) {
                iEx.printStackTrace();

                return waitForSolution(taskId);
            }
        }
    }

    public String solveTask(String task,String tasktype){
        long necessaryPaymentAmount = getPaymentForTaskType(tasktype);
        InvokeScriptTransaction tx;
        Id txId;
        String taskId;

        try {
            tx = InvokeScriptTransaction.builder(contractAddress,
                    Function.as("registerTask",
                            StringArg.as(task),
                            StringArg.as(tasktype)
                    )).payments(Amount.of(necessaryPaymentAmount, assetId)
            ).getSignedWith(PrivateKey.fromSeed(this.seed));
            txId = node.broadcast(tx).id();
            node.waitForTransaction(txId);
            taskId = txId.toString() + "_" + tx.sender() + "_result_" + tasktype;

            return this.waitForSolution(taskId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
