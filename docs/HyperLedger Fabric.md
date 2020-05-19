# HyperLedger Fabric 

## 1. 개념 및 용어 설명

### HyperLedger Fabric?

- 하이퍼레저 프로젝트는 5개의 프레임인 Fabric, Iroha, Swathooth, Burrow, Indy와 5개의 툴인 Quilt, Composer, Explorer, Cello, Caliper로 구성된다.
- 하이퍼레저 패브릭은 엔터프라이즈 수준의 설계를 고려한 허가형 블록체인이다. 
- 1세대 블록체인 비트코인, 2세대 블록체인 이더리움은 서로 신뢰의 여부를 확인할 수 없는 많은 사람들이 모여서 공개적으로 합의하고 타임스탬프와 전자 서명을 통해 새로운 합의된 신뢰를 만들 수 있다.  -> 하이퍼레저에 참여하고있는 기관들은 이 합의 시스템을 "시간이 걸리는 이상적인 제안" 으로 간주하고 있다.
- 블록체인의 스마트 컨트랙트(Smart contract)이 기존에 존재하는 많은 자산의 흐름을 대신할 수 있지만, 현실세계의 많은 계약들은 기밀성을 요구하고 있다. 따라서 누구든지 원하는 사람은 모두 참여할 수 있는 공개된 네트워크가 아니라 가입이 통제되고 회원 관리가 가능한 권한형/허가형 블록체인(Permissioned BlockChain)이 필요하다는 것이 하이퍼레저 프로젝트에 참여하고 있는 기관들의 논리이다.

### 구조 및 용어 설명

#### [Architecture]

![architectures](C:\Users\multicampus\Desktop\blockchain\img\architectures.png)

##### Membership Service

**Registration/등록, Identity Management/계정관리,  Auditability/감사가능성

- 권한부여, 인증과 관련된 모듈, 패브릭 네트워크에서 신뢰의 근원이 되는 모듈이다. 
- 네트워크 참여자들의 신원 인증 시 사용되며, 공개키 기반 구조(PKI, Public Key Infrastructure)를 활용하여 인증 키, 인증서의 발급과 배포 및 관리를 수행한다.

##### Blockchain Service

**consensus Manager/합의 관리자, Distributed Ledger/분산된 원장, P2P Protocol, Ledger Storage/원장 저장소 

- 블록체인 서비스는 HTTP/2 표준을 기반으로 P2P프로토콜을 통해 분산 원장을(Distributed Ledger)를 관리한다. 데이터 구조는 해시 알고리즘을 통해 World State를 복제하는 등 관리 하는데 가장 효율적으로 관리할 수 있도록 최적화되어 있다.

  *world state: 트랜잭션에 의해서 체인코드가 호출될 때 상태 및 데이터 저장을 위한 Key-value 데이터베이스.

  *transaction: 데이터를 장부에 기록하거나 함수를 호출할 때 블록체인에 전송하는 요청 또는 거래이다. 패브릭에서의 트랜잭션은 체인코드에 의해 통신 채널 별로 구현 된다.

##### Chaincode Service

**Secure Container: 스마트 계약 실행을 위해 Docker 컨테이너에 체인코드가 배치된다. 현재 GoLang(체인코드 개발언어)은 주 스마트 컨트랙트 언어로 지원되고 있지만, 다른 주류 언어는 요청 시 추가 및 활성화할 수 있다. 

**Secure Registry: 이것은 스마트 컨트랙트가 포함된 모든 이미지의 기록을 제공한다.

- 체인코드 서비스는 Validating(검증) 노드에서 안전하고 가벼운 방법으로 체인코드가 실행되도록 보장한다.

#### [용어]

##### 명세서 기반

