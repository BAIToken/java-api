import com.fasterxml.jackson.databind.JsonSerializer;
import connectors.*;
import io.github.cdimascio.dotenv.Dotenv;
import connectors.EVMConnection;
import connectors.WavesConnection;

public class BAIAPI {

    private String type;
    private BlockChainConnection blockChainConnection;

    public BAIAPI() {
        Dotenv dotenv = Dotenv.load();
        this.type = dotenv.get("type");

        if (type.equals("Waves")) {
            this.blockChainConnection = new WavesConnection();
        } else if (type.equals("evm")) {
            this.blockChainConnection = new EVMConnection();
        }
    }

    public String solveTask(String task, String tasktype) {
        return this.blockChainConnection.solveTask(task, tasktype);
    }

}
