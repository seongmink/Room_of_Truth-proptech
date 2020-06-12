package com.roomoftruth.rot.fabric;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.sdk.BlockEvent.TransactionEvent;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FabricCCService implements IFabricCCService {
    private static final Logger logger = LoggerFactory.getLogger(FabricCCService.class);

    /**
     * 패브릭 네트워크를 이용하기 위한 정보
     */
    @Value("${fabric.ca-server.url}")
    private String CA_SERVER_URL;

    @Value("${fabric.ca-server.admin.name}")
    private String CA_SERVER_ADMIN_NAME;

    @Value("${fabric.ca-server.pem.file}")
    private String CA_SERVER_PEM_FILE;

    @Value("${fabric.org.name}")
    private String ORG_NAME;

    @Value("${fabric.org.msp.name}")
    private String ORG_MSP_NAME;

    @Value("${fabric.org.admin.name}")
    private String ORG_ADMIN_NAME;

    @Value("${fabric.peer.name}")
    private String PEER_NAME;

    @Value("${fabric.peer.url}")
    private String PEER_URL;

    @Value("${fabric.peer.pem.file}")
    private String PEER_PEM_FILE;

    @Value("${fabric.orderer.name}")
    private String ORDERER_NAME;

    @Value("${fabric.orderer.url}")
    private String ORDERER_URL;

    @Value("${fabric.orderer.pem.file}")
    private String ORDERER_PEM_FILE;

    @Value("${fabric.org.user.name}")
    private String USER_NAME;

    @Value("${fabric.org.user.secret}")
    private String USER_SECRET;

    @Value("${fabric.channel.name}")
    private String CHANNEL_NAME;

    private Channel channel;
    private HFClient hfClient;

    /**
     * 채널 접근 체인코드를 이용하기 위하여 구축해놓은 패브릭 네트워크의 채널을 가져오는 기능을 구현한다.
     */

    public boolean loadChannel() {
        HFCAClient caClient = null;
        CryptoSuite cryptoSuite = null;

        try {
            cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
            caClient = HFCAClient.createNewInstance(CA_SERVER_URL, FabricUtil.getPropertiesWith(CA_SERVER_PEM_FILE));
            caClient.setCryptoSuite(cryptoSuite);
            Enrollment adminEnroll = caClient.enroll("admin", "adminpw");
            FabricUser adminUser = new FabricUser(ORG_ADMIN_NAME, ORG_NAME, cryptoSuite);
            adminUser.setEnrollment(adminEnroll);
            adminUser.setMspId(ORG_MSP_NAME);

            hfClient = HFClient.createNewInstance();
            hfClient.setCryptoSuite(cryptoSuite);
            hfClient.setUserContext(adminUser);

            Orderer orderer = hfClient.newOrderer(ORDERER_NAME, ORDERER_URL,
                    FabricUtil.getPropertiesWith(ORDERER_PEM_FILE));
            Peer peer = hfClient.newPeer(PEER_NAME, PEER_URL, FabricUtil.getPropertiesWith(PEER_PEM_FILE));
            channel = hfClient.newChannel(CHANNEL_NAME);
            channel.addOrderer(orderer);
            channel.addPeer(peer);
            channel.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(" channel Load Success ! ");
        return true;
    }

    /**
     * @param num
     * @return findContractByNum
     */
    @Override
    public ContractRecord queryContract(String num) {
        ChaincodeID id = ChaincodeID.newBuilder().setName("rot02").build();
        QueryByChaincodeRequest qpr = hfClient.newQueryProposalRequest();
        qpr.setChaincodeID(id);
        qpr.setFcn("queryContract");
        qpr.setArgs(new String[]{num});
        Collection<ProposalResponse> res;
        try {
            res = channel.queryByChaincode(qpr);
            List<ProposalResponse> invalid = res.stream().filter(r -> r.isInvalid()).collect(Collectors.toList());
            if (!invalid.isEmpty()) {
                invalid.forEach(response -> {
                    logger.info(response.getMessage());
                });
            }
            ContractRecord contractRecord = null;
            for (ProposalResponse response : res) {
                logger.info(new String(response.getChaincodeActionResponsePayload()));
                JsonReader parser = Json
                        .createReader(new ByteArrayInputStream(response.getChaincodeActionResponsePayload()));
                contractRecord = getFabricContractRecord(parser.readObject());
            }
            return contractRecord;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param num
     * @return findStatusByNum
     */
    @Override
    public StatusRecord queryStatus(String num) {
        ChaincodeID id = ChaincodeID.newBuilder().setName("rot02").build();
        QueryByChaincodeRequest qpr = hfClient.newQueryProposalRequest();
        qpr.setChaincodeID(id);
        qpr.setFcn("queryStatus");
        qpr.setArgs(new String[]{num});
        Collection<ProposalResponse> res;
        try {
            res = channel.queryByChaincode(qpr);
            List<ProposalResponse> invalid = res.stream().filter(r -> r.isInvalid()).collect(Collectors.toList());
            if (!invalid.isEmpty()) {
                invalid.forEach(response -> {
                    logger.info(response.getMessage());
                });
            }
            StatusRecord statusRecord = null;
            for (ProposalResponse response : res) {
                logger.info(new String(response.getChaincodeActionResponsePayload()));
                JsonReader parser = Json
                        .createReader(new ByteArrayInputStream(response.getChaincodeActionResponsePayload()));
                statusRecord = getFabricStatusRecord(parser.readObject());
            }
            return statusRecord;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param contractRecord
     * @return registerContract
     */
    @Override
    public boolean registerContract(ContractRecord contractRecord) {
        ChaincodeID id = ChaincodeID.newBuilder().setName("rot02").build();
        TransactionProposalRequest tpr = hfClient.newTransactionProposalRequest();

        tpr.setChaincodeID(id);
        tpr.setFcn("registerContract");
        String[] args = {
                contractRecord.getContract_id(), contractRecord.getAround_around_id(), contractRecord.getExclusive(),
                contractRecord.getFloor(), contractRecord.getHo(), contractRecord.getKind(),
                contractRecord.getDetail(), contractRecord.getCost(), contractRecord.getMonthly(),
                contractRecord.getLicense(), contractRecord.getImage(), contractRecord.getContract_date(),
                contractRecord.getCreated_at(), contractRecord.getIs_expired()
        };
        tpr.setArgs(args);
        Collection<ProposalResponse> res;
        try {

            res = channel.sendTransactionProposal(tpr);

            List<ProposalResponse> invalid = res.stream().filter(r -> r.isInvalid()).collect(Collectors.toList());
            if (!invalid.isEmpty()) {
                invalid.forEach(response -> {
                    logger.info(response.getMessage());
                });
            }

            CompletableFuture<TransactionEvent> cf = channel.sendTransaction(res);

            TransactionEvent block = cf.get();

            for (ProposalResponse response : res) {
                logger.info(new String(response.getChaincodeActionResponsePayload()));
            }

            if (res.size() >= 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * @param statusRecord
     * @return registerStatus
     */
    @Override
    public boolean registerStatus(StatusRecord statusRecord) {
        ChaincodeID id = ChaincodeID.newBuilder().setName("rot02").build();
        TransactionProposalRequest tpr = hfClient.newTransactionProposalRequest();

        tpr.setChaincodeID(id);
        tpr.setFcn("registerStatus");
        String[] args = {
                statusRecord.getStatus_id(), statusRecord.getAround_around_id(),
                statusRecord.getFloor(), statusRecord.getHo(), statusRecord.getCategory(),
                statusRecord.getDetail(), statusRecord.getCost(), statusRecord.getLicense(),
                statusRecord.getImage(), statusRecord.getStart_date(), statusRecord.getEnd_date(),
                statusRecord.getCreated_at(), statusRecord.getIs_expired()
        };
        tpr.setArgs(args);
        Collection<ProposalResponse> res;
        try {

            res = channel.sendTransactionProposal(tpr);

            List<ProposalResponse> invalid = res.stream().filter(r -> r.isInvalid()).collect(Collectors.toList());
            if (!invalid.isEmpty()) {
                invalid.forEach(response -> {
                    logger.info(response.getMessage());
                });
            }

            CompletableFuture<TransactionEvent> cf = channel.sendTransaction(res);

            TransactionEvent block = cf.get();

            for (ProposalResponse response : res) {
                logger.info(new String(response.getChaincodeActionResponsePayload()));
            }

            if (res.size() >= 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private ContractRecord getFabricContractRecord(JsonObject object) {
        ContractRecord contractRecord = new ContractRecord(object.getString("contract_id"),
                object.getString("around_around_id"), object.getString("exclusive"),
                object.getString("floor"), object.getString("ho"), object.getString("kind"),
                object.getString("detail"), object.getString("cost"), object.getString("monthly"),
                object.getString("license"), object.getString("image"), object.getString("contract_date"),
                object.getString("created_at"), object.getString("is_expired")
        );

        return contractRecord;
    }

    private StatusRecord getFabricStatusRecord(JsonObject object) {
        StatusRecord statusRecord = new StatusRecord(object.getString("status_id"), object.getString("around_around_id"),
                object.getString("floor"), object.getString("ho"), object.getString("category"),
                object.getString("detail"), object.getString("cost"), object.getString("license"),
                object.getString("image"), object.getString("start_date"), object.getString("end_date"),
                object.getString("created_at"), object.getString("is_expired")
        );

        return statusRecord;
    }
}