- HyperLedger Fabric CA(Certificate Authority): 멤버쉽 서비스를 구축하는 용도이다. 기관, 관리자, 사용자들의 인증서를 발급할 수 있다.
- Transaction: 데이터를 장부에 기록하거나 함수를 호출할 때 블록체인에 전송하는 요청 또는 거래이다. 패브릭에서의 트랜잭션은 체인코드에 의해 통신 채널 별로 구현된다.
- Chaincode: 체인코드는 패브릭에서 제공하는 스마트 컨트랙트로 Go, Java, Node.js 등의 언어를 지원한다. 체인코드는 패브릭 네트워크의 전역상태(World State)를 관리할 수 있는 트랜잭션을 발생시키며, 보안이 적용된 도커 컨테이너를 실행하여 동작한다. 체인코드는 패브릭이 동작하는 근원이라고 할 수 있으며, 서비스에 필요한 비즈니스 로직을 함수 별로 구현하여 상황에 적합한 트랜잭션을 발생시킬 수 있다.
- Consensus: 블록체인에서 가장 중요한 것 중 하나인 합의를 담당하는 요소이다. 하이퍼레저는 여러가지 합의 과정을 도입하여 사용할 수 있는 확장성을 가지고 있으며, 1.4버전까지는 Kafka기반, Raft 두 가지를 선택할 수 있다. 하이퍼레저에서는 정렬 서비스(Ordering Service)를 이용하여 합의를 수행한다. 정렬 서비스는 Orderer라는 역할의 구성원이 제공해 주며 수신한 트랜잭션을 블록으로 묶어 일괄적으로 처리한다.
- Ledger: 패브릭에서는 데이터 저장 및 관리를 위해 데이터베이스를 운용하고 있다. 현재는 기본 DB인 Level DB와 조금 더 복잡한 로직의 쿼리를 지원하는 Couch DB 두 가지를 선택하여 사용할 수 있다. Chaincode로 인해 발생하는 모든 거래를 불변하게 기록하게 된다.
- Peer: 체인코드 및 원장의 관리를 담당하는 참여자이다. Orderer로 부터 정렬된 트랜잭션을 수힌하면 이를 검증하고 최종적으로 블록체인에 저장하기 위해 검증을 수행하고 제출한다. Peer는 다시 Committing Peer와 Endorsing Peer로 구분된다. Committing Peer는 원장과 상태를 유지하고, Endorsing Peer는 트랜잭션 제안을 승인 또는 거절할 수 있는 역할을 수행한다.

##### IBM 공식 문서 기반

- Transactor: 트랜잭션(거래)를 일으키는 엔티티를 말한다. 대표적으로 클라이언트 애플리케이션이다.
- Transaction: 트랜잭션은 블록체인 네트워크에 대해사 비즈니스 로직을 수행하기 위한 요청이다. 트랜잭션의 유형은 deploy, invoke, query 이며, 체인코드를 통해서 사전 정의된 인터페이스에 대한 함수를 구현한다.
- World State: 트랜잭션에 의해서 체인코드가 호출될 때 상태 및 데이터 저장을 위한 Key-value 데이터베이스다.
- Chaincode: 다양한 트랜잭션의 유형을 구현한 블록체인에 임베드되는 로직이다. 개발자에 의해 체인코드가 작성되고 블록체인 네트워크로 디플로이 한다. 최종 사용하는 블록체인 네트워크를 구성하는 피어 또는 노드와 인터페이스 되어 있는 클라이언트 애플리케이션을 통해서 체인코드를 실행시킨다. 체인코드는 트랜잭션을 일으키고 유효성이 확인되면 공유원장에 추가하고 World State를 수정한다.
- Validating Peer(VP): 블록체인 네트워크에서 원장을 관리 유지하기 위해서 트랜잭션의 유효성을 검증하는 합의 프로토콜을 실행하는 노드이다. 검증된 트랜잭션은 원장에 블록단위로 추가 된다. 트랜잭션이 합의에 실패하면 블록에서 제거되므로 장부에 기록되지 않는다. Validating Peer는 체인코드를 deploy, invoke, query할 권한을 가진다.
- Non-Validating Peer(NVP): Transactor가 Validating Peer에 접속할 수 있도록 프록시 역할을 하는 노드이다. Non-Validating Peer는 호출된 요청을 Validating Peer에 접속할 수 있도록 프록시 역할을 하는 노드이다. Non-Validating Peer는 호출된 요청을 Validating Peer로 전달하며, 이벤트 스트림, REST 서비스를 담당하는 노드이다.
- Consensus: 블록체인 네트워크의 트랜잭션 순서를 유지하는 프로토콜, Validating 노드들은 합의 프로토콜을 구현하여 트랜잭션을 승인하기 위해서 함께 동작한다.
- Permissioned Network: 각 노드는 블록체인 네트워크에서 접근 권한을 관리해야 하는 노드이며, 각 노드는 권한이 있는 사용자만 접근할 수 있다.



