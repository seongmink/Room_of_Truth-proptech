package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	//"strconv"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)


type ContractRecord struct {
	Contract_Id string `json:"contract_id"`
	Address string `json:"address"`
	Exclusive string `json:"exclusive"`
	Floor string `json:"floor"`	
	Ho string `json:"ho"`
	Kind string `json:"kind"`
	Detail string `json:"detail"`
	Cost string `json:"cost"`
	Monthly string `json:"monthly"`
	License string `json:"license"`
	Image string `json:"image"`
	Contract_date string `json:"contract_date"`
	Around_Around_Id string `json:"around_around_id"`
	Created_At string `json:"created_at"`
	IS_Expired string `json:"is_expired"`

}



type SmartContract struct {
}

func (s *SmartContract) Init(APIstub shim.ChaincodeStubInterface) pb.Response {
	return shim.Success(nil)
}

func (s *SmartContract) Invoke(APIstub shim.ChaincodeStubInterface) pb.Response {
	function, args := APIstub.GetFunctionAndParameters()

	if function == "setContractRecord" {
		return s.setContractRecord(APIstub, args)
	}	else if function == "getContractRecord" {
		return s.getContractRecord(APIstub, args)
	}

	fmt.Println("Please check your function: " + function)
	return shim.Error("Unknown function")

}

func main () {
		err := shim.Start(new(SmartContract))
		if err != nil {
			fmt.Printf("Error starting Simple chaincode: %s", err)
		}
}


func (s *SmartContract) setContractRecord(APIstub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 15 {
		return shim.Error("Incorrect number of arguments. Expecting 4")
	}
	var contractRecord = ContractRecord{Contract_Id: args[0], Address: args[1], Exclusive: args[2], Floor: args[3], Ho: args[4], Kind: args[5], Detail: args[6], Cost: args[7], 
		Monthly: args[8], License: args[9], Image: args[10], Contract_date: args[11], Around_Around_Id: args[12], Created_At: args[13], IS_Expired: args[14]}
	contractAsJSONBytes, _ := json.Marshal(contractRecord)
	APIstub.PutState(contractRecord.Contract_Id, contractAsJSONBytes)

	return shim.Success(nil)
}

func (s *SmartContract) getContractRecord(stub shim.ChaincodeStubInterface, args []string) pb.Response {

	contractAsBytes, err := stub.GetState(args[0])
	if err != nil {
		fmt.Println(err.Error())
	}

	contractRecord := ContractRecord{}
	json.Unmarshal(contractAsBytes, &contractRecord)
	
	var buffer bytes.Buffer
	buffer.WriteString("[")
	bArrayMemberAlreadyWritten := false

	if bArrayMemberAlreadyWritten == true {
		buffer.WriteString(",")
	}
	buffer.WriteString("[\"Contract_Id\":")
	buffer.WriteString("\"")
	buffer.WriteString(contractRecord.Contract_Id)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Address\":")
	buffer.WriteString("\"")
	buffer.WriteString(contractRecord.Address)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Exclusive\":")
	buffer.WriteString("\"")
	buffer.WriteString(contractRecord.Exclusive)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Floor\":")
	buffer.WriteString("\"")
	buffer.WriteString(contractRecord.Floor)
	buffer.WriteString("\"")
	
	buffer.WriteString(", \"Ho\":")
	buffer.WriteString("\"")
	buffer.WriteString(contractRecord.Ho)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Kind\":")
	buffer.WriteString("\"")
	buffer.WriteString(contractRecord.Kind)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Detail\":")
	buffer.WriteString("\"")
	buffer.WriteString(contractRecord.Detail)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Cost\":")
	buffer.WriteString("\"")
	buffer.WriteString(contractRecord.Cost)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Monthly\":")
	buffer.WriteString("\"")
	buffer.WriteString(contractRecord.Monthly)
	buffer.WriteString("\"")

	buffer.WriteString(", \"License\":")
	buffer.WriteString("\"")
	buffer.WriteString(contractRecord.License)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Image\":")
	buffer.WriteString("\"")
	buffer.WriteString(contractRecord.Image)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Contract_date\":")
	buffer.WriteString("\"")
	buffer.WriteString(contractRecord.Contract_date)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Around_Around_Id\":")
	buffer.WriteString("\"")
	buffer.WriteString(contractRecord.Around_Around_Id)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Created_At\":")
	buffer.WriteString("\"")
	buffer.WriteString(contractRecord.Created_At)
	buffer.WriteString("\"")

	buffer.WriteString(", \"IS_Expired\":")
	buffer.WriteString("\"")
	buffer.WriteString(contractRecord.IS_Expired)
	buffer.WriteString("\"")
	
	buffer.WriteString("}")
	bArrayMemberAlreadyWritten = true
	buffer.WriteString("]")

	return shim.Success(buffer.Bytes())

}