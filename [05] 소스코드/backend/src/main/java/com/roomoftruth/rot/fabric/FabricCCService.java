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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

	private FabricContractRecord getFabricContractRecord(JsonObject object) {
		FabricContractRecord fabricContractRecord = new FabricContractRecord(object.getString("contract_id"), object.getString("address"),
				object.getString("sd"), object.getString("sgg"), object.getString("emd"), object.getString("longitude"),
				object.getString("latitude"), object.getString("exclusive"), object.getString("floor"), object.getString("ho"),
				object.getString("kind"), object.getString("detail"), object.getString("cost"), object.getString("monthly"),
				object.getString("license"), object.getString("image"), object.getString("contract_date")
				);

		return fabricContractRecord;
	}

	private FabricStatusRecord getFabricStatusRecord(JsonObject object) {
		FabricStatusRecord fabricStatusRecord = new FabricStatusRecord(object.getString("status_id"), object.getString("address"),
				object.getString("sd"), object.getString("sgg"), object.getString("emd"), object.getString("longitude"),
				object.getString("latitude"), object.getString("floor"), object.getString("ho"), object.getString("category"),
				object.getString("detail"), object.getString("cost"), object.getString("license"), object.getString("image"),
				object.getString("exclusive"), object.getString("contract_date"), object.getString("end_date")
		);

		return fabricStatusRecord;
	}

	@Override
	public FabricContractRecord queryContract(String num) {
		ChaincodeID id = ChaincodeID.newBuilder().setName("rot01").build();
		QueryByChaincodeRequest qpr = hfClient.newQueryProposalRequest();
		qpr.setChaincodeID(id);
		qpr.setFcn("queryContract");
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
			FabricContractRecord fabricContractRecord = null;
			for (ProposalResponse response : res) {
				logger.info(new String(response.getChaincodeActionResponsePayload()));
				JsonReader parser = Json
						.createReader(new ByteArrayInputStream(response.getChaincodeActionResponsePayload()));
				fabricContractRecord = getFabricContractRecord(parser.readObject());
			}
			return fabricContractRecord;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public FabricStatusRecord queryStatus(String num) {
		ChaincodeID id = ChaincodeID.newBuilder().setName("rot01").build();
		QueryByChaincodeRequest qpr = hfClient.newQueryProposalRequest();
		qpr.setChaincodeID(id);
		qpr.setFcn("queryStatus");
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
			FabricStatusRecord fabricStatusRecord = null;
			for (ProposalResponse response : res) {
				logger.info(new String(response.getChaincodeActionResponsePayload()));
				JsonReader parser = Json
						.createReader(new ByteArrayInputStream(response.getChaincodeActionResponsePayload()));
				fabricStatusRecord = getFabricStatusRecord(parser.readObject());
			}
			return fabricStatusRecord;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean registerContract(FabricContractRecord fc) {
		ChaincodeID id = ChaincodeID.newBuilder().setName("rot01").build();
		TransactionProposalRequest tpr = hfClient.newTransactionProposalRequest();

		tpr.setChaincodeID(id);
		tpr.setFcn("registerContract");
		String[] args = {
				fc.getContract_id(), fc.getAddress(), fc.getSd(), fc.getSgg(), fc.getEmd(), fc.getLongitude(),
				fc.getLatitude(), fc.getExclusive(), fc.getFloor(), fc.getHo(), fc.getKind(), fc.getDetail(),
				fc.getCost(), fc.getMonthly(), fc.getLicense(), fc.getImage(), fc.getContract_date()
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


	@Override
	public boolean registerStatus(FabricStatusRecord fs) {
		logger.info("Request Regist Building Date :" + fs.toString());

		ChaincodeID id = ChaincodeID.newBuilder().setName("rot01").build();
		TransactionProposalRequest tpr = hfClient.newTransactionProposalRequest();

		tpr.setChaincodeID(id);
		tpr.setFcn("registerStatus");
		String[] args = {
				fs.getStatus_id(), fs.getAddress(), fs.getSd(), fs.getSgg(), fs.getEmd(), fs.getLongitude(),
				fs.getLatitude(), fs.getFloor(), fs.getHo(), fs.getCategory(), fs.getDetail(), fs.getCost(),
				fs.getLicense(), fs.getImage(), fs.getExclusive(), fs.getContract_date(), fs.getEnd_date()
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

}