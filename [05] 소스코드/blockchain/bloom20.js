/** 
 * FabricRecord
 * private String num; // AI-DB 에서 받아오겠습니다. PK, Index
 * private String address;
 * private String dong;
 * private String ho;
 * private String latitude; //위도
 * private String longitude; // 경도
 * private String supply; // 공급면적
 * private String exclusive; // 전용면적
 * private String details; // 계약내용
 * private String cost; // 계약비용
 * private String startDate;  //시작 날짜
 * private String endDate; //종료 날짜
 * private String name; //임대인
 * private String license; // 공인 중개사 자격번호
 * private String image; // 건물 사진파일 이름
 * private String createdAt; // 생성날짜 2020-04-22 형식
 * private String expiredAt; // 파기 날짜 defualt "0", 파기시 1을 넣으면 자동으로 타임스탬프 찍을게요
**/

const shim = require('fabric-shim');


var Chaincode = class {
    /**
     * Chaincode Instantiation method.
     * @param {Object} stub 
     * @return {SuccessResponse} shim.success() returns a standard response object with status code 200 and an optional payload.
     */
    async Init(stub) {
        console.info('Instantiated completed');
        return shim.success();
    }

    /**
     * Chaincode Invoking method.
     * @param {Object} stub The chaincode object
     * @return {SuccessResponse} status code and optional payload
     */
    async Invoke(stub) {

        /** Get method name and parameter from the chaincode arguments */
        let ret = stub.getFunctionAndParameters();
        let method = this[ret.fcn];

        /** Undefined method calling exception(but do not throw error) */
        if (!method) {
            console.log('Method name [' + ret.fcn + '] is not defined');
            return shim.success();
        }

        /** Method call */
        try {
            let payload = await method(stub, ret.params);
            return shim.success(payload);

        } catch (err) {
            console.log(err);
            return shim.error(err);
        }
    }


    /**
     * Add a record of a new secondhand item registration 
     * Mandatory requirement: Create a composite key for state recording.
     * Composite key structure: Asset.num 
     * @param {Object} stub 
     * @param {string[]} args args[0]: num, args[1~15]: recorder
     */
    async registerBuildingInfo(stub, args) {

        /** Inappropriate argument exception */
        if (args.length != 17) {
            throw new Error('Incorrect number of arguments. Expecting 17, but received ' + args.length);
        }

        /** !!! Generate composite key !!! */
        let compositeKey = stub.createCompositeKey("Asset.", [args[0]]);

        /** Duplicated asset checking */
        
        let dupCheck = await stub.getState(compositeKey);

        var isExist = function(value) {
            if (value == "" || value == null || value == undefined ||
                (value != null && typeof value == "object" && !Object.keys(value).length)) {
		console.log("return true");
		return true;
            } else {
		console.log("return false");
                return false;
            }
        };

        if (isExist(dupCheck) != true) {
            throw new Error('AssetID ' + compositeKey + 'is already registered.');
		return false;
        }
	
	
        /** Get transaction timestampe using 'stub' */
        let txTimestamp = stub.getTxTimestamp();
        /** Timestamp formatting 'YYYY-MM-DD HH:MM:SS' */

        let tsSec = txTimestamp.seconds;
        let tsSecValue = tsSec.low + (540 * 60);
        let dataTimeObj = new Date(tsSecValue * 1000);

        // let timestampString;
        // timestampString = dataTimeObj.getFullYear() + '-' + ('0' + (dataTimeObj.getMonth() + 1)).slice(-2) + '-' +
        //     ('0' + dataTimeObj.getDate()).slice(-2) + ' ' + (dataTimeObj.getHours() + 9) + ':' + ('0' + dataTimeObj.getMinutes()).slice(-2) + ':' + dataTimeObj.getSeconds();
        var timestampString = dataTimeObj.toISOString().replace(/T|Z|\.\d{3}/g, ' ').trim().substring(0,10);


        /** Consist asset information structure */
        var buildingInfo = {
	num: args[0],
 	address: args[1],
        dong: args[2],
        ho: args[3],
 	latitude: args[4],
 	longitude: args[5],
 	supply: args[6],
 	exclusive: args[7],
 	details: args[8],
 	cost: args[9],
 	startDate: args[10],
 	endDate: args[11],
 	name: args[12],
 	license: args[13],
	image: args[14],
 	createdAt: timestampString,
 	expiredAt: args[16]
        };


	if(buildingInfo.expiredAt !=="0"){
	    buildingInfo.expiredAt = timestampString;
	}
	
        /** Put the asset information */
        await stub.putState(compositeKey, Buffer.from(JSON.stringify(buildingInfo)));
	console.info("buildingInfo is registered");
	
	console.info("buildingInfo");
    }

    async registerMaintenanceInfo(stub, args) {

	    if (args.length != 15) {
		throw new Error('Incorrect number of arguments.' + args.length);
	    }

	    let compositeKey = stub.createCompositeKey("Asset.", [args[0]]);

	    let dupCheck = await stub.getState(compositeKey);

	    var isExist = function(value) {
		if (value == "" || value == null || value == undefined || (value != null && typeof value == "object" && !Object.keys(value).length)) {
	            console.log("return true");
		    return true;
		} else {
		    console.log("return false");
		    return false;
		}
	    };

	    if (isExist(dupCheck) != true) {
		throw new Error('AssetID ' + compositeKey + 'is already registered.');
		return false;
	    }

	    let txTimestamp = stub.getTxTimestamp();

	    let tsSec = txTimestamp.seconds;
	    let tsSecValue = tsSec.low + (540 * 60);
	    let dataTimeObj = new Date(tsSecValue * 1000);

	    var timestampString = dataTimeObj.toISOString().replace(/T|Z|\.\d{3}/g, ' ').trim().substring(0,10);

	    var maintenanceInfo = {
	    num: args[0],
	    address: args[1],
	    dong: args[2],
	    ho: args[3],
	    latitude: args[4],
	    longitude: args[5],
	    category: args[6],
	    details: args[7],	// 상세 정보
	    cost: args[8],	//	발생 비용
	    license: args[9],
	    image: args[10],		// 등록할 사진
	    startDate: args[11],
	    endDate: args[12],
	    createdAt: timestampString,
	    expiredAt: args[14]
            };

	    if(maintenanceInfo.expiredAt !== "0") {
		maintenanceInfo.expiredAt = timestampString;
	    }

	    await stub.putState(compositeKey, Buffer.from(JSON.stringify(maintenanceInfo)));

	}



     /* @param {Object} stub 
     * @param {string[]} args args[0]: assetID
     * @return {string} The asset information of assetID
     */
    async query(stub, args) {

        /** !!! Generate composite key !!! */
        let searchKey = stub.createCompositeKey("Asset.", [args[0]]);

        /** Get state */
        let asset = await stub.getState(searchKey);
        console.info(asset);
        /** Return asset state */
        return asset;
    }


    async queryMaintenance(stub, args) {
        let searchKey = stub.createCompositeKey("Asset.", [args[0]]);

	let asset = await stub.getState(searchKey);
	console.info(asset);
	return asset;
    }




    /*
     * Get item's history of state
     * @param {Object} stub
     * @param {string[]} args args[0]: assetID
     * @return {string} The history of records
     */
    async getBuildingHistory(stub, args) {

     	/** Inappropriate argument exception */
	if (args.length != 1) {
	    throw new Error('Incorrect number of arguments. Expecting assetID as an argument');
        }
	/** !!! Generate composite key !!! */
        let searchKey = stub.createCompositeKey("Asset.", [args[0]]);
            //address
        /** Get the history of state */
        var historyIter = await stub.getHistoryForKey(searchKey);
        /** Copy the history to array and parse to string*/
        let results = [];
        let res = { done: false };
        while (!res.done) {
           res = await historyIter.next();
           try {
               if (res && res.value && res.value.value) {
	           let val = res.value.value.toString('utf8');
		       if (val.length > 0) {
			   results.push(JSON.parse(val));
                      	   console.info(JSON.parse(val));
                       }
               }
           } catch (err) {
             console.info(err);
           }

          if (res && res.done) {
              try {
                  historyIter.close();
              } catch (err) {
                  console.info(err);
              }
          }
       }
	/** Return the history as string */
       return Buffer.from(JSON.stringify(results));
    }

    async queryAllBuilding(stub, args) {
        const startKey = "1";
        const endKey = "101";
        let key1 = stub.createCompositeKey("Asset.",startKey);
        let key2 = stub.createCompositeKey("Asset.",endKey);
        var iterator =  await stub.getStateByRange(key1,key2);
        let results = [];
        let res = { done: false };
        while(!res.done){
	    res = await iterator.next();
	    if(res && res.value && res.value.value){
	        let val = res.value.value.toString('utf8');
	        if(val.length>0){
	            results.push(JSON.parse(val));
	        }
	    }
	    if(res && res.done){
                iterator.close();
            }
	}
	return Buffer.from(JSON.stringify(results));
    }
};

/** Start chaincode */
shim.start(new Chaincode());
