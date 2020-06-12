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
    if (args.length != 14) {
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
      contract_id: args[0],
      around_around_id: args[1],
      exclusive: args[2],
      floor: args[3],
      ho: args[4],
      kind: args[5],
      detail: args[6],
      cost: args[7],
      monthly: args[8],
      license: args[9],
      image: args[10],
      contract_date: args[11],
      created_at: timestampString,
      is_expired: args[12],
    };

    if (contractInfo.expiredAt !== "N") {
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
      status_id: args[0],
      around_around_id: args[1],
      floor: args[2],
      ho: args[3],
      category: args[4],
      detail: args[5],
      cost: args[6],
      license: args[7],
      image: args[8],
      start_date: args[9],
      end_date: args[10],
      created_at: timestampString,
      is_expired: args[11],
    };

    if (statusInfo.expiredAt !== "N") {
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
