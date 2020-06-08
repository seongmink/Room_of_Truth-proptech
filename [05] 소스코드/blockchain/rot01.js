const shim = require("fabric-shim");

var Chaincode = class {
  /**
   * Chaincode Instantiation method.
   * @param {Object} stub
   * @return {SuccessResponse} shim.success() returns a standard response object with status code 200 and an optional payload.
   */
  async Init(stub) {
    console.info("Instantiated completed");
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
      console.log("Method name [" + ret.fcn + "] is not defined");
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
  async registerContract(stub, args) {
    /** Inappropriate argument exception */
    if (args.length != 17) {
      throw new Error(
        "Incorrect number of arguments. Expecting 17, but received " +
          args.length
      );
    }

    /** !!! Generate composite key !!! */
    let compositeKey = stub.createCompositeKey("Asset.", [args[0]]);

    /** Duplicated asset checking */

    let dupCheck = await stub.getState(compositeKey);

    var isExist = function (value) {
      if (
        value == "" ||
        value == null ||
        value == undefined ||
        (value != null &&
          typeof value == "object" &&
          !Object.keys(value).length)
      ) {
        console.log("return true");
        return true;
      } else {
        console.log("return false");
        return false;
      }
    };

    if (isExist(dupCheck) != true) {
      throw new Error("AssetID " + compositeKey + "is already registered.");
      return false;
    }

    /** Get transaction timestampe using 'stub' */
    let txTimestamp = stub.getTxTimestamp();
    /** Timestamp formatting 'YYYY-MM-DD HH:MM:SS' */

    let tsSec = txTimestamp.seconds;
    let tsSecValue = tsSec.low + 540 * 60;
    let dataTimeObj = new Date(tsSecValue * 1000);

    // let timestampString;
    // timestampString = dataTimeObj.getFullYear() + '-' + ('0' + (dataTimeObj.getMonth() + 1)).slice(-2) + '-' +
    //     ('0' + dataTimeObj.getDate()).slice(-2) + ' ' + (dataTimeObj.getHours() + 9) + ':' + ('0' + dataTimeObj.getMinutes()).slice(-2) + ':' + dataTimeObj.getSeconds();
    var timestampString = dataTimeObj
      .toISOString()
      .replace(/T|Z|\.\d{3}/g, " ")
      .trim()
      .substring(0, 10);

    /** Consist asset information structure */
    var contractInfo = {
      contract_id: args[0], // PK
      address: args[1], // 주소
      sd: args[2], // 시도
      sgg: args[3], // 시군구
      emd: args[4], // 읍면동
      longitude: args[5], // 경도
      latitude: args[6], // 위도
      exclusive: args[7], // 전용면적
      floor: args[8], // 층
      ho: args[9], // 호
      kind: args[10], // 매물 유형
      detail: args[11], // 거래 내용
      cost: args[12], // 비용
      monthly: args[13], // 월세
      license: args[14], // 공인중개사
      image: args[15], // 이미지
      contract_date: args[16], // 계약 일시
    };

    if (contractInfo.expiredAt !== "0") {
      contractInfo.expiredAt = timestampString;
    }

    /** Put the asset information */
    await stub.putState(
      compositeKey,
      Buffer.from(JSON.stringify(contractInfo))
    );
    console.info("contractInfo is registered");

    console.info("contractInfo");
  }

  async registerStatus(stub, args) {
    if (args.length != 17) {
      throw new Error("Incorrect number of arguments." + args.length);
    }

    let compositeKey = stub.createCompositeKey("Asset.", [args[0]]);

    let dupCheck = await stub.getState(compositeKey);

    var isExist = function (value) {
      if (
        value == "" ||
        value == null ||
        value == undefined ||
        (value != null &&
          typeof value == "object" &&
          !Object.keys(value).length)
      ) {
        console.log("return true");
        return true;
      } else {
        console.log("return false");
        return false;
      }
    };

    if (isExist(dupCheck) != true) {
      throw new Error("AssetID " + compositeKey + "is already registered.");
      return false;
    }

    let txTimestamp = stub.getTxTimestamp();

    let tsSec = txTimestamp.seconds;
    let tsSecValue = tsSec.low + 540 * 60;
    let dataTimeObj = new Date(tsSecValue * 1000);

    var timestampString = dataTimeObj
      .toISOString()
      .replace(/T|Z|\.\d{3}/g, " ")
      .trim()
      .substring(0, 10);

    var statusInfo = {
      status_id: args[0], // 유지보수 이력 num
      address: args[1], // 주소
      sd: args[2], // 시도
      sgg: args[3], // 시군구
      emd: args[4], // 읍면동
      longitude: args[5], // 경도
      latitude: args[6], // 위도
      floor: args[7], // 층
      ho: args[8], //	호
      category: args[9], // 유지보수 내용
      detail: args[10], // 상세 내용
      cost: args[11], // 비용
      license: args[12], // 공인중개사
      image: args[13], // 이미지
      exclusive: args[14], // 전용 면적
      start_date: args[15],
      end_date: args[16],
    };

    if (statusInfo.expiredAt !== "0") {
      statusInfo.expiredAt = timestampString;
    }

    await stub.putState(compositeKey, Buffer.from(JSON.stringify(statusInfo)));
  }

  /* @param {Object} stub
   * @param {string[]} args args[0]: assetID
   * @return {string} The asset information of assetID
   */
  async queryContract(stub, args) {
    /** !!! Generate composite key !!! */
    let searchKey = stub.createCompositeKey("Asset.", [args[0]]);

    /** Get state */
    let asset = await stub.getState(searchKey);
    console.info(asset);
    /** Return asset state */
    return asset;
  }

  async queryStatus(stub, args) {
    let searchKey = stub.createCompositeKey("Asset.", [args[0]]);

    let asset = await stub.getState(searchKey);
    console.info(asset);
    return asset;
  }
};

/** Start chaincode */
shim.start(new Chaincode());