### 그래서 Peer가 뭐야?

#### [peer]

![Peer](C:\Users\multicampus\Desktop\blockchain\img\Peer.png)

- 블록체인 네트워크는 Peer들의 집합으로 이루어져 있다.
- Peer는 원장 상태(Ledger State)와 체인코드(Chaincode)를 관리하는 네트워크 노드이다.
- Peer들은 Ledgers와 Chaincode(Smart Contracts)를 호스팅하는 블록체인 네트워크의 기본적인 요소이다. 
- Ledger는 Chaincode로 인해 발생하는 모든 거래를 불변하게 기록하게 된다.
- Chaincode와 Ledger는 각각 공유 프로세스와 정보를 네트워크에 캡슐화하는데 사용된다.

#### [Block Network]

![blockchain_network](C:\Users\multicampus\Desktop\blockchain\img\blockchain_network.png)

- 블록체인 네트워크는 Peer로 구성되며 각 Peer는 Ledger 사본과 Chaincode 사본을 보유할 수 있다.
- 네트워크 N은 Peer P1, P2, P3로 구성되어 있으며 각 Peer는 분산 Ledger L1의 자체 인스턴스를 유지관리한다.
- P1, P2, P3는 동일한 Chaincode S1을 사용하여 L1 사본에 엑세스한다.

#### [Multiple Ledgers]

![blockchain_multipleledger](C:\Users\multicampus\Desktop\blockchain\img\blockchain_multipleledger.png)

- Peer는 둘 이상의 Ledger를 호스팅할 수 있으므로 유연한 시스템 설계가 가능하다.
- 단순한 구성은 Peer가 단일 Ledger를 관리하는 것이지만 필요할 때는 둘 이상의 Ledger를 호스팅하는 것이 적합할 수 있다.
- Peer는 하나 이상의 Ledger를 호스팅하고 각 Ledger는 0개 이상의 Chaincode가 적용된다.
- Peer P1이 Ledger L1, L2를 호스팅하고 있으며, Ledger L1은 Chaincode S1을 사용하여 엑세스 된다.
- L2는 Chaincode S1, S2를 사용하여 엑세스 할 수 있다.

#### [Multiple Chaincode]

- Peer가 가지고 있는 Ledger 수와 해당 Ledger에 접근할 수 있는 Chaincode의 수는 고정되어 있지 않다.
- Peer는 많은 Chaincode와 Ledger를 사용할 수 있다.

#### [Application and Peers]

![application](C:\Users\multicampus\Desktop\blockchain\img\application.png)

- Peer는 Orderer와 함께 모든 Ledger가 최신 상태로 유지되도록 한다.

- Application A는 Peer P1에 연결하고 Chaincode S1을 호출하여 Ledger L1을 query 또는 upgrade 한다.

  -> 체인코드가 이미 install 되어있다고 가정함.

- Peer P1은 Chaincode S1을 호출하여 query의 결과 또는 Proposal(제안/신청)된  Ledger 갱신을 포함하는 Proposal Response를 생성한다.

- Application A는 Proposal Response를 수신하고 query의 경우 프로세스가 완료된다.

- update의 경우 Application A는 모든 Response에 대해 Transaction을 생성하여 Order(정렬?)을 위해 Orderer에게 전송한다.

