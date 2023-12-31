package pt.tecnico.distledger.adminclient.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.StatusRuntimeException;
import pt.tecnico.distledger.contract.DistLedgerCommonDefinitions.LedgerState;
import pt.tecnico.distledger.contract.admin.AdminDistLedger;
import pt.tecnico.distledger.contract.admin.AdminServiceGrpc;
import pt.tecnico.distledger.contract.distledgerserver.CrossServerDistLedger.PropagateStateRequest;
import pt.tecnico.distledger.contract.distledgerserver.CrossServerDistLedger.PropagateStateResponse;
import pt.tecnico.distledger.contract.user.UserServiceGrpc;

public class AdminService {

    private  ManagedChannel channel;
    private  AdminServiceGrpc.AdminServiceBlockingStub stub;

    public AdminService() {
       // this.channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
       // this.stub = AdminServiceGrpc.newBlockingStub(channel);
    }

    // Operations supported are:
    // 1. Activate
    // 2. Deactivate
    // 3. Get Ledger State
    // 4. Gossip

    public AdminDistLedger.ActivateResponse activate(AdminDistLedger.ActivateRequest request, String server) {
        // in the first phase we ignore the server because there is only one
        connect(server);
        return this.stub.activate(request);
    }

    public AdminDistLedger.DeactivateResponse deactivate(AdminDistLedger.DeactivateRequest request, String server) {
        // in the first phase we ignore the server because there is only one
    
        connect(server);
        return this.stub.deactivate(request);
        
    }

    public AdminDistLedger.GetLedgerStateResponse getLedgerState(AdminDistLedger.GetLedgerStateRequest request, String server) {
        // in the first phase we ignore the server because there is only one
        connect(server);
        return this.stub.getLedgerState(request);
    }

    public AdminDistLedger.GossipResponse gossip(AdminDistLedger.GossipRequest request, String server) {
        connect(server);
        return this.stub.gossip(request);
        
    }
    
    public void connect(String server){
        if(server.equals("A")){
           int port = 2001;                // port de servidor A
           String host = "localhost" ;     //host de servidor A 
           String target = host + ":" + port;
          
           this.channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
           this.stub = AdminServiceGrpc.newBlockingStub(channel);
        }
       else{
           int port = 2002;                // port de servidor B
           String host = "localhost" ;     //host de servidor B
           String target = host + ":" + port;

           this.channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
           this.stub = AdminServiceGrpc.newBlockingStub(channel);
       }
   }

    public void shutdown() {
        channel.shutdown();
    }
}
