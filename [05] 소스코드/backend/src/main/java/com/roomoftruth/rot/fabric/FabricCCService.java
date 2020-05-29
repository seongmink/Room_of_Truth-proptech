package com.roomoftruth.rot.fabric;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//import com.rot.service.IAddressService;
//import com.rot.service.impl.BuildingService;

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
		return true;
	}

	private FabricRecord getFabricRecord(JsonObject object) {
		FabricRecord fabircRecord = new FabricRecord(object.getString("num"), object.getString("address"),
				object.getString("dong"), object.getString("ho"), object.getString("latitude"),
				object.getString("longitude"), object.getString("supply"), object.getString("exclusive"),
				object.getString("details"), object.getString("cost"), object.getString("startDate"),
				object.getString("endDate"), object.getString("name"), object.getString("license"),
				object.getString("image"), object.getString("createdAt"), object.getString("expiredAt"));

		logger.info("num : " + object.getString("num") + ", address : " + object.getString("address") + ", dong : "
				+ object.getString("dong") + ", ho : " + object.getString("ho") + ", latitude : "
				+ object.getString("latitude") + ", longitude : " + object.getString("longitude") + ", supply : "
				+ object.getString("supply") + ", exclusive : " + object.getString("exclusive") + ", details : "
				+ object.getString("details") + ", cost : " + object.getString("cost") + ", startDate : "
				+ object.getString("startDate") + ", endDate : " + object.getString("endDate") + ", name : "
				+ object.getString("name") + ", license : " + object.getString("license") + ", image : "
				+ object.getString("image") + " ,createdAt : " + object.getString("createdAt") + ", expiredAt : "
				+ object.getString("expiredAt"));
		return fabircRecord;
	}

	@Override
	public FabricRecord query(String num) {
		ChaincodeID id = ChaincodeID.newBuilder().setName("bloom20").build();
		QueryByChaincodeRequest qpr = hfClient.newQueryProposalRequest();
		qpr.setChaincodeID(id);
		qpr.setFcn("query");
		qpr.setArgs(new String[] { num });
		Collection<ProposalResponse> res;
		try {
			res = channel.queryByChaincode(qpr);
			List<ProposalResponse> invalid = res.stream().filter(r -> r.isInvalid()).collect(Collectors.toList());
			if (!invalid.isEmpty()) {
				invalid.forEach(response -> {
					logger.info(response.getMessage());
				});
			}
			FabricRecord fabricrecord = null;
			for (ProposalResponse response : res) {
				logger.info(new String(response.getChaincodeActionResponsePayload()));
				JsonReader parser = Json
						.createReader(new ByteArrayInputStream(response.getChaincodeActionResponsePayload()));
				fabricrecord = getFabricRecord(parser.readObject());
			}
			return fabricrecord;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<FabricRecord> getBuildingHistory(String Serial) {
		ChaincodeID id = ChaincodeID.newBuilder().setName("bloom20").build();
		QueryByChaincodeRequest qpr = hfClient.newQueryProposalRequest();
		qpr.setChaincodeID(id);
		qpr.setFcn("getBuildingHistory");
		qpr.setArgs(new String[] { String.valueOf(Serial) });
		Collection<ProposalResponse> res;
		try {
			res = channel.queryByChaincode(qpr);
			List<ProposalResponse> invalid = res.stream().filter(r -> r.isInvalid()).collect(Collectors.toList());
			if (!invalid.isEmpty()) {
				invalid.forEach(response -> {
					logger.info(response.getMessage());
				});
			}
			List<FabricRecord> records = new ArrayList<>();
			for (ProposalResponse response : res) {
				logger.info(new String(response.getChaincodeActionResponsePayload()));
				JsonReader parser = Json
						.createReader(new ByteArrayInputStream(response.getChaincodeActionResponsePayload()));
				for (JsonValue record : parser.readArray()) {
					FabricRecord rec = getFabricRecord((JsonObject) record);
					records.add(rec);
				}
			}
			return records;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean registerBuildingInfo(FabricRecord fb) {
		logger.info("Request Regist Building Date :" + fb.toString());

		ChaincodeID id = ChaincodeID.newBuilder().setName("bloom20").build();
		TransactionProposalRequest tpr = hfClient.newTransactionProposalRequest();

		tpr.setChaincodeID(id);
		tpr.setFcn("registerBuildingInfo");
		String[] args = { fb.getNum(), fb.getAddress(), fb.getDong(), fb.getHo(), fb.getLatitude(), fb.getLongitude(),
				fb.getSupply(), fb.getExclusive(), fb.getDetails(), fb.getCost(), fb.getStartDate(), fb.getEndDate(),
				fb.getName(), fb.getLicense(), fb.getImage(), fb.getCreatedAt(), fb.getExpiredAt() };
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

}