- Orderer O1은 수집한 Transaction을 모든 Peer에 배포하며, Peer P1은 Ledger L1에 적용하기 전에 Transaction의 유효성을 검사한다.

- Ledger L1이 update가 되면 Peer P1은 Application A가 수신할 수 있는 이벤트를 생성하고 완료된다.

#### [Transaction Flow]

![transaction](C:\Users\multicampus\Desktop\blockchain\img\transaction.png)

- Peer는 query요청에 만족 시킬 수 있는 모든 정보가 Peer's Ledger 사본에 있으므로 query 결과를 즉시 Application에 반환할 수 있다. 또한 query를 위해 다른 Peer들과 통신할 필요가 없다.

- 그러나 Application은 하나 이상의 Peer를 연결하여 query를 발행할 수 있다. 예를들어 여러 Peer간에 결과를 확증하거나 정보가 오래되었다는 의심이 들 경우 다른 Peer들에서 최신 결과를 검색할 수 있다.

- Update Transaction은 Query Transaction와 동일한 방식으로 시작되지만 추가적인 단계가 있다.

  -> Ledger Update Application은 Ledger Query Application과 달리 Chaincode를 호출하기 위해 Peer에 연결되지만, 개별 Peer에 Update를 수행할 수 없다. 다른 Peer들이 먼저 변경사항에 대해 동의를 해야하기 때문이다.

  -> 동의하는 절차를 Consensus(합의)라고 부른다. 따라서 Peer는 Proposal Update(다른 Peer들에게 사전 동의를 받아 적용될 업데이트)를 Application에 반환한다.

  -> Application은 Proposal Update와 일치하는 적절한 집합을 Peer 전체 네트워크에 보내어 각 Ledger에 대해 Commit을 위해 Transaction을 요구합니다.

  -> 이것은 Orderer를 사용하여 Transaction을 블록으로 패키징하고 이를 Peer의 전체 네트워크에 분배하여 각 Peer의 Ledger사본에 적용되기 전에 확인 할 수 있다.

  -> 이 전체 Order 처리가 완료되기까지 몇시간이 걸리므로 Application은 비동적으로 응답을 전해받는다.

#### [Peers and Cahnnels]

![channel](C:\Users\multicampus\Desktop\blockchain\img\channel.png)

- Channel: 블록체인 네트워크 내의 구성요소로 개인적으로 통신하고 거래할 수 있는 매커니즘.
- 구성요소는 일반적으로 Peer, Orderer, Application이며, 채널에 가입함으로써 공동 작업을 통해 해당 채널에 연결된 Ledger의 동일한 복사본을 공동으로 공유하고 관리하는데 동의 되어진다.

#### [Peers and Organization]

![organization](C:\Users\multicampus\Desktop\blockchain\img\organization.png)

- 블록체인 네트워크는 조직의 집합에 의해 관리된다. Peer들은 이러한 종류의 분산 네트워크가 어떻게 구성되어있는지에 대한 핵심 역할을 한다.
- 여러 조직과 블록체인 네트워크에 있는 Peer들, 블록체인 네트워크는 소유된 Peer들과 조직들의 기여로 구성된다. 위 그림은 네트워크 형성을 위해 4개의 조직에 기여가 되는 8개의 Peer를 볼 수 있다.
- Channel C는 블록체인 네트워크 N에서 Peer P1, P3, P5, P7, P8을 연결한다.
- Channel C에 가입하지 않았지만 일반적으로 모든 Peer는 하나 이상의 Channel에 가입을 한다.
- 특정조직에서 개발한 Application은 다른 조직의 Application과 마찬가지로 자신의 조직 Peer와 연결되게 된다.
- 네트워크는 리소스를 제공하는 여러 조직에 의해 형성되고 관리 되어진다. Peer는 이 항목에서 논의 중인 리소스 이지만 조직에서 제공해주는 리소스는 단순한 것 이상이다. 네트워크는 말 그대로 조직이 개별 리소스를 집단 네트워크에 기